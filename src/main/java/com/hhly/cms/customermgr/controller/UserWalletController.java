package com.hhly.cms.customermgr.controller;

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
import com.hhly.cms.customermgr.service.UserWalletService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.MemberEnum.UserWalletStatus;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.MatchPattern;
import com.hhly.skeleton.cms.customermgr.vo.UserWalletVO;

/**
 * @desc 用户钱包管理控制
 * @author huangb
 * @date 2017年2月8日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/customermgr/user/wallet")
public class UserWalletController extends BaseController {

	/**
	 * 钱包服务
	 */
	@Autowired
	private UserWalletService userWalletService;
	/**
	 * 上传查询的手机号选项标识
	 */
	private final String CUS_MOBILE = "cus_mobile";

	/**
	 * @desc 跳转到钱包管理页
	 * @author huangb
	 * @date 2017年2月8日
	 * @return 钱包管理页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "customermgr/user_wallet";
	}

	/**
	 * @desc 分页查询钱包列表
	 * @author huangb
	 * @date 2017年2月8日
	 * @param userWalletVO
	 *            查询参数
	 * @return 分页钱包列表
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(UserWalletVO userWalletVO) {
		return userWalletService.findPaging(userWalletVO);
	}

	/**
	 * @desc 修改钱包状态（启用钱包）
	 * @author huangb
	 * @date 2017年2月8日
	 * @param ids
	 *            钱包的id列表(逗号分隔)
	 * @return 启用钱包
	 */
	@RequestMapping(value = "/enableWallet")
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object enableWallet(@RequestParam("ids") String ids) {
		userWalletService.updWalletStatusByIds(ids, UserWalletStatus.ENABLE.getValue());
		// 启用钱包结果(（如果是已经启用的数据，再次启用不会更新）,所以没异常就启用成功)
		return getResult(true);
	}

	/**
	 * @desc 修改钱包状态（禁用钱包）
	 * @author huangb
	 * @date 2017年2月8日
	 * @param ids
	 *            钱包的id列表(逗号分隔)
	 * @return 禁用钱包
	 */
	@RequestMapping(value = "/disableWallet")
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object disableWallet(@RequestParam("ids") String ids) {
		userWalletService.updWalletStatusByIds(ids, UserWalletStatus.DISABLE.getValue());
		// 禁用钱包结果(（如果是已经禁用的数据，再次启用不会更新）,所以没异常就启用成功)
		return getResult(true);
	}

	/**
	 * @desc 导出订单追号彩期excel
	 * @author huangb
	 * @date 2017年1月20日
	 * @param response
	 * @param orderAddIssue
	 *            查询对象
	 * @throws IOException
	 */
	@RequestMapping("/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportExcel(HttpServletResponse response, UserWalletVO userWalletVO) throws IOException {
		ByteArrayOutputStream outputStream = userWalletService.findExcel(userWalletVO);
		excel("user_wallet", outputStream, response);
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
		UserWalletVO tempVO = new UserWalletVO();
		tempVO.setTypeField(type);
		tempVO.setJoinUser(true);// 关联用户表查询
		tempVO.setTypeValues(list);
		tempVO.setPageIndex(Constants.NUM_0);
		tempVO.setPageSize(list.size());
		ResultBO<?> result = getResultSuccess(userWalletService.findPaging(tempVO).getData());
		return JsonUtil.object2Json(result, DateUtil.DEFAULT_FORMAT);
	}
}
