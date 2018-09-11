package com.hhly.cms.sportmgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sportmgr.service.MatchInfoService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchSourceInfoVO;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 赛事信息控制层
 * @date 2017/9/21
 * @company 益彩网络科技公司
 */
@Controller
@RequestMapping("/sport/matchInfo")
public class MatchInfoController extends BaseController {

    @Autowired
    private MatchInfoService matchInfoService;

    @RequestMapping()
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "sysmgr/baseData/sports/match_info";
    }

    @RequestMapping(value = "/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object queryMatch(SportMatchInfoVO vo) {
        return matchInfoService.findList(vo);
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
    public Object add(@RequestBody @Valid(GroupValue.ADD) SportMatchInfoVO vo, HttpSession session){
        return getSaveResult(matchInfoService.insertMatchInfo(vo));
    }

    @RequestMapping(value="/upd", method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public Object update(@RequestBody @Valid(GroupValue.UPD) SportMatchInfoVO vo,HttpSession session){
        vo.setModifyBy(getUserName(session));
        return getSaveResult(matchInfoService.updateMatchInfo(vo));
    }

    @RequestMapping(value="/del/{id}", method = RequestMethod.DELETE)
    @Authority(privilege=AuthEnum.DEL)
    @ResponseBody
    public Object del(@PathVariable(value = "id") String id){
        StringVO vo = new StringVO();
        vo.setStr(id);
        return getSaveResult(matchInfoService.deleteBatchMatchInfo(vo));
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Object uploadLogo() {
        return null;
    }
    
    @RequestMapping(value = "/source/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object queryMatch(SportMatchSourceInfoVO vo) {
        return matchInfoService.findList(vo);
    }
    

    @RequestMapping(value="/source/upd", method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public Object update(@RequestBody @Valid(GroupValue.UPD) SportMatchSourceInfoVO vo,HttpSession session){
        return getSaveResult(matchInfoService.updateMatchSourceInfo(vo));
    }
    
    @RequestMapping(value="/source/remove", method = RequestMethod.GET)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public Object remove(@RequestParam(value = "id", required = false) Integer id){
        return getSaveResult(matchInfoService.removeBind(id));
    }
    
    @RequestMapping(value="/source/del/{id}", method = RequestMethod.DELETE)
    @Authority(privilege=AuthEnum.DEL)
    @ResponseBody
    public Object delete(@PathVariable(value = "id") String id){
        StringVO vo = new StringVO();
        vo.setStr(id);
        matchInfoService.deleteBatchMatchSource(vo);
        return getResult(true);
    }    
}
