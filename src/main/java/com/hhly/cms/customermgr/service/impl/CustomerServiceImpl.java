package com.hhly.cms.customermgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.rabbitmq.SendMessage;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.customermgr.service.CustomerService;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cms.utils.PasswordUtil;
import com.hhly.cms.utils.StringUtil;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.util.EncryptUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ICustomerMgrService iCustomerMgrService;

	@Autowired
	private MarketChannelService marketChannelService;

	@Autowired
	private ExcelExportService excelExportService;

	@Autowired
	private SendMessage sendMessage;

	@Override
	public PagingBO<LottoCustomerBO> findLottoCustomer(LottoCustomerVO vo) {
		return iCustomerMgrService.findLottoCustomer(vo);
	}

	@Override
	public ByteArrayOutputStream getLottoCustomerExcel(LottoCustomerVO vo) {
		Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
		otherDic.put("channelId", marketChannelService.findAllChannelDic());
		return excelExportService.dataToExeclByStreamDictionary("user", iCustomerMgrService.findLottoCustomerExcel(vo),
				otherDic);
	}

	@Override
	public LottoCustomerBO findLottoCustomerDetail(StringVO vo) {
		LottoCustomerBO bo = iCustomerMgrService.findLottoCustomerDetail(vo);
		bo.setRcode(null);
		bo.setAccountPassword(null);
		return bo;
	}

	@Override
	public int updLottoCustomer(LottoCustomerVO vo) {

		return iCustomerMgrService.updLottoCustomer(vo);
	}

	@Override
	public List<Integer> findIds(LottoCustomerVO vo) {
		return iCustomerMgrService.findIds(vo);
	}

	@Override
	public int findTotal(LottoCustomerVO vo) {
		return iCustomerMgrService.findTotal(vo);
	}

	@Override
	public void updatePassword(LottoCustomerVO vo) {
		LottoCustomerBO bo = iCustomerMgrService.findLottoCustomerDetail(new StringVO(vo.getId().toString()));
		checkUserInfo(bo);
		String account = bo.getAccountName();
		String password = vo.getResetPassword();
		if ("0".equals(vo.getType())) {
			if(StringUtils.isBlank(password)
					|| password.length()!=6
					|| !password.matches("[0-9]+")){
				throw new ServiceRuntimeException("密码设置格式错误，只能是6位纯数字");
			}
		} else if ("1".equals(vo.getType())) {
			password = StringUtil.randomNumber(6);
		} else {
			throw new ServiceRuntimeException("参数错误");
		}
		try {
			String firstEncuypt = PasswordUtil.encrypt(password);
			vo.setAccountPassword(EncryptUtil.encrypt(firstEncuypt, bo.getRcode()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new ServiceRuntimeException("加密错误！", e);
		}
		iCustomerMgrService.updLottoCustomerPassword(vo);
		if ("1".equals(vo.getType())) {
			String mobile = bo.getCusMobile() == null ? "" : bo.getCusMobile().toString();
			if (StringUtils.isNotBlank(mobile) && mobile.length() == 11) {
				String message = "尊敬的" + account + "用户：您的账户：" + account + "正在进行重置密码操作，重置后密码为：" + password
						+ "。请及时登录您的账户，并修改密码。";
				sendMessage.sendQueueMobile(mobile, message);
			} else {
				String message = "亲爱的会员：<br> " + account + "您好！您的账户：" + account + "正在进行重置密码操作，重置后密码为：" + password
						+ "。请及时登录您的账户，并修改密码。";
				sendMessage.sendQueueEmail(bo.getCusMail(), message);
			}
		}
	}

	/**
	 * 判断用户是否能重置密码
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午4:20:53
	 * @param bo
	 */
	private void checkUserInfo(LottoCustomerBO bo) {
		if (StringUtils.isBlank(bo.getAccountPassword())) {
			throw new ServiceRuntimeException("用户未设置密码不能修改或重置密码");
		}
		if (StringUtils.isNotBlank(bo.getQqOpenId()) || StringUtils.isNotBlank(bo.getSinaBlogOpenId())
				|| StringUtils.isNotBlank(bo.getBaiduOpenId()) || StringUtils.isNotBlank(bo.getWechatOpenId())
				|| StringUtils.isNotBlank(bo.getAlipayOpenId()) || StringUtils.isNotBlank(bo.getJdOpenId())) {
			throw new ServiceRuntimeException("用户绑定第三方登录不能修改或重置密码");
		}
	}

	@Override
	public void updateCleanMessage(LottoCustomerVO vo) {
		iCustomerMgrService.updateCleanMessage(vo);
	}
}
