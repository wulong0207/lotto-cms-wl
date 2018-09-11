package com.hhly.cms.lotterymgr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hhly.cms.base.rabbitmq.SendMessage;
import com.hhly.cms.lotterymgr.service.LotteryLimitService;
import com.hhly.cmscore.cms.remote.service.ILotteryMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitInfoBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitInfoVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitVO;
import com.hhly.skeleton.msg.MsgEnum;

/**
 * @desc 限号管理的服务接口
 * @author huangb
 * @date 2017年2月15日
 * @company 益彩网络
 * @version v1.0
 */
@Service
public class LotteryLimitServiceImpl implements LotteryLimitService {

	/**
	 * 远程服务
	 */
	@Autowired
	private ILotteryMgrService iLotteryMgrService;
	
	@Autowired
	private SendMessage sendMessage;
	
	private static Logger logger = Logger.getLogger(LotteryLimitServiceImpl.class);

	@Override
	public LotteryLimitBO findSingleLimit(LotteryLimitVO lotteryLimitVO) {
		return iLotteryMgrService.findSingleLimit(lotteryLimitVO);
	}

	@Override
	public PagingBO<LotteryLimitBO> findPagingLimit(LotteryLimitVO lotteryLimitVO) {
		return iLotteryMgrService.findPagingLimit(lotteryLimitVO);
	}

	@Override
	public int addLimit(LotteryLimitVO lotteryLimitVO) {
		return iLotteryMgrService.addLimit(lotteryLimitVO);
	}

	@Override
	public int updLimit(LotteryLimitVO lotteryLimitVO) {
		int affected = iLotteryMgrService.updLimit(lotteryLimitVO);
		logger.info(String.format("**********限号信息额修改，推送到mq,彩种：%d*******************", lotteryLimitVO.getLotteryCode()));
		sendMessage.sendUpdateNotice(lotteryLimitVO.getLotteryCode(), MsgEnum.EventType.LimitChanged.getIntValue());
		return affected;
	}

	@Override
	public LotteryLimitInfoBO findSingleLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO) {
		return iLotteryMgrService.findSingleLimitInfo(lotteryLimitInfoVO);
	}

	@Override
	public PagingBO<LotteryLimitInfoBO> findPagingLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO) {
		return iLotteryMgrService.findPagingLimitInfo(lotteryLimitInfoVO);
	}

	@Override
	public int addLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO) {
		return iLotteryMgrService.addLimitInfo(lotteryLimitInfoVO);
	}

	@Override
	public int updLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO) {
		return iLotteryMgrService.updLimitInfo(lotteryLimitInfoVO);
	}

	@Override
	public int saveLimitInfo(List<LotteryLimitInfoVO> list) {
		int affect = iLotteryMgrService.saveLimitInfo(list);
		if(!CollectionUtils.isEmpty(list)) {
			int lotteryCode = list.get(0).getLotteryChildCode()/100;
			logger.info(String.format("**********限号信息额修改，推送到mq,彩种：%d*******************", lotteryCode));
			sendMessage.sendUpdateNotice(lotteryCode, MsgEnum.EventType.LimitChanged.getIntValue());
		}
		return affect;
	}

}
