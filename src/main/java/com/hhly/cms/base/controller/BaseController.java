package com.hhly.cms.base.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.service.IFile;
import com.hhly.cms.utils.DateUtil;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.exception.ValidException;
import com.hhly.skeleton.base.valid.MatchPattern;

public class BaseController {
	

	private static final ResultBO<?> SUCCESS;

	private static final ResultBO<?> FAIL;
	
	private final String CUS_MOBILE= "cus_mobile";
	
	static {
		SUCCESS = new ResultBO<>();
		SUCCESS.setErrorCode(ResultBO.SUCCESS_CODE);
		SUCCESS.setSuccess(1);
		SUCCESS.setMessage("操作成功");
		FAIL = new ResultBO<>();
		FAIL.setErrorCode(ResultBO.FAIL_CODE);
		FAIL.setSuccess(0);
		FAIL.setMessage("操作失败");
	}
	@Resource(name="fileProxy")
	protected IFile uploadFile;
	
	@Value("${before_file_url}")
	protected String beforeFileUrl;
	
	@Value("${before_file_dir}")
	protected String beforeFileDir;

	
	// 为了nigix映射方便，上传文件夹要加"_"前缀
	protected final String defaultImageDir = "_upload_images/default";  

	/**
	 * 获取登录用户真实姓名
	 * 
	 * @return
	 */
	protected String getUserRealName(HttpSession session) {
		return (String) session.getAttribute(WebConstant.LOGINNAME);
	}

	/**
	 * @param session
	 *            会话
	 * @return 登录账户
	 * @Desc 获取登录账户
	 */
	protected String getUserName(HttpSession session) {
		return (String) session.getAttribute(WebConstant.USERNAME);
	}

	/**
	 * @desc   获取用户昵称
	 * @author Tony Wang
	 * @create 2017年4月27日
	 * @param session
	 * @return 
	 */
	protected String getUserCName(HttpSession session) {
		return (String) session.getAttribute(WebConstant.USER_CNAME);
	}
	
	/**
	 * 前端返回
	 * 
	 * @param resultMsg
	 * @return
	 */
	public String returnMsg(Object resultMsg) {
		String str = "success";
		if (resultMsg instanceof Boolean) {
			str = (Boolean) resultMsg == true ? "success" : "fail";
		} else {
			str = resultMsg.toString();
		}
		return str;
	}

	/**
	 * @param flag
	 *            结果标识 true/false
	 * @return 结果标识
	 * @Desc 返回结果标识
	 */
	protected ResultBO<?> getResult(boolean flag) {
		if (flag) {
			return SUCCESS;
		}
		return FAIL;
	}

	/**
	 * 用于获取保存返回结果判断
	 * 
	 * @param num
	 *            受影响行数
	 * @return
	 */
	protected ResultBO<?> getSaveResult(int num) {
		return getResult(num > 0);
	}

	/**
	 * 获取成功结果
	 * 
	 * @param t
	 * @return
	 */
	protected <T> ResultBO<T> getResultSuccess(T t) {
		ResultBO<T> resultBO = new ResultBO<T>(1, ResultBO.SUCCESS_CODE, t);
		resultBO.setMessage("操作成功");
		return resultBO;
	}
	/**
	 * excel导出
	 * 
	 * @param outputStream
	 * @param fileName
	 * @throws IOException
	 */
	protected void excel(String fileName, ByteArrayOutputStream outputStream, HttpServletResponse response)
			throws IOException {
		fileName = fileName + SymbolConstants.UNDERLINE + DateUtil.getNow("yyMMddHHmmss") + ".xls";
		ServletOutputStream sos = response.getOutputStream();
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		outputStream.writeTo(sos);
	}

	/**
	 * 对上传文件类容进行解析 文件中每一行解析为一个字符串,会去除前后空格
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	protected List<String> analysisFile(MultipartFile file) {
		String str = null;
		try {
			String charet = codeString(file);
			str = new String(file.getBytes(), charet);
		} catch (Exception e) {
			throw new ResultJsonException("文件解析错误！", e);
		}
		// 将内容回车换行替换
		// 参数验证
		str = str.replaceAll("[\\t\\r]", StringUtils.EMPTY);
		str = str.replaceAll("[\\ufeff]", StringUtils.EMPTY);
		String value[] = str.split("\\n");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < value.length; i++) {
			String v = value[i].trim();
			if (StringUtils.isNotEmpty(v)) {
				list.add(v);
			}
		}
		return list;
	}
	/**
	 * 上传文件到服务器
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 下午3:43:19
	 * @param file 文件
	 * @param catalogue 文件保存路径
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected UploadFileBO uploadFile(MultipartFile file, String catalogue) throws IllegalStateException, IOException {
		return uploadFile.uploadFile(file, catalogue);
	}
	/**
	 * 删除文件
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月20日 上午10:49:11
	 * @param fileName
	 */
	public void deleteFile(String ... fileName ){
		uploadFile.deleteFile(fileName);
	}

	/**
	 * 批量会员信息查询
	 * @param str 会员信息 
	 * @return
	 */
	protected List<String> beachSearchCondition(String str,String type) {
		// 将内容回车换行替换
		List<String> list = analysisContent(str);
		// 参数验证
		if(CollectionUtils.isEmpty(list)|| StringUtils.isEmpty(type)){
			throw new ValidException("参数错误！");
		}else if(CUS_MOBILE.equals(type)){
			for (String string : list) {
				if(!string.matches(MatchPattern.NUM)){
					throw new ValidException("参数错误！手机号必须是纯数字");
				}
			}
		}
		return list;
	}
	/**
	 * 文件中每一行解析为一个字符串,会去除前后空格
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 上午10:56:27
	 * @param str
	 * @return
	 */
	protected List<String> analysisContent(String str){
		str = str.replaceAll("[\\t\\r]", StringUtils.EMPTY);
		String value[] = str.split("\\n");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < value.length; i++) {
			String v = value[i].trim();
			if (StringUtils.isNotEmpty(v)) {
				list.add(value[i]);
			}
		}
		return list;
	}
	
	/** 
	 * 判断只用记事本打开的txt文件的编码格式 
	 * 只适合带了bom头的文件
	 * @param fileName :file 
	 * @return 文件编码格式 
	 * @throws Exception 
	 */ 	
	protected String codeString(MultipartFile file) throws Exception {  
	    BufferedInputStream bin = new BufferedInputStream(file.getInputStream());  
	    int p = (bin.read() << 8) + bin.read();  
	    String code = null;  
	      
	    switch (p) {  
	        case 0xefbb:  
	            code = "UTF-8";  
	            break;  
	        case 0xfffe:  
	            code = "Unicode";  
	            break;  
	        case 0xfeff:  
	            code = "UTF-16BE";  
	            break;  
	        default:  
	            code = "GBK";  
	    }  
	    return code;
	}
	
}
