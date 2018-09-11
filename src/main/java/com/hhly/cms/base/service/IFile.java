package com.hhly.cms.base.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;

public interface IFile {
	/**
	 * 上传文件  
	 * 如果file 为模拟 MockMultipartFile，catalogue参数为文件保存路径
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月20日 下午3:41:28
	 * @param file
	 * @param catalogue
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	UploadFileBO uploadFile(MultipartFile file, String catalogue) throws IllegalStateException, IOException;
	/**
	 * 流上传文件
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月20日 下午4:23:06
	 * @param stream
	 * @param catalogue
	 * @return 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	List<QiniuUploadResultVO> uploadFileStream(byte[] stream, String catalogue) throws IllegalStateException, IOException;
	/**
	 * 删除文件
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月20日 下午3:41:49
	 * @param fileName
	 */
	void deleteFile(String ... fileName);
	/**
	 * 获取访问路径
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月20日 下午3:49:08
	 * @return
	 */
	String  getUrl();
}
