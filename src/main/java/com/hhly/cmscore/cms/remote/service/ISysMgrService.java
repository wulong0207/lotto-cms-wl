package com.hhly.cmscore.cms.remote.service;


import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataBO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataDetailVO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataVO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSMenuBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleMenuBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserAuthTreeBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserLoginBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsKeywordBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsUserLogBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsUserTypeBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSMenuVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserMenuVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsKeywordVO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsUserLogVO;
import com.hhly.skeleton.cms.sysmgr.vo.PayBankSegmentVO;

/**
 * Created by lgs on 2016/11/11.
 * 系统管理hessian接口
 */
public interface ISysMgrService {

    /*菜单管理begin*/

    /**
     * 查询所有菜单
     * @return
     */
    List<CMSMenuBO> selectAll();

	/**
	 * 根据userid查询用户id
	 * @param userId
	 * @return
     */
	List<CMSMenuBO> findMenuByUserId(Integer userId);

	/**
	 * 更新菜单
	 * @param updBo
	 * @param insBo
	 * @param ids
     * @return
     */
	int updateCmsMenu(List<CMSMenuBO> updBo , List<CMSMenuBO> insBo , List<Integer> ids);
	
	/**
	 * @desc   根据条件查询菜单
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	List<CMSMenuBO> findMenus(CMSMenuVO vo);

	/*菜单管理end*/

	/*角色管理begin*/

	/**
	 * 查询角色数据
	 * @param cmsRoleVO
	 * @return
     */
	PagingBO<?> selectRoleData(CMSRoleVO cmsRoleVO);

	/**
	 * 查询角色名称
	 * @return
     */
	List<CMSRoleBO> findRoleName();


	/**
	 * 根据角色id查询角色所属权限
	 * @param roleId
	 * @return
	 */
	List<CMSRoleMenuBO> findAuthorityByRoleId(Integer roleId);

	/**
	 * 保存角色的权限改变
	 * @param roleMenus
	 * @param vo
     * @return
     */
	int saveRoleChange(List<CMSRoleMenuVO> roleMenus, CMSRoleVO vo);

	/**
	 * 删除角色
	 * @param vo
	 * @return
	 */
	int deleteRole(CMSRoleVO vo) ;
	/*角色管理end*/

	/*用户管理begin*/

	/**
	 * 查询用户分页数据
	 * @param vo
	 * @return
     */
	PagingBO<?> findUserData(CMSUserVO vo);

	/**
	 * 查询用户权限树
	 * @param vo
	 * @return
     */
	List<CMSUserAuthTreeBO> findUserAuth(CMSUserVO vo);

	/**
	 * 保存用户
	 * @param vo
	 * @param userMenuVOs
     * @return
     */
	Integer saveUser(CMSUserVO vo,List<CMSUserMenuVO> userMenuVOs);

	/**
	 * 删除用户
	 * @param userId
	 * @return
     */
	Integer delUser(Integer userId);


	/**
	 * 查询用户id
	 * @param vo
	 * @return
	 */
	String findUserName(CMSUserVO vo);

	/**
	 * 查询用户id
	 * @param vo
	 * @return
	 */
	Integer findCount(CMSUserVO vo);

	/**
	 * 修改密码
	 * @param vo
	 * @return
     */
	Integer updatePwd(CMSUserVO vo);
	/*用户管理end*/

	/*用户登录begin*/
	CMSUserLoginBO login(CMSUserVO vo);

	/**
	 * 更新用户最后登录时间
	 * @param vo
	 * @return
     */
	Integer updateUserLastLoginTime(CMSUserVO vo);
	/*用户登录end*/

    /**
     * 分析查询字典
     * @param dicDataVO
     * @return
     */
    PagingBO<DicDataBO> findDicData(DicDataVO dicDataVO);
    /**
     * 添加字典
     * @param dicDataVO
     * @return
     */
    int addDicData(DicDataVO dicDataVO);
    /**
     * 修改字典（字典编号不允许修改）
     * @param dicDataVO
     * @return
     */
	int updDicData(DicDataVO dicDataVO);
	/**
	 * 查询字典详情
	 * @param vo
	 * @return
	 */
	List<DicDataDetailBO> findDicDataDetail(StringVO vo);

	/**
	 * 查询字典详情-带分页
	 * @param vo
	 * @return
	 */
	PagingBO<DicDataDetailBO> findDicDataDetails(DicDataDetailVO vo);

    /**
     * 删除字典详情（多个id用“_”分开）
     * @param vo
     * @return
     */
	int delDicDataDetail(StringVO vo);
    /**
     * 修改字典详情
     * @param vo
     * @return
     */
	int updDicDataDetail(DicDataDetailVO vo);
    /**
     * 添加字典详情
     * @param vo
     * @return
     */
	int addDicDataDetail(DicDataDetailVO vo);
	/**
	 * 查询有效的字典详情信息，用于其他页面下拉框
	 * @param vo
	 * @return
	 */
	List<DicDataDetailBO> findDicDataDetailSimple(StringVO vo);
	
	/**
	 * 
	 * @Desc 批量新增敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
	int addKeywordList(List<CmsKeywordVO> list);
	/**
	 * 
	 * @Desc 敏感词分页列表
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
    PagingBO<CmsKeywordBO> findKeyword(CmsKeywordVO vo);
    /**
	 * 
	 * @Desc 批量更新敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
    int updateKeywordList(List<CmsKeywordVO> list);
    /**
	 * 
	 * @Desc 导出excel敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
    List<CmsKeywordBO> keywordExcelList(CmsKeywordVO vo);

    /**
     * @desc  查询所有用户类型
     * @author Tony Wang
     * @create 2017年4月21日
     * @return 
     */
	List<CmsUserTypeBO> findUserType();
	
	/**
	 * @desc   根据条件查询用户
	 * @author Tony Wang
	 * @create 2017年6月9日
	 * @return 
	 */
	List<CMSUserBO> findUsers(CMSUserVO vo);
	
	/***************************用户操作日志相关 start*************************************************/
	/**
	 * @desc   添加操作日志
	 * @author Tony Wang
	 * @create 2017年5月12日
	 * @param vo
	 * @return 
	 */
	int addUserLog(CmsUserLogVO vo);
	
	/**
	 * @desc   查询操作日志列表
	 * @author Tony Wang
	 * @create 2017年5月15日
	 * @param vo
	 * @return 
	 */
	PagingBO<CmsUserLogBO> listUserLog(CmsUserLogVO vo);
	
	/**
	 * @desc   根据条件查询操作日志
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	CmsUserLogBO findUserLog(CmsUserLogVO vo);
	/***************************用户操作日志相关 end *************************************************/

	/***************************银行卡BIN码管理表 start *************************************************/
    PagingBO<PayBankSegmentVO> pagePayBankSegment(PayBankSegmentVO vo);

	int mergePayBankSegment(PayBankSegmentVO vo);
	/***************************银行卡BIN码管理表 end *************************************************/

	/**
	 * 
	 * @Description 推单分析师级别 
	 * @author HouXiangBao289
	 * @return
	 */
	List<DictionaryBO> findRcmdUserLevel();
}
