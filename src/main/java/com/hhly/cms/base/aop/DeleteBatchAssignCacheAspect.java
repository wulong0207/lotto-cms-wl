package com.hhly.cms.base.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;


@Aspect
@Component
public class DeleteBatchAssignCacheAspect {
	
	private static Logger log = Logger.getLogger(DeleteBatchAssignCacheAspect.class);

	@Autowired
	private IRedisMgrService redisMgrService;
	
	@Pointcut("@annotation(com.hhly.cms.base.annotation.DeleteBatchAssignCache)")
	public void deleteCachePointcut(){}


	@AfterReturning(pointcut = "deleteCachePointcut()", returning="rvt")
	@SuppressWarnings("unchecked")
	public Object deleteCache(JoinPoint joinPoint, Object rvt) throws Exception {
		Object result = null;
		Exception exception = null;
		boolean flag = false;
		Method targetMethod = null;
		try {
			ResultBO<?> BO = (ResultBO<?>) rvt;
			if(BO.getErrorCode().equals(ResultBO.SUCCESS_CODE)){
				log.info("当新增,修改,删除执行成功时, 执行删除缓存操作");
				MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
				Method method = methodSignature .getMethod();
				DeleteBatchAssignCache cacheable = method.getAnnotation(DeleteBatchAssignCache.class);
				Class<?> service = cacheable.GetCacheEnumService();
				boolean isLikeQuery = cacheable.isLikeQuery();
//				Method targetMethod = service.getMethod("getCacheKey");
				Method[] methods = service.getMethods();
				if(!ObjectUtil.isBlank(methods)){
					for (int i = 0; i < methods.length; i++) {
						targetMethod = methods[i];
						if(targetMethod.getName().equals("getCacheKey") && 
								targetMethod.getParameterTypes().length == 0){
							flag = true;
							break;
						}
					}
					if(flag){
						List<String> list = (List<String>) targetMethod.invoke(null);	
						redisMgrService.delCacheList(list, isLikeQuery);					
					}				
				}				
			}
		} catch (Exception e) {
			exception = e;
			log.error("AOP批量删除缓存异常 : " + e.getMessage());
		} finally {
			if(exception != null){
				// 把异常继续抛
				throw exception;
			}
		}
		return result;
	}
}
