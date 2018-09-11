package com.hhly.cms.sysmgr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sysmgr.service.CMSMenuService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSMenuVO;

/**
 * Created by lgs on 2016/11/8.
 * 菜单管理Controller
 */
@Controller
@RequestMapping(value = "/sysmgr/menu")
public class MenuMgrController extends BaseController{

    @Autowired
    private CMSMenuService cmsMenuService;

    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    public String index(){
        return "sysmgr/menu";
    }

    @RequestMapping(value="/list",method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege= AuthEnum.SEARCH)
    public List<CMSMenuBO> list() throws Exception {
        return cmsMenuService.selectAll();
    }
    
    @RequestMapping(value="/dic",method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege= AuthEnum.SEARCH)
    public List<DictionaryBO> findAsDic(CMSMenuVO vo) {
    	return cmsMenuService.findAsDic(vo);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Authority(privilege=AuthEnum.SAVE)
    public Object save(@RequestParam(value = "treeJson", required = true) String treeJson,
                       @RequestParam(value = "idJson", required = false, defaultValue = "") String idJson,
                       HttpSession session) throws Exception {
        List<CMSMenuTreeVO> treeList = JSON.parseArray(treeJson,CMSMenuTreeVO.class);
        List<Integer> dId = JSON.parseArray(idJson, Integer.class);
        List<CMSMenuVO> vos = new ArrayList<CMSMenuVO>();
        getTreeToVO(treeList,vos);
        String by = (String) session.getAttribute(WebConstant.USERNAME);
        return returnMsg(cmsMenuService.saveMenuTree(vos,dId,by));
    }

    /**
     * 转换树
     * @param treeList
     * @return
     */
    private List<CMSMenuVO> getTreeToVO(List<CMSMenuTreeVO> treeList,List<CMSMenuVO> vos){
        for (CMSMenuTreeVO param : treeList) {
            CMSMenuVO vo = new CMSMenuVO();
            vo.setMenuId(param.getMenuId());
            vo.setMenuTitle(param.getMenuTitle());
            vo.setParentMenuId(param.getParentMenuId());
            vo.setMenuUrl(param.getMenuUrl());
            vo.setMenuButton(param.getMenuButton());
            vo.setMenuDesc(param.getMenuDesc());
            vo.setMenuLevel(param.getMenuLevel());
            vo.setOrderId(param.getOrderId());
            vos.add(vo);
            if(param.getChildren() != null && param.getChildren().size() > 0){
                getTreeToVO(param.getChildren(),vos);
            }
        }

        return vos;
    }
}
