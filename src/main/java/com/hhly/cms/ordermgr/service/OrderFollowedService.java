package com.hhly.cms.ordermgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderFollowedBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderFollowedVO;

/**
 * @desc    发单管理
 * @author  Tony Wang
 * @date    2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OrderFollowedService {

	List<OrderFollowedBO> list(OrderFollowedVO vo);

	PagingBO<OrderFollowedBO> page(OrderFollowedVO vo);

	ByteArrayOutputStream excel(OrderFollowedVO vo);
}
