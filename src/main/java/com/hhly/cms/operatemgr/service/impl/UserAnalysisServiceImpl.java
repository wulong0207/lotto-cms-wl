package com.hhly.cms.operatemgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cms.operatemgr.service.UserAnalysisService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateUserAnalysisBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateUserAnalysisExcelBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateUserAnalysisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 会员访问信息分析的数据接口实现
 * @author huangb
 * @date 2017年2月10日
 * @company 益彩网络
 * @version v1.0
 */
@Service
public class UserAnalysisServiceImpl implements UserAnalysisService {

	/**
	 * 运营管理服务（其中包含会员访问信息分析的接口）
	 */
	@Autowired
	private IOperateMgrService iOperateMgrService;
	
	/**
	 * 渠道的服务接口
	 */
	@Autowired
	private MarketChannelService marketChannelService;

	/**
	 * 导出服务
	 */
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<OperateUserAnalysisBO> findPaging(OperateUserAnalysisVO operateUserAnalysis) {
		return iOperateMgrService.findPagingUserAnalysis(operateUserAnalysis);
	}

	@Override
	public ByteArrayOutputStream findExcel(OperateUserAnalysisVO operateUserAnalysis) {
		List<OperateUserAnalysisBO> data = iOperateMgrService.findExcelUserAnalysis(operateUserAnalysis);
		List<OperateUserAnalysisExcelBO> targetList = new ArrayList<OperateUserAnalysisExcelBO>();
		OperateUserAnalysisExcelBO target = null;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				target = new OperateUserAnalysisExcelBO(i + 1, data.get(i));
				targetList.add(target);
			}
		}
		// 渠道字典
		Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
		otherDic.put("channelId", marketChannelService.findAllChannelDic());
		return excelExportService.dataToExeclByStreamDictionary("user analysis", targetList, otherDic);
	}
}
