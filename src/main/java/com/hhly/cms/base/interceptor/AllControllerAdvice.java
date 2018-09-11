package com.hhly.cms.base.interceptor;

import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.exception.ValidException;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-15 下午3:19:12
 * @Desc 异常处理
 */
@ControllerAdvice
public class AllControllerAdvice {
	
	public static  Logger logger = Logger.getLogger(AllControllerAdvice.class);
	/**
	 * 用于解析日期格式参数
	 * @param binder
	 */
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}
	/**
	 * 异常处理
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody Object exp(HttpServletRequest request, IllegalArgumentException ex) {
		handleException(ex);
		return ResultBO.errMessage("20001",ex.getMessage());
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public @ResponseBody Object exp(HttpServletRequest request, IllegalStateException ex) {
		handleException(ex);
		return ResultBO.errMessage("20002",ex.getMessage());
	}
	@ExceptionHandler(ValidException.class)
	public @ResponseBody Object exp(HttpServletRequest request, ValidException ex) {
		handleException(ex);
		return ResultBO.errMessage("20001",ex.getMessage());
	}
	
	@ExceptionHandler(ResultJsonException.class)
	public @ResponseBody Object exp(HttpServletRequest request, ResultJsonException ex) {
		handleException(ex);
		if(ex.getResult() != null) {
			return ex.getResult();
		}
		return ResultBO.errMessage(String.valueOf(ResultBO.FAIL_CODE),ex.getMessage());
	}
	
	@ExceptionHandler(ServiceRuntimeException.class)
	public @ResponseBody Object exp(HttpServletRequest request, ServiceRuntimeException ex) {
		handleException(ex);
		return ResultBO.errMessage(ex.getCode(),ex.getMsg());
	}

	
	@ExceptionHandler(Exception.class)
	public @ResponseBody Object exp(HttpServletRequest request, Exception ex) {
		handleException(ex);
		return ResultBO.err();
	}
	
	
	private void handleException(Exception ex){
		logger.error(ex);
		ex.printStackTrace();
	}
		
}
