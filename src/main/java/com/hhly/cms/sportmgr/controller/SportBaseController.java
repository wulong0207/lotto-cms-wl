package com.hhly.cms.sportmgr.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.cms.sportmgr.bo.SportMatchInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportTeamInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamInfoVO;

/**
 * 体育基础参数查询
 * 
 * @desc
 * @author huangchengfang1219
 * @date 2017年9月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "sportmgr/base")
public class SportBaseController {

	@Autowired
	private ISportBallMgrService iSportBallMgrService;

	/**
	 * 查找赛事信息
	 * 
	 * @param sportMatchInfoVO
	 * @param response
	 * @return
	 */
	@RequestMapping("/findMatchInfo")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public List<SportMatchInfoBO> findMatchInfo(SportMatchInfoVO sportMatchInfoVO, HttpServletResponse response) {
		List<SportMatchInfoBO> infoList = iSportBallMgrService.findMatchLikeList(sportMatchInfoVO);
		if(infoList != null && infoList.size() > 50) {
			infoList = infoList.subList(0, 50);
		}
//		List<SportMatchInfoBO> resultList = new ArrayList<>();
//		for (SportMatchInfoBO sportMatchInfoBO : infoList) {
//			SportMatchInfoBO bo = new SportMatchInfoBO();
//			bo.setMatchFullName(sportMatchInfoBO.getMatchFullName());
//			bo.setMatchShortName(sportMatchInfoBO.getMatchShortName());
//			resultList.add(bo);
//		}
		return infoList;
	}

	/**
	 * 查找球队信息
	 * 
	 * @param sportMatchInfoVO
	 * @param response
	 * @return
	 */
	@RequestMapping("/findTeamInfo")
	@ResponseBody
	@Authority(privilege = AuthEnum.ALL)
	public List<SportTeamInfoBO> findTeamInfo(SportTeamInfoVO sportTeamInfoBVO, HttpServletResponse response){
		List<SportTeamInfoBO> infoList =iSportBallMgrService.findTeamLikeList(sportTeamInfoBVO);
		if(infoList != null && infoList.size() > 50) {
			infoList = infoList.subList(0, 50);
		}
//		List<SportTeamInfoBO> resultList = new ArrayList<>();
//		for (SportTeamInfoBO sportTeamInfoBO : infoList) {
//			SportTeamInfoBO bo = new SportTeamInfoBO();
//			bo.setTeamFullName(sportTeamInfoBO.getTeamFullName());
//			bo.setTeamShortName(sportTeamInfoBO.getTeamShortName());
//			resultList.add(bo);
//		}		
		return infoList;
	}

}
