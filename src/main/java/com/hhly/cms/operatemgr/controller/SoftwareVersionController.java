package com.hhly.cms.operatemgr.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateSoftwareVersionService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.ChannelEnum.ChannelTypeEnum;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.VerSionCacheEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.valid.Param;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;

import net.sf.json.JSONObject;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年5月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/operatemgr/software/version")
public class SoftwareVersionController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SoftwareVersionController.class);
	
	@Autowired
	OperateSoftwareVersionService operateSoftwareVersionService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public Object index(){
		return "operatemgr/software_version";
	}
	
	@RequestMapping(value="/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OperateSoftwareVersionVO vo){
		// 2017-11-07包管理只上传官方包，所以只查询官方android和官方iOS的信息
		vo.setChannelIds(Arrays.asList(ChannelTypeEnum.IOS.getCode().shortValue(),ChannelTypeEnum.ANDROID.getCode().shortValue()));
		return operateSoftwareVersionService.findSoftwareVersionList(vo);
	}
	
	@RequestMapping(value="name/distinct")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object distinctName(OperateSoftwareVersionVO vo){
		return operateSoftwareVersionService.distinctSoftwareVersionName(new OperateSoftwareVersionVO());
	}
	
	@RequestMapping(value="/base")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public Object base(OperateSoftwareVersionVO vo){
		vo.setChannelIds(Arrays.asList(ChannelTypeEnum.IOS.getCode().shortValue(),ChannelTypeEnum.ANDROID.getCode().shortValue()));
		return operateSoftwareVersionService.findBaseSoftwareVersion(vo);
	}	
	
	
	@RequestMapping(value = "/add")
	@Authority(privilege = AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = VerSionCacheEnum.class)
	public Object add(HttpSession session, OperateSoftwareVersionVO vo){
		OperateSoftwareVersionVO criteria = new OperateSoftwareVersionVO();
		criteria.setCode(vo.getCode());
		criteria.setChannelId(vo.getChannelId());
		Assert.isTrue(operateSoftwareVersionService.count(criteria)<1, "同一平台已存在该版本号");
		vo.setCreateBy(getUserName(session));
		int cacheNum  = operateSoftwareVersionService.addSoftwareVersion(vo);
		return getSaveResult(cacheNum);
	}
	
	@RequestMapping(value = "/del/{id}" , method = RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = VerSionCacheEnum.class)	
	public Object del(@PathVariable(value = "id") String id){
		StringVO vo = new StringVO();
		vo.setStr(id);
		return getSaveResult(operateSoftwareVersionService.delSoftwareVersionByIds(vo));
	}	
	
	@RequestMapping(value = "/upd")
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = VerSionCacheEnum.class)
	public Object upd(HttpSession session, OperateSoftwareVersionVO vo){
		Assert.notNull(vo.getChannelId(), "更新包版本时渠道号为空");
		OperateSoftwareVersionVO criteria = new OperateSoftwareVersionVO();
		criteria.setNegativeId(vo.getId());
		criteria.setCode(vo.getCode());
		criteria.setChannelId(vo.getChannelId());
		Assert.isTrue(operateSoftwareVersionService.count(criteria)<1, "同一平台已存在该版本号");
		vo.setModifyBy(getUserName(session));
		int cacheNum  = operateSoftwareVersionService.updSoftwareVersion(vo);
		return getSaveResult(cacheNum);
	}
	
	@RequestMapping(value = "/updIsnew")
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	@ParameterValid({
		@Param(index =1 ,notNull=true,msg="是否最新版本"), 
		@Param(index =2 ,notNull=true,msg="移动包类型")})
	@DeleteBatchAssignCache(GetCacheEnumService = VerSionCacheEnum.class)
	public Object updIsnew(HttpSession session, int isnew, int type){
		int cacheNum  = operateSoftwareVersionService.updateIsnew(isnew, type);
		return getSaveResult(cacheNum);
	}	
	
	/**
	 * 
	 * @Description: 上传应用文件到服务器
	 * @param file
	 * @param session
	 * @param catalogue
	 * @param groupid
	 * @param source
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author wuLong
	 * @date 2017年8月2日 下午3:17:30
	 */
	@RequestMapping(value = "/upload")
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object upload(@RequestParam MultipartFile appfile,HttpServletRequest request)
			throws IllegalStateException, IOException {
		Map<String,Object> map= new HashMap<String,Object>();  
		String originalFilename=appfile.getOriginalFilename();  
		//Android_V1.0.0_1_official_20170714_1903.apk
		String path=beforeFileDir+Constants.UPLOAD_SUBDIRECTORY;  
		String[] of = originalFilename.split(SymbolConstants.UNDERLINE);
		if(of.length!=6){
			map.put("errorCode", "10002");  
			map.put("message", "文件格式错误"); 
		}else{
			try{  
				//把上传的图片放到服务器的文件夹下  
				FileUtils. copyInputStreamToFile(appfile.getInputStream(), new File(path,originalFilename));  
				String appType = of[0];
				String versionName = of[1];
				String version = of[2];
				byte[] bs = appfile.getBytes();
				//coding  
				map.put("errorCode", "10001");  
				map.put("message", "文件上传成功"); 
				map.put("byte", bs.length); 
				map.put("fileAddress", beforeFileUrl+Constants.UPLOAD_SUBDIRECTORY+originalFilename); 
				map.put("appType", appType);
				map.put("versionName", versionName);
				map.put("version", version);
				map.put("versionSize", getPrintSize(bs.length));
				map.put("mobileAddress", "http://m.2ncai.com");
				map.put("originalFilename", originalFilename);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("errorCode", "10002");  
				map.put("message", "文件上传失败"); 
			}  
		}
		String result=String. valueOf(JSONObject.fromObject (map));  
		return result;  
	}
	
	@RequestMapping(value = "/delfile")
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public Object delFile(@RequestParam String fileName){
		Map<String,Object> map= new HashMap<String,Object>(); 
		String path=beforeFileDir+Constants.UPLOAD_SUBDIRECTORY+fileName;  
		File file = new File(path);
		if (file.isFile()){
            if(deleteFile(fileName)){
            	map.put("errorCode", "10001");  
				map.put("message", "文件删除成功"); 
            }
		}
		String result=String. valueOf(JSONObject.fromObject (map));  
		return result;  
	}
	
	/**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
            	logger.info("删除单个文件" + fileName + "成功！");
                return true;
            } else {
            	logger.info("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
        	logger.info("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
	
	public static String getPrintSize(double size) {  
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
	    //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	    size = size / 1024 / 1024 ;  
	    return df.format(size);  
	}  
	
	public static void main(String[] args) {
		System.out.println(getPrintSize(43566448));
	}
}
