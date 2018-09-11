package com.hhly.cms.operatemgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.UserAnalysisService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.MatchPattern;
import com.hhly.skeleton.cms.operatemgr.vo.OperateUserAnalysisVO;

/**
 * @desc 用户访问信息分析控制
 * @author huangb
 * @date 2017年2月10日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/operatemgr/useranalysis")
public class UserAnalysisController extends BaseController {

	/**
	 * 用户访问信息分析服务
	 */
	@Autowired
	private UserAnalysisService userAnalysisService;
	/**
	 * 上传查询的手机号选项字段标识
	 */
	private final String CUS_MOBILE = "cus_mobile";

	/**
	 * @desc 跳转到用户访问信息分析页
	 * @author huangb
	 * @date 2017年2月10日
	 * @return 用户访问信息分析页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "operatemgr/user_analysis";
	}

	/**
	 * @desc 查询分页数据列表
	 * @author huangb
	 * @date 2017年2月10日
	 * @param operateUserAnalysis
	 * @return 分页数据列表
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OperateUserAnalysisVO operateUserAnalysis) {
		return userAnalysisService.findPaging(operateUserAnalysis);
	}

	/**
	 * @desc 导出excel
	 * @author huangb
	 * @date 2017年2月10日
	 * @param response
	 * @param operateUserAnalysis
	 *            参数对象
	 * @throws IOException
	 */
	@RequestMapping("/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportExcel(HttpServletResponse response, OperateUserAnalysisVO operateUserAnalysis)
			throws IOException {
		ByteArrayOutputStream outputStream = userAnalysisService.findExcel(operateUserAnalysis);
		excel("user_analysis", outputStream, response);
	}

	/**
	 * @desc 上传文件查询
	 * @author huangb
	 * @date 2017年2月9日
	 * @param file
	 * @param type
	 * @return 上传文件查询结果
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload/search")
	@Authority(privilege = AuthEnum.UPLOAD)
	public @ResponseBody Object uploadSearch(@RequestParam MultipartFile file, @RequestParam("type") String type)
			throws IOException {
		// 1.解析文件
		List<String> list = analysisFile(file);
		if (list.isEmpty() || StringUtil.isBlank(type)) {
			throw new ResultJsonException("参数错误！");
		}
		// 2.如果是选手机号查询，则验证下手机号格式
		if (CUS_MOBILE.equals(type)) {
			for (String str : list) {
				if (!str.matches(MatchPattern.NUM)) {
					throw new ResultJsonException("参数错误！手机号必须是纯数字");
				}
			}
		}
		// 3.一页显示上传查询的数据
		OperateUserAnalysisVO tempVO = new OperateUserAnalysisVO();
		tempVO.setTypeField(type);
		tempVO.setJoinUser(true);// 关联用户表查询
		tempVO.setTypeValues(list);
		tempVO.setPageIndex(Constants.NUM_0);
		tempVO.setPageSize(list.size());
		ResultBO<?> result = getResultSuccess(userAnalysisService.findPaging(tempVO).getData());
		return JsonUtil.object2Json(result, DateUtil.DEFAULT_FORMAT);
	}
}
