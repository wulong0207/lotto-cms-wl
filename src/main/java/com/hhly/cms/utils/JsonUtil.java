package com.hhly.cms.utils;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Json 工具类
 * @author HouXB
 *
 */
@SuppressWarnings("rawtypes")
public class JsonUtil {

	/**
	 * JSON 转 java对象
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static Object json2Object(String jsonString, Class cls) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return JSONObject.toBean(jsonObject, cls);
	}

	/**
	 * JSON 转 java对象 对象中有变量属于Class
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static Object json2Object(String jsonString, Class cls, Map<String, Class> classMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return JSONObject.toBean(jsonObject, cls, classMap);
	}
	
	/**
	 * json 转 java对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] json2ObjectArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * json 转 java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List json2ObjectList(String jsonString, Class cls) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object obj;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss"};
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
			jsonObject = jsonArray.getJSONObject(i);
			obj = JSONObject.toBean(jsonObject, cls);
			list.add(obj);
		}
		return list;
	}
	
	
	/**
     * json字符串转map集合
     * @author ducc
     * @param jsonStr json字符串
     * @param map 接收的map
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String jsonStr){
    	return JSONObject.fromObject(jsonStr); 
    }
    
	/**
	 * java对象 转 json
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2Json(Object obj) {
		JSONObject json;
		json = JSONObject.fromObject(obj);
		return json.toString();
	}
	
	/**
	 * java对象列表 转 json
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectList2Json(Object obj) {
		JSONArray json;
		json = JSONArray.fromObject(obj);
		return json.toString();
	}

	/**
	 * java对象 转 json, 并设定日期格式
	 * 
	 * @param obj
	 * @param dataFormat
	 * @return
	 */
	public static String object2Json(Object obj, String dataFormat) {

		if (dataFormat == null) {
			dataFormat = "yyyy-MM-dd HH:mm:ss";
		}
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(obj, jsonConfig);
		return json.toString();
	}
	
	/**
	 * java对象列表 转 json, 并设定日期格式
	 * 
	 * @param obj
	 * @param dataFormat
	 * @return
	 */
	public static String object2ListJson(Object obj, String dataFormat) {

		JSONArray json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONArray.fromObject(obj, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * JSON 时间解析器具
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	/**
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

}


class DateJsonValueProcessor implements JsonValueProcessor {  
	  
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";     
    private DateFormat dateFormat;
  
    /*
     * 构造方法.   
     * @param datePattern 日期格式   
     */    
    public DateJsonValueProcessor(String datePattern) {     
            
        if( null == datePattern )  
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);    
        else  
            dateFormat = new SimpleDateFormat(datePattern);   
    }     
  
      
    public Object processArrayValue(Object arg0, JsonConfig arg1){  
        return process(arg0);     
    }  
  
    public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2){  
        return process(arg1);     
    }  
      
    private Object process(Object value){
    	if (value == null) {
			return "";
		}
        return dateFormat.format((Date) value);     
    }     
}

