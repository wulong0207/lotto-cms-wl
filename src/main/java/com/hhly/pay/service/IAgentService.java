package com.hhly.pay.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.agent.bo.AgentTransTakenBO;

import java.util.List;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc    LottoPay对代理系统的服务
 * @date 2018/3/7 15:04
 * @company 益彩网络科技有限公司
 */
public interface IAgentService {

    /**
     * 方法说明: 批量更新提款状态【供CMS后台审核】
     * @auth: xiongJinGang
     * @param list；trans_taken_code、review_by、trans_status、agent_id必填；trans_fail_info为审核不通过时，必填
     * @param operateType；操作类型，1审核、2提交银行、3银行处理结果、4CMS确认完成，参考 TakenOperateTypeEnum
     * @throws Exception
     * @time: 2018年3月10日 下午5:56:39
     * @return: ResultBO<?>
     */
    ResultBO<?> updateTakenStatusByBatch(List<AgentTransTakenBO> list, Short operateType) throws Exception;
}
