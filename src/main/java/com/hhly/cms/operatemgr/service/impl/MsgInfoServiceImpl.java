package com.hhly.cms.operatemgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.rabbitmq.MQProducer;
import com.hhly.cms.operatemgr.service.MsgInfoService;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.cmscore.cms.remote.service.ILotteryMgrService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.OrderEnum.NumberCode;
import com.hhly.skeleton.base.common.msg.UseStatus;
import com.hhly.skeleton.base.util.OrderNoUtil;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgLotteryConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgNewBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgTemplateBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSendBatchBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateWechatTemplateBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgLotteryConfigVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgNewVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgTemplateVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgUserConfigVo;
import com.hhly.skeleton.cms.operatemgr.vo.OperateWechatTemplateVO;
import com.hhly.skeleton.user.bo.UserInfoBO;

import common.Logger;

/**
 *	@Desc	   通知信息管理
 *	@Author  HouXiangBao
 *	@Date    2017年2月28日 下午2:50:12
 *  @Company 益彩网络科技公司
 *  @Version 1.0.0
 */
@Service
public class MsgInfoServiceImpl implements MsgInfoService{
	
	private Logger logger = Logger.getLogger(MsgInfoServiceImpl.class);
	
	@Autowired
    private IOperateMgrService iOperateMgrService;
	
	@Autowired
	private ICustomerMgrService iCustomerMgrService;
	
	@Autowired
	private ILotteryMgrService iLotteryMgrService;
	
	@Resource  
    private MQProducer producer;
	
//	@Autowired
//	private ExcelExportService excelExportService;
	
	@Value("${msg_queue_name}")
	private String msgQueueName; // MQ 队列名称
	
	
	@Override
	public PagingBO<OperateMsgInfoBO> findMsgInfo(
			OperateMsgInfoVO vo) {
		return iOperateMgrService.findMsgInfo(vo);
	}

	@Override
	public PagingBO<OperateMsgTemplateBO> findMsgTemplate(OperateMsgTemplateVO vo) {
		return iOperateMgrService.findMsgTemplatePage(vo);
	}
	
	@Override
	public List<DictionaryBO> findAllTemplateDic(String code) 
	{
		List<DictionaryBO> rs = new ArrayList<DictionaryBO>();
		if("1001".equals(code))
		{
			// 查询模板
			List<OperateMsgTemplateBO> list = iOperateMgrService.findMsgTemplate(new OperateMsgTemplateVO());
			for(OperateMsgTemplateBO bo:list)
			{
				DictionaryBO dic = new DictionaryBO();
				dic.setId(bo.getId()+"");
				dic.setText(bo.getTypeName());
				rs.add(dic);
			}
		}
		else if("1002".equals(code))
		{
			// 查询微信公众号模板
			OperateWechatTemplateVO vo = new OperateWechatTemplateVO();
			vo.setStatus(1);
			List<OperateWechatTemplateBO> list = iOperateMgrService.findWechatTemplate(vo);
			for(OperateWechatTemplateBO bo:list)
			{
				DictionaryBO dic = new DictionaryBO();
				dic.setId(bo.getId()+"");
				dic.setText(bo.getTypeName());
				rs.add(dic);
			}
		}
		else if("1003".equals(code.substring(0, 4)))
		{
			// 根据消息分类查询有效模板
			Integer msgType = Integer.parseInt(code.substring(4));
			OperateMsgTemplateVO vo = new OperateMsgTemplateVO();
			vo.setMsgType(msgType);
			vo.setStatus((int)com.hhly.skeleton.base.constants.Constants.YES);//有效
			List<OperateMsgTemplateBO> list = iOperateMgrService.findMsgTemplate(vo);
			for(OperateMsgTemplateBO bo:list)
			{
				DictionaryBO dic = new DictionaryBO();
				dic.setId(bo.getId()+"");
				dic.setText(bo.getTypeName());
				rs.add(dic);
			}
		}
		return rs;
	}

