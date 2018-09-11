package com.hhly.cms.lotterymgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hhly.cms.base.rabbitmq.SendMessage;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.base.service.FileProxy;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cmscore.cms.remote.service.ILotteryMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryBettingMulBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryChildBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeExcelBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryWinningBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryBettingMulCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryChildCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryWinningVO;
import com.hhly.skeleton.msg.MsgEnum;
@Service
public class LotteryTypeServiceImpl implements LotteryTypeService {
	@Autowired
	private ExcelExportService excelExportService;
	@Autowired
	private ILotteryMgrService iLotteryMgrService ;
	@Autowired
	private SendMessage sendMessage;
	
	private static Logger logger = Logger.getLogger(LotteryTypeServiceImpl.class);
	
	@Autowired
	protected FileProxy uploadFile;

	@Override
	public PagingBO<LotteryTypeBO> find(LotteryTypeVO vo) {
		PagingBO<LotteryTypeBO> pagingBO = iLotteryMgrService.findLotteryType(vo);
		List<LotteryTypeBO> lotteries = pagingBO.getData();
		if(!CollectionUtils.isEmpty(lotteries)) {
			for(LotteryTypeBO lottery : lotteries) {
				lottery.setLotteryLogoUrl(uploadFile.getUrl() + lottery.getLotteryLogoUrl());
				lottery.setLotteryLogoMobile(uploadFile.getUrl() + lottery.getLotteryLogoMobile());
			}
		}
		return pagingBO;
	}
	
	@Override
	public LotteryTypeBO findSingle(LotteryTypeVO vo) {
		return iLotteryMgrService.findSingle(vo);
	}

	@Override
	public List<LotteryBettingMulBO> findBettingMul(StringVO vo) {
		return iLotteryMgrService.findLotteryDettingMul(vo);
	}
	@Override
	public List<LotteryChildBO> findChild(StringVO vo) {
		return iLotteryMgrService.findLotteryChild(vo);
	}
	@Override
	public int updLotteryType(LotteryTypeVO vo) {
		return iLotteryMgrService.updLotteryType(vo);
	}
	@Override
	public int addLotteryBettingMul(LotteryBettingMulCmsVO vo) {
		return iLotteryMgrService.addLotteryBettingMul(vo);
	}
	@Override
	public int addLotteryChild(LotteryChildCmsVO vo) {
		return iLotteryMgrService.addLotteryChild(vo);
	}
	@Override
	public int updLotteryBettingMul(LotteryBettingMulCmsVO vo) {
		return iLotteryMgrService.updLotteryBettingMul(vo);
	}
	@Override
	public int updLotteryChild(LotteryChildCmsVO vo) {
		return iLotteryMgrService.updLotteryChild(vo);
	}
	@Override
	public int delBettingMul(StringVO vo) {
		return iLotteryMgrService.delLotteryBettingMul(vo);
	}
	@Override
	public int delChild(StringVO vo) {
		return iLotteryMgrService.delLotteryChild(vo);
	}
	@Override
	public int saveLotteryBettingMul(List<LotteryBettingMulCmsVO> list) {
		return iLotteryMgrService.saveLotteryBettingMul(list);
	}
	@Override
	public int saveLotteryChild(List<LotteryChildCmsVO> list) {
		// 新增和修改都是调用这个方法
		int affect = iLotteryMgrService.saveLotteryChild(list);
		if(!CollectionUtils.isEmpty(list)) {
			int lotteryCode = list.get(0).getLotteryCode();
			logger.info(String.format("**********子玩法信息额修改，推送到mq,彩种：%d*******************", lotteryCode));
			sendMessage.sendUpdateNotice(lotteryCode, MsgEnum.EventType.ChildChanged.getIntValue());
		}
		return affect;
	}
	
	@Override
	public ByteArrayOutputStream getLotteryTypeExcel(LotteryTypeVO vo) {
		List<LotteryTypeExcelBO> data=  iLotteryMgrService.findLotteryTypeToExcel(vo);
		return excelExportService.dataToExeclByStream("lottery",data);
	}
	@Override
	public int addLotteryType(LotteryTypeVO vo) {
		return iLotteryMgrService.addLotteryType(vo);
	}
	@Override
	public List<DictionaryBO> findTypeDictionary(StringVO vo) {
		List<LotteryTypeBO> list = iLotteryMgrService.findLotteryTypeDic(vo);
		List<DictionaryBO> result = new ArrayList<DictionaryBO>();
		for (LotteryTypeBO bo : list) {
			DictionaryBO dic = new DictionaryBO();
			dic.setId(bo.getLotteryCode().toString());
			dic.setText(bo.getLotteryName());
			result.add(dic);
		}
		return result;
	}

	@Override
	public List<DictionaryBO> findChildDictionary(LotteryChildCmsVO lotteryChildCms) {
		List<LotteryChildBO> list = iLotteryMgrService.findMultipleChild(lotteryChildCms);
		List<DictionaryBO> dicList = new ArrayList<DictionaryBO>();
		DictionaryBO dic = null;
		for (LotteryChildBO temp : list) {
			dic = new DictionaryBO();
			dic.setId(String.valueOf(temp.getLotteryChildCode()));
			dic.setText(temp.getChildName());
			dicList.add(dic);
		}
		return dicList;
	}

	@Override
	public List<DictionaryBO> findWinningDictionary(LotteryWinningVO lotteryWinningVO) {
		List<LotteryWinningBO> list = iLotteryMgrService.findMultipleLotWinning(lotteryWinningVO);
		List<DictionaryBO> dicList = new ArrayList<DictionaryBO>();
		DictionaryBO dic = null;
		for (LotteryWinningBO temp : list) {
			dic = new DictionaryBO();
			dic.setId(String.valueOf(temp.getCode()));
			dic.setText(temp.getName());
			dicList.add(dic);
		}
		return dicList;
	}
}
