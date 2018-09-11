package com.hhly.cms.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.skeleton.base.excel.ReplaceService;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-11 下午2:31:20
 * @Desc 数据替换服务（用字典数据来替换结果值）
 */
@Service
public class DictionaryReplaceService extends ReplaceService {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	protected Map<String, Map<String,String>> getDictionary(
			Map<String, String> attrDicCode) {
		Map<String, Map<String,String>> result = new HashMap<String,Map<String,String>>();
		
		Map<String, Map<String,String>> dics = dictionaryService.findDictionaryMap(attrDicCode.values());
		for (Map.Entry<String,String> entry : attrDicCode.entrySet()) {
			if(dics.containsKey(entry.getValue())){
				result.put(entry.getKey(), dics.get(entry.getValue()));
			}
		}
		return result;
	}

}
