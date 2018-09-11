package com.hhly.cms.base.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.utils.DateUtil;
import com.hhly.cms.utils.StringUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
/**
 * 七牛
 * @desc
 * @author jiangwei
 * @date 2017年9月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class SevenCattleFile extends AbstractFile {
	
	/** 7牛accessKey  **/
	@Value("${accessKey}")
	protected String accessKey;
	/** 7牛secretKey **/
	@Value("${secretKey}")
	protected String secretKey;
	/** bucketName **/
	@Value("${bucketName}")
	protected String bucketName;
	/** 允许批量上传文件数量  **/
	@Value("${uploadLimit}")
	protected Integer uploadLimit;
	/** 允许上传文件类型 **/
	@Value("${fileType}")
	protected String fileType;
	/** 文件访问路径  **/
	@Value("${uploadURL}")
	protected String uploadURL;
	/**域名和文件名中间的路径*/
	@Value("${savePath}")
	protected String savePath;
	/**允许批量上传文件大小*/
	@Value("${limitSize}")
	protected String limitSize;
	
	@Override
	public UploadFileBO uploadFile(MultipartFile file, String catalogue) throws IllegalStateException, IOException {
		QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, savePath, Long.parseLong(limitSize));
		qiniuUploadVO.setUploadURL(uploadURL);
		String name = "";
		String imagePath = "";
		if(StringUtils.isEmpty(file.getOriginalFilename())){
			imagePath =  catalogue;
		}else{
			// 拆分文件名和后缀
		    String[] tokens = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
		    // 新的文件名
		    String newFileName = DateUtil.getNow("yyMMddkkmmss") + getAscend() + SymbolConstants.DOT + tokens[1];
		    name = tokens[0];
		 // 这个是指定文件相对路径方式上传，如果使用了这个相对路径，上传时优先使用该路径
		    imagePath = catalogue + SymbolConstants.OBLIQUE_LINE +newFileName;
		}
		qiniuUploadVO.setFileRelativePath(imagePath);
		QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);

		// 1、普通上传
		ResultBO<?> resultBO = qiniuUpload.uploadFile(file);
		if(resultBO.isOK()){
			@SuppressWarnings("unchecked")
			List<QiniuUploadResultVO> list =  (List<QiniuUploadResultVO>) resultBO.getData();
			if(list.size() == 0 || !QiniuUploadResultVO.SUCCESS_CODE.equals(list.get(0).getCode())){
				throw new ServiceRuntimeException("上传图片失败");
			}
		}
		// 获取分辨率		
		BufferedImage image = ImageIO.read(file.getInputStream());
		
		UploadFileBO vo = new UploadFileBO();
		vo.setDir(imagePath);
		vo.setName(name);
		if(image != null){
			vo.setResolution(image.getWidth() + " * " + image.getHeight());	
		}
		vo.setSizes(String.valueOf(file.getSize() / 1024));
		vo.setUrl(vo.getDir());
		return vo;
	}

	@Override
	public void deleteFile(String... fileName) {
		QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, savePath, Long.parseLong(limitSize));
		QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
		 ResultBO<?> resultBO = null;
		if (fileName.length > 1) {
			resultBO = qiniuUpload.deleteFiles(fileName);
		} else {
			resultBO = qiniuUpload.deleteFile(fileName[0]);
		}
		if(!resultBO.isOK()){
			throw new ServiceRuntimeException("删除文件失败");
		}
	}

	@Override
	public String getUrl() {
		return uploadURL;
	}

	@Override
	public List<QiniuUploadResultVO> uploadFileStream(byte[] stream, String catalogue) throws IllegalStateException, IOException {
		QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, savePath, Long.parseLong(limitSize));
		qiniuUploadVO.setUploadURL(uploadURL);
		// 这个是指定文件相对路径方式上传，如果使用了这个相对路径，上传时优先使用该路径
		qiniuUploadVO.setFileRelativePath(catalogue);
		QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);

		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(stream);
		ResultBO<?> resultBO = qiniuUpload.uploadFileNotRename(byteInputStream);
		if(resultBO.isOK()){
			@SuppressWarnings("unchecked")
			List<QiniuUploadResultVO> list = (List<QiniuUploadResultVO>) resultBO.getData();
			if(list.size() ==1 && QiniuUploadResultVO.SUCCESS_CODE.equals(list.get(0).getCode())){
				return list;
			}
			
		}
		throw new ServiceRuntimeException("上传文件失败");
	}

}
