package com.hhly.cms.agent.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.bo.AgentSubMemberBO;
import com.hhly.skeleton.cms.agent.bo.AgentTransLogBO;
import com.hhly.skeleton.cms.agent.vo.AgentQueryVO;
import com.hhly.skeleton.cms.agent.vo.AgentTransTakenVO;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc    代理系统service
 * @date 2018/3/8 17:51
 * @company 益彩网络科技有限公司
 */
public interface AgentService {

    /**
     * 分页查询直属用户
     * @param vo
     * @return
     */
    PagingBO<AgentSubMemberBO> pageDirectMember(AgentQueryVO vo);

    /**
     * 分页查询下级代理
     * @param vo
     * @return
     */
    PagingBO<AgentSubMemberBO> pageSubMember(AgentQueryVO vo);

    /**
     * 分页查询代理钱包流水
     * @param vo
     * @return
     */
    PagingBO<AgentTransLogBO> pageTrans(AgentQueryVO vo);

    /**
     * 分页查询代理提现记录
     * @param vo
     * @return
     */
    PagingBO<AgentTransTakenVO> pageTaken(AgentTransTakenVO vo);

    /**
     * 查询符合条件的代理提现记录数量
     * @param vo
     * @return
     */
    int countTaken(AgentTransTakenVO vo);

    /**
     * 查询代理提现记录
     * @param vo
     * @return
     */
    List<AgentTransTakenVO> findTaken(AgentTransTakenVO vo);

    /**
     * 导出excel
     * @param vo
     * @return
     */
    ByteArrayOutputStream getTakenExcel(AgentTransTakenVO vo);

    /**
     * 导出银行提款Excel
     * @param vo
     * @return
     */
    ByteArrayOutputStream getTakenBankExcel(AgentTransTakenVO vo);

    /**
     * 导出代理流水excel
     * @param vo
     * @return
     */
    ByteArrayOutputStream getTransExcel(AgentQueryVO vo);
}
