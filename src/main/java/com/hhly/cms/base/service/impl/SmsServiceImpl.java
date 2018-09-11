package com.hhly.cms.base.service.impl;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.SmsService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.util.MailUtil;
import com.hhly.skeleton.base.util.SmsSendUtil;
import com.hhly.skeleton.cms.customerservice.vo.SendMessageVO;
import com.hhly.skeleton.msg.SmsChannelEnum;

/**
 * 短信发送实现类
 * @desc
 * @author zhouyang
 * @date 2017年2月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
	
	private static final Logger logger = Logger.getLogger(SmsServiceImpl.class);
	
	@Override
	public boolean doSendSms(SendMessageVO userMessageVO) throws Exception {
		try {
			boolean isSuc = SmsSendUtil.mdsmssend(SmsChannelEnum.VALIDATECODE.getCode(),userMessageVO.getAccount(), userMessageVO.getMessage(), "", "", "", "");
			return isSuc;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(ResultBO.err(MessageCodeConstants.SMS_SEND_DEFEAT_SYS));
			return false;
		}
	}

	@Override
	public boolean doSendMail(SendMessageVO userMessageVO) throws Exception {
		boolean isSuccess = false;
		try {
			MailUtil mail = new MailUtil();
			isSuccess = mail.sendMail(userMessageVO.getMessage(), UserConstants.YICAI_NET, UserConstants.YICAI_EMAIL, userMessageVO.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(ResultBO.err(MessageCodeConstants.MAIL_SEND_DEFEAT_SYS));
			return false;
		}
		return isSuccess;
	}
}
