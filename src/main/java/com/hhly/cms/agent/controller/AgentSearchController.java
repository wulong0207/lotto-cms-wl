package com.hhly.cms.agent.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hhly.agent.remote.service.AgentReportService;
import com.hhly.agent.remote.service.AgentUserInfoService;
import com.hhly.agent.service.AgentService;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.cms.agent.vo.AgentIncomeVO;
import com.hhly.skeleton.cms.agent.vo.AgentRebateConfigHistoryVO;
import com.hhly.skeleton.cms.agent.vo.AgentRebateConfigVO;
import com.hhly.skeleton.cms.agent.vo.AgentSetVO;
import com.hhly.skeleton.cms.agent.vo.MonthIncomeExcelVO;
import com.hhly.skeleton.cms.agent.vo.MonthIncomeVO;

@Controller
@RequestMapping("/agent")
public class AgentSearchController extends BaseController{
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private AgentReportService agentReportService;
	@Autowired
	private AgentUserInfoService agentInfoService;
	
	@Autowired
	private ExcelExportService excelExportService;
	
	@RequestMapping("/agentSearch")
	@Authority(privilege = AuthEnum.ALL)
	public String index(){
		return "agent/agentSearch";
	}
	
	
	@RequestMapping("/agentList")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object getAgentList(AgentSetVO vo){
		return agentService.getAgentList(vo);
	}
	
	@RequestMapping("/updateStatus")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object updateStatus(AgentSetVO vo){
		return agentInfoService.updateAgent(vo);
	}
	
	@RequestMapping("/set")
	@Authority(privilege = AuthEnum.ALL)
	public ModelAndView agentSet(AgentSetVO vo){
		ModelAndView model  = new ModelAndView();
		model.setViewName("agent/agentSet");
		//agentId
		model.addObject("agentId",vo.getAgentId());
		//变更日志
		List<AgentRebateConfigHistoryVO> history = agentInfoService.getAgentRebateHistory(vo);
		model.addObject("history", history);
		return model;
	}
	
	@RequestMapping("/agentRebateConfigs")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object getAgentRebateConfigs(AgentSetVO vo){
		return agentInfoService.getAgentRebateConfigs(vo);
	}
	
	/**
	 * 返佣数据查询
	 * @desc 
	 * @create 2018年3月13日
	 * @return String
	 */
	@RequestMapping("/incomeSearch")
	@Authority(privilege = AuthEnum.ALL)
	public String incomeSearch(){
		return  "agent/incomeSearch";
	}
	
	@RequestMapping("/incomeList")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object getIncomeList(AgentIncomeVO vo){
		List<AgentSetVO> userInfos = agentInfoService.getUserInfo(vo.getAccountName());
		//如果accountName非空，那么肯定只有可能有一个用户
		if(vo.getAccountName()!=null && vo.getAccountName()!=""){
			vo.setUserId(userInfos.get(0).getId());
		}
		//否则，则查找所有
		PagingBO<MonthIncomeVO> incomeList = agentReportService.getIncomeList(vo);
		if(incomeList!=null){
			List<MonthIncomeVO> data = incomeList.getData();
			if(data==null || data.size()<=0){return incomeList;}
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < userInfos.size(); j++) {
					if(data.get(i)==null || userInfos.get(j)==null){continue;}
					if(data.get(i).getUserId().equals(userInfos.get(j).getId())){
						data.get(i).setAccountName(userInfos.get(j).getAccountName());
					}
				}
			}
		}
		return incomeList; 
	}
	
	
	@RequestMapping("/excelAboutIncome")
	@Authority(privilege = AuthEnum.ALL)
	public void excelAbountIncome(AgentIncomeVO vo,HttpServletResponse response) throws IOException{
		List<MonthIncomeExcelVO> incomeList = agentReportService.getIncomeListOfExcel(vo);
		if(incomeList!=null){
			excel("rebateData", excelExportService.dataToExeclByStream(incomeList), response);	
		}
	}
	
	
	
	
	@RequestMapping("/dayIncomeSearch")
	@Authority(privilege = AuthEnum.ALL)
	public String dayIncomeSearch(){
		return  "agent/dayIncomeSearch";
	}
	
	@RequestMapping("/dayIncomeList")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object getDayIncomeList(AgentIncomeVO vo){
		return agentReportService.getDayIncomeList(vo);
	}
	
	@RequestMapping("/addConfigs")
	@Authority(privilege = AuthEnum.ALL)
	@ParameterValid
	public @ResponseBody Object addConfig(@RequestBody AgentRebateConfigVO[] lists,HttpSession session) {
		for (int i = 0; i < lists.length; i++) {
			AgentRebateConfigVO agentRebateConfigVO = lists[i];
			agentRebateConfigVO.setConfigCreateBy(getUserRealName(session));
			agentRebateConfigVO.setAddTime(new Date());
			agentRebateConfigVO.setConfigCreateTime(new Date());
			agentRebateConfigVO.setCreateBy(getUserRealName(session));
			//0 配置 1  默认
			agentRebateConfigVO.setDefaultFlag(0);
		}
		ResultBO<Object> addConfig = agentInfoService.addConfig(lists);
		Object data = addConfig.getData();
		return getSaveResult((Integer)data);
	}
	
	@RequestMapping("/defaultSet")
	@Authority(privilege = AuthEnum.ALL)
	public ModelAndView defaultSet(){
		ModelAndView model  = new ModelAndView();
		model.setViewName("agent/defaultAgentSet");
		return model;
	}
	
	
	@RequestMapping("/defaultAgentRebateConfigs")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object getDefaultAgentRebateConfigs(){
		return agentInfoService.getDefaultAgentRebateConfigs();
	}
	
	@RequestMapping("/addDefaultConfigs")
	@Authority(privilege = AuthEnum.ALL)
	public @ResponseBody Object addDefaultConfigs(@RequestBody AgentRebateConfigVO[] lists,HttpSession session) {
		for (int i = 0; i < lists.length; i++) {
			AgentRebateConfigVO agentRebateConfigVO = lists[i];
			agentRebateConfigVO.setConfigCreateBy(getUserRealName(session));
			agentRebateConfigVO.setUpdateBy(getUserRealName(session));
			agentRebateConfigVO.setAddTime(new Date());
			agentRebateConfigVO.setConfigCreateTime(new Date());
			agentRebateConfigVO.setCreateBy(getUserRealName(session));
		}
		ResultBO<Object> addConfig = agentInfoService.addDefaultConfigs(lists);
		Object data = addConfig.getData();
		return getSaveResult((Integer)data);
	}
	
	
	@RequestMapping("/applyAllAgents")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public Object applyAllAgents(@RequestBody AgentRebateConfigVO[] lists,HttpSession session){
		for (int i = 0; i < lists.length; i++) {
			AgentRebateConfigVO agentRebateConfigVO = lists[i];
			agentRebateConfigVO.setConfigCreateBy(getUserRealName(session));
			agentRebateConfigVO.setUpdateBy(getUserRealName(session));
			agentRebateConfigVO.setAddTime(new Date());
			agentRebateConfigVO.setConfigCreateTime(new Date());
			agentRebateConfigVO.setCreateBy(getUserRealName(session));
		}
		return  agentInfoService.applyAllAgents(lists);
	}
}
