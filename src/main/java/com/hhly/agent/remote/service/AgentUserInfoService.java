package com.hhly.agent.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.agent.vo.AgentRebateConfigHistoryVO;
import com.hhly.skeleton.cms.agent.vo.AgentRebateConfigVO;
import com.hhly.skeleton.cms.agent.vo.AgentSetVO;

public interface AgentUserInfoService {
	
	PagingBO<AgentSetVO> getUserList(AgentSetVO vo);
	
	List<AgentSetVO>  getUserInfo(String accountName);
	
	ResultBO<?> updateAgent(AgentSetVO vo);
	
	//返佣设置
	List<AgentRebateConfigVO> getAgentRebateConfigs(AgentSetVO vo);
	
	//变更历史
	List<AgentRebateConfigHistoryVO> getAgentRebateHistory(AgentSetVO vo);

	ResultBO<Object> addConfig(AgentRebateConfigVO[] lists);

	List<AgentRebateConfigVO> getDefaultAgentRebateConfigs();

	ResultBO<Object> addDefaultConfigs(AgentRebateConfigVO[] lists);

	ResultBO<?> applyAllAgents(AgentRebateConfigVO[] lists);
	
}
