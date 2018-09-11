package com.hhly.cms.customermgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.rabbitmq.SendMessage;
import com.hhly.cms.customermgr.service.CustomerService;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICmsCacheService;
import com.hhly.cmscore.cms.remote.service.ITransMgrService;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.mq.msg.MessageModel;
import com.hhly.skeleton.base.mq.msg.OperateNodeMsg;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
/**
 * 
 * @desc
 * @author jiangwei
 * @date 2017年4月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/customermgr/user")
public class CustomerController extends BaseController {
	
	@Autowired
    private CustomerService customerService;
	
	@Autowired
	private MarketChannelService marketChannelService;

	@Autowired
	private ICmsCacheService iCmsCacheService;

	@Autowired
	private ITransMgrService iTransMgrService;
	
	@Autowired
	private SendMessage sendMessage;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "customermgr/user";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(LottoCustomerVO vo){
		return customerService.findLottoCustomer(vo);
	}
	
	@RequestMapping(value = "/ids")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  List<Integer> ids(LottoCustomerVO vo){
		return customerService.findIds(vo);
	}
	/**
	 * 查询渠道信息
	 * @return
	 */
	@RequestMapping(value = "/channel")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public  Object channel(){
		return marketChannelService.findAllChannelDic();
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,LottoCustomerVO vo) throws IOException{
		ByteArrayOutputStream outputStream=customerService.getLottoCustomerExcel(vo);
		excel("user", outputStream, response);
	}
	/**
	 * 查询用户数据详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object detail(@PathVariable("id")String id){
		StringVO vo = new StringVO();
		vo.setStr(id);
		return getResultSuccess(customerService.findLottoCustomerDetail(vo));
	}
	/**
	 * 修改会员信息
	 * @param vo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpate(@Valid(GroupValue.UPD)LottoCustomerVO vo,HttpSession session) {
		if(Objects.equals(vo.getAccountStatus(),(short)0))
			Assert.notNull(vo.getForbitEndTime(),"账户禁用时，禁用时间不能为空");
		vo.setModifyBy(getUserRealName(session));
		StringVO voStringVO = new StringVO();
		//voStringVO.setStr(vo.getAccountId());
		voStringVO.setStr(vo.getId().toString());
		if(ObjectUtil.isBlank(vo.getActualName())){
			vo.setActualName(null);
		}
		if(ObjectUtil.isBlank(vo.getIdNum())){
			vo.setIdNum(null);
		}

		LottoCustomerBO oldCustomer = customerService.findLottoCustomerDetail(voStringVO);

		// 如果不是禁用用户，则把禁用时间置空
		// 0：禁用；1：启用；2：注销
		if(!Objects.equals(vo.getAccountStatus(),(short)0))
			vo.setForbitEndTime(null);
		int num = customerService.updLottoCustomer(vo);
		// 如果有修改了真实姓名，则把trans_taken表对应create_by也修改
		if(!Objects.equals(oldCustomer.getActualName(), vo.getActualName())) {
			iTransMgrService.updateTakenCreateBy(vo.getActualName(), vo.getId());
		}
		// 如果更新成功
		// Cms修改会员资料信息也需要推送消息,涉及的修改信息为（昵称/手机号码/真实姓名/身份证号码）；
		if(num>0) {
			if(
				!Objects.equals(oldCustomer.getNickName(), vo.getNickName()) ||
				!Objects.equals(oldCustomer.getCusMobile(), vo.getCusMobile()) ||
				!Objects.equals(oldCustomer.getActualName(), vo.getActualName()) ||
				!Objects.equals(oldCustomer.getIdNum(), vo.getIdNum())
			) {
				OperateNodeMsg nodeMsg = new OperateNodeMsg(1, vo.getId().toString());
				MessageModel msg = new MessageModel("cms", "nodeMsgSend", nodeMsg);
				sendMessage.sendToMsgQueue(msg);
			}
			// 如果禁用用户，则要发送消息
			if(Objects.equals(vo.getAccountStatus(),(short)0) && !Objects.equals(oldCustomer.getAccountStatus(),(short)0)) {
				OperateNodeMsg nodeMsg = new OperateNodeMsg(19, vo.getId().toString());
				MessageModel msg = new MessageModel("cms", "nodeMsgSend", nodeMsg);
				sendMessage.sendToMsgQueue(msg);
			}
			// 如果是由"禁用"变成启用，则清除"密码输错10次禁用用户"的缓存
			if(Objects.equals(vo.getAccountStatus(),(short)1) && Objects.equals(oldCustomer.getAccountStatus(),(short)0)) {
				iCmsCacheService.clearCache(CacheConstants.C_CORE_VERIFY_CODE_ERR_COUNT+vo.getId()+ vo.getCusMail());
				iCmsCacheService.clearCache(CacheConstants.C_CORE_VERIFY_CODE_ERR_COUNT+vo.getId()+ vo.getCusMobile());
			}
		}
	    return getSaveResult(num);
	}
	/**
	 * 批量查询
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	@RequestMapping(value = "/beach/search")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object beachSearch(@RequestParam(value = "type", required = true)String type
			,@RequestParam(value = "content", required = true)String content){
		List<String> list = beachSearchCondition(content, type);
		LottoCustomerVO vo =new LottoCustomerVO();
		vo.setType(type);
		vo.setValues(list);
		vo.setPageIndex(0);
		vo.setPageSize(list.size());
		return getResultSuccess(customerService.findLottoCustomer(vo).getData());
	}
	/**
	 * 修改用户密码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午12:15:23
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/reset/password")
	@Authority(privilege=AuthEnum.UPLOAD)
	@ResponseBody
	@ParameterValid
	public  Object resetPassword(@Valid("resetPassword")LottoCustomerVO vo,HttpSession session){
		vo.setModifyBy(getUserRealName(session));
		customerService.updatePassword(vo);
		return getResultSuccess(null);
	}
	/**
	 * 清空短信信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月11日 下午5:10:04
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/clean/message")
	@Authority(privilege=AuthEnum.UPLOAD)
	@ResponseBody
	@ParameterValid
	public  Object cleanMessage(@Valid("cleanMessage")LottoCustomerVO vo,HttpSession session){
		vo.setModifyBy(getUserRealName(session));
		customerService.updateCleanMessage(vo);
		return getResultSuccess(null);
	}
}
