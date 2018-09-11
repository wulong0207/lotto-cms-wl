package com.hhly.cms.dicmgr.service.impl;

import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataBO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataDetailVO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class DictionaryServiceImpl implements DictionaryService{
	
	@Autowired
	private ISysMgrService iSysMgrService;

	@Override
	public PagingBO<DicDataBO> find(DicDataVO vo) {
		return  iSysMgrService.findDicData(vo);
	}

	@Override
	public int add(DicDataVO vo) {
		return iSysMgrService.addDicData(vo);
	}

	@Override
	public int update(DicDataVO vo) {
		return iSysMgrService.updDicData(vo);
	}

	@Override
	public List<DicDataDetailBO> findDetail(StringVO vo) {
		return iSysMgrService.findDicDataDetail(vo);
	}

	@Override
	public PagingBO<DicDataDetailBO> findDetails(DicDataDetailVO vo) {
		return iSysMgrService.findDicDataDetails(vo);
	}

	@Override
	public int addDetail(DicDataDetailVO vo) {
		
		return iSysMgrService.addDicDataDetail(vo);
	}

	@Override
	public int updDetail(DicDataDetailVO vo) {
		
		return iSysMgrService.updDicDataDetail(vo);
	}

	@Override
	public int delDetail(StringVO vo) {
		return iSysMgrService.delDicDataDetail(vo);
	}

	@Override
	public Map<String, List<DictionaryBO>> findDictionary(StringVO vo) {
		List<DicDataDetailBO> list = iSysMgrService.findDicDataDetailSimple(vo);
		Map<String, List<DictionaryBO>> result = packagingDictionary(list);
		return result;
	}

	private Map<String, List<DictionaryBO>> packagingDictionary(
			List<DicDataDetailBO> list) {
		Map<String, List<DictionaryBO>> result = new HashMap<String, List<DictionaryBO>>();
		//数据封装
		for (DicDataDetailBO dicBO : list) {
			List<DictionaryBO> dictionaryBOs;
			if(!result.containsKey(dicBO.getDicCode())){
				dictionaryBOs= new ArrayList<DictionaryBO>();
				result.put(dicBO.getDicCode(), dictionaryBOs);
			}else{
				dictionaryBOs = result.get(dicBO.getDicCode());
			}
			DictionaryBO bo = new DictionaryBO();
			bo.setId(dicBO.getDicDataValue());
			bo.setText(dicBO.getDicDataName());
			dictionaryBOs.add(bo);
		}
		return result;
	}

	@Override
	public Map<String, Map<String, String>> findDictionaryMap(Collection<String> dicCode) {
		StringVO vo = new StringVO();
		vo.setStr(StringUtils.join(dicCode, SymbolConstants.COMMA));
		List<DicDataDetailBO> list = iSysMgrService.findDicDataDetailSimple(vo);
		
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		//数据封装
		for (DicDataDetailBO dicBO : list) {
			Map<String, String> map;
			if(!result.containsKey(dicBO.getDicCode())){
				map= new HashMap<String, String>();
				result.put(dicBO.getDicCode(), map);
			}else{
				map = result.get(dicBO.getDicCode());
			}
			map.put(dicBO.getDicDataValue(), dicBO.getDicDataName());
		}
		return result;
	}

	@Override
	public List<DictionaryBO> findRcmdUserLevel() {
		return iSysMgrService.findRcmdUserLevel();
	}
}
