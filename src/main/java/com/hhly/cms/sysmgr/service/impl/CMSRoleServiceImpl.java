package com.hhly.cms.sysmgr.service.impl;

import com.hhly.cms.sysmgr.service.CMSRoleService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.sysmgr.bo.ButtonsBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgs on 2016/11/16.
 */
@Service
public class CMSRoleServiceImpl implements CMSRoleService{

    @Autowired
    private ISysMgrService iSysMgrService;

    @Override
    public PagingBO<?> selectRoleData(CMSRoleVO vo) {
        return iSysMgrService.selectRoleData(vo);
    }

    @Override
    public List<CMSRoleBO> findRoleName() {
        return iSysMgrService.findRoleName();
    }

    @Override
    public List<CMSRoleMenuBO> findAuthorityByRoleId(Integer roleId) {
        return iSysMgrService.findAuthorityByRoleId(roleId);
    }

    @Override
    public int saveRoleChange(List<CMSRoleMenuTreeVO> treeList, CMSRoleVO form) {
        List<CMSRoleMenuVO> roleMenus = new ArrayList<CMSRoleMenuVO>();
        try {
            getRoleMenus(treeList,roleMenus);
            return iSysMgrService.saveRoleChange(roleMenus,form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(CMSRoleVO vo) {
        try {
            return iSysMgrService.deleteRole(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取CMSRoleMenuVO
     * @param treeList
     * @param roleMenus
     */
    private void getRoleMenus(List<CMSRoleMenuTreeVO> treeList,
                              List<CMSRoleMenuVO> roleMenus){
        for(CMSRoleMenuTreeVO vo : treeList) {
            CMSRoleMenuVO roleMenuVO = new CMSRoleMenuVO();
            roleMenuVO.setMenuId(vo.getMenuId());
            String checked = vo.getChecked();
            if(checked.equals("false")){
                continue;
            }
            List<ButtonsBO> bos = vo.getButtons();

            String button = "";
            if(bos != null){
                for (ButtonsBO bo : bos) {
                    if(bo.getChecked().equals("false")){
                        continue;
                    }
                    if(button==null||button.equals("")){
                        button=bo.getButton();
                    } else {
                        button += SymbolConstants.COMMA+bo.getButton();
                    }
                }
            }
            if(!button.equals("")) {
                roleMenuVO.setMenuButton(button);
            }
            roleMenus.add(roleMenuVO);

            if(vo.getChildren()!=null){
                getRoleMenus(vo.getChildren(),roleMenus);
            }
        }
    }
}
