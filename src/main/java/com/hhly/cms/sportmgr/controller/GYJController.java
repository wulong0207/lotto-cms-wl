package com.hhly.cms.sportmgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sportmgr.service.GjLotteryService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportAgainstInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @desc    冠军对阵、冠亚军对阵
 * @author  Tony Wang
 * @date    2018年3月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("lotterymgr")
@Controller
public class GYJController extends BaseController {

	@Autowired
	private GjLotteryService gjLotteryService;

	@RequestMapping(value = "gyj",method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String gj() {
		return "lotterymgr/gyj";
	}

	@RequestMapping(value = "gyj/page",method=RequestMethod.POST)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<SportAgainstInfoBO> pageGj(SMGLotteryParamVO vo) {
		PagingBO<SportAgainstInfoBO> page = gjLotteryService.pageGj(vo);
		if(page.getTotal()>0) {
			page.getData().stream().forEach(agains -> {
				if(StringUtils.hasText(agains.getVisitName())) {
					agains.setHomeName(agains.getHomeName()+"-"+agains.getVisitName());
					agains.setVisitName(null);
				}
			});
		}
		return page;
	}

	@RequestMapping(value = "gyj",method=RequestMethod.PUT)
	@Authority(privilege = AuthEnum.SAVE)
	@ResponseBody
	public int update(SportDataBaseVO vo, HttpSession session) {
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
		vo.setModifyTime(new Date());
		return gjLotteryService.update(vo);
	}
}
