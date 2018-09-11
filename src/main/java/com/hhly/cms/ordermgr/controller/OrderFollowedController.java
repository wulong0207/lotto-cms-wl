package com.hhly.cms.ordermgr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderFollowedService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.ordermgr.bo.OrderFollowedBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderFollowedVO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserMenuBO;

/**
 * @desc    发单管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("/ordermgr/followed")
public class OrderFollowedController extends BaseController {
	
	@Autowired
	private OrderFollowedService orderFollowedService;

	@RequestMapping("list")
	@ResponseBody
	@Authority(privilege=AuthEnum.ALL)
	public List<OrderFollowedBO> list(OrderFollowedVO vo, HttpSession session){
		isAuth(session,"/ordermgr/issue", AuthEnum.SEARCH);
		return orderFollowedService.list(vo);
	}
	
	@RequestMapping("page")
	@ResponseBody
	@Authority(privilege=AuthEnum.ALL)
	public PagingBO<OrderFollowedBO> page(OrderFollowedVO vo, HttpSession session){
		isAuth(session, "/ordermgr/issue", AuthEnum.SEARCH);
		return orderFollowedService.page(vo);
	}

	@RequestMapping("excel")
	@Authority(privilege=AuthEnum.ALL)
	public void exportExcel(HttpServletResponse response,HttpSession session, OrderFollowedVO vo) throws IOException{
		isAuth(session, "/ordermgr/issue", AuthEnum.EXPORT);
		excel("跟单用户", orderFollowedService.excel(vo), response);
	}
	
	/**
	 * @desc   验证是否有权限
	 * @author Tony Wang
	 * @create 2017年10月12日
	 * @param string
	 * @param search 
	 */
	private void isAuth(HttpSession session, String url, AuthEnum auth) {
		@SuppressWarnings("unchecked")
		List<CMSUserMenuBO> userAuthList = (List<CMSUserMenuBO>) session.getAttribute(WebConstant.AUTERITY);
		String urlAccess = SymbolConstants.ENPTY_STRING;
		for (CMSUserMenuBO bo : userAuthList) {
			if(ObjectUtil.isBlank(bo.getUrl()))
				continue;
			if (url.equals("/"+bo.getUrl())) {
				urlAccess+=bo.getUserAuth();
				break;
			}
			
		}
		// 若该用户此菜单的权限为空
		Assert.isTrue(StringUtils.hasText(urlAccess), "没有权限");
		boolean flag = false;
		String[] urlAccessAuths = urlAccess.split(SymbolConstants.COMMA);
		for (String urlAccessAuth : urlAccessAuths) {
			if (urlAccessAuth.equals(auth.getCode())) {
				 flag=true;
				 break;
			}
		}
		Assert.isTrue(flag, "没有权限");
	}
}
