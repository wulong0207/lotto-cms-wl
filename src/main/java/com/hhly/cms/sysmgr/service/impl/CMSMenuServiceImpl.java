package com.hhly.cms.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.sysmgr.service.CMSMenuService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.cms.sysmgr.bo.CMSMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSMenuVO;

/**
 * Created by lgs on 2016/11/10.
 * 菜单管理Service
 */
@Service
public class CMSMenuServiceImpl implements CMSMenuService {

    @Autowired
    private ISysMgrService iSysMgrService;

    @Override
    public List<CMSMenuBO> selectAll()  {
        List<CMSMenuBO> results = iSysMgrService.selectAll();
        return results;
    }


    @Override
    public boolean saveMenuTree(List<CMSMenuVO> list, List<Integer> delId,String by) {
        List<CMSMenuBO> insertArr = new ArrayList<CMSMenuBO>();
        List<CMSMenuBO> updateArr = new ArrayList<CMSMenuBO>();
        List<Integer> deletelArr = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            CMSMenuVO vo = list.get(i);
            Integer id = vo.getMenuId() == null ? null : vo.getMenuId();
            Integer parentMenuId = vo.getParentMenuId();
            String menuTitle = vo.getMenuTitle();
            Integer menuLevel = vo.getMenuLevel();
            Integer orderId = vo.getOrderId() == null ? 0 : vo.getOrderId();
            String menuUrl = vo.getMenuUrl() == null ? "" : vo.getMenuUrl();
            String menuButton = vo.getMenuButton() == null ? "" : vo.getMenuButton();

            CMSMenuBO bo = new CMSMenuBO();
            bo.setMenuTitle(menuTitle);
            bo.setParentMenuId(parentMenuId);
            bo.setMenuUrl(menuUrl);
            bo.setMenuButton(menuButton);
            bo.setMenuLevel(menuLevel);
            bo.setOrderId(orderId);

            try {
                if (id != null && id != 0) {
                    bo.setMenuId(id);
                    bo.setModifyBy(by);
                    bo.setUpdateTime(new Date());
                    bo.setModifyTime(new Date());
                    updateArr.add(bo);
                } else {
                    bo.setCreateBy(by);
                    bo.setModifyBy(by);
                    bo.setUpdateTime(new Date());
                    bo.setCreateTime(new Date());
                    insertArr.add(bo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
        for (int id : delId) {
            try {
                deletelArr.add(id);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        int flag = iSysMgrService.updateCmsMenu(updateArr, insertArr, deletelArr);
        if(flag < 1){
            return false;
        }
        return true;
    }


	/**
	 * @desc   根据条件查询菜单，以数据字典形式返回
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	@Override
	public List<DictionaryBO> findAsDic(CMSMenuVO vo) {
		List<CMSMenuBO> menus = iSysMgrService.findMenus(vo);
		Assert.notEmpty(menus, "菜单为空");
		return DicUtils.toDic(menus, "menuId", Integer.class, "menuTitle", String.class);
	}
}
