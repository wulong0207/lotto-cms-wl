package com.hhly.cms.operatemgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateUserAnalysisBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateUserAnalysisVO;

import java.io.ByteArrayOutputStream;

/**
 * @desc 会员访问信息分析的数据接口
 * @author huangb
 * @date 2017年2月10日
 * @company 益彩网络
 * @version v1.0
 */
public interface UserAnalysisService {

	/**
	 * @desc 查询分页列表
	 * @author huangb
	 * @date 2017年2月8日
	 * @param userWalletVO
	 *            参数对象
	 * @return 查询分页列表
	 */
	PagingBO<OperateUserAnalysisBO> findPaging(OperateUserAnalysisVO operateUserAnalysis);

	/**
	 * @desc 查询excel导出的数据
	 * @author huangb
	 * @date 2017年2月10日
	 * @param operateUserAnalysis
	 *            参数对象
	 * @return 查询excel导出的数据并输出字节流
	 */
	ByteArrayOutputStream findExcel(OperateUserAnalysisVO operateUserAnalysis);
}
