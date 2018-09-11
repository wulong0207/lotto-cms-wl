package com.hhly.pay.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.bo.TransTakenBO;

/**
 * @desc 提款交易服务接口
 * @author xiongjingang
 * @date 2017年3月2日 上午10:44:46
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface TransTakenConfirmService {

	

	/**  
	* 方法说明: 供CMS调用批量更新提款审核状态。提交过来的list都是统一状态，全部通过或者全部不过。
	* @auth: xiongJinGang
	* @param list；trans_taken_code、review_by、trans_status必填；trans_fail_info为审核不通过时，必填
	* @param operateType；操作类型，1审核、2提交银行、3银行处理结果，参考 TakenOperateTypeEnum
	* @throws Exception
	* @time: 2017年8月7日 下午3:39:54
	* @return: ResultBO<?> 
	*/
	ResultBO<?> updateTakenStatusByBatch(List<TransTakenBO> list, Short operateType) throws Exception;

}
