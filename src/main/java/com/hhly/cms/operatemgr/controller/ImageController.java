package com.hhly.cms.operatemgr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.ImageService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.OperateImgBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateImgVO;

/**
 * @desc 图片管理
 * @author jiangwei
 * @date 2017年4月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/operatemgr/image")
public class ImageController extends BaseController {
	private final String TYPE = "single";

	@Autowired
	private ImageService imageService;

	/**
	 * 图片管理页面 （js 弹窗图片管理窗口）{dir} 图片保存路径，data 选择图片
	 * Cms.imageManage("image/{dir}",function(data){
				   operate_ad.setImage(data);
	    });
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月7日 上午11:54:53
	 * @return
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String image(HttpServletResponse response,String catalogue) {
		if(StringUtils.isEmpty(catalogue)){
			catalogue = defaultImageDir;
		}
		Cookie cookie = new Cookie("catalogue", catalogue);
		response.addCookie(cookie);
		return "operatemgr/image";
	}
	
	/**
	 * js 调用上传图片代码 （第一个参数表示上传图片保存目录）
	 * Cms.uploadWindow("image/customer",function(data){
				mini.alert(JSON.stringify(data));
	   });
	 * 上传图片路径
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 下午3:00:26
	 * @param response
	 * @param catalogue
	 * @return
	 */
	@RequestMapping("/upload/page")
	@Authority(privilege = AuthEnum.UPLOAD)
	public String uploadPage(HttpServletResponse response,
			@RequestParam(value = "catalogue", required = true) String catalogue,String type) {
		if(TYPE.equals(type)){
			response.addCookie(new Cookie("type", TYPE));
		}else{
			response.addCookie(new Cookie("type", ""));
		}
		response.addCookie(new Cookie("catalogue", catalogue));
		return "operatemgr/image_upload_page";
	}
	/**
	 * 分页查询
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月10日 上午10:10:29
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OperateImgVO vo) {
		PagingBO<OperateImgBO> pagingBO = imageService.listOperateImg(vo);
		for (OperateImgBO bo : pagingBO.getData()) {
			bo.setUrl(uploadFile.getUrl() + bo.getDir());
		}
		return getResultSuccess(pagingBO);
	}
	/**
	 * 查询图片信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月14日 下午12:20:41
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/detail")
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public Object imageDetail(OperateImgVO vo) {
		OperateImgBO bo = imageService.getOperateImg(vo);
		if(bo == null){
			throw new ServiceRuntimeException("图片不存在");
		}
		bo.setUrl(uploadFile.getUrl() + bo.getDir());
		return getResultSuccess(bo);
	}

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
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object upload(@RequestPart MultipartFile[] file, HttpSession session,
			@RequestParam(required = true, value = "catalogue") String catalogue,
			@RequestParam(required = true, value = "groupid") Integer groupid,
			@RequestParam(required = false, value = "source") String source)
			throws IllegalStateException, IOException {
		if (null == file || file.length == 0) {
			throw new ResultJsonException("请选择一张图片");
		}
		String createBy = getUserRealName(session);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("state", "ERROR");
		List<OperateImgVO> vos = new ArrayList<>();
		for (MultipartFile multipartFile : file) {
			if (multipartFile == null || multipartFile.getSize() == 0) {
				continue;
			}
			UploadFileBO bo = uploadFile(multipartFile, catalogue);
			OperateImgVO vo = new OperateImgVO(bo, groupid, createBy);
			vos.add(vo);
			param.put("title", vo.getName());
			param.put("url", uploadFile.getUrl() + vo.getDir());
			param.put("size", multipartFile.getSize());
			param.put("original", multipartFile.getOriginalFilename());
			param.put("type", multipartFile.getContentType());
		}
		try {
			imageService.addOperateImg(vos);
		} catch (ServiceRuntimeException e) {
			for (OperateImgVO operateImgVO : vos) {
				deleteFile(operateImgVO.getDir());
			}
			throw e;
		}
		if(!ObjectUtil.isBlank(source) && source.equals("ueditor")){
			param.put("state", "SUCCESS");
			return param;
		}
		return getResultSuccess(vos);
	}

	/**
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:30:43
	 * @param vo
	 *            修改图片基本信息
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public Object udpate(@Valid(GroupValue.UPD) OperateImgVO vo, HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = imageService.updOperateImg(vo);
		return getSaveResult(num);
	}

	/**
	 * 删除图片
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:31:19
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	@ParameterValid
	public Object delete(@RequestParam(value = "ids", required = true) String ids, HttpSession session) {
		String[] id = StringUtils.tokenizeToStringArray(ids, SymbolConstants.COMMA);
		if (ObjectUtil.isBlank(id)) {
			throw new ServiceRuntimeException("请选择要删除的图片");
		}
		List<OperateImgBO> list = imageService.listOperateImgInfo(Arrays.asList(id));
		for (OperateImgBO operateImgBO : list) {
			deleteFile(operateImgBO.getDir());
			imageService.delOperateImg(Arrays.asList(operateImgBO.getId().toString()));
		}
		return getResult(true);
	}

	/**
	 * 移动图片分组
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:31:09
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/move", method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public Object move(@Valid("move") OperateImgVO vo, HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = imageService.updOperateImgMove(vo);
		return getSaveResult(num);
	}
}
