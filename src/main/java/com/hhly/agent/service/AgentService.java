package com.hhly.agent.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.vo.AgentSetVO;

public interface AgentService {
	
	PagingBO<AgentSetVO> getAgentList(AgentSetVO vo);
	
 }
