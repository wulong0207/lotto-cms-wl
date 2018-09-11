package com.hhly.cms.sysmgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSMenuBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserAuthTreeBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserLoginBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;

/**
 * Created by lgs on 2016/11/21.
 */
public interface CMSUserService {

    /**
     * 查询用户数据
     * @param vo
     * @return
     */
    PagingBO<?> findUserData(CMSUserVO vo) ;

    /**
     * 查询用户权限树
     * @param vo
     * @return
     * @throws Exception
     */
    List<CMSUserAuthTreeBO> findUserAuthTree(CMSUserVO vo) ;


    /**
     * 查询用户id
     * @param vo
     * @return
     */
    String findUserName(CMSUserVO vo) ;

    /**
     * 用户登录
     * @param vo
     * @return
     */
    CMSUserLoginBO login(CMSUserVO vo) ;

    /**
     * 根据userid查询用户id
     * @param userId
     * @return
     */
    List<CMSMenuBO> findMenuByUserId(Integer userId);


    /**
     * 更新用户最后登录时间
     * @param vo
     * @return
     */
    Integer updateUserLastLoginTime(CMSUserVO vo);

    /**
     * 保存用户
     * @param treeList
     * @param vo
     * @return
     * @throws Exception
     */
    Integer saveUser(List<CMSRoleMenuTreeVO> treeList,CMSUserVO vo);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Integer delUser(Integer userId);

    /**
     * 修改密码
     * @param vo
     * @return
     */
    Integer updatePwd(CMSUserVO vo,String newPwd);

    /**
     * 查询用户id
     * @param vo
     * @return
     */
    Integer findCount(CMSUserVO vo);

    /**
     * @desc  查询所有用户类型，以MiniUI数据字典的格式{id:1,text:'字典值'}返回 
     * @author Tony Wang
     * @create 2017年4月21日
     * @return 
     */
    List<DictionaryBO> findUserTypeAsDic();
    
	/**
	 * @desc   根据条件查询用户
	 * @author Tony Wang
	 * @create 2017年6月9日
	 * @return 
	 */
	List<CMSUserBO> findUsers(CMSUserVO vo);
}
