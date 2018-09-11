package com.hhly.cms.operatemgr.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.exception.ResultJsonException;

/**
 * @desc 多媒体上传
 * @author jiangwei
 * @date 2017年4月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/operatemgr/multimedia")
public class MultimediaController extends BaseController {

	private static Logger logger = LogManager.getLogger(MultimediaController.class);
	/**
	 * 上传图片到图库
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月10日 下午4:12:56
	 * @param file
	 * @param session
	 * @param catalogue
	 *            图片保存模块路径
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload")
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public Object upload(@RequestPart MultipartFile[] file, HttpSession session,
			@RequestParam(required = true, value = "catalogue") String catalogue)
			throws IllegalStateException, IOException {
		if (null == file || file.length == 0) {
			throw new ResultJsonException("请选择一个文件");
		}
		// 按ueditor的格式返回
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("state", "ERROR");
		for (MultipartFile multipartFile : file) {
			if (multipartFile == null || multipartFile.getSize() == 0) {
				continue;
			}
			UploadFileBO bo = null;
			try {
				bo = uploadFile(multipartFile, catalogue);
			} catch (Exception e) {
				logger.error(e);
				return param;
			}
			param.put("title", bo.getName());
			param.put("url", uploadFile.getUrl() + bo.getDir());
			param.put("size", multipartFile.getSize());
			param.put("original", multipartFile.getOriginalFilename());
			param.put("type", multipartFile.getContentType());
		}
		param.put("state", "SUCCESS");
		return param;
	}
}
