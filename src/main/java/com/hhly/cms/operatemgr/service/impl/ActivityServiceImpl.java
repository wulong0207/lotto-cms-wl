package com.hhly.cms.operatemgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.bo.TreeGridBO;
import com.hhly.cms.operatemgr.service.ActivityService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OperateCouponEnum.CouponConfigTypeEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.operatemgr.bo.*;
import com.hhly.skeleton.cms.operatemgr.vo.*;
import com.hhly.skeleton.lotto.base.operate.bo.OperateCouponTempBO;
import com.hhly.skeleton.lotto.base.operate.bo.SportAgainstBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateCouponTempVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActivityServiceImpl implements ActivityService{
	@Autowired
    private IOperateMgrService iOperateMgrService;
	
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<OperateActivityBO> findActivity(OperateActivityVO vo) {
		return iOperateMgrService.findActivity(vo);
	}

    @Override
    public PagingBO<OperateActivityCdkeyBO> findCdkeyList(OperateActivityCdkeyVO vo) {
        return iOperateMgrService.findCdkeyList(vo);
    }

	@Override
	public OperateActivityCdkeyBO findCdkCount(OperateActivityCdkeyVO vo) {
		return iOperateMgrService.findCdkCount(vo);
	}

	@Override
    public int updOperateActivity(OperateActivityVO vo) {
        return iOperateMgrService.updOperateActivity(vo);
	}

	@Override
	public int addOperateActivity(OperateActivityVO vo) {
		return iOperateMgrService.addOperateActivity(vo);
	}

	@Override
	public OperateActivityBO findActivityDetail(int id) {
		return iOperateMgrService.findOperateActivityDetail(id);
	}

	@Override
	public ByteArrayOutputStream getExcelActivity(OperateActivityVO vo) {
		List<OperateActivityExcelBO> data=  iOperateMgrService.findExcelActivity(vo);
		return excelExportService.dataToExeclByStream("activity", data);
	}

	@Override
	public  List<TreeGridBO>  findChannelList(String channelId) {
		String channelIds[] = StringUtils.tokenizeToStringArray(channelId, SymbolConstants.COMMA);
		List<OperateMarketChannelBO> channels = iOperateMgrService.findChannelAllSimple();
		List<TreeGridBO> grids= new ArrayList<TreeGridBO>();
		//Map<String, List<FunctionBO>> functions = new HashMap<String, List<FunctionBO>>();
		/*for (OperateMarketChannelBO channel : channels) {
			FunctionBO  funciton = new FunctionBO();
			funciton.setAction(channel.getChannelId());
			funciton.setChecked("false");
			funciton.setName(channel.getChannelName());
			if(!functions.containsKey(channel.getParentChannelId())){
				functions.put(channel.getParentChannelId(),new ArrayList<FunctionBO>());
			}
			functions.get(channel.getParentChannelId()).add(funciton);
		}*/
		//记录所有的父级id
		Set<String> parentId =new  HashSet<String>();
		for (OperateMarketChannelBO channel : channels) {
			TreeGridBO  grid = new TreeGridBO();
			grid.setId(channel.getChannelId());
			grid.setName(channel.getChannelName());
			grid.setPid(channel.getParentChannelId()==null?"-1":channel.getParentChannelId());
			parentId.add(grid.getPid());
			for (int i = 0; i < channelIds.length; i++) {
				if(channel.getChannelId().equals(channelIds[i])){
					grid.setChecked(true);
					break;
				}
			}			
			/*List<FunctionBO> fun =functions.get(channel.getChannelId());
			grid.setFunctions(fun);*/
			grids.add(grid);
		}
		for (TreeGridBO bo : grids) {
			if(parentId.contains(bo.getId())){
				bo.setChecked(false);
			}
		}
		return grids;
	}

	 /**
	  * 活动配置相关操作
	  */
	@Override
	 public int mergeActivityConfig(OperateActivityConfigVO vo) {
		return iOperateMgrService.mergeActivityConfig(vo);
	}

	@Override
	public int mergeSportRule(OperateActivitySportRuleVO vo) {
		return iOperateMgrService.mergeSportRule(vo);
	}

	@Override
	public OperateActivityConfigBO findActivityConfigDetail(OperateActivityConfigVO vo) {
		return iOperateMgrService.findActivityConfigDetail(vo);
	}

	@Override
	public int addActivityRule(OperateActivityRuleVO vo) {
		return iOperateMgrService.addActivityRule(vo);
	}

	@Override
	public int updActivityRule(OperateActivityRuleVO vo) {
		return iOperateMgrService.updActivityRule(vo);
	}

	@Override
	public int delActivityRule(Integer id) {
		return iOperateMgrService.delActivityRule(id);
	}

	@Override
	public PagingBO<OperateActivityRuleBO> findActivityRuleList(OperateActivityRuleVO vo) {
		return iOperateMgrService.findActivityRuleList(vo);
	}

	@Override
	public OperateActivityRuleBO findActivityRuleDetail(Integer id) {
		return iOperateMgrService.findActivityRuleDetail(id);
	}

	@Override
	public PagingBO<SportAgainstBO> findSportAgainstInfo(SportAgainstVO vo) {
		return iOperateMgrService.findSportAgainstInfo(vo);
	}

	@Override
	public List<DictionaryBO> findActivityStatusTrue() {
		return iOperateMgrService.findActivityStatusTrue();
	}

	@Override
	public OperateActivityAwardBO addPrizeAwardConfig(OperateActivityAwardVO vo) {
		return iOperateMgrService.addPrizeAwardConfig(vo);
	}

	@Override
	public int delPrizeAwardConfig(OperateActivityAwardVO vo) {
		return iOperateMgrService.delPrizeAwardConfig(vo);
	}
	@Override
	public OperateActivityAddedBO addAddCodeConfig(OperateActivityAddedVO vo) {
		return iOperateMgrService.addAddCodeConfig(vo);
	}

	@Override
	public int delAddCodeConfig(OperateActivityAddedVO vo) {
		return iOperateMgrService.delAddCodeConfig(vo);
	}
	
	@Override
	public int updateAddCodeIssue(OperateActivityAddedVO vo){
		return iOperateMgrService.updateAddCodeIssue(vo);
	}

	@Override
	public OperateActivityRechargeBO addRechargeConfig(OperateActivityRechargeVO vo) {
		return iOperateMgrService.addRechargeConfig(vo);
	}

	@Override
	public ResultBO<List<OperateCouponTempBO>> findRechargeDetail(OperateActivityRechargeVO vo) {
		OperateCouponTempVO couponVO = new OperateCouponTempVO();
		couponVO.setConfigId(vo.getId());
		couponVO.setConfigType(CouponConfigTypeEnum.ACTIVITY_RULE.getValue());
		return iOperateMgrService.findCouponTemp(couponVO);
	}

	/**
	 * 查询世界杯冠军竞猜球队信息
	 * @return
	 * @param vo
	 */
	@Override
	public List<OperateActivityChpGuessBO> findChpTeams(OperateActivityChpGuessBO vo) {
		return iOperateMgrService.findChpTeams(vo);
	}

	@Override
	public PagingBO<OperateActivityChpInfoBO> pageChpUsers(OperateActivityChpInfoBO vo) {
		return iOperateMgrService.pageChpUsers(vo);
	}

	@Override
	public ByteArrayOutputStream excelChpUsers(OperateActivityChpInfoBO vo) {
		List<OperateActivityChpInfoBO> data=  iOperateMgrService.findChpUsers(vo);
		return excelExportService.dataToExeclByStream("chp_users", data);
	}

	@Override
	public int addActivityChpInfo(List<OperateActivityChpInfoBO> chpInfoes) {
		return iOperateMgrService.addActivityChpInfo(chpInfoes);
	}
}