package com.hhly.cms.paymentmgr.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DicUtils;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.cache.pay.PayCacheEnum.Pay;
import com.hhly.skeleton.base.common.cache.pay.PayCacheEnum.PayChannelLimit;
import com.hhly.skeleton.cms.payment.bo.PayChannelBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelLimitBO;
import com.hhly.skeleton.cms.payment.bo.PayChannelMgrBO;
import com.hhly.skeleton.cms.payment.vo.PayChannelLimitVO;
import com.hhly.skeleton.cms.payment.vo.PayChannelMgrVO;
import com.hhly.skeleton.cms.payment.vo.PayChannelVO;

/**
 * @desc    支付渠道管理
 * @author  Tony Wang
 * @date    2017年12月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("paymentmgr/channel")
@Controller
public class PaymentChannelController extends BaseController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ExcelExportService excelExportService;
	
	@RequestMapping
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/paymentmgr/pay_channel_mgr";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<PayChannelMgrBO> page(PayChannelMgrVO vo){
		return paymentService.pageChannelMgrInfo(vo);
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void excel(HttpServletResponse response,PayChannelMgrVO vo) throws IOException{
		excel("pay_channel", excelExportService.dataToExeclByStream(paymentService.findChannelMgrExcelInfo(vo)), response);
	}
	
	@RequestMapping("bank/page")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<PayChannelBO> pageChannelBankInfo(PayChannelVO vo){
		if(vo.getPayChannelMgrId()==null) 
			return new PagingBO<PayChannelBO>();
		// 如果有按银行名称查询，则要join表pay_bank
		if(StringUtils.hasText(vo.getBankName()))
			vo.setJoinBank(true);
		return paymentService.pageChannelBankInfo(vo);
	}
	
	@RequestMapping(value="bank", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public ResultBO<?> batchMergeChannelBankInfo(@RequestBody List<PayChannelVO> vos, HttpSession session){
		if(CollectionUtils.isEmpty(vos))
			return getSaveResult(1);
		String realName = getUserRealName(session);
		for(PayChannelVO vo : vos) {
			if(vo.getId()==null) {
				vo.setCreateTime(new Date());
				vo.setCreateBy(realName);
			} else {
				vo.setModifyTime(new Date());
				vo.setModifyBy(realName);
			}
		}
		int ret = paymentService.batchMergeChannelBankInfo(vos);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="limit", method={RequestMethod.POST,RequestMethod.PUT})
	@Authority(privilege={AuthEnum.ADD,AuthEnum.UPD})
	@DeleteBatchAssignCache(GetCacheEnumService = PayChannelLimit.class)
	@ResponseBody
	public ResultBO<?> batchMergeChannelLimitInfo(@RequestBody List<PayChannelLimitVO> vos, HttpSession session){
		if(CollectionUtils.isEmpty(vos))
			return getSaveResult(1);
		int ret = paymentService.batchMergeChannelLimitInfo(vos);
		return getSaveResult(ret);
	}
	
    @RequestMapping(value="/dic", method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege =AuthEnum.SEARCH)
    public List<DictionaryBO> findDic() {
    	List<PayChannelMgrBO> channels = paymentService.findChannelMgrInfo(new PayChannelMgrVO());
		return DicUtils.toDic(channels, "id", Integer.class, "name", String.class);
    }
	
    @RequestMapping(value="/map", method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege =AuthEnum.SEARCH)
    public Map<Integer,PayChannelMgrBO> map() {
    	List<PayChannelMgrBO> channels = paymentService.findChannelMgrInfo(new PayChannelMgrVO());
    	Map<Integer,PayChannelMgrBO> channnelMap = new HashMap<>();
    	for(PayChannelMgrBO bo : channels) {
    		channnelMap.put(bo.getId(), bo);
    	}
    	return channnelMap;
    }
    
	@RequestMapping(method={RequestMethod.PUT,RequestMethod.POST})
	@Authority(privilege={AuthEnum.ADD,AuthEnum.UPD})
	@DeleteBatchAssignCache(GetCacheEnumService = Pay.class)
	@ResponseBody
	public ResultBO<?> merge(PayChannelMgrVO vo, HttpSession session){
		String realName = getUserRealName(session);
		if(vo.getId()==null) {
			vo.setCreateTime(new Date());
			vo.setCreateBy(realName);
		} else {
			vo.setModifyTime(new Date());
			vo.setModifyBy(realName);
		}
		int ret = paymentService.mergeChannelMgrInfo(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="limit/page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<PayChannelLimitBO> pageLimitInfo(PayChannelLimitVO vo){
		if(vo.getPayChannelMgrId()==null) 
			return new PagingBO<PayChannelLimitBO>();
		return paymentService.pageChannelLimitInfo(vo);
	}
	
	@RequestMapping(value="limit",method=RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@DeleteBatchAssignCache(GetCacheEnumService = PayChannelLimit.class)
	@ResponseBody
	public Object deletePayChannel(@RequestBody PayChannelLimitVO vo) {
		int num = paymentService.deleteChannelLimitInfo(vo);
		return getSaveResult(num);
	}
}
