package com.hhly.usercore.cms.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.UserRedAddParamVo;

/**
 * 
 * @desc usercode 钱包hession操作类   
 * @author  cheng chen
 * @date    2017年5月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserWalletService {

	/**  
	* 方法说明: 提供给cms后台，添加彩金红包金额
	* @auth: xiongJinGang
	* @param list
	* @time: 2017年5月31日 下午4:34:59
	* @return: ResultBO<?> 
	* @throws Exception 
	*/
	ResultBO<?> addRedColorAmount(List<UserRedAddParamVo> list) throws Exception;
	
	 /**  
	 * 方法说明: 提供给cms后台，作废彩金红包
	 * @auth: xiongJinGang
	 * @param list
	 * @throws Exception
	 * @time: 2017年6月29日 下午3:30:54
	 * @return: ResultBO<?> 
	 */
	 ResultBO<?> subRedColorAmount(List<UserRedAddParamVo> list) throws Exception;
}
