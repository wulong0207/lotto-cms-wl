package com.hhly.cms.sysmgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleVO;

import java.util.List;

/**
 * Created by lgs on 2016/11/16.
 * 角色管理service
 */
public interface CMSRoleService {

    /**
     * 分页查询
     * @param vo
     * @return
     */
    PagingBO<?> selectRoleData(CMSRoleVO vo) ;


    /**
     * 查询角色名称
     * @return
     */
    List<CMSRoleBO> findRoleName() ;

    /**
     * 根据角色id查询角色所属权限
     * @param roleId
     * @return
     */
    List<CMSRoleMenuBO> findAuthorityByRoleId(Integer roleId) ;

    /**
     * 保存修改的角色
     * @param treeList
     * @param form
     * @return
     */
    int saveRoleChange(List<CMSRoleMenuTreeVO> treeList , CMSRoleVO form);

    /**
     * 删除角色
     * @param vo
     * @return
     */
    int delete(CMSRoleVO vo);
}
