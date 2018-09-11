package com.hhly.pay.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.CmsRechargeVO;

/**
 * @desc 充值统一接口
 * @author xiongJinGang
 * @date 2017年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface RechargeService {

	/**  
	* 方法说明: 提供给CMS人工充值（充值现金、充值红包）
	* @auth: xiongJinGang
	* @param cmsRecharge
	* @time: 2017年7月6日 下午5:26:47
	* @return: ResultBO<?> 
	*/
	ResultBO<?> updateRecharge(CmsRechargeVO cmsRecharge);

}
