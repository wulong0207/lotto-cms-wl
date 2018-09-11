package com.hhly.cms.base.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.DictionaryBO;


/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-11 下午2:30:52
 * @Desc excel 导出服务
 */
public interface ExcelExportService {
	/**
	 * 导出excel属性类
	 * 
	 * @param data
	 * @return
	 */
	public <T> ByteArrayOutputStream dataToExeclByStream(List<T> data);

	/**
	 * 导出excel属性类
	 * 
	 * @param data
	 *            excel 原始数据
	 * @param name
	 *            导出excel 页名
	 * @return 生成二进制流
	 */
	public <T> ByteArrayOutputStream dataToExeclByStream(String name,
			List<T> data);

	/**
	 * 导出excel属性类，替换字典值，
	 * 
	 * @param data
	 *            excel 原始数据
	 * @param otherDic
	 *            其它字典值
	 * @param name
	 *            导出excel 页名
	 * @return 生成二进制流
	 */
	public <T> ByteArrayOutputStream dataToExeclByStream(String name,
			List<T> data, Map<String,Map<String,String>> otherDic);
	/**
	 * 字典对象
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 下午2:39:25
	 * @param name
	 * @param data
	 * @param otherDic
	 * @return
	 */
	public <T> ByteArrayOutputStream dataToExeclByStreamDictionary(String name,
			List<T> data, Map<String,List<DictionaryBO>> otherDic);
	/**
	 * excel 多页数据导出
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 下午2:55:30
	 * @param data
	 * @param otherDic
	 * @return
	 */
	public <T> ByteArrayOutputStream dataToExeclByStream(Map<String, List<T>> data, Map<String,Map<String,String>> otherDic);
	/**
	 * excel 多页数据导出
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 下午2:55:30
	 * @param data
	 * @param otherDic
	 * @return
	 */
	public <T> ByteArrayOutputStream dataToExeclByStream(Map<String, List<T>> data);
	/**
	 * excel 多页数据导出
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 下午2:55:56
	 * @param data
	 * @param otherDic
	 * @return
	 */
	public <T> ByteArrayOutputStream dataToExeclByStreamDictionary(Map<String, List<T>> data, Map<String,List<DictionaryBO>> otherDic);
}
