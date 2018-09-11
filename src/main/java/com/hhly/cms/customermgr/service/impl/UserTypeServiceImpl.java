package com.hhly.cms.customermgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.customermgr.service.UserTypeService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.UserTypeBO;
import com.hhly.skeleton.cms.customermgr.bo.UserTypeExcelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserTypeVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年4月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	ICustomerMgrService iCustomerMgrService;
	
	/**
	 * 导出服务
	 */
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public int addUserType(UserTypeVO vo) {
		return iCustomerMgrService.addUserType(vo);
	}

	@Override
	public int delUserTypeByIds(StringVO vo) {
		return iCustomerMgrService.delUserTypeByIds(vo);
	}

	@Override
	public PagingBO<UserTypeBO> findUserTypeList(UserTypeVO vo) {
		return iCustomerMgrService.findUserTypeList(vo);
	}

	@Override
	public int updateUserTypeById(UserTypeVO vo) {
		return iCustomerMgrService.updateUserTypeById(vo);
	}

	@Override
	public List<DictionaryBO> findBaseUserType() {
		List<UserTypeBO> list = iCustomerMgrService.findBaseUserType();
		return DicUtils.toDic(list, "id", Integer.class, "name", String.class);
	}

	@Override
	public ByteArrayOutputStream findExcel(Integer typeId) {
		List<UserTypeExcelBO> list = iCustomerMgrService.findExcelByTypeId(typeId);
		return excelExportService.dataToExeclByStream("user type", list);		
	}

	@Override
	public int valiUserTypeCode(String code) {
		return iCustomerMgrService.valiUserTypeCode(code);
	}

	@Override
	public void addTypeToUser(String typeCode) {
		iCustomerMgrService.addTypeToUser(typeCode);
	}
	
	
	
}
