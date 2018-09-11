package com.hhly.cms.sysmgr.service.impl;

import com.hhly.cms.sysmgr.service.CMSUserService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cms.utils.MD5Util;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.sysmgr.bo.*;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserMenuVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgs on 2016/11/21.
 */
@Service
public class CMSUserServiceImpl implements CMSUserService{

    @Autowired
    private ISysMgrService iSysMgrService;
	
    @Override
    public PagingBO<?> findUserData(CMSUserVO vo)  {
        return iSysMgrService.findUserData(vo);
    }

    @Override
    public List<CMSUserAuthTreeBO> findUserAuthTree(CMSUserVO vo) {
        return iSysMgrService.findUserAuth(vo);
    }

    @Override
    public String findUserName(CMSUserVO vo) {
        return iSysMgrService.findUserName(vo);
    }

    @Override
    public CMSUserLoginBO login(CMSUserVO vo) {
        return iSysMgrService.login(vo);
    }

    @Override
    public List<CMSMenuBO> findMenuByUserId(Integer userId)  {
        return iSysMgrService.findMenuByUserId(userId);
    }

    @Override
    public Integer updateUserLastLoginTime(CMSUserVO vo) {
        return iSysMgrService.updateUserLastLoginTime(vo);
    }

    @Override
    public Integer saveUser(List<CMSRoleMenuTreeVO> treeList, CMSUserVO vo)  {
        List<CMSUserMenuVO> menuVOs = new ArrayList<CMSUserMenuVO>();
        getUserMenus(treeList,menuVOs);
        return iSysMgrService.saveUser(vo,menuVOs);
    }

    @Override
    public Integer delUser(Integer userId)  {
        return iSysMgrService.delUser(userId);
    }

    @Override
    public Integer updatePwd(CMSUserVO vo,String newPwd) {
        CMSUserLoginBO bo = login(vo);
        if(bo == null || bo.getUserId() == null){
            return null;
        }
        vo.setUserPassword(MD5Util.encrypt(newPwd));
        return iSysMgrService.updatePwd(vo);
    }


    /**
     * 获取CMSUserMenuVO
     * @param treeList
     * @param menuVOs
     */
    private void getUserMenus(List<CMSRoleMenuTreeVO> treeList,
                              List<CMSUserMenuVO> menuVOs){
        for(CMSRoleMenuTreeVO vo : treeList) {
            CMSUserMenuVO userMenuVO = new CMSUserMenuVO();
            userMenuVO.setMenuId(vo.getMenuId());
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
                userMenuVO.setUserAuth(button);
            }
            menuVOs.add(userMenuVO);

            if(vo.getChildren()!=null){
                getUserMenus(vo.getChildren(),menuVOs);
            }
        }
    }

    @Override
    public Integer findCount(CMSUserVO vo) {
        return iSysMgrService.findCount(vo);
    }

    /**
     * @desc  查询所有用户类型，以MiniUI数据字典的格式{id:1,text:'字典值'}返回 
     * @author Tony Wang
     * @create 2017年4月21日
     * @return 
     */
	@Override
	public List<DictionaryBO> findUserTypeAsDic() {
		List<CmsUserTypeBO> userTypes = iSysMgrService.findUserType();
		return DicUtils.toDic(userTypes, "id", Long.class, "name", String.class);
	}

	@Override
	public List<CMSUserBO> findUsers(CMSUserVO vo) {
		return iSysMgrService.findUsers(vo);
	}
    
    
}

