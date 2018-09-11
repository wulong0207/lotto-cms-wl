package com.hhly.cms.ticket.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketInfoBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoStatusVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoVO;

import java.io.ByteArrayOutputStream;

/**
 * @desc 票信息服务接口
 * @author huangb
 * @date 2017年2月21日
 * @company 益彩网络
 * @version v1.0
 */
public interface TicketInfoService {
	/**
	 * @desc 票管理：分页查询
	 * @author huangb
	 * @date 2017年2月20日
	 * @param ticketInfoVO
	 *            参数对象
	 * @return 票管理：分页查询
	 */
	PagingBO<TicketInfoBO> findPagingTicket(TicketInfoVO ticketInfoVO);

	/**
	 * @desc 票管理：查询单个
	 * @author huangb
	 * @date 2017年2月20日
	 * @param ticketInfoVO
	 *            查询对象
	 * @return 票管理：查询单个
	 */
	TicketInfoBO findSingleTicket(TicketInfoVO ticketInfoVO);

	/**
	 * @desc 票管理：查询excel导出
	 * @author huangb
	 * @date 2017年2月20日
	 * @param ticketInfoVO
	 *            查询对象
	 * @return 票管理：查询excel导出
	 */
	ByteArrayOutputStream findExcelTicket(TicketInfoVO ticketInfoVO);

	/**
	 * @desc 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 * @author huangb
	 * @date 2017年2月20日
	 * @param ticketInfoVO
	 * @return 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 */
	int updTicket(TicketInfoVO ticketInfoVO);
    
	/**
	 * 修改票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月20日 下午2:41:01
	 * @param vo
	 * @return
	 */
	String updateTicketStatus(TicketInfoStatusVO vo);
    /**
     * 上传票号
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年3月21日 上午10:47:49
     * @param ticketType
     * @param ticketNo
     */
	void uploadTicketNo(String ticketType, String ticketNo);
	
	/**
	 * 根据票状态执行票操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月21日 上午10:57:41
	 * @param operate
	 * @param id
	 * @return
	 */
	String updateTicketOperate(String operate, String id,String modifyBy);
}
