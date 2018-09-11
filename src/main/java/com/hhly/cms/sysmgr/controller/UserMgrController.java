package com.hhly.cms.sysmgr.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.sysmgr.service.CMSRoleService;
import com.hhly.cms.sysmgr.service.CMSUserService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.cms.utils.MD5Util;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OperateEnum;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;

/**
 * Created by lgs on 2016/11/8.
 * 用户管理Controller
 */
@Controller
@RequestMapping(value = "/sysmgr/user")
public class UserMgrController extends BaseController{

    public static final Logger LOGGER = Logger.getLogger(UserMgrController.class);

    @Autowired
    private CMSUserService cmsUserService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private CMSRoleService cmsRoleService;

    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    public String index(){
        return "sysmgr/user";
    }

    @RequestMapping(value="/list",method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public PagingBO<?> list(CMSUserVO vo){
        PagingBO<?> bo = null;
        try {
            bo = cmsUserService.findUserData(vo);
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method::list:::::"+e.getMessage()+"::param::"+ JsonUtil.object2Json(vo));
        }
        return bo;
    }


    /**
     * 获取字典表数据
     * @param code
     * @return
     */
    @RequestMapping(value="/dic",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public List<DicDataDetailBO> list(@RequestParam(value = "code", required = true) String code){
        List<DicDataDetailBO>  result = null;
        StringVO vo = new StringVO();
        vo.setStr(code);
        try {
            result = dictionaryService.findDetail(vo);
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method::dic/list"+e.getMessage()+"::param::"+ JsonUtil.object2Json(vo));
        }
        return result;
    }

    /**
     * 获取角色
     * @return
     */
    @RequestMapping(value="/rolenames",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public List<CMSRoleBO> findRoles(){
        List<CMSRoleBO> bos = null;
        try {
            bos = cmsRoleService.findRoleName();
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method:findRoles:rolenames"+e.getMessage());
        }
        return bos;
    }

    /**
     * 获取角色
     * @return
     */
    @RequestMapping(value="/menus",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object findUserMenuTree(@RequestParam(value = "roleId",required = true, defaultValue = "0") Integer roleId,
                                                    @RequestParam(value = "userId",required=false) Integer userId){
        CMSUserVO vo = new CMSUserVO();
        vo.setCmsRoleId(roleId);
        vo.setUserId(userId);
        try {
        	if(userId!=null){
        		return cmsUserService.findUserAuthTree(vo);
        	}else{
        		return cmsRoleService.findAuthorityByRoleId(roleId);
        	}                        
        } catch (Exception e) {        	
            LOGGER.error("UserMgrController::::::::method:findUserMenuTree:menus"+e.getMessage());
            return null;
        }
    }

    @RequestMapping(value="/check/{userName}", method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object findByUserName(@PathVariable String userName){
        CMSUserVO vo = new CMSUserVO();
        vo.setUserName(userName);
        boolean flag = false;
        try {
            String uname = cmsUserService.findUserName(vo);
            if(uname==null){
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method:findByUserName:/check/{userName}"+e.getMessage());
            return flag;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.SAVE)
    @ResponseBody
    public Object save(@RequestParam(value = "treeJson",required = true)String treeJson,
                       @RequestParam(value = "form" ,required = true)String form,
                       HttpSession session){
        List<CMSRoleMenuTreeVO> treeList = JSON.parseArray(treeJson,CMSRoleMenuTreeVO.class);
        CMSUserVO vo = JSON.parseObject(form,CMSUserVO.class);
        String createBy = (String) session.getAttribute(WebConstant.USERNAME);
        vo.setCreateBy(createBy);
        vo.setModifyBy(createBy);
        Integer obj = null;
        try {
            obj = cmsUserService.saveUser(treeList,vo);
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method:save:user"+e.getMessage());
        }
        return obj;
    }

    @RequestMapping(value="/{userId}" , method = RequestMethod.DELETE)
    @Authority(privilege=AuthEnum.DEL)
    @ResponseBody
    public Object delete(@PathVariable Integer userId){
        Integer obj = null;
        try {
            obj = cmsUserService.delUser(userId);
        } catch (Exception e) {
            LOGGER.error("UserMgrController::::::::method:delete:user/{userId}"+e.getMessage());
        }
        return obj;
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value="/pwd",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.ALL)
    public String modifyPwd(){
        return "sysmgr/modify_pwd";
    }

    @RequestMapping(value="/pwd",method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ALL)
    @ResponseBody
    public Object updatePwd(@RequestParam(value = "oldPwd",required = true) String oldPwd,
                            @RequestParam(value = "pwd",required = true) String pwd,
                            @RequestParam(value = "newpwd",required = true) String newpwd,
                            HttpSession session) throws Exception {
        if(!pwd.equals(newpwd)){
            return ResultBO.err();
        }
        String userName = (String) session.getAttribute(WebConstant.USERNAME);
        Integer userId = (Integer) session.getAttribute(WebConstant.USERID);
        CMSUserVO vo = new CMSUserVO();
        vo.setUserId(userId);
        vo.setUserName(userName);
        vo.setUserPassword(MD5Util.encrypt(oldPwd));
        Integer flag = cmsUserService.updatePwd(vo,pwd);
        if(flag == null){
            return ResultBO.err();
        }
        return ResultBO.ok();
    }
    
    /**
     * @desc   查询所有用户类型，以MiniUI数据字典的格式{id:1,text:'字典值'}返回
     * @author Tony Wang
     * @create 2017年4月21日
     * @return 
     */
    @RequestMapping(value="/type/dic", method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public List<DictionaryBO> findUserTypeAsDic() {
    	//增加"全部用户选项"
    	List<DictionaryBO> options = new ArrayList<>(cmsUserService.findUserTypeAsDic());
    	DictionaryBO allUser = new DictionaryBO(OperateEnum.AD_ALL_USERS.toString(), "全部用户");
    	options.add(0, allUser);
    	return options;
    }
}
