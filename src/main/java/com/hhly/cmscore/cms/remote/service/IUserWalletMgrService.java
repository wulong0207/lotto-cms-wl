package com.hhly.cmscore.cms.remote.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWalletBO;
import com.hhly.skeleton.cms.customermgr.vo.UserWalletVO;

import java.util.List;

/**
 * @desc 会员钱包管理接口
 * @author huangb
 * @date 2017年2月8日
 * @company 益彩网络
 * @version v1.0
 */
public interface IUserWalletMgrService {

	/**
	 * @desc 查询分页列表
	 * @author huangb
	 * @date 2017年2月8日
	 * @param userWalletVO
	 *            参数对象
	 * @return 查询分页列表
	 */
	PagingBO<UserWalletBO> findPaging(UserWalletVO userWalletVO);

	/**
	 * @desc 修改钱包状态（如：批量启用、批量禁用）
	 * @author huangb
	 * @date 2017年2月8日
	 * @param ids
	 *            钱包id列表(逗号分隔)
	 * @param status
	 *            更新的状态值
	 * @return 修改钱包状态
	 */
	int updWalletStatusByIds(String ids, Short status);
	
	/**
	 * @desc 查询excel导出的用户钱包数据
	 * @author huangb
	 * @date 2017年2月9日
	 * @param userWalletVO
	 *            查询参数
	 * @return excel导出的用户钱包数据
	 */
	List<UserWalletBO> findExcel(UserWalletVO userWalletVO);
}
