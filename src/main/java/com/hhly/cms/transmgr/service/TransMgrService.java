package com.hhly.cms.transmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.transmgr.bo.*;
import com.hhly.skeleton.cms.transmgr.vo.TransChannelSettleVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRechargeVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRedVO;
import com.hhly.skeleton.cms.transmgr.vo.TransTakenVO;
import com.hhly.skeleton.cms.transmgr.vo.TransUserVO;
import com.hhly.skeleton.pay.vo.CmsRechargeVO;

/**
 * 财务管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月4日 下午4:39:39
 */
public interface TransMgrService {

	/**
	 * 查询用户流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransUserBO> findUserTrans(TransUserVO vo);

//	/**
//	 * 查询用户流水详情
//	 * @param vo
//	 * @return
//	 */
//	TransUserBO findTransUserDetail(TransUserVO vo);

	/**
	 * 查询红包流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransRedBO> findRedTrans(TransRedVO vo);

	/**
	 * 查询用户充值流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransRechargeBO> findRechargeTrans(TransRechargeVO vo);

	/**
	 * 查询用户提款流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransTakenBO> findTakenTrans(TransTakenVO vo);

	/**
	 * 查询渠道结算流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransChannelSettleBO> findChannelSettleTrans(TransChannelSettleVO vo);


	/**
		* @desc   导出用户流水excel
		* @author Tony Wang
		* @create 2017年1月18日
		* @param  
		* @return
	 */
	ByteArrayOutputStream getTransUserExcel(TransUserVO vo);

	
	/**
	 * @desc   导出红包流水excel
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	ByteArrayOutputStream getTransRedExcel(TransRedVO vo);

	/**
	 * @desc   导出充值流水excel
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	ByteArrayOutputStream getTransRechargeExcel(TransRechargeVO vo);

	/**
	 * @desc   导出提款流水excel
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	ByteArrayOutputStream getTransTakenExcel(TransTakenVO vo);
	
	/**
	 * @desc   导出审核通过的流水excel，提交银行
	 * @author Tony Wang
	 * @create 2017年8月3日
	 * @param vo
	 * @return 
	 */
	ByteArrayOutputStream getTransTakenBankExcel(TransTakenVO vo);
	
	/**
	 * @desc   根据条件查询提款流水数量
	 * @author Tony Wang
	 * @create 2017年11月21日
	 * @param vo
	 * @return 
	 */
	int countTransTaken(TransTakenVO vo);

	/**
	 * @desc   导出渠道结算流水excel
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	ByteArrayOutputStream getTransChannelSettleExcel(TransChannelSettleVO vo);

	/**
	 * @desc   补单
	 * @author Tony Wang
	 * @create 2017年1月20日
	 * @param vo
	 * @return 成功补单的记录数
	 */
	int resupply(TransRechargeVO vo);

//	/**
//	 * @desc   更新提款记录状态(可能会同时更新操作人员信息)
//	 * @author Tony Wang
//	 * @create 2017年5月22日
//	 * @param vos
//	 * @return 
//	 */
//	int updateTakenStatus(List<TransTakenVO> vos);

	/**
	 * @desc   人工充值
	 * @author Tony Wang
	 * @create 2017年7月6日
	 * @param vo
	 * @return 
	 */
	ResultBO<?> manual(CmsRechargeVO vo);

	/**
	 * @desc   查询批次号
	 * @author Tony Wang
	 * @create 2017年8月4日
	 * @param vo
	 * @return 
	 */
	List<String> findTransTakenBatchNums(TransTakenVO vo);

	/**
	 * @desc   查询提款申请记录
	 * @author Tony Wang
	 * @create 2017年8月4日
	 * @param vo
	 * @return 
	 */
	List<TransTakenBO> findTakenTransNoPaging(TransTakenVO vo);

	/**
	 * 分页查询汇款记录
	 * @param vo
	 * @return
	 */
    PagingBO<TransRemittingBO> pageTransRemitting(TransRemittingBO vo);

	/**
	 * 导出汇款记录
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream excelTransRemitting(TransRemittingBO vo);

	/**
	 * 更新汇款记录
	 * @param vo
	 * @return
	 */
	int updateTransRemitting(TransRemittingBO vo);
}
