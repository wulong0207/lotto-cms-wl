package com.hhly.cms.ordermgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderDetailBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoDetailCmsBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoVO;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-6 上午9:34:18
 * @Desc 用户方案管理
 */
public interface OrderService {
    /**
     * 查询订单信息
     * @param vo
     * @return
     */
	PagingBO<OrderInfoCmsBO> find(OrderInfoCmsSearchVO vo);
    /**
     * 订单详情
     * @param vo
     * @return
     */
	OrderInfoDetailCmsBO findOrderInfoDetail(StringVO vo);
    /**
     * 查询订单投注内容详情
     * @param vo
     * @return
     */
	PagingBO<OrderDetailBO> findOrderDetail(OrderInfoCmsSearchVO vo);
    /**
     * 修改订单信息
     * @param vo
     * @return
     */
	int updOrderInfo(OrderInfoVO vo);
    /**
     * 导出excel
     * @param vo
     * @param type 导出类型
     * @return
     */
	ByteArrayOutputStream getOrderInfoExcel(OrderInfoCmsSearchVO vo, String type);
	/**
	 * 修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 下午5:27:22
	 * @param vo
	 * @return
	 */
	int updateOrderStatus(OrderInfoVO vo);
	/**
	 * 订单执行指定操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月15日 下午4:43:15
	 * @param vo
	 * @return
	 */
	String updateOrderOperate(OrderInfoVO vo);
	
	/*******************彩期时间或赛事时间变动修改***********/
	/**
	 * 修改订单的截止出票和检票时间
	 * @param lotteryCode 彩种编码
	 * @param issueCode 彩期编码
	 * @param endTicketTime 出票截止时间
	 * @param endCheckTime 检票截止时间
	 * @param endSysTime 本站销售截止时间
	 * @param systemCode 赛事系统编码
	 * @return
	 * @date 2017年5月15日下午4:01:54
	 * @author cheng.chen
	 */
	int updTicketAndCheckTime(Integer lotteryCode, String issueCode, Date endTicketTime, Date endCheckTime,
			Date endSysTime, String systemCode);
	
	/**
	 * 修改撤销理由到订单备注
	 * @param orderCode
	 * @param remark
	 * @return
	 * @date 2017年6月11日上午11:07:25
	 * @author cheng.chen
	 */
	int updCancelOrderRemark(String orderCode, String remark);		
}
