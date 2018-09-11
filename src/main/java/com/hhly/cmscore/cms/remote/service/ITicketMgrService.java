package com.hhly.cmscore.cms.remote.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.*;
import com.hhly.skeleton.cms.ticketmgr.vo.*;

import java.util.List;

/**
 * 
 * @desc 运维管理
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITicketMgrService {

    PagingBO<TicketChannelBO> listTicketChannel(
            TicketChannelVO vo);

    List<String> listDrawerName();
    
    List<TicketChannelBO> listDrawerIdName(String lotteryCode);
    
    int updOrderTicketChannel(TicketChannelVO vo);

    int addOrderTicketChannel(TicketChannelVO vo);

    int addTicketConfig(TicketConfigVO vo);

    int updTicketConfig(TicketConfigVO vo);

    PagingBO<TicketConfigBO> listTicketConfig(TicketConfigVO vo);

    /**
     * 报警信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午2:37:01
     * @param vo
     * @return
     */
    PagingBO<TicketAlarmInfoBO> listTicketAlarmInfo(TicketAlarmInfoVO vo);
    /**
     * 报警配置信息查询
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午2:37:24
     * @param vo
     * @return
     */
    PagingBO<TicketAlarmConfigBO> listTicketAlarmConfig(TicketAlarmConfigVO vo);
    
    /**
     * 报警配置信息修改
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午2:37:47
     * @param vo
     * @return
     */
    int updTicketAlarmConfig(TicketAlarmConfigVO vo);
    
    /**
     * 报警配置信息添加
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午2:38:22
     * @param vo
     * @return
     */
    int addTicketAlarmConfig(TicketAlarmConfigVO vo);
    
    /**
     * 批量修改报警信息状态
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午5:42:03
     * @param vo
     * @return
     */
    int updTicketAlarmInfoStatus(TicketAlarmInfoVO vo);

    /**
     * 出票监控配置信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 上午9:14:44
     * @param vo
     * @return
     */
    PagingBO<TicketMonitorConfigBO> listTicketMonitorConfig(
            TicketMonitorConfigVO vo);

    /**
     * 修改监控配置信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 上午9:14:57
     * @param vo
     * @return
     */
    int updTicketMonitorConfig(TicketMonitorConfigVO vo);

    /**
     * 添加监控配置信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 上午9:15:25
     * @param vo
     * @return
     */
    int addTicketMonitorConfig(TicketMonitorConfigVO vo);

    /**
     * 监控常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 上午9:15:39
     * @return
     */
    List<TicketMonitorCommonBO> listTicketMonitorCommon();

    /**
     * 修改常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 上午9:16:13
     * @param list
     * @return
     */
    int updTicketMonitorCommon(List<TicketMonitorCommonVO> list);
    /**
     * 查询彩种出票监控信息(同步方法)
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-14 下午4:20:52
     * @return
     */
    OrderTicketMonitorBO getTicketMonitor(int lotteryCode);
    
    /**
     * 查询出票监控设置的彩种code集合
     * @return
     * @date 2017年8月19日下午4:58:04
     * @author cheng.chen
     */
    List<String> queryLotteryMonitorConfig(Integer lotteryCategory);

    /*************************************** 票管理操作 **********************************************/
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
	List<TicketInfoBO> findExcelTicket(TicketInfoVO ticketInfoVO);

	/**
	 * @desc 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 * @author huangb
	 * @date 2017年2月20日
	 * @param ticketInfoVO
	 * @return 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 */
	int updTicket(TicketInfoVO ticketInfoVO);

	/*************************************** 票管理操作 **********************************************/
	
	/**
	 * 修改票管理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月20日 下午2:54:10
	 * @param vo
	 * @return
	 */
	String updateTicketStatus(TicketInfoStatusVO vo);
   
	/**
	 * 上传票号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月21日 上午10:48:24
	 * @param ticketType
	 * @param ticketNo
	 */
	void uploadTicketNo(String ticketType, String ticketNo);

	/**
	 * 根据票状态执行操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月21日 上午10:58:41
	 * @param operate 操作类型
	 * @param id 票ID
	 * @return
	 */
	String updateTicketOperate(String operate, String id,String modifyBy);
    /**
     * 添加报警进行
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年8月8日 上午11:17:05
     * @param vo
     * @return 
     */
	int addTicketAlarmInfo(TicketAlarmInfoVO vo);

	/**
	 * @desc   查询符合条件的报警信息
	 * @author Tony Wang
	 * @create 2017年10月25日
	 * @param vo
	 * @return 
	 */
	List<TicketAlarmInfoBO> findTicketAlarmInfo(TicketAlarmInfoVO vo);
}
