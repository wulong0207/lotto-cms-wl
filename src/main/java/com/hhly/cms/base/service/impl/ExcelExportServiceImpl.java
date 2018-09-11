package com.hhly.cms.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.excel.ReplaceService;
import com.hhly.skeleton.base.util.ExcelUtil;

@Service
public class ExcelExportServiceImpl  implements ExcelExportService {
	
	@Autowired
	ReplaceService replaceService;
	
	@Override
	public <T> ByteArrayOutputStream dataToExeclByStream(List<T> data) {
		return dataToExeclByStream(null, data, null);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStream(String name,
			List<T> data) {
		return dataToExeclByStream(name, data, null);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStream(String name,
			List<T> data, Map<String,Map<String,String>> otherDic) {
		name = name == null? "data":name;
		Map<String, List<T>> dataMap = new HashMap<>();
		if(data!=null && data.size()>0){
			dataMap.put(name, data);
		}
		return dataToExeclByStream(dataMap, otherDic);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStreamDictionary(String name,
			List<T> data, Map<String, List<DictionaryBO>> otherDic) {
		name = name == null? "data":name;
		Map<String, List<T>> dataMap = new HashMap<>();
		dataMap.put(name, data);
		return dataToExeclByStreamDictionary(dataMap, otherDic);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStream(Map<String, List<T>> data,
			Map<String, Map<String, String>> otherDic) {
		for (List<T> d : data.values()) {
			replaceService.dataReplace(d, otherDic);
		}
		return ExcelUtil.dataToExeclByStream(data);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStreamDictionary(Map<String, List<T>> data,
			Map<String, List<DictionaryBO>> otherDic) {
		Map<String,Map<String,String>> dic = new HashMap<String, Map<String,String>>();
		for (Map.Entry<String,List<DictionaryBO>> entry : otherDic.entrySet()) {
			Map<String,String> map = new HashMap<String, String>();
			for (DictionaryBO bo : entry.getValue()) {
				map.put(bo.getId(), bo.getText());
			}
			dic.put(entry.getKey(), map);
		}
		return dataToExeclByStream(data, dic);
	}

	@Override
	public <T> ByteArrayOutputStream dataToExeclByStream(Map<String, List<T>> data) {
		return dataToExeclByStream(data,null);
	}
}
