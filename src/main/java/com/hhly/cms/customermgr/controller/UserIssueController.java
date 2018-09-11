package com.hhly.cms.customermgr.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.customermgr.service.UserIssueLevelService;
import com.hhly.cms.customermgr.service.UserIssueService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueExcelBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueLevelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueLevelVO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueVO;

/**
 * @desc    推单用户管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("customermgr/issue")
public class UserIssueController extends BaseController {
	
	@Autowired
    private UserIssueService userIssueService;
	
	@Autowired
	private UserIssueLevelService userIssueLevelService;
	
	@Autowired
	private ExcelExportService excelExportService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "customermgr/user_issue";
	}
	
	@RequestMapping("page")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<UserIssueBO> list(UserIssueVO vo){
		// 要查询会员账号
		vo.setJoinUser(true);
		// 如果有按彩种查询则或按'用户'、'专家查询',一个专家对应多个level，join level 表会导致记录重复
		//if(vo.getLotteryCode() != null || vo.getLevel() != null)
		//	vo.setJoinIssueLevel(true);
		// 若有按'最后发单时间查询'
		if(Objects.equals(vo.getTimeType(), 2))
			vo.setJoinOrderIssue(true);
		return userIssueService.page(vo);
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,UserIssueVO vo) throws IOException{
		// 要查询会员账号
		vo.setJoinUser(true);
		// 如果有按彩种查询则或按'用户'、'专家查询'
		if(vo.getLotteryCode() != null || vo.getLevel() != null)
			vo.setJoinIssueLevel(true);
		// 若有按'最后发单时间查询'
		if(Objects.equals(vo.getTimeType(), 2))
			vo.setJoinOrderIssue(true);
		List<UserIssueExcelBO> ret = userIssueService.excel(vo);
		excel("推单用户", excelExportService.dataToExeclByStream(ret), response);
	}
	
	@RequestMapping(value="level", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> mergeUserIssueLevel(UserIssueLevelVO vo,HttpSession session){
		if(vo.getId() != null) {
			vo.setModifyBy(getUserRealName(session));
		}
		return getSaveResult(userIssueLevelService.merge(vo));
	}
	
	@RequestMapping(value="level", method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public UserIssueLevelBO findOneIssueLevel(UserIssueLevelVO vo){
		return userIssueLevelService.findOne(vo);
	}
	
	@RequestMapping(value="status", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> updateStatus(UserIssueVO vo){
		UserIssueVO target = new UserIssueVO();
		target.setId(vo.getId());
		target.setStatus(vo.getStatus());
		return getSaveResult(userIssueService.update(target));
	}
}
