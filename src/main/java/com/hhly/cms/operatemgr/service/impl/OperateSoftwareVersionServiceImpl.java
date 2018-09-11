package com.hhly.cms.operatemgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hhly.cms.operatemgr.service.OperateSoftwareVersionService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSoftwareVersionBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年5月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateSoftwareVersionServiceImpl implements OperateSoftwareVersionService {
	
	@Value("${before_file_url}")
	protected String beforeFileUrl;
	
	@Autowired
	private IOperateMgrService iOperateMgrService;

	@Override
	public int addSoftwareVersion(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.addSoftwareVersion(vo);
	}

	@Override
	public PagingBO<OperateSoftwareVersionBO> findSoftwareVersionList(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.findSoftwareVersionList(vo);
	}
	
	
	@Override
	public List<DictionaryBO> findBaseSoftwareVersion(OperateSoftwareVersionVO vo) {
		List<OperateSoftwareVersionBO> list = iOperateMgrService.findBaseSoftwareVersion(vo);
		return DicUtils.toDic(list, "code", Integer.class, "code", Integer.class);
	}
	
	@Override
	public int delSoftwareVersionByIds(StringVO vo) {
		return iOperateMgrService.delSoftwareVersionByIds(vo);
	}

	@Override
	public int updSoftwareVersion(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.updSoftwareVersion(vo);
	}
	
	@Override
	public int updateIsnew(int isnew, int type) {
		return iOperateMgrService.updateIsnew(isnew, type);
	}

	@Override
	public int valiSoftwareVersionCode(int code) {
		return iOperateMgrService.valiSoftwareVersionCode(code);
	}

	@Override
	public int count(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.countSoftwareVersion(vo);
	}

	@Override
	public int updSoftwareVersionSwitch(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.updSoftwareVersionSwitch(vo);
	}

	@Override
	public List<OperateSoftwareVersionBO> findNewSoftwareVersion(OperateSoftwareVersionVO vo) {
		List<OperateSoftwareVersionBO> list = iOperateMgrService.findNewSoftwareVersion(vo);
		if(!org.springframework.util.CollectionUtils.isEmpty(list)) {
			for(OperateSoftwareVersionBO bo : list) {
				if(StringUtil.hasText(bo.getNewAppUrl()))
					bo.setNewAppUrl(beforeFileUrl + bo.getNewAppUrl());
			}
		}
		return list;
	}

	@Override
	public OperateSoftwareVersionBO findOneSoftwareVersion(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.findOneSoftwareVersion(vo);
	}

//	@Override
//	public int updSoftwareVersionSwitchByChannlId(OperateSoftwareVersionVO vo) {
//		return iOperateMgrService.updSoftwareVersionSwitchByChannlId(vo);
//	}

	@Override
	public int updSoftwareVersionSwitchSynOfficial(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.updSoftwareVersionSwitchSynOfficial(vo);
	}

	@Override
	public List<OperateSoftwareVersionBO> distinctSoftwareVersionName(OperateSoftwareVersionVO vo) {
		return iOperateMgrService.distinctSoftwareVersionName(vo);
	}

}
