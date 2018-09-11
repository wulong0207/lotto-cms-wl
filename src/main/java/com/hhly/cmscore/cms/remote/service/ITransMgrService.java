package com.hhly.cmscore.cms.remote.service;

import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.transmgr.bo.*;
import com.hhly.skeleton.cms.transmgr.vo.TransChannelSettleVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRechargeVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRedVO;
import com.hhly.skeleton.cms.transmgr.vo.TransTakenVO;
import com.hhly.skeleton.cms.transmgr.vo.TransUserVO;

/**
 * @desc    
 * @author  Tony Wang
 * @date    2017年1月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITransMgrService {

	/**
	 * 查询用户流水
	 * @param vo
	 * @return
	 */
	PagingBO<TransUserBO> findUserTrans(TransUserVO vo);

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
		* @author   : wytong
		* @create : 2017年1月18日
		* @param  :
		* @describe : 查询用户流水ExcelBO
		* @return   :
	 */
	List<TransUserExcelBO> getTransUserExcel(TransUserVO vo);

	/**
	 * @desc   查询红包流水ExcelBO
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	List<TransRedExcelBO> getTransRedExcel(TransRedVO vo);

	/**
	 * @desc   查询充值流水ExcelBO
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	List<TransRechargeExcelBO> getTransRechargeExcel(TransRechargeVO vo);

	/**
	 * @desc   查询提款流水ExcelBO
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	List<TransTakenExcelBO> getTransTakenExcel(TransTakenVO vo);
	
	/**
	 * @desc   导出审核通过的流水excel，提交银行
	 * @author Tony Wang
	 * @create 2017年8月3日
	 * @param vo
	 * @return 
	 */
	Map<String, List<TransTakenCMBCBankExcelBO>> getTransTakenBankExcel(TransTakenVO vo);

	/**
	 * @desc   查询渠道结算流水ExcelBO
	 * @author Tony Wang
	 * @create 2017年1月19日
	 * @param  
	 * @return 
	 */
	List<TransChannelSettleExcelBO> getTransChannelSettleExcel(TransChannelSettleVO vo);

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

	int countTransTaken(TransTakenVO vo);

	/**
	 * 更新流水表的真实姓名
	 * @param actualName
	 * @param id
	 * @return
	 */
	int updateTakenCreateBy(String actualName, Integer id);

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
	List<TransRemittingExcelBO> excelTransRemitting(TransRemittingBO vo);

	/**
	 * 更新汇款记录
	 * @param vo
	 * @return
	 */
	int updateTransRemitting(TransRemittingBO vo);
}
