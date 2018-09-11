package com.hhly.cms.sysmgr.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sysmgr.service.CMSUserService;
import com.hhly.cms.utils.DateUtil;
import com.hhly.cms.utils.MD5Util;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserLoginBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;

/**
 * Created by lgs on 2016/11/23.
 * CMS登录Controller
 */
@Controller
public class LoginController extends BaseController{

    public static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Autowired
    private CMSUserService cmsUserService;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "password",required = true) String password,
                        HttpServletRequest request)  {
    	
    	HttpSession session=request.getSession();
    	
        if(StringUtils.isBlank(userName)){
            return ResultBO.errMessage("20001","用户名不能为空");
        }

        if(StringUtils.isBlank(password)){
            return ResultBO.errMessage("20001","密码不能为空");
        }
        CMSUserVO vo = new CMSUserVO();
        vo.setUserName(userName);
        vo.setUserPassword(MD5Util.encrypt(password));
        LOGGER.info("参数为:"+vo);
        CMSUserLoginBO bo = null;
        try {
            bo = cmsUserService.login(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("返回结果为:"+bo);
        if(ObjectUtil.isNull(bo) || ObjectUtil.isNull(bo.getUserId())){
            return ResultBO.errMessage("20001","用户名或者密码错误");
        }
        session.setAttribute(WebConstant.USERID,bo.getUserId());//
        session.setAttribute(WebConstant.LOGINNAME,bo.getUserRealName());
        session.setAttribute(WebConstant.USERNAME,bo.getUserName());
        session.setAttribute(WebConstant.AUTERITY,bo.getUserMenus());
        // 用户昵称
        session.setAttribute(WebConstant.USER_CNAME,bo.getUserCname());
        vo.setUserId(bo.getUserId());
        LOGGER.info("更新最后登录时间参数:"+vo);
        
        String ip  =  request.getRemoteAddr();
        vo.setThisLoginIP(ip);
        
        int count=bo.getMonthLoginCount()!=null?bo.getMonthLoginCount():0;
        Date lastLoginTime=bo.getThisLoginTime();
        
        if(lastLoginTime!=null){
        	 Calendar c = Calendar.getInstance();
             c.setTime(lastLoginTime);
             int lastLoginYear = c.get(Calendar.YEAR);
             int lastLoginMonth = c.get(Calendar.MONTH) + 1;
             
             c.setTime(new Date());
             int thisLoginYear = c.get(Calendar.YEAR);
             int thisLoginMonth = c.get(Calendar.MONTH) + 1;
             
             if(thisLoginYear>lastLoginYear||((thisLoginYear==lastLoginYear)&&(thisLoginMonth>lastLoginMonth))){
            	 count=0;
             }
        }
        vo.setMonthLoginCount(count+1);
        
        try {
        	
            cmsUserService.updateUserLastLoginTime(vo);
            
            Map<String,Object> loginUserInfo=new HashMap<String,Object>();
            
            loginUserInfo.put("monthLoginCount", vo.getMonthLoginCount());
            loginUserInfo.put("thisLoginIP", vo.getThisLoginIP());
            loginUserInfo.put("thisLoginTime", DateUtil.convertDateToStr(new Date()));
            loginUserInfo.put("lastLoginIp",bo.getThisLoginIP());
            loginUserInfo.put("lastLoginTime",DateUtil.convertDateToStr(bo.getThisLoginTime()));
            
            request.getSession().setAttribute("loginUserInfo",loginUserInfo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("更新最后登录时间成功!!!!");
        return  ResultBO.errMessage("1005","登录成功");
    }

    @RequestMapping(value = "/index/menu",method = RequestMethod.GET)
    @ResponseBody
    public Object indexMenu(HttpSession session) {
        Object obj = session.getAttribute(WebConstant.USERID);
        if(ObjectUtil.isNull(obj)){
            return ResultBO.errMessage("20001","登录失败");
        }
        Integer userId = (Integer) obj;
        return  cmsUserService.findMenuByUserId(userId);
    }

    @RequestMapping(value = "/login/out")
    public String loginOut(HttpSession session){
        session.removeAttribute(WebConstant.USERID);
        session.removeAttribute(WebConstant.ISADMIN);
        session.removeAttribute(WebConstant.AUTERITY);
        session.removeAttribute(WebConstant.ROLEID);
        session.removeAttribute(WebConstant.USERNAME);
        session.removeAttribute(WebConstant.LOGINNAME);
        session.removeAttribute("loginUserInfo");//清除首页信息
        return "login";
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session){
        if(ObjectUtil.isNull(session) || ObjectUtil.isNull(session.getAttribute(WebConstant.USERID))) {
            return "login";
        }else{
            return "index";
        }
    }

    /**
     * 找回密码页面
     * @return
     */
    @RequestMapping(value = "/get_back_password",method = RequestMethod.GET)
    public String getBackPassword(){
        return "get_back_password";
    }

    /**
     * 找回密码页面
     * @return
     */
    @RequestMapping(value = "/get_back_password",method = RequestMethod.POST)
    @ResponseBody
    public Object getBackPassword(@RequestParam(value = "userName",required = true) String userName,
                                  @RequestParam(value = "userRealName",required = true) String userRealName,
                                  @RequestParam(value = "userEmail",required = true) String userEmail,
                                  @RequestParam(value = "userMobile",required = true) String userMobile){
        CMSUserVO vo = new CMSUserVO();
        vo.setUserName(userName);
        vo.setUserRealName(userRealName);
        vo.setUserEmail(userEmail);
        vo.setUserMobile(userMobile);
        Integer count =  cmsUserService.findCount(vo);

        return count;
    }
}
