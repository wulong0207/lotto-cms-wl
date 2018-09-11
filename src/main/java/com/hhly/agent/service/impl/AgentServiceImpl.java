package com.hhly.agent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.agent.remote.service.AgentReportService;
import com.hhly.agent.remote.service.AgentUserInfoService;
import com.hhly.agent.service.AgentService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.vo.AgentSetVO;

@Service("agentService")
public class AgentServiceImpl implements AgentService {
	
	@Autowired
	private AgentUserInfoService agentUserInfoService;
	@Autowired
	private AgentReportService agentDetailMonthService;

	/**
	 * 通过查询lotto库和report库组装数据
	 */
	@Override
	public PagingBO<AgentSetVO> getAgentList(AgentSetVO vo) {
		//1.过滤条件在lotto库生效
		PagingBO<AgentSetVO> userList = agentUserInfoService.getUserList(vo);
		//返佣金额从report库获取
		List<AgentSetVO> agentSetVOList = userList.getData();
		if(agentSetVOList==null || agentSetVOList.size()<=0){
			return userList;
		}
		List<AgentSetVO> agentDetailMonthList = agentDetailMonthService.getAgentDetailMonthList(agentSetVOList);
		//给每个代理商赋值返佣金额
		for (int i = 0; i < agentSetVOList.size(); i++) {
			AgentSetVO agentSetVO = agentSetVOList.get(i);
			if(agentDetailMonthList!=null && agentDetailMonthList.size()>0){
				for (AgentSetVO monthvo : agentDetailMonthList) {
					if(agentSetVO.getAgentId() == monthvo.getAgentId()){
						agentSetVO.setMonthDirectIncome(monthvo.getMonthDirectIncome());
						agentSetVO.setMonthAgentIncome(monthvo.getMonthAgentIncome());
					}
				}
			}
		}
		return userList;
	}

}
