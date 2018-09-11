package com.hhly.cms.sysmgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSMenuVO;

/**
 * Created by lgs on 2016/11/10.
 */
public interface CMSMenuService {

    /**
     * 查询所有菜单
     * @return
     */
    List<CMSMenuBO> selectAll();


    /**
     * 更新菜单
     * @param list
     * @param delId
     * @return
     */
    boolean saveMenuTree(List<CMSMenuVO> list, List<Integer> delId,String by);


	/**
	 * @desc   根据条件查询菜单，以数据字典形式返回
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	List<DictionaryBO> findAsDic(CMSMenuVO vo);
}
