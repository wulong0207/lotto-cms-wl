package com.hhly.cms.customermgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.customermgr.service.UserWalletService;
import com.hhly.cmscore.cms.remote.service.IUserWalletMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWalletBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWalletExcelCmsBO;
import com.hhly.skeleton.cms.customermgr.vo.UserWalletVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 会员钱包管理服务
 * @author huangb
 * @date 2017年2月8日
 * @company 益彩网络
 * @version v1.0
 */
@Service
public class UserWalletServiceImpl implements UserWalletService {

	/**
	 * 用户钱包远程服务
	 */
	@Autowired
	private IUserWalletMgrService iUserWalletMgrService;

	/**
	 * 导出服务
	 */
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<UserWalletBO> findPaging(UserWalletVO userWalletVO) {
		return iUserWalletMgrService.findPaging(userWalletVO);
	}

	@Override
	public int updWalletStatusByIds(String ids, Short status) {
		return iUserWalletMgrService.updWalletStatusByIds(ids, status);
	}

	@Override
	public ByteArrayOutputStream findExcel(UserWalletVO userWalletVO) {
		List<UserWalletBO> data = iUserWalletMgrService.findExcel(userWalletVO);
		List<UserWalletExcelCmsBO> targetList = new ArrayList<UserWalletExcelCmsBO>();
		UserWalletExcelCmsBO target = null;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				target = new UserWalletExcelCmsBO(i + 1, data.get(i));
				targetList.add(target);
			}
		}
		return excelExportService.dataToExeclByStream("user wallet", targetList);
	}
}
