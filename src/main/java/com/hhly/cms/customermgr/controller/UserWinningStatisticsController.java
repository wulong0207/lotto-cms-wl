package com.hhly.cms.customermgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.customermgr.service.UserWinningStatisticsService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.PropertyUtil;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.customermgr.vo.UserWinningStatisticsVO;

import jxl.read.biff.BiffException;
/**
 * 
 * @desc 用户中奖统计
 * @author jiangwei
 * @date 2017年4月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/customermgr/uws")
public class UserWinningStatisticsController extends BaseController{
	
	@Autowired
	private UserWinningStatisticsService service;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "customermgr/user_winning_statistics";
	}
	/**
	 * 分页查询结果
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 上午10:47:35
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	@ParameterValid
	public  Object list(@Valid("search")UserWinningStatisticsVO vo){
		return service.listUserWinningStatistics(vo);
	}
	
	/**
	 * 批量查询
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	@RequestMapping(value = "/beach/search")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object beachSearch(@RequestParam(value = "type", required = true)String type
			,@RequestParam(value = "content", required = true)String content){
		List<String> list = beachSearchCondition(content, type);
		UserWinningStatisticsVO vo =new UserWinningStatisticsVO();
		vo.setType(type);
		vo.setValues(list);
		vo.setPageIndex(0);
		vo.setPageSize(list.size());
		return getResultSuccess(service.listUserWinningStatistics(vo).getData());
	}
	/**
	 * 导出excel
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 上午11:04:36
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,@Valid("search")UserWinningStatisticsVO vo) throws IOException{
		ByteArrayOutputStream outputStream=service.listUserWinningStatisticsExcel(vo);
		excel("userWinningStatistics", outputStream, response);
	}
	
	/**
	 * 增量统计最新用户中奖信息
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/countnew")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object countHalfday() throws IOException, URISyntaxException{
		String url = PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"lotto_task_url");
		String str = HttpUtil.doGet(url+"userWinning/counthalfday");
		return getResultSuccess(str);
	}
}
