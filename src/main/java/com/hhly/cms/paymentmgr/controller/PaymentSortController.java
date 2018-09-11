package com.hhly.cms.paymentmgr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.base.common.cache.pay.PayCacheEnum.Pay;
import com.hhly.skeleton.cms.payment.bo.PayBankBO;
import com.hhly.skeleton.cms.payment.vo.PayBankVO;

/**
 * @desc 支付方式排序
 * @author tangxiaobo
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("paymentmgr/sort")
@Controller
public class PaymentSortController extends BaseController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/paymentmgr/sort";
	}

	/**
	 * 根据排序id获取银行列表
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:55:02
	 * @param sortType
	 * @return
	 */
	@RequestMapping(value = "list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<PayBankBO> list(PayBankVO vo) {

		return paymentService.findBank(vo);
	}

	/**
	 * 更新排序
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:55:19
	 * @param json
	 * @param session
	 * @return
	 */
	@RequestMapping("update")
	@Authority(privilege = AuthEnum.UPD)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public Object update(String json, HttpSession session) {

		@SuppressWarnings("unchecked")
		List<PayBankVO> list = JsonUtil.json2ObjectList(json,PayBankVO.class);
		List<PayBankVO> orderList = new ArrayList<>();
		// 只更新PC,Android,iOS,H5的排序字段
		PayBankVO tmp;
		for (PayBankVO vo : list) {
			tmp = new PayBankVO();
			tmp.setOrderAndroid(vo.getOrderAndroid());
			tmp.setOrderH5(vo.getOrderH5());
			tmp.setOrderIos(vo.getOrderIos());
			tmp.setOrderPc(vo.getOrderPc());
			tmp.setId(vo.getId());
			tmp.setModifyBy(getUserRealName(session));
			orderList.add(tmp);
		}
		int num = paymentService.updateBankList(orderList);
		return getSaveResult(num);
	}
}
