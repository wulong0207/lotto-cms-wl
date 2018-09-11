package com.hhly.cms.burypoint.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.burypoint.service.BuryPointService;
import com.hhly.cms.burypoint.vo.BpButtonVO;
import com.hhly.cms.burypoint.vo.BpModeVO;
import com.hhly.cms.burypoint.vo.BpPageVO;
import com.hhly.cms.burypoint.vo.BpPointVO;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DicUtils;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc    埋点
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("bp/point")
@Controller
public class PointController extends BaseController {

	@Autowired
	private BuryPointService buryPointService;
	
//	@Autowired
//	private ExcelExportService excelExportService;
	
	@RequestMapping(method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "burypoint/point";
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public ResultBO<?> delete(@RequestBody BpPointVO vo) {
		int ret = buryPointService.deletePoint(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="posititon", method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public ResultBO<?> deletePostiton(@RequestParam Integer pageId,@RequestParam Integer modeId,@RequestParam Integer buttonId) {
		//int ret = buryPointService.deletePoint(vo);
		int ret=0;
		if(buttonId != null) {
			BpButtonVO vo = new BpButtonVO();
			vo.setId(buttonId);
			ret = buryPointService.deleteButton(vo);
		} else if(modeId != null) {
			BpModeVO vo = new BpModeVO();
			vo.setId(modeId);
			ret = buryPointService.deleteMode(vo);
		} else if(pageId != null) {
			BpPageVO vo = new BpPageVO();
			vo.setId(pageId);
			ret = buryPointService.deletePage(vo);
		}
		return getSaveResult(ret);
	}
	/**
	 * @desc   添加埋点
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public ResultBO<?> add(BpPointVO vo, HttpSession session){
		// 删除状态:	1:启用;0:已删除;
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
		int ret = buryPointService.addPoint(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping("add")
	@Authority(privilege = AuthEnum.SEARCH)
	public String toAdd() {
		return "burypoint/point_add";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<BpPointVO> page(BpPointVO vo){
		return buryPointService.pagePoint(vo);
	}
	
	@RequestMapping(value="dic",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<JSONObject> dic(BpPointVO vo){
		List<BpPointVO> points = buryPointService.findPoint(vo);
		// 加上埋点编号'访问（2013351）'
		List<JSONObject> dics = new ArrayList<>();
		JSONObject dic;
		for(BpPointVO point : points) {
			dic = new JSONObject();
			dic.put("id",point.getCode());
			dic.put("text", String.format("%s(%d)",point.getName(),point.getCode()));
			dics.add(dic);
		}
		return dics;
		//return DicUtils.toDic(points, "code", Integer.class, "name", String.class);
	}
	
	/**
	 * @desc   以数据字典的形式显示埋点的页面信息
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value="pointpage/dic")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> findPageAsDic(BpPageVO vo){
		List<BpPageVO> pages = buryPointService.findPage(vo);
		// 只显示启用的页面信息
		return DicUtils.toDic(pages, "id", Integer.class, "name", String.class);
	}
	
	/**
	 * @desc   添加页面
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="pointpage",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public ResultBO<?> addPage(BpPageVO vo, HttpSession session){
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
		int ret = buryPointService.addPage(vo);
		return getSaveResult(ret);
	}
	
	/**
	 * @desc   添加板块
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="mode",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public ResultBO<?> addMode(BpModeVO vo, HttpSession session){
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
		int ret = buryPointService.addMode(vo);
		return getSaveResult(ret);
	}
	
	/**
	 * @desc   添加按钮
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="button",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public ResultBO<?> addButton(BpButtonVO vo, HttpSession session){
		// 删除状态:	1:启用;0:已删除;
		String realName = getUserRealName(session);
		vo.setModifyBy(realName);
//		vo.setDelStatic(1);
//		vo.setCreateTime(new Date());
		int ret = buryPointService.addButton(vo);
		// 只显示启用的页面信息
		return getSaveResult(ret);
	}
	
	/**
	 * @desc   查询板块
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="mode/dic",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> findModeAsDic(BpModeVO vo){
		List<BpModeVO> modes = buryPointService.findMode(vo);
		return DicUtils.toDic(modes, "id", Integer.class, "name", String.class);
	}
	
	/**
	 * @desc   查询按钮
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="button/dic",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> findButtonAsDic(BpButtonVO vo){
		List<BpButtonVO> buttons = buryPointService.findButton(vo);
		return DicUtils.toDic(buttons, "id", Integer.class, "name", String.class);
	}
}
