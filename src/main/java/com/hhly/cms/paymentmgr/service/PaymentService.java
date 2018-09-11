package com.hhly.cms.paymentmgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.payment.bo.PayBankBO;
import com.hhly.skeleton.cms.payment.bo.PayBankLimitBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelLimitBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelMgrBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelMgrExcelBO;
import com.hhly.skeleton.cms.payment.vo.*;

/**
 * @desc 支付管理接口
 * @author tangxiaobo
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface PaymentService {
	
	/**
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月9日 下午4:05:55
	 * @param vo
	 * @return 银行卡列表
	 */
	List<PayBankBO> findBank(PayBankVO vo);
	
	/**
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月9日 下午4:06:51
	 * @param bankId
	 * @param type 渠道支付类型
	 * @return
	 */
	List<PayChannelBO> findChannel(PayChannelVO vo);
	
	/**
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月9日 下午4:08:28
	 * @param vo
	 * @return
	 */
	int addBank(PayBankVO vo);
	
	PayBankBO findPayBankById(Integer id);
	
	int updateBankById(PayBankVO vo);
	
	int updateBankList(List<PayBankVO> list);
	
	/**
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月9日 下午4:11:01
	 * @param list 
	 * @return批量新增或更新
	 */
	int saveOrUpdateChannelList(List<PayChannelVO> list);
	
	List<PayBankLimitBO> findBankLimit(Integer bankId);

	int saveOrUpdateBankLimitList(List<PayBankLimitVO> list);

	/**
	 * @desc   删除支付渠道
	 * @author Tony Wang
	 * @create 2017年6月27日
	 * @param vo
	 * @return 
	 */
	int deleteChannel(PayChannelVO vo);

	/**
	 * @desc   删除付款金额超限说明
	 * @author Tony Wang
	 * @create 2017年6月27日
	 * @param vo
	 * @return 
	 */
	int deleteBankLimit(PayBankLimitVO vo);

	/**
	 * @desc   分页查询支付渠道管理
	 * @author Tony Wang
	 * @create 2017年12月1日
	 * @param vo
	 * @return 
	 */
	PagingBO<PayChannelMgrBO> pageChannelMgrInfo(PayChannelMgrVO vo);

	/**
	 * @desc   查询支付渠道管理
	 * @author Tony Wang
	 * @create 2017年12月1日
	 * @param vo
	 * @return 
	 */
	PagingBO<PayChannelLimitBO> pageChannelLimitInfo(PayChannelLimitVO vo);

	/**
	 * @desc   添加或更新渠道管理信息
	 * @author Tony Wang
	 * @create 2017年12月1日
	 * @param vo
	 * @return 
	 */
	int mergeChannelMgrInfo(PayChannelMgrVO vo);

	/**
	 * @desc   查询支付渠道管理
	 * @author Tony Wang
	 * @create 2017年12月2日
	 * @param vo
	 * @return 
	 */
	List<PayChannelMgrBO> findChannelMgrInfo(PayChannelMgrVO vo);

	
	/**
	 * @desc   查询excel数据
	 * @author Tony Wang
	 * @create 2017年12月7日
	 * @param vo
	 * @return 
	 */
	List<PayChannelMgrExcelBO> findChannelMgrExcelInfo(PayChannelMgrVO vo);
	
	/**
	 * @desc   查询渠道和银行的配置信息(表pay_channel)
	 * @author Tony Wang
	 * @create 2017年12月5日
	 * @param vo
	 * @return 
	 */
	PagingBO<PayChannelBO> pageChannelBankInfo(PayChannelVO vo);

	/**
	 * @desc   批量更新或添加渠道银行的配置信息
	 * @author Tony Wang
	 * @create 2017年12月5日
	 * @param vos
	 * @return 
	 */
	int batchMergeChannelBankInfo(List<PayChannelVO> vos);

	/**
	 * @desc   批量更新或添加渠道付款限额信息
	 * @author Tony Wang
	 * @create 2017年12月6日
	 * @param vos
	 * @return 
	 */
	int batchMergeChannelLimitInfo(List<PayChannelLimitVO> vos);

	/**
	 * @desc   删除渠道付款限额信息
	 * @author Tony Wang
	 * @create 2017年12月6日
	 * @param vo
	 * @return 
	 */
	int deleteChannelLimitInfo(PayChannelLimitVO vo);

	/**
	 * @desc   查询用户银行卡信息
	 * @author Tony Wang
	 * @create 2017年12月6日
	 * @param vo
	 * @return
	 */
    PagingBO<PayBankCardVO> pageBankCard(PayBankCardVO vo);

	/**
	 * @desc   更新用户银行卡信息
	 * @author Tony Wang
	 * @create 2017年12月6日
	 * @param vo
	 * @return
	 */
    int updateBankCard(PayBankCardVO vo);

	/**
	 * @desc   批量更新用户银行卡信息
	 * @author Tony Wang
	 * @create 2017年12月6日
	 * @param vos
	 * @return
	 */
	int batchUpdateBankCard(List<PayBankCardVO> vos);
}
