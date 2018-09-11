/**
 * @Desc    缓存delete方法注解
 * @author  scott
 * @date    2017-1-17
 * @version v1.0
 */
package com.hhly.cms.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hhly.skeleton.base.common.cache.CacheEnumService;



@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface DeleteBatchAssignCache {  
	
	/** 获取缓存枚举对象 **/
	Class<?> GetCacheEnumService() default CacheEnumService.class;
	
	boolean isLikeQuery() default true;
}
