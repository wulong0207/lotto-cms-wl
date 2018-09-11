package com.hhly.cms.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hhly.cms.base.common.AuthEnum;

/**
 * 用于识别在进行action调用的时候，标注该方法调用是否需要权限控制，需要什么样的权限的注解类。
 * 
 * 该注解类一般会包括两个属性，一个是需要的权限，一个是对应的action。
 *
 * @author Mohw
 *
 */
//表示在什么级别保存该注解信息
@Retention(RetentionPolicy.RUNTIME)
//表示该注解用于什么地方
@Target(ElementType.METHOD)
public @interface Authority {
	AuthEnum [] privilege();
    String url() default "";
}
