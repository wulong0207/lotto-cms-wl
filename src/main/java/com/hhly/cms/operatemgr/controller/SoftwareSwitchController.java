package com.hhly.cms.operatemgr.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cms.operatemgr.service.OperateSoftwareVersionService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.OperateSoftwareVersionEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSoftwareVersionBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;

import net.sf.json.JSONObject;

/**
 * @desc    审核开关
 * @author  Tony Wang
 * @date    2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("operatemgr/software/switch")
public class SoftwareSwitchController extends BaseController {
	
	private static Logger logger = LogManager.getLogger(SoftwareSwitchController.class);

	@Autowired
	OperateSoftwareVersionService operateSoftwareVersionService;
	@Autowired
	private MarketChannelService marketChannelService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public Object index(){
		return "operatemgr/software_switch";
	}
	
	@RequestMapping("/channel")
	@Authority(privilege=AuthEnum.UPD)
	public Object toChannel(){
		return "operatemgr/software_switch_channel";
	}
	
	/**
	 * @desc   显示每个渠道最新版本的开关
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @return 
	 */
	@RequestMapping(value = "list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object listMarketChannel(){
		OperateSoftwareVersionVO vo = new OperateSoftwareVersionVO();
		vo.setPageSize(Integer.MAX_VALUE);
		return setOther(operateSoftwareVersionService.findNewSoftwareVersion(vo));
	}
	
	@RequestMapping(value="page")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OperateSoftwareVersionBO> list(OperateSoftwareVersionVO vo){
		vo.setSortField("S.CODE");
		vo.setSortOrder("desc");
		PagingBO<OperateSoftwareVersionBO> page = operateSoftwareVersionService.findSoftwareVersionList(vo);
		setOther(page.getData());
		return page;
	}
	
	/**
	 * @desc   设置'购彩状态','隐藏内容'
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param data 
	 */
	private List<OperateSoftwareVersionBO> setOther(List<OperateSoftwareVersionBO> list) {
		// 获取属于购彩功能的模块
		if(!CollectionUtils.isEmpty(list)) {
			List<String> lotteryFunIds = OperateSoftwareVersionEnum.Modul.getLotteryModul();
			List<String> hides;
			String hide;
			for(OperateSoftwareVersionBO bo : list) {
				hide = bo.getHide() == null ? "" : bo.getHide();
				hides = Arrays.asList(hide.split(","));
				bo.setLotteryStatus(hides.containsAll(lotteryFunIds) ? 0 : 1);
				// 设置隐藏内容
				StringBuilder sb = new StringBuilder();
				String hideText;
				for (OperateSoftwareVersionEnum.Modul modul : OperateSoftwareVersionEnum.Modul.values()) {
					if(hides.contains(modul.getValue()))
						sb.append(modul.getText()).append("、");
				}
				if(sb.length() == 0) {
					hideText = sb.toString();
				} else {
					hideText = sb.substring(0, sb.length()-1).toString();
				}
				bo.setHideContent(hideText);
			}
		}
		return list;
	}

	/**
	 * @desc   更新审核开关
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param session
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "", method=RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object updSwitch(HttpSession session, OperateSoftwareVersionVO vo){
//		// 由于页面把一级菜单‘个人中心栏’分成两处地方勾选，这里判断所有子项是否选中，是则把‘个人中心栏’也选中，否则不选中
//		String hide = vo.getHide() == null ? "" : vo.getHide();
//		List<String> hides = new ArrayList<>(Arrays.asList(hide.split(",")));
//		// 获取个人中心栏的模块
//		List<String> modulIds = OperateSoftwareVersionEnum.Modul.getUserCenterSubModul();
//		if(hides.containsAll(modulIds))
//			hides.add(OperateSoftwareVersionEnum.Modul.USER_CENTER.getValue());
//		else 
//			hides.remove(OperateSoftwareVersionEnum.Modul.USER_CENTER.getValue());
//		// 由于页面把一级菜单‘开奖公告’分成两处地方勾选，这里判断所有子项是否选中，是则把‘开奖公告’也选中，否则不选中
//		// 获取个人中心栏的模块
//		modulIds = OperateSoftwareVersionEnum.Modul.getDrawNoticeSubModul();
//		if(hides.containsAll(modulIds))
//			hides.add(OperateSoftwareVersionEnum.Modul.DRAW_NOTICE.getValue());
//		else 
//			hides.remove(OperateSoftwareVersionEnum.Modul.DRAW_NOTICE.getValue());
		OperateSoftwareVersionVO target = new OperateSoftwareVersionVO();
		target.setId(vo.getId());
		target.setHide(vo.getHide());
		target.setSwitchRemark(vo.getSwitchRemark());
		target.setSwitchModifyBy(getUserName(session));
		target.setSwitchModifyTime(new Date());
		target.setHideArea(vo.getHideArea());
		int ret  = operateSoftwareVersionService.updSoftwareVersionSwitch(target);
		return getSaveResult(ret);
	}
	
	/**
	 * @desc   更新是否同步官方操作
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param session
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "synofficial", method=RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object updSynOfficial(HttpSession session, OperateSoftwareVersionVO vo){
		//如果是开启同步，则把所有的
//		if(vo.getSynOfficial()==1){
//			OperateSoftwareVersionVO findSynOfficial = new OperateSoftwareVersionVO();
//			findSynOfficial.setId(vo.getId());
//			OperateSoftwareVersionBO operateSoftwareVersionBO = operateSoftwareVersionService.findOneSoftwareVersion(vo);
//			if(operateSoftwareVersionBO!=null){
//				OperateSoftwareVersionVO closeSynOfficial = new OperateSoftwareVersionVO();
//				closeSynOfficial.setChannelId(operateSoftwareVersionBO.getChannelId());
//				closeSynOfficial.setSynOfficial(0);
//				operateSoftwareVersionService.updSoftwareVersionSwitchByChannlId(closeSynOfficial);
//			}
//		}
		OperateSoftwareVersionVO target = new OperateSoftwareVersionVO();
		target.setId(vo.getId());
		target.setChannelId(vo.getChannelId());
		target.setSynOfficial(vo.getSynOfficial());
		target.setSwitchModifyBy(getUserName(session));
		target.setSwitchModifyTime(new Date());
		int ret  = operateSoftwareVersionService.updSoftwareVersionSwitchSynOfficial(target);
		return getSaveResult(ret);
	}
	
	/**
	 * @desc   更新是否开启购彩
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param session
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "lotterystatus", method=RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object updLotterystatus(HttpSession session, OperateSoftwareVersionVO vo){
		Assert.notNull(vo.getLotteryStatus(), "更新购彩时未指定是关闭还是开启");
		int ret = 0;
		OperateSoftwareVersionVO target = new OperateSoftwareVersionVO();
		target.setId(vo.getId());
		target.setSwitchModifyBy(getUserName(session));
		target.setSwitchModifyTime(new Date());
		String hide = vo.getHide() == null ? "" : vo.getHide();
		List<String> hides = new ArrayList<>(Arrays.asList(hide.split(",")));
		// 获取属于购彩功能的模块
		List<String> lotteryFunIds = OperateSoftwareVersionEnum.Modul.getLotteryModul();
		if(vo.getLotteryStatus() == 0) {
			// 如果要关闭购彩,若还有没关闭的购彩功能则要关闭
			for(String lotteryFunId : lotteryFunIds) {
				if(!hides.contains(lotteryFunId))
					hides.add(lotteryFunId);
			}
			// 更新hide字段
			target.setHide(join(hides));
			ret  = operateSoftwareVersionService.updSoftwareVersionSwitch(target);
		} else if(vo.getLotteryStatus() == 1) {
			if(!StringUtils.hasText(hide)) {
				// 不用更新
				ret = 1;
			} else {
				// 如果要开启购彩,若还有隐藏的购彩功能则要取消隐藏
				for(String lotteryFunId : lotteryFunIds) {
					if(hides.contains(lotteryFunId))
						hides.remove(lotteryFunId);
				}
				target.setHide(join(hides));
				ret  = operateSoftwareVersionService.updSoftwareVersionSwitch(target);
			}
			
		}
		return getSaveResult(ret);
	}

	@RequestMapping(value = "/upload")
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object upload(@RequestParam MultipartFile appfile,HttpServletRequest request)
			throws IllegalStateException, IOException {
		Map<String,Object> map= new HashMap<String,Object>();
		String channelId = request.getParameter("channelId");
		if(!StringUtils.hasText(channelId)) {
			map.put("errorCode", "10002");  
			map.put("message", "上传应用包时，渠道id为空");
			return String.valueOf(JSONObject.fromObject (map)); 
		}
		String originalFilename=appfile.getOriginalFilename();  
		if(!originalFilename.endsWith(".ipa") && !originalFilename.endsWith(".apk")){
			map.put("errorCode", "10002");  
			map.put("message", "文件格式错误");
			return String.valueOf(JSONObject.fromObject (map));  
		}
		// 检查是否有同名的应用包
		OperateMarketChannelVO vo = new OperateMarketChannelVO();
		vo.setAppUrlLike(originalFilename);
		if(marketChannelService.count(vo)>0) {
			map.put("errorCode", "10002");  
			map.put("message", "应用包"+originalFilename+"已存在，请重命名后再上传！");
			return String.valueOf(JSONObject.fromObject (map));  
		}
		try{  
			//把上传的图片放到服务器的文件夹下  
			//上传.ipa或.apk
			String path=beforeFileDir+Constants.UPLOAD_APP_FOR_OPERATE; 
			FileUtils.copyInputStreamToFile(appfile.getInputStream(), new File(path,originalFilename));
			// 更新operate_market_channel表的app_url字段
			vo = new OperateMarketChannelVO();
			//String channelId = (String)(request.getParameterMap().get("")[0]);
			vo.setChannelId(channelId);
			vo.setAppUrl(Constants.UPLOAD_APP_FOR_OPERATE+originalFilename);
			marketChannelService.updAppUrl(vo);
			byte[] bs = appfile.getBytes();
			map.put("errorCode", "10001");  
			map.put("message", "文件上传成功"); 
			map.put("byte", bs.length); 
		} catch (Exception e) {
			logger.error(e);
			map.put("errorCode", "10002");  
			map.put("message", "文件上传失败"); 
		}  
		String result=String.valueOf(JSONObject.fromObject (map));  
		return result;  
	}
	
	private String join(List<String> hides) {
		StringBuilder sb = new StringBuilder();
		for(String hide : hides) {
			if(ObjectUtil.isBlank(hide)){
				continue;
			}
			sb.append(hide);
			sb.append(",");
		}
		if(sb.length() == 0) {
			return sb.toString();
		} else {
			return sb.substring(0, sb.length()-1).toString();
		}
	}
}
