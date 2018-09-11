package com.hhly.cms.burypoint.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.burypoint.service.BuryPointService;
import com.hhly.cms.burypoint.vo.BpFunnelAnalyVO;
import com.hhly.cms.burypoint.vo.BpFunnelVO;
import com.hhly.cms.burypoint.vo.BpUserBaseInfoPO;
import com.hhly.cms.burypoint.vo.BpUserVO;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DicUtils;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @desc    埋点漏斗
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("bp/funnel")
@Controller
public class FunnelController extends BaseController {

	@Autowired
	private BuryPointService buryPointService;
	
//	@Autowired
//	private ExcelExportService excelExportService;
	
	@RequestMapping
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "burypoint/funnel";
	}
	
	@RequestMapping("analysis")
	@Authority(privilege = AuthEnum.SEARCH)
	public String analysis() {
		return "burypoint/funnel_analysis";
	}

	@RequestMapping(value="page",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<BpFunnelVO> page(BpFunnelVO vo){
		vo.setDelStatic(1);
		return buryPointService.pageFunnel(vo);
	}
	
	@RequestMapping(value="one",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public BpFunnelVO findOne(BpFunnelVO vo){
		vo.setDelStatic(1);
		return buryPointService.findOneFunnel(vo);
	}
	
	@RequestMapping(value="dic",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> dic(BpFunnelVO vo){
		vo.setDelStatic(1);
		List<BpFunnelVO> funnels = buryPointService.findFunnel(vo);
		return DicUtils.toDic(funnels, "id", Integer.class, "name", String.class);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public ResultBO<?> delete(@RequestBody BpFunnelVO vo) {
		int ret = buryPointService.deleteFunnel(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping(method={RequestMethod.PUT,RequestMethod.POST})
	@Authority(privilege={AuthEnum.ADD,AuthEnum.UPD})
	@ResponseBody
	public ResultBO<?> merge(BpFunnelVO vo, HttpSession session){
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
		int ret = buryPointService.mergeFunnel(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="user/page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<BpUserBaseInfoPO> pageUser(BpUserVO vo){
		return buryPointService.pageUser(vo);
	}
	
	@RequestMapping(value="analysis/search", method=RequestMethod.POST)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public String analysisSearch(@RequestBody BpFunnelAnalyVO vo) {
		return buryPointService.analyzeFunnel(vo);
	}
}
