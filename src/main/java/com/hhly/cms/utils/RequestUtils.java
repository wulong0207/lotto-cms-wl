package com.hhly.cms.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.StringUtil;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class RequestUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, String> converMap(String queryString){
		if(!StringUtil.isBlank(queryString)){
			try {
				Map<String, String> param = new HashMap<String, String>();				
				queryString = URLDecoder.decode(queryString,"UTF-8");
				String[] querys = queryString.split(SymbolConstants.AND);
				for (String query : querys) {
					String[] val = query.split(SymbolConstants.EQUAL_SIGN);
					if(val.length == 2){
						param.put(val[0], val[1]);
					}
				}
				return param;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return Collections.EMPTY_MAP;
	}
	
	public static void main(String[] args) {
		String queryString = "jobId=1&cc=bb&_=1493896852193";
		converMap(queryString);
	}
}
