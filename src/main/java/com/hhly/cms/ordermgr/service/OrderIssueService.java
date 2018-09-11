package com.hhly.cms.ordermgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderIssueBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderIssueVO;

/**
 * @desc    发单管理
 * @author  Tony Wang
 * @date    2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OrderIssueService {

	PagingBO<OrderIssueBO> page(OrderIssueVO vo);

	List<OrderIssueBO> list(OrderIssueVO vo);

	int update(OrderIssueVO target);

}
