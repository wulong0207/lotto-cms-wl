package com.hhly.cms.base.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.utils.DateUtil;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.util.FileUtil;

/**
 * 本地文件
 * 
 * @desc
 * @author jiangwei
 * @date 2017年9月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class LocalFile extends AbstractFile {

	@Value("${before_file_dir}")
	protected String beforeFileDir;

	@Value("${before_file_url}")
	protected String beforeFileUrl;

	@Override
	public UploadFileBO uploadFile(MultipartFile file, String catalogue) throws IllegalStateException, IOException {// 拆分文件名和后缀
		String[] tokens = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
		// 新的文件名
		String newFileName = DateUtil.getNow("yyMMddkkmmss") + getAscend() + SymbolConstants.DOT + tokens[1];
		String savePath = beforeFileDir + catalogue;
		FileUtil.createDir(savePath);
		String imagePath = savePath + SymbolConstants.OBLIQUE_LINE + newFileName;
		// 获取分辨率
		BufferedImage image = ImageIO.read(file.getInputStream());
		file.transferTo(new File(imagePath));
		UploadFileBO vo = new UploadFileBO();
		vo.setDir(catalogue + SymbolConstants.OBLIQUE_LINE + newFileName);
		vo.setName(tokens[0]);
		if (image != null) {
			vo.setResolution(image.getWidth() + " * " + image.getHeight());
		}
		vo.setSizes(String.valueOf(file.getSize() / 1024));
		vo.setUrl(vo.getDir());
		return vo;
	}

	@Override
	public void deleteFile(String... fileName) {
		for (String name : fileName) {
			String path = beforeFileDir + name;
			new File(path).delete();
		}
	}

	@Override
	public String getUrl() {
		return beforeFileUrl;
	}

	@Override
	public List<QiniuUploadResultVO> uploadFileStream(byte[] stream, String catalogue) throws IllegalStateException, IOException {
		// 从_article/web/news/xxx.html 截取出 article/web/news/
		String fullDir = beforeFileDir + catalogue.substring(0, catalogue.lastIndexOf(SymbolConstants.OBLIQUE_LINE));
		// 如果文章对应的路径不存在，则创建
		File directory = new File(fullDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		String fullPath = beforeFileDir + catalogue;
		Files.write(Paths.get(fullPath), stream);
		return null;
	}

}
