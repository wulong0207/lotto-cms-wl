package com.hhly.cms.base.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
/**
 * @desc 文件上传代理
 * @author jiangwei
 * @date 2017年9月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class FileProxy implements IFile{
	
	@Resource(name="sevenCattleFile")
	private IFile uploadFile;

	@Override
	public UploadFileBO uploadFile(MultipartFile file, String catalogue) throws IllegalStateException, IOException {
		return uploadFile.uploadFile(file, catalogue);
	}

	@Override
	public void deleteFile(String... fileName) {
		uploadFile.deleteFile(fileName);
	}

	@Override
	public String getUrl() {
		return uploadFile.getUrl();
	}

	@Override
	public List<QiniuUploadResultVO> uploadFileStream(byte[] stream, String catalogue) throws IllegalStateException, IOException {
		 return uploadFile.uploadFileStream(stream, catalogue);
	}

}
