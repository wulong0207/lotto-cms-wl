package com.hhly.cms.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author Bruce Liu
 * @create on: 2016-5-11  下午05:25:04
 * @describe : used it to get/set Properties' value
 */
public class PropertiesHandler {
	private static Properties prop = null;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropertiesHandler.class);
	
	private static final String SYS_PROPERTIES_PATH = "sys.properties";
    
	static{
		try {
			prop  = PropertiesLoaderUtils.loadAllProperties(SYS_PROPERTIES_PATH);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			
		}
		
	}
	/**
	 * 获取系统配置文件 sys.properties 的属性
	 * @param propertyName
	 * @return
	 */
	public static String getConfigValue(String propertyName) {
		return prop.getProperty(propertyName);
	}
	
	public static void setConfigValue(String propertyName, String propertyValue) {
		prop.setProperty(propertyName, propertyValue);
	}
	
	public static String getPropertiesValue(String ClassPathResource,String key){
		Properties properties =null;
		try {
			properties = PropertiesLoaderUtils.loadAllProperties(ClassPathResource);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		return properties.getProperty(key);
	}
}
