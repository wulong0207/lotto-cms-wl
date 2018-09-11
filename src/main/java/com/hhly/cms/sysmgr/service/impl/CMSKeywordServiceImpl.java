package com.hhly.cms.sysmgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sysmgr.service.CMSKeywordService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.sysmgr.bo.CmsKeywordBO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsKeywordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CMSKeywordServiceImpl implements CMSKeywordService {

	@Autowired
	private ISysMgrService iSysMgrService;
	 @Autowired
	    private ExcelExportService excelExportService;
	
	@Override
	public int addKeywordList(CmsKeywordVO vo) {
		
		String[] keywordArray=vo.getKeywords().split(SymbolConstants.COMMA);
		
		List<CmsKeywordVO> voList=new ArrayList<CmsKeywordVO>();
		for(String keyword:keywordArray){
			CmsKeywordVO indexVo=new CmsKeywordVO();
			
			indexVo.setKeyword(keyword);
			indexVo.setReplaced(vo.getReplaced());
			indexVo.setStatus(vo.getStatus());
			indexVo.setGrade(vo.getGrade());
			indexVo.setCreateBy(vo.getCreateBy());
			
			voList.add(indexVo);
			
		}
		
		
		return iSysMgrService.addKeywordList(voList);
	}

	@Override
	public PagingBO<CmsKeywordBO> findKeyword(CmsKeywordVO vo) {
		return iSysMgrService.findKeyword(vo);
	}

	@Override
	public int updateKeywordList(List<CmsKeywordVO> list) {
		return iSysMgrService.updateKeywordList(list);
	}

	@Override
	public ByteArrayOutputStream getExcelStream(CmsKeywordVO vo) {
		List<CmsKeywordBO> data=iSysMgrService.keywordExcelList(vo);
		return excelExportService.dataToExeclByStream("keyword",data);
	}

}
