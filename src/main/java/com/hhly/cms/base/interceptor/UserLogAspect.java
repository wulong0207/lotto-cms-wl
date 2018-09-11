package com.hhly.cms.base.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.customermgr.service.UserLogService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.cms.sysmgr.vo.CmsUserLogVO;

@Aspect
@Component
public class UserLogAspect {
	
	private static Logger logger = LogManager.getLogger(UserLogAspect.class);
	
	@Autowired
	private UserLogService userLogService;
	
	@Pointcut("execution(* com.hhly.cms.sysmgr.controller.DictionaryController.list*(..)) || "
			+ "execution(* com.hhly.cms.sysmgr.controller.DictionaryController.index(..)) || "
			// CMS用户日志操作记录时，在做图片上传时不要记录了，数据量太大了。
			+ "execution(* com.hhly.cms.operatemgr.controller.ImageController.*(..)) || " 
			+ "execution(* com.hhly.cms.base.controller.BaseController.*(..)) || " 
			// 参数包含response的controller方法不做切面
			+ "execution(* com.hhly..controller..*(.., javax.servlet.http.HttpServletResponse, ..)) || "
			// 参数包含request的controller方法不做切面
			// com.hhly.cms.taskmgr.controller.TaskController.list(TaskVO, HttpServletRequest) 报 java.lang.IllegalStateException: getInputStream() has already been called for this request
			+ "execution(* com.hhly..controller..*(.., javax.servlet.http.HttpServletRequest, ..))"
			)
	public void dontLog(){}
	@Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
	public void needToLog(){}
	@Pointcut("@target(classRequestMapping) && @annotation(requestMapping) && (needToLog() && ! dontLog())")
	public void userLogPointcut(RequestMapping classRequestMapping, RequestMapping requestMapping){}


	@Around("userLogPointcut(classRequestMapping, requestMapping)")
	public Object addUserLog(ProceedingJoinPoint joinpoint, RequestMapping classRequestMapping, RequestMapping requestMapping) throws Exception {
		Object result = null;
		Exception exception = null;
		CmsUserLogVO vo = null;
		try {
			vo = new CmsUserLogVO();
			MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
			Method method = methodSignature .getMethod();
			vo.setDetails(method.toString());
			vo.setInContent(JSON.toJSONString(joinpoint.getArgs()));
			// controller类上的RequestMapping注解的值
			vo.setControllerPath(classRequestMapping.value()[0]);
			HttpSession session = (HttpSession) RequestContextHolder
                    .currentRequestAttributes()
                    .resolveReference(RequestAttributes.REFERENCE_SESSION);
			vo.setUserName((String)session.getAttribute(WebConstant.LOGINNAME));
			try {
				result = joinpoint.proceed();	
			} catch (Exception e) {
				exception = e;
			}
			// 如查有异常，则记录异常，否则记录方法的执行结果
			if(exception == null) {
				vo.setOutContent(JSON.toJSONString(result));
			} else {
				vo.setOutContent(ExceptionUtils.getStackTrace(exception));
			}
			// 若UserLogService异常，要确保不影响主流程执行
			try {
				userLogService.add(vo);
			} catch (Exception e) {
				logger.error("UserLogService记录日志出错！参数为："+vo);
			}
			
		} catch (Throwable t) {
			logger.error(t.getStackTrace());
		}
		if(exception != null){
			// 把异常继续抛
			throw exception;
		}
		return result;
	}
}
