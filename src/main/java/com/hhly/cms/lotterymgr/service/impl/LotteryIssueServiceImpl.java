package com.hhly.cms.lotterymgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hhly.cms.base.rabbitmq.SendMessage;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.lotterymgr.service.LotteryIssueService;
import com.hhly.cmscore.cms.remote.service.ILotteryMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.LotteryEnum.LotIssueSaleStatus;
import com.hhly.skeleton.base.mq.msg.DrawResultData;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueCmsBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueExcelBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryIssueCmsVO;
import com.hhly.skeleton.lotto.base.issue.bo.IssueLottBO;
@Service
public class LotteryIssueServiceImpl implements LotteryIssueService {
	
	private static Logger logger = Logger.getLogger(LotteryIssueServiceImpl.class);
	
	@Autowired
	private ILotteryMgrService iLotteryMgrService ;

	@Autowired
	private ExcelExportService excelExportService;
	
	@Autowired
	private SendMessage sendMessage;
	
	@Override
	public List<DictionaryBO> findIssueCode(LotteryIssueCmsVO vo) {
		List<String> list = iLotteryMgrService.findIssueCode(vo);
		List<DictionaryBO> res = new ArrayList<DictionaryBO>();
		for (String str : list) {
			DictionaryBO bo = new DictionaryBO();
			bo.setId(str);
			bo.setText(str);
			res.add(bo);
		}
		return res;
	}
	@Override
	public PagingBO<LotteryIssueCmsBO> findLotteryIssue(LotteryIssueCmsVO vo) {
		return iLotteryMgrService.findLotteryIssue(vo);
	}
	@Override
	public int updLotteryIssue(LotteryIssueCmsVO vo) {
		StringVO stringVO =new StringVO();
		stringVO.setStr(vo.getId().toString());
		LotteryIssueCmsBO lotteryIssueCmsBO = iLotteryMgrService.findLotteryIssueDetail(stringVO);
		
		int num =  iLotteryMgrService.updLotteryIssue(vo);
		if(num > 0){
			//判断是否发送开奖号码审核通知
			if(!Objects.equals(lotteryIssueCmsBO.getSaleStatus(),vo.getSaleStatus())
					&& Objects.equals(LotIssueSaleStatus.APPROVED.getValue(),vo.getSaleStatus())){
				sendMessage.sendDrawCodeMesssage(vo.getLotteryCode(), vo.getIssueCode(), vo.getDrawCode());
				// 推送公奖公告给前端
				sendLastestIssue(vo.getLotteryCode(), vo.getIssueCode(), 1);
			}
			iLotteryMgrService.updOmotAll(vo.getLotteryCode(), vo.getIssueCode(), vo.getDrawCode());
		}
		sendMessage.sendUpdateNotice(vo.getLotteryCode(), 3);
		return num;
	}
	
	@Override
	public int addLotteryIssue(LotteryIssueCmsVO vo) {
		int num =  iLotteryMgrService.addLotteryIssue(vo);
		if(num > 0){
			iLotteryMgrService.updOmotAll(vo.getLotteryCode(), vo.getIssueCode(), vo.getDrawCode());
		}
		return num;
	}
	@Override
	public ByteArrayOutputStream getLotteryIssueExcel(LotteryIssueCmsVO vo) {
		List<LotteryIssueExcelBO> data =  iLotteryMgrService.findLotteryIssueToExcel(vo);
		return excelExportService.dataToExeclByStream("issue", data);
	}
	@Override
	public LotteryIssueCmsBO findLotteryIssueDetail(StringVO vo) {
		return iLotteryMgrService.findLotteryIssueDetail(vo);
	}
	
	@Override
	public LotteryIssueCmsBO findIssue(Integer lotteryCode, Short currentIssue) {
		return iLotteryMgrService.findIssue(lotteryCode, currentIssue);
	}
	@Override
	public int updateAuditDrawCode(LotteryIssueCmsVO vo) {
		int num = iLotteryMgrService.updateAuditDrawCode(vo);
		// 最新开奖 ，审核开奖时切换此状态；0：否；1：是
		if(num == 1){
			sendMessage.sendDrawCodeMesssage(vo.getLotteryCode(), vo.getIssueCode(), vo.getDrawCode());
			sendLastestIssue(vo.getLotteryCode(), vo.getIssueCode(), 1);
		}
		return num;
	}
	
	private void sendLastestIssue(Integer lotteryCode, String issueCode, Integer i) {
		logger.debug("########推送最新开奖start");
		IssueLottBO lastestIssue = iLotteryMgrService.findIssueByParam(lotteryCode, issueCode, i);
		if(lastestIssue == null){
			logger.debug("###########查询到的最新开奖信息为空:"+lastestIssue+"。不作推送");
			return;
		}
		DrawResultData drawResultData = new DrawResultData();
		drawResultData.setList(Arrays.asList(lastestIssue));
		logger.debug("###########推送内容："+drawResultData);
		sendMessage.sendLastestIssue(drawResultData);
		logger.debug("##########推送最新开奖end");
	}
	@Override
	public List<DictionaryBO> cutOffHistoryIssue(LotteryIssueCmsVO vo) {
		Assert.notNull(vo.getLotteryCode(), "彩种编号不能为空");
		Assert.isTrue(vo.getQryCount() > 0, "查询期数必须大于0");
		List<String> list = iLotteryMgrService.cutOffHistoryIssue(vo.getLotteryCode(),vo.getQryCount());
		List<DictionaryBO> res = new ArrayList<DictionaryBO>();
		for (String str : list) {
			DictionaryBO bo = new DictionaryBO();
			bo.setId(str);
			bo.setText(str);
			res.add(bo);
		}
		return res;
	}
}

