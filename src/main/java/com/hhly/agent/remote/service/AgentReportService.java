package com.hhly.agent.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.vo.AgentIncomeVO;
import com.hhly.skeleton.cms.agent.vo.AgentSetVO;
import com.hhly.skeleton.cms.agent.vo.DayIncomeVO;
import com.hhly.skeleton.cms.agent.vo.MonthIncomeExcelVO;
import com.hhly.skeleton.cms.agent.vo.MonthIncomeVO;

public interface AgentReportService {
	
	List<AgentSetVO> getAgentDetailMonthList(List<AgentSetVO> vo);
	
	PagingBO<MonthIncomeVO> getIncomeList(AgentIncomeVO vo);
	
	List<MonthIncomeExcelVO> getIncomeListOfExcel(AgentIncomeVO vo);
	
	PagingBO<DayIncomeVO> getDayIncomeList(AgentIncomeVO vo);
	
}
