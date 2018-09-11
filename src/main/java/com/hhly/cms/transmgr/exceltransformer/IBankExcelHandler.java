package com.hhly.cms.transmgr.exceltransformer;

import com.hhly.skeleton.pay.bo.TransTakenBO;
import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBankExcelHandler {

	
	/**
	 * @desc   模板方法：解析excel内容给页面显示
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 * @param multipartFile
	 * @return 
	 */
	String transform(String bankId, MultipartFile multipartFile);

	/**
	 * @desc   模板方法：从excel文件中解析出流水记录，传给lotto-pay更新流水状态
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 * @param multipartFile
	 * @return
	 */
	List<TransTakenBO> getTransTakens(String bankId, MultipartFile multipartFile);
	
	/**
	 * @desc   各银行各自的验证excel是否合法
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 */
	void validateCustom(String bankId);
	
	/**
	 * @desc   验证excel是否合法的公共逻辑
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 */
	void validateCommon(String bankId);
	
	/**
	 * @desc   上传前，验证流水的合法性
	 * @author Tony Wang
	 * @create 2017年11月24日
     * @param bankId
     */
	void validateTakenTrans(String bankId);
	
	/**
	 * @desc   各个银行各自解析excel内容给页面显示
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 * @return
	 */
	List<JSONArray> customTransform(String bankId);
	
	/**
	 * @desc   各个银行从excel文件中解析出流水记录
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @param bankId
	 * @return
	 */
	List<TransTakenBO> getCustomTransTakens(String bankId);
	
	/**
	 * @desc   获取表头信息
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	List<String> getHeaders();
	
	/**
	 * @desc   交易流水号的列索引，从0开始算
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getTransTakenCodeIndex();

	/**
	 * @desc   交易状态的列索引，从0开始算
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getTransStatusIndex();
	
	/**
	 * @desc   到账金额的列索引，从0开始算
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getRealAmountIndex();
	
	/**
	 * @desc   收方账号列索引
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getBankAccountIndex();
	
	/**
	 * @desc  收方户名列索引
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getBankUserIndex();
	
	/**
	 * @desc  失败原因列索引
	 * @author Tony Wang
	 * @create 2017年11月24日
	 * @return 
	 */
	int getFailReasonIndex();
}
