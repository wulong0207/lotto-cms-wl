package com.hhly.cms.lotterymgr.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.util.StringUtil;
/**
 * @desc 重置开奖
 * @author jiangwei
 * @date 2017-2-4
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/lotterymgr/draw")
public class DrawIssueController extends BaseController{
    
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "lotterymgr/draw";
    }
    /**
     *  按票号重置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-4 下午4:33:03
     * @param session
     * @return
     */
    @RequestMapping(value="ticket",method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    public @ResponseBody Object udpateTicket(HttpSession session) {
       // vo.setModifyBy(getUserRealName(session));
        return getSaveResult(1);
    }
    /**
     * 按彩期重置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-4 下午4:33:38
     * @param session
     * @return
     */
    @RequestMapping(value="issue",method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    public @ResponseBody Object udpateIssue(HttpSession session) {
       // vo.setModifyBy(getUserRealName(session));
        return getSaveResult(1);
    }
    /**
     * 竞彩重置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-4 下午4:33:56
     * @param session
     * @return
     */
    @RequestMapping(value="sport",method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    public @ResponseBody Object udpateSport(HttpSession session) {
       // vo.setModifyBy(getUserRealName(session));
        return getSaveResult(1);
    }
    /**
     * 开奖进度
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年6月15日 上午10:59:00
     * @param response
     * @param lotteryCode
     * @param lotteryIssue
     * @return
     */
    @RequestMapping(value="schedule")
	@Authority(privilege = AuthEnum.ALL)
	public String image(HttpServletResponse response,String  lotteryCode,String lotteryIssue,String handleType) {
		Cookie code = new Cookie("lotteryCode", lotteryCode);
		response.addCookie(code);
		if(StringUtil.isBlank(lotteryIssue)){
			lotteryIssue = "";
		}
		Cookie issue = new Cookie("lotteryIssue", lotteryIssue);
		response.addCookie(issue);
		Cookie type = new Cookie("handleType", handleType);
		response.addCookie(type);
		return "lotterymgr/draw_schedule";
	}
}