	@Override
	public OperateMsgInfoBO findMsgInfoById(int id){
		return iOperateMgrService.findMsgInfoById(id);
	}

	@Override
	public OperateMsgTemplateBO findMsgTemplateById(int id){
		return iOperateMgrService.findMsgTemplateById(id);
	}

	@Override
	public OperateWechatTemplateBO findWechatTemplateById(int id) {
		return iOperateMgrService.findWechatTemplateById(id);
	}

	@Override
	public int addMsgTemplate(OperateMsgTemplateVO vo) {
		return iOperateMgrService.addMsgTemplate(vo);
	}

	@Override
	public int addWechatTemplate(OperateWechatTemplateVO vo) {
		return iOperateMgrService.addWechatTemplate(vo);
	}

	@Override
	public int updateMsgStatus(List<OperateMsgInfoVO> list)
	{
		int result = iOperateMgrService.updateMsgStatus(list);
		// 重新发送
		if(list.size() > 0)
		{
			if(list.get(0).getStatus() == Constants.NO_SEND_STATUS)
			{
				List<Integer> msgIds = new ArrayList<Integer>(); 
				for(OperateMsgInfoVO vo:list){
					msgIds.add(vo.getId());
				}
				//发送到消息服务端
				try 
				{
			        // 设置的路由配置 要与rabbitmq.xml中的配置的key一致,才能保证发送到相应队列中
			        producer.sendDataToQueue(msgQueueName,msgIds,com.hhly.skeleton.base.constants.Constants.MSG_RESEND);
				} 
				catch (Exception e) 
				{
					logger.error("【通知信息管理】重新发送通知信息发生异常：");
					e.printStackTrace();
				}
			}
			
		}
		return result;
	}

	@Override
	public int updateMsgTemplate(OperateMsgTemplateVO vo) {
		return iOperateMgrService.updateMsgTemplate(vo);
	}

	@Override
	public int updateWechatTemplate(OperateWechatTemplateVO vo) {
		return iOperateMgrService.updateWechatTemplate(vo);
	}

	@Override
	public List<OperateMsgConfigBO> findConfigByUserId(int userId) {
		return iOperateMgrService.findConfigByUserId(userId);
	}

	@Override
	public int updateNewMsg(OperateMsgNewVO vo,List<String> userList) 
	{
		int result = iOperateMgrService.updateNewMsg(vo,userList);
		if(vo.getStatus() == Constants.NO_SEND_STATUS){
			// 添加到发送队列
			// 设置的路由配置 要与rabbitmq.xml中的配置的key一致,才能保证发送到相应队列中
	        producer.sendDataToQueue(msgQueueName,
	        		new OperateSendBatchBO(vo.getMsgBatch(),vo.getPreSendTime().getTime()),
	        		com.hhly.skeleton.base.constants.Constants.MSG_BATCH_SEND);
		}
		return result;
	}

	@Override
	public int addNewMsg(OperateMsgNewVO vo,List<String> userList)
	{
		//生成发送批次号
		String batchCode = OrderNoUtil.getOrderNo(NumberCode.SEND_BATCH);
		vo.setMsgBatch(batchCode);
		int result = iOperateMgrService.addNewMsg(vo,userList);
		if(vo.getStatus() == Constants.NO_SEND_STATUS){
			// 添加到发送队列
			producer.sendDataToQueue(msgQueueName,
					new OperateSendBatchBO(batchCode,vo.getPreSendTime().getTime()),
					com.hhly.skeleton.base.constants.Constants.MSG_BATCH_SEND);
		}
		return result;
	}
	
	@Override
	public OperateMsgNewBO findNewMsgById(int id) {
		return iOperateMgrService.findNewMsgById(id);
	}

	@Override
	public PagingBO<OperateMsgNewBO> findNewMsg(OperateMsgNewVO vo) {
		return iOperateMgrService.findNewMsg(vo);
	}

