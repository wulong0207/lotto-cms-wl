package com.hhly.cms.sysmgr.controller;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.sysmgr.service.CMSRoleService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleBO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSRoleMenuBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleMenuTreeVO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSRoleVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lgs on 2016/11/8.
 * 角色管理Controller
 */
@Controller
@RequestMapping(value = "/sysmgr/role")
public class RoleMgrController extends BaseController{

    public static final Logger LOGGER = Logger.getLogger(RoleMgrController.class);

    @Autowired
    private CMSRoleService cmsRoleService;

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    public String index(){
        return "sysmgr/role";
    }


    @RequestMapping(value="/list",method = RequestMethod.GET)
    @Authority(privilege={AuthEnum.SEARCH})
    @ResponseBody
    public PagingBO<?> list(CMSRoleVO cmsRoleVO){
        PagingBO<?> bo = null;
        try{
            bo = cmsRoleService.selectRoleData(cmsRoleVO);
        }catch (Exception e){
            LOGGER.error("RoleMgrController::::::::method::list:::::"+e.getMessage()+"::param::"+ JsonUtil.object2Json(cmsRoleVO));
        }
        return bo;
    }


    @RequestMapping(value="/dic/list",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.ALL)
    @ResponseBody
    public List<DicDataDetailBO> findDic(@RequestParam(value = "code", required = true) String code){
        List<DicDataDetailBO>  result = null;
        StringVO vo = new StringVO();
        vo.setStr(code);
        try {
            result = dictionaryService.findDetail(vo);
        } catch (Exception e) {
            LOGGER.error("RoleMgrController::::::::method::dic/findDic"+e.getMessage()+"::param::"+ JsonUtil.object2Json(vo));
        }
        return result;
    }

    @RequestMapping(value="/rolenames",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.ALL)
    @ResponseBody
    public List<CMSRoleBO> findRoles(){
        List<CMSRoleBO> bos = null;
        try {
            bos = cmsRoleService.findRoleName();
        } catch (Exception e) {
            LOGGER.error("RoleMgrController::::::::method:findRoles:rolenames"+e.getMessage());
        }
        return bos;
    }


    @RequestMapping(value="/menus",method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.ALL)
    @ResponseBody
    public List<CMSRoleMenuBO> list(@RequestParam(value = "roleId",required = true, defaultValue = "0") Integer roleId){
        List<CMSRoleMenuBO> bos = null;
        try {
            bos = cmsRoleService.findAuthorityByRoleId(roleId);
        } catch (Exception e) {
            LOGGER.error("RoleMgrController::::::::method:list:menus"+e.getMessage());
        }
        return bos;
    }


    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.SAVE)
    @ResponseBody
    public Object save(@RequestParam(value = "treeJson" ,required = true)  String treeJson ,
                       @RequestParam(value = "form" ,required = true) String form,
                       HttpSession session){
        List<CMSRoleMenuTreeVO> treeList = JSON.parseArray(treeJson,CMSRoleMenuTreeVO.class);
        CMSRoleVO vo = JSON.parseObject(form,CMSRoleVO.class);
        String createBy = (String) session.getAttribute(WebConstant.USERNAME);
        vo.setCreateBy(createBy);
        vo.setModifyBy(createBy);
        return cmsRoleService.saveRoleChange(treeList,vo);
    }

    @RequestMapping(value="/{roleId}" , method = RequestMethod.DELETE)
    @Authority(privilege=AuthEnum.DEL)
    @ResponseBody
    public Object delete(@PathVariable Integer roleId){
        CMSRoleVO vo = new CMSRoleVO();
        vo.setRoleId(roleId);
        return cmsRoleService.delete(vo);
    }
}
