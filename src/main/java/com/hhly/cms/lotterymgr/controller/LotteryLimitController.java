package com.hhly.cms.lotterymgr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.lotterymgr.service.LotteryLimitService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.cache.lottery.LotteryCacheEnum.LotteryLimitCacheEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitInfoVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitVO;

/**
 * @desc 彩种限号管理控制
 * @author huangb
 * @date 2017年2月15日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/lotterymgr/limit")
public class LotteryLimitController extends BaseController {

	/**
	 * 限号管理服务
	 */
	@Autowired
	private LotteryLimitService lotteryLimitService;

	/**
	 * @return
	 * @Desc 跳转到限号管理页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "lotterymgr/lottery_limit";
	}

	/**
	 * @desc 分页查询：限号查询
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 分页查询：限号查询
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(LotteryLimitVO lotteryLimitVO) {
		return lotteryLimitService.findPagingLimit(lotteryLimitVO);
	}

	/**
	 * @desc 详情查询：限号查询
	 * @author huangb
	 * @date 2017年2月15日
	 * @param id
	 *            数据编号
	 * @return 详情查询：限号查询
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object detail(@PathVariable(value = "id") Integer id) {
		return getResultSuccess(lotteryLimitService.findSingleLimit(new LotteryLimitVO(id)));
	}

	/**
	 * @desc 添加：限号数据
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @param session
	 *            会话
	 * @return 添加：限号数据
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryLimitCacheEnum.class)
	public Object add(@Valid(GroupValue.ADD) LotteryLimitVO lotteryLimitVO, HttpSession session) {
		// 断言验证
		assertValidation(lotteryLimitVO);
		String userName = getUserName(session);
		lotteryLimitVO.setCreateBy(userName);
		lotteryLimitVO.setModifyBy(userName);
		int num = lotteryLimitService.addLimit(lotteryLimitVO);
		return getResult(num > Constants.NUM_0);
	}

	/**
	 * @desc 修改：限号数据
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @param session
	 *            会话
	 * @return 修改：限号数据
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryLimitCacheEnum.class)
	public Object update(@Valid(GroupValue.UPD) LotteryLimitVO lotteryLimitVO, HttpSession session) {
		// 断言验证
		assertValidation(lotteryLimitVO);
		lotteryLimitVO.setModifyBy(getUserName(session));
		int num = lotteryLimitService.updLimit(lotteryLimitVO);
		return getResult(num > Constants.NUM_0);
	}
	
	/**
	 * @desc 断言验证参数对象
	 * @author huangb
	 * @date 2017年2月28日
	 * @param lotteryLimitVO
	 *            参数对象
	 */
	private void assertValidation(LotteryLimitVO lotteryLimitVO) {
		if (!StringUtil.isBlank(lotteryLimitVO.getIssueStart()) && !StringUtil.isBlank(lotteryLimitVO.getIssueEnd())) {
			Assert.isTrue(lotteryLimitVO.getIssueEnd().compareTo(lotteryLimitVO.getIssueStart()) >= 0, "限号彩期的结束彩期不能小于开始彩期！");
		}
		if (lotteryLimitVO.getTimeStart() != null && lotteryLimitVO.getTimeEnd() != null) {
			Assert.isTrue(lotteryLimitVO.getTimeEnd().compareTo(lotteryLimitVO.getTimeStart()) >= 0, "限号时间的结束时间不能小于开始时间！");
		}
	}
	/******************* 限号内容相关操作 ********************/
	/**
	 * @desc 分页查询：限号内容查询
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 分页查询：限号内容查询
	 */
	@RequestMapping(value = "/info/list")
	@Authority(privilege = AuthEnum.SEARCH)
	public @ResponseBody Object limitInfoList(LotteryLimitInfoVO lotteryLimitInfoVO) {
		return lotteryLimitService.findPagingLimitInfo(lotteryLimitInfoVO);
	}

	/**
	 * @desc 详情查询：限号内容查询
	 * @author huangb
	 * @date 2017年2月15日
	 * @param id
	 *            数据编号
	 * @return 详情查询：限号内容查询
	 */
	@RequestMapping(value = "/info/detail/{id}", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object limitInfoDetail(@PathVariable(value = "id") Integer id) {
		return getResultSuccess(lotteryLimitService.findSingleLimitInfo(new LotteryLimitInfoVO(id)));
	}

	/**
	 * @desc 添加：限号内容数据
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @param session
	 *            会话
	 * @return 添加：限号内容数据
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryLimitCacheEnum.class)
	public Object add(@Valid(GroupValue.ADD) LotteryLimitInfoVO lotteryLimitInfoVO, HttpSession session) {
		int num = lotteryLimitService.addLimitInfo(lotteryLimitInfoVO);
		return getResult(num > Constants.NUM_0);
	}

	/**
	 * @desc 修改：限号内容数据
	 * @author huangb
	 * @date 2017年2月15日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @param session
	 *            会话
	 * @return 修改：限号内容数据
	 */
	@RequestMapping(value = "/info", method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryLimitCacheEnum.class)
	public Object update(@Valid(GroupValue.UPD) LotteryLimitInfoVO lotteryLimitInfoVO, HttpSession session) {
		int num = lotteryLimitService.updLimitInfo(lotteryLimitInfoVO);
		return getResult(num > Constants.NUM_0);
	}
	
	/**
	 * @desc 保存：限号内容数据（批量操作，包含新增和修改的数据）
	 * @author huangb
	 * @date 2017年2月16日
	 * @param json
	 *            参数对象列表(json串)
	 * @return 保存：限号内容数据（批量操作，包含新增和修改的数据）
	 */
	@RequestMapping(value = "/info/save", method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryLimitCacheEnum.class)
	public Object save(@RequestParam(value = "json", required = true) String json) {
		List<LotteryLimitInfoVO> list = JsonUtil.json2ObjectList(json, LotteryLimitInfoVO.class);
		for (LotteryLimitInfoVO temp : list) {
			ParamUtil.validation(temp, null);
		}
		int num = lotteryLimitService.saveLimitInfo(list);
		return getResult(num > Constants.NUM_0);
	}
}
