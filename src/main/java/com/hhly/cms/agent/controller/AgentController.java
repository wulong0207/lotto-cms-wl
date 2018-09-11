package com.hhly.cms.agent.controller;

import com.hhly.cms.agent.service.AgentService;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.bo.AgentSubMemberBO;
import com.hhly.skeleton.cms.agent.bo.AgentTransLogBO;
import com.hhly.skeleton.cms.agent.vo.AgentQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc    代理
 * @author  Tony Wang
 * @date    2018年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("agent")
@Controller
public class AgentController extends BaseController {

	@Autowired
	private AgentService agentService;

	@RequestMapping(value = "direct",method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String directIndex() {
		return "agent/direct";
	}

	@RequestMapping(value = "direct/page",method=RequestMethod.POST)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<AgentSubMemberBO> pageDirect(AgentQueryVO vo) {
		return agentService.pageDirectMember(vo);
	}

	@RequestMapping(value = "sub",method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String subIndex() {
		return "agent/sub";
	}

	@RequestMapping(value = "sub/page",method=RequestMethod.POST)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<AgentSubMemberBO> pageSub(AgentQueryVO vo) {
		return agentService.pageSubMember(vo);
	}

	@RequestMapping(value = "trans",method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String transIndex() {
		return "agent/trans";
	}

	@RequestMapping(value = "trans/page",method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<AgentTransLogBO> pageTrans(AgentQueryVO vo) {
		return agentService.pageTrans(vo);
	}

	@RequestMapping("trans/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response, AgentQueryVO vo) throws IOException {
		excel("agent_trans", agentService.getTransExcel(vo), response);
	}
}
