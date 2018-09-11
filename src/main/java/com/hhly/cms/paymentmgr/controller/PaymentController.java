package com.hhly.cms.paymentmgr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.cmscore.cms.remote.service.ICmsCacheService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.common.cache.pay.PayCacheEnum.Pay;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.payment.bo.PayBankBO;
import com.hhly.skeleton.cms.payment.bo.PayBankLimitBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelBO;
import com.hhly.skeleton.cms.payment.vo.PayBankLimitVO;
import com.hhly.skeleton.cms.payment.vo.PayBankVO;
import com.hhly.skeleton.cms.payment.vo.PayChannelVO;

/**
 * @desc 支付管理
 * @author tangxiaobo
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("paymentmgr/index")
@Controller
public class PaymentController extends BaseController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
    private ICmsCacheService cmsCacheService;

	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/paymentmgr/index";
	}

	/**
	 * 获取银行列表
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:53:50
	 * @return
	 */
	@RequestMapping(value = "bankList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<PayBankBO> bankList(PayBankVO vo) {

		return paymentService.findBank(vo);
	}

	/**
	 * 根据银行id获取渠道列表
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:53:28
	 * @param bankId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "channelList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<PayChannelBO> channelList(PayChannelVO vo) {
		vo.setSortField("ORDER_ID");
		vo.setSortOrder("desc");
		vo.setPageIndex(null);
		vo.setPageSize(null);
		return paymentService.findChannel(vo);
	}
	/**
	 * 添加银行
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:53:17
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping("addBank")
	@Authority(privilege = AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ParameterValid
	public Object addBank(@Valid(GroupValue.ADD) PayBankVO vo, HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		int num = paymentService.addBank(vo);
		return getSaveResult(num);
	}

	/**
	 * 根据id获取银行
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:53:06
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getBank/{id}")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object getBank(@PathVariable("id") Integer id) {

		return getResultSuccess(paymentService.findPayBankById(id));
	}

	/**
	 * 保存银行信息
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:52:52
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping("updateBank")
	@Authority(privilege = AuthEnum.UPD)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	@ParameterValid
	public Object updateBank(@Valid(GroupValue.UPD) PayBankVO vo, HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		int num = paymentService.updateBankById(vo);
		return getSaveResult(num);
	}

	/**
	 * 保存渠道列表
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:52:36
	 * @param json
	 * @param session
	 * @return
	 */
	@RequestMapping("saveChannel")
	@Authority(privilege = AuthEnum.UPD)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public Object saveChannel(@RequestParam(value = "json", required = true) String json, HttpSession session) {

		@SuppressWarnings("unchecked")
		List<PayChannelVO> list = JsonUtil.json2ObjectList(json,PayChannelVO.class);
		 
		String optionBy = getUserRealName(session);

		for (PayChannelVO vo : list) {
			vo.setCreateBy(optionBy);
			vo.setModifyBy(optionBy);

			ParamUtil.validation(vo, GroupValue.UPD);
		}

		int num = paymentService.saveOrUpdateChannelList(list);
		return getSaveResult(num);
	}

	@RequestMapping(value = "bankLimitList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<PayBankLimitBO> bankLimitList(Integer bankId) {

		return paymentService.findBankLimit(bankId);
	}

	/**
	 * 保存银行限额列表
	 * 
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午4:49:24
	 * @param json
	 * @return
	 */
	@RequestMapping("saveBankLimit")
	@Authority(privilege = AuthEnum.UPD)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public Object saveBankLimit(@RequestParam(value = "json", required = true) String json) {

		@SuppressWarnings("unchecked")
		List<PayBankLimitVO> list = JsonUtil.json2ObjectList(json,PayBankLimitVO.class);
		
		for (PayBankLimitVO vo : list) {
			ParamUtil.validation(vo, null);
		}

		int num = paymentService.saveOrUpdateBankLimitList(list);
		return getSaveResult(num);
	}
	
	@RequestMapping(value="/paychannel",method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.ALL)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public Object deletePayChannel(@RequestBody PayChannelVO vo) {
		int num = paymentService.deleteChannel(vo);
		cmsCacheService.clearCache("USER_PAY_CHANNEL_*");
		return getSaveResult(num);
	}
	
	@RequestMapping(value="/banklimit",method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public Object deleteBankLimit(@RequestBody PayBankLimitVO vo) {
		int num = paymentService.deleteBankLimit(vo);
		return getSaveResult(num);
	}

	@RequestMapping(value = "/banks/dic")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> dic() {
		List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
		return DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
	}
}
