package com.hhly.cms.operatemgr.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.customermgr.service.CustomerService;
import com.hhly.cms.operatemgr.service.MsgInfoService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.common.msg.UseStatus;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgLotteryConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgTemplateBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgNewVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgTemplateVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgUserConfigVo;
import com.hhly.skeleton.cms.operatemgr.vo.OperateWechatTemplateVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateCouponTempVO;

/**
 *	@Desc	   通知信息管理
 *	@Author  HouXB
 *	@Date    2017年2月28日 下午2:46:47
 *  @Company 益彩网络科技公司
 *  @Version 1.0.0
 */
@Controller
@RequestMapping(value = "/operatemgr/msginfo")
public class MsgInfoController extends BaseController {
	
	@Autowired
	private MsgInfoService msgInfoService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private IOperateMgrService operateMgrService;
	 
	@RequestMapping("/index")
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/msg_info";
	}
	
	/** 优惠券 **/

	@RequestMapping("/ManagerCoupon")
	@Authority(privilege=AuthEnum.SEARCH)
	public String coupon(){
		return "operatemgr/ManagerCoupon";
	}
	
	@RequestMapping("/couponList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object couponList(OperateCouponTempVO vo){
		return operateMgrService.findCouponTemp(vo);
	}
	
	
	@RequestMapping("/insertCouponTemp")
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object insertCouponTemp(OperateCouponTempVO vo){
		return operateMgrService.insertCouponTemp(vo);
	}
	
	@RequestMapping("/updateCouponTemp")
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object updateCouponTemp(OperateCouponTempVO vo){
		return operateMgrService.updateCouponTemp(vo);
	}
	
	
	@RequestMapping("/deleteCouponTemp")
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object deleteCouponTemp(OperateCouponTempVO vo){
		return operateMgrService.deleteCouponTemp(vo);
	}
	
	
	
	
	/** 优惠券 **/
	
	
	@RequestMapping("/new")
	@Authority(privilege=AuthEnum.SEARCH)
	public String sendNewMsg(){
		return "operatemgr/msg_new";
	}
	
	@RequestMapping("/template")
	@Authority(privilege=AuthEnum.SEARCH)
	public String template(){
		return "operatemgr/msg_template";
	}
	
	@RequestMapping("/config")
	@Authority(privilege=AuthEnum.SEARCH)
	public String userMsgConfig(){
		return "operatemgr/msg_user_config";
	}
	
	/**
	 * 
	 * @Desc   查询通知信息
	 * @Author HouXB
	 * @Date   2017年3月7日 下午3:44:27
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(OperateMsgInfoVO vo){
		return msgInfoService.findMsgInfo(vo);
	}
	
	/**
	 * 
	 * @Desc   查询信息模板
	 * @Author HouXB
	 * @Date   2017年3月7日 下午3:45:32
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/templatelist")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object templateList(OperateMsgTemplateVO vo){
		return msgInfoService.findMsgTemplate(vo);
	}
	
	/**
	 * 
	 * @Desc   查询下拉列表 1001通知信息模板下拉列表，1002微信公众号模板下拉列表
	 * @Author HouXB
	 * @Date   2017年3月7日 下午3:46:15
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/dic")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object dic(@RequestParam(value = "code", required = true) String code){
		return msgInfoService.findAllTemplateDic(code);
	}
	
	/**
	 * 
	 * @Desc   根据id查询通知信息详情
	 * @Author HouXB
	 * @Date   2017年3月7日 下午4:11:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object findDetail(@PathVariable(value = "id") int id){
		return getResultSuccess(msgInfoService.findMsgInfoById(id));
	}
	
	/**
	 * 
	 * @Desc   根据id查询模板详情
	 * @Author HouXB
	 * @Date   2017年3月7日 下午4:11:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/templatedetail/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object findTemplateDetail(@PathVariable(value = "id") int id){
		OperateMsgTemplateBO bo = msgInfoService.findMsgTemplateById(id);
		return getResultSuccess(bo);
	}
	
	/**
	 * 
	 * @Desc   根据id查询微信公众号模板详情
	 * @Author HouXB
	 * @Date   2017年3月7日 下午4:11:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/wechattemplate/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object findWechatTemplate(@PathVariable(value = "id") int id){
		return getResultSuccess(msgInfoService.findWechatTemplateById(id));
	}
	
	/**
	 * 
	 * @Desc   更新信息模板
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:52:25
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/updateTemplate",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpateTemplate(@Valid(GroupValue.UPD)OperateMsgTemplateVO vo,HttpSession session) 
	{
		String modifyBy = (String)session.getAttribute(WebConstant.USERNAME);
		vo.setModifyBy(modifyBy);
		int num = msgInfoService.updateMsgTemplate(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 
	 * @Desc   更新微信公众号模板
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/updateWechatTemplate",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpateWechatTemplate(@Valid(GroupValue.UPD)OperateWechatTemplateVO vo){
		int num = msgInfoService.updateWechatTemplate(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 
	 * @Desc   新增微信公众号模板
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addWechatTemplate",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public  Object addWechatTemplate(@Valid(GroupValue.ADD)OperateWechatTemplateVO vo) {
		int num = msgInfoService.addWechatTemplate(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 
	 * @Desc   新增通知信息模板
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public  Object addTemplate(@Valid(GroupValue.ADD)OperateMsgTemplateVO vo,HttpSession session) 
	{
		String createBy = (String) session.getAttribute(WebConstant.USERNAME);
		vo.setCreateBy(createBy);
		int num = msgInfoService.addMsgTemplate(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 
	 * @Desc   更新通知信息状态
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateMsgStatus",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public  Object updateMsgStatus(@RequestParam(value = "json", required = true) String json) 
	{
		List<OperateMsgInfoVO> list = JsonUtil.json2ObjectList(json,
				OperateMsgInfoVO.class);
		msgInfoService.updateMsgStatus(list);
		return getResultSuccess(null);
	}
	
	/**
	 * 
	 * @Desc   更新通知信息状态
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelSendMsg",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public  Object cancelSendMsg(@RequestParam(value = "json", required = true) String json) 
	{
		List<OperateMsgInfoVO> list = JsonUtil.json2ObjectList(json,
				OperateMsgInfoVO.class);
		msgInfoService.cancelSendMsg(list);
		return getResultSuccess(null);
	}
	
	/**
	 * 
	 * @Desc   手动发送信息
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addNewMsg",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object addNewMsg(@RequestParam MultipartFile file,
			@Valid(GroupValue.ADD)OperateMsgNewVO vo,HttpSession session) throws IOException
	{
		String createBy = (String) session.getAttribute(WebConstant.USERNAME);
		vo.setCreateBy(createBy);
		List<String> userList = null;
		// 判断上传类型
		if(Constants.UPLOAD_USER.equals(vo.getUserType()))
		{
			// 判断上传用户名单文件不为空
			if(file.getSize() == 0)
				return JsonUtil.object2Json(getSaveResult(0));
			// 解析文件
			userList = analysisFile(file);
		}
		int num = msgInfoService.addNewMsg(vo,userList);
		return JsonUtil.object2Json(getSaveResult(num));
	}
	
	/**
	 * 
	 * @Desc   查询批次发送信息
	 * @Author HouXB
	 * @Date   2017年3月7日 下午3:44:27
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/newmsglist")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object newmsglist(OperateMsgNewVO vo){
		return msgInfoService.findNewMsg(vo);
	}
	
	/**
	 * 
	 * @Desc   查询批次发送信息详情
	 * @Author HouXB
	 * @Date   2017年3月7日 下午4:11:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/newmsg/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object findNewMsgById(@PathVariable(value = "id")int id){
		return getResultSuccess(msgInfoService.findNewMsgById(id));
	}
	
	/**
	 * 
	 * @Desc   更新批次发送信息
	 * @Author HouXB
	 * @Date   2017年3月9日 下午3:51:58
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/updatenewmsg")
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object updatenewmsg(@RequestParam MultipartFile file,
			@Valid(GroupValue.UPD)OperateMsgNewVO vo,HttpSession session) throws Exception
	{
		String createBy = (String) session.getAttribute(WebConstant.USERNAME);
		vo.setModifyBy(createBy);
		List<String> userList = null;
		if(Constants.UPLOAD_USER.equals(vo.getUserType()))
		{
			if(file.getSize() == 0)
				return JsonUtil.object2Json(getSaveResult(0));
			userList = analysisFile(file);
		}
		int num = msgInfoService.updateNewMsg(vo,userList);
		return JsonUtil.object2Json(getSaveResult(num));
	}
	
	/**
	 * 
	 * @Description 查询用户信息
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/userList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object userList(LottoCustomerVO vo){
		return customerService.findLottoCustomer(vo);
	}
	
	/**
	 * 获取用户接收配置列表
	 * @Description 
	 * @author HouXiangBao289
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/userConfig")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<OperateMsgConfigBO> userList(Integer userId){
		
		List<OperateMsgConfigBO> list=new ArrayList<>();
		
		// 查询用户关闭的通知设置
		List<OperateMsgConfigBO> closeTemplatelist=msgInfoService.findUserCloseNoticConfig(userId);
		
		// 查询所有消息类型
		List<DictionaryBO> templateList=msgInfoService.findAllTemplateDic("1001");
		
		for(DictionaryBO allTemplate:templateList)
		{
			
			OperateMsgConfigBO bo=new OperateMsgConfigBO();
			//初始化用户接收设置
			bo.setTemplateId(Integer.parseInt(allTemplate.getId()));
			bo.setTemplateName(allTemplate.getText());
			bo.setApp(UseStatus.VALID.getCode());
			bo.setSite(UseStatus.VALID.getCode());
			bo.setMob(UseStatus.VALID.getCode());
			bo.setWechat(UseStatus.VALID.getCode());
			
			for(OperateMsgConfigBO closeTemplate:closeTemplatelist)
			{
				if(Integer.parseInt(allTemplate.getId())==closeTemplate.getTemplateId())
				{
					// 用户设置
					bo.setApp(closeTemplate.getApp());
					bo.setSite(closeTemplate.getSite());
					bo.setMob(closeTemplate.getMob());
					bo.setWechat(closeTemplate.getWechat());
				}
			}
			
			list.add(bo);
			
		}
		
		return list;
	}
	
	
	/**
	 * 更新用户接收配置
	 * @Description 
	 * @author HouXiangBao289
	 * @param str
	 * @return
	 */
	@RequestMapping(value = "/updateUserConfig")
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public Object updateUserConfig(String str)
	{
		OperateMsgUserConfigVo vo=JSONObject.parseObject(str, OperateMsgUserConfigVo.class);
		msgInfoService.updateMsgConfig(vo);
		return getResultSuccess(null);
	}
	
	/**
	 * 获取用户各彩种接收配置
	 * @Description 
	 * @author HouXiangBao289
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/lotteryConfig")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<OperateMsgLotteryConfigBO> lotteryConfig(Integer userId,Integer type){
		return msgInfoService.findLotteryConfig(userId, type);
	}
	
	/**
	 * 
	 * @Desc   查询信息模板编号是否重复
	 * @Author HouXB
	 * @Date   2017年3月7日 下午4:11:15
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/template/{typeId}")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object findTemplateTypeId(@PathVariable(value = "typeId") int typeId){
		 OperateMsgTemplateBO bo = msgInfoService.findTemplateTypeId(typeId);
		return getResultSuccess(bo);
	}
	
}