	@Override
	public List<OperateMsgConfigBO> findUserCloseNoticConfig(Integer userId) {
		return iOperateMgrService.findUserCloseNoticConfig(userId);
	}

	@Override
	public void updateMsgConfig(OperateMsgUserConfigVo vo) 
	{
		//更新总开关
		iCustomerMgrService.updateMsgConfig(vo);
		//删除原已关闭用户配置记录
		iOperateMgrService.deleteMsgConfByUserId(vo.getUserId());
		//添加新已关闭用户配置记录
		iOperateMgrService.addMsgConfig(vo.getList(),vo.getUserId());
		
		//保存用户彩种相关消息接收配置
		for(OperateMsgLotteryConfigVO v:vo.getLotMsgConfigList())
		{
			OperateMsgLotteryConfigBO bo = iOperateMgrService.findLotteryConfigById(v.getId());
			if(bo == null)
				iOperateMgrService.addLotteryConfig(v);
			else
				iOperateMgrService.updateLotteryConfig(v);
		}
	}

	@Override
	public List<OperateMsgLotteryConfigBO> findLotteryConfig(Integer userId, Integer type) {
		List<OperateMsgLotteryConfigBO> list = iOperateMgrService.findLotteryConfig(userId, type);
	    if(list.size() == 0){
	    	List<LotteryTypeBO> data = iLotteryMgrService.findLFLottery();
	    	for(LotteryTypeBO lot:data){
	    		OperateMsgLotteryConfigBO userLotConfig = new OperateMsgLotteryConfigBO();
	    		userLotConfig.setLotteryCode(lot.getLotteryCode());
	    		userLotConfig.setType(type);
	    		userLotConfig.setUserId(userId);
	    		int status = UseStatus.INVALID.getCode().intValue();
	    		int lotCode = lot.getLotteryCode();
	    		if(lotCode == 100 || lotCode == 102){// 默认只支持双色球和大乐透发送
	    			status = UseStatus.VALID.getCode().intValue();
	    		}
	    		userLotConfig.setApp(status);
	    		list.add(userLotConfig);
	    	}
	    }
		return list;
	}

	@Override
	public OperateMsgNewBO findNewMsgByBatch(String msgBatch) {
		return iOperateMgrService.findNewMsgByBatch(msgBatch);
	}

	@Override
	public int updateNewMsgStatus(int status, Integer id) {
		return iOperateMgrService.updateNewMsgStatus(status, id);
	}

	@Override
	public List<UserInfoBO> findUserInfoByIds(List<Integer> list) {
		return iOperateMgrService.findUserInfoByIds(list);
	}

	@Override
	public int addMsgInfo(List<OperateMsgInfoVO> list) {
		return iOperateMgrService.addMsgInfo(list);
	}

	@Override
	public OperateMsgConfigBO findUserConfig(Integer userId, Integer typeId) {
		return iOperateMgrService.findUserConfig(userId, typeId);
	}

	@Override
	public int findValidUserInfoCount() {
		return iOperateMgrService.findValidUserInfoCount();
	}

	@Override
	public List<UserInfoBO> findValidUserInfo(Integer begin, Integer end) {
		return iOperateMgrService.findValidUserInfo(begin, end);
	}


	@Override
	public List<String> getSendUserList(String msgBatch) {
		return iOperateMgrService.getSendUserList(msgBatch);
	}

	@Override
	public OperateMsgTemplateBO findTemplateTypeId(int typeId) {
		OperateMsgTemplateVO vo = new OperateMsgTemplateVO();
		vo.setTypeId(typeId);
		List<OperateMsgTemplateBO> list = iOperateMgrService.findMsgTemplate(vo);
		if(list.size() == 1){
			return list.get(0);
		}else{
			return new OperateMsgTemplateBO();
		}
	}

	@Override
	public void cancelSendMsg(List<OperateMsgInfoVO> list) {
		iOperateMgrService.cancelSendMsg(list);
	}

}
