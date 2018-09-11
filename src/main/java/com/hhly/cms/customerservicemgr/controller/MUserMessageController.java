package com.hhly.cms.customerservicemgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.SmsService;
import com.hhly.cms.customerservicemgr.service.MUserMessageService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.customerservice.vo.MUserMessageVO;
import com.hhly.skeleton.cms.customerservice.vo.SendMessageVO;

@RequestMapping("/customerService/mUserMessage")
@Controller
public class MUserMessageController extends BaseController {

	@Autowired
	private MUserMessageService mUserMessageService;
	
	@Autowired
	private SmsService smsService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "customerService/mUserMessage";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(MUserMessageVO vo){
		return mUserMessageService.findMUserMeaasge(vo);
	}
	
	@RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	public  Object sendMessage(SendMessageVO vo){
		boolean isOk;
		try {
			if(vo.getAccount().contains("@"))
				isOk = smsService.doSendMail(vo);
			else
				isOk = smsService.doSendSms(vo);
			if(isOk){
				int sucStatus = 1;
				mUserMessageService.updateStatus(vo.getId(), sucStatus);
			}
			return getResult(isOk);
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(false);
		}
		
	}
}
