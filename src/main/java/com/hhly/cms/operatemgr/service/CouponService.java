package com.hhly.cms.operatemgr.service;

import java.io.ByteArrayOutputStream;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateCouponBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateCouponVO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-9 上午9:53:56
 * @Desc 优惠券管理
 */
public interface CouponService {

	PagingBO<OperateCouponBO> findCoupon(OperateCouponVO vo);

	ByteArrayOutputStream getExcelCoupon(OperateCouponVO vo);

	OperateCouponBO findCouponDetail(StringVO vo);
	/**
	 * 修改优惠券（状态）
	 * @param vo
	 * @return
	 */
	int updCoupon(OperateCouponVO vo);
}
