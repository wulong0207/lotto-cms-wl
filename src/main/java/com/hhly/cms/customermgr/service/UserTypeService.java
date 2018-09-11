package com.hhly.cms.customermgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.UserTypeBO;
import com.hhly.skeleton.cms.customermgr.vo.UserTypeVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年4月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserTypeService {
	
	/**
	 * 添加会员类型
	 * @param vo
	 * @return
	 */
	int addUserType(UserTypeVO vo);
	
	/**
	 * 会员添加会员类型标签
	 * @param typeCode
	 * @return
	 */
	void addTypeToUser(String typeCode);
	
	/**
	 * 根据会员类型id删除
	 * @param vo
	 * @return
	 */
	int delUserTypeByIds(StringVO vo);
	
	/**
	 * 会员类型分页查询
	 * @param vo
	 * @return
	 */
	PagingBO<UserTypeBO> findUserTypeList(UserTypeVO vo);
	
	/**
	 * 根据会员类型id修改信息
	 * @param vo
	 * @return
	 */
	int updateUserTypeById(UserTypeVO vo);
	
	/**
	 * 查询用户时间类型
	 * @return
	 */
	List<DictionaryBO> findBaseUserType();
	
	/**
	 * 通过类型查询用户信息
	 * @return
	 */
	ByteArrayOutputStream findExcel(Integer typeId);
	
	/**
	 * 验证用户类型编码唯一
	 */
	int valiUserTypeCode(String code);
}
