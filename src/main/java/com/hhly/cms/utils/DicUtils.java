package com.hhly.cms.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.hhly.skeleton.base.bo.DictionaryBO;

/**
 * @desc    字典工具类
 * @author  Tony Wang
 * @date    2017年4月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class DicUtils {

	/**
	 * @desc   转换成数据字典
	 * @author Tony Wang
	 * @create 2017年4月21日
	 * @param list 原始数据
	 * @param idField 原始数据中需转成字典id的字段名
	 * @param idClass 原始数据中需转成字典id的字段所属类
	 * @param textField 原始数据中需转成字典text的字段名
	 * @param textClass 原始数据中需转成字典text的字段所属类
	 * @return 
	 */
	public static <T> List<DictionaryBO> toDic(List<T> list, String idField, Class<?> idClass, String textField, Class<?> textClass) {
		if(CollectionUtils.isEmpty(list)) {
			return Collections.<DictionaryBO> emptyList();
		}
		MethodHandle idGetMethod = findGetMethod(list.get(0).getClass(), idField, idClass);
		MethodHandle textGetMethod = findGetMethod(list.get(0).getClass(), textField, textClass);
		List<DictionaryBO> dics = new ArrayList<DictionaryBO>();
		for(T t : list) {
			try {
				String id = idGetMethod.invoke(t).toString();
				String text = textGetMethod.invoke(t).toString();
				dics.add(new DictionaryBO(id, text));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return dics;
	}

	/**
	 * @desc   获取字段的get方法
	 * @author Tony Wang
	 * @create 2017年4月21日
	 * @param clazz
	 * @param fieldName
	 * @param fieldClass
	 * @return 
	 */
	private static <T> MethodHandle findGetMethod(Class<?> clazz, String fieldName, Class<?> fieldClass) {
		MethodHandle mh;
		MethodType mt = MethodType.methodType(fieldClass);
	    MethodHandles.Lookup lk = MethodHandles.lookup();
	    String methodName = new StringBuilder("get").append(fieldName.substring(0,1).toUpperCase()).append(fieldName.substring(1)).toString();
		try {
			mh = lk.findVirtual(clazz, methodName, mt);
		} catch (NoSuchMethodException | IllegalAccessException mhx) {
			throw (AssertionError) new AssertionError().initCause(mhx);
		}
		return mh;
	}
}
