package com.hhly.cmscore.cms.remote.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customerservice.bo.MUserMessageBO;
import com.hhly.skeleton.cms.customerservice.vo.MUserMessageVO;

/**
 * 
 * @desc 客户服务接口
 * @author tangxiaobo
 * @date 2017年4月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ICustomerServiceMgrService {
	/**
	 * @Desc 消息管理列表分页查询
	 * @author tangxiaobo
	 * @CreatDate 2017年4月5日 上午11:12:03
	 * @param vo
	 * @return
	 */
	PagingBO<MUserMessageBO> findMUserMeaasge(MUserMessageVO vo);
	
	/**
	 * 
	 * @Description 更新发送状态 
	 * @author HouXiangBao289
	 * @param id
	 * @param sendStatus
	 */
	void updateStatus(Integer id,Integer sendStatus);
}
