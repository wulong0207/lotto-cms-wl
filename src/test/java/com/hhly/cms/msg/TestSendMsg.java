package com.hhly.cms.msg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.rabbitmq.MQProducer;
import com.hhly.skeleton.base.mq.msg.MessageModel;
import com.hhly.skeleton.base.mq.msg.OperateNodeMsg;
import com.hhly.skeleton.base.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSendMsg {
	@Autowired
	MQProducer mqProducer;
	
	@Value("${msg_queue_name}")
	private String QUEUE_NAME;
	
	@Test
	public void sendObsoletedNoticeMsg() {
		
		// 世界杯球队淘汰提醒
		OperateNodeMsg vo = new OperateNodeMsg();
		vo.setNodeId(21);//节点Id
		vo.setNodeData("309;09;巴西-哥伦比亚");//
		MessageModel message = new MessageModel();
	    message.setMessage(vo);
	    message.setMessageSource(Constants.CMS);
	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	     
	}

	@Test
	public void sendExchangeErrNoticeMsg() {
		
//		// 兑换码兑换失败提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(20);//节点Id
//		vo.setNodeData("D18030912023801600001");//
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	     
	}
	
	@Test
	public void sendForbitNoticeMsg() {
		
//		// 用户禁用消息
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(19);//节点Id
//		vo.setNodeData("185");//
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	     
	}
	
	@Test
	public void sendOpenCodeMsg() {
		
//		// 开奖号码通知
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(6);//节点Id
//		vo.setNodeData("100;2017068;01,02,03,04,05,06|08");//
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	     
	}
	
	@Test
	public void sendRedPacketMsg() {
//		// 红包到账通知
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(2);//节点Id
//		vo.setNodeData("1326;HB18030915060817300003");//
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	    
	}
	
	@Test
	public void sendRechargeMsg() {
//		// 充值提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(4);//节点Id
//		vo.setNodeData("1;I17101415151017300066");//
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	
	@Test
	public void sendUserInfoUpdateNotice() {
//		//会员资料变更提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(1);//节点Id
//		vo.setNodeData("1");//用户Id
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendDrawMoneySubmitNotice() {
//		//提交提款申请提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(5);//节点Id
//		vo.setNodeData("39;O17111722093917300245");
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendOrderPrizeNotice() {
//		//订单中奖派奖通知提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(7);//节点Id
//		vo.setNodeData("D17112110572317200089");//订单号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo); 
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendCancelOrderNotice() {
//		//撤单提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(13);//节点Id
//		vo.setNodeData("D17101717524417300019");//订单号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
//	@Autowired
//	private AmqpTemplate amqpTemplate;
	
	@Test
	public void sendSP() {
//		JzSpMessageData messageData = new JzSpMessageData();
//		JczqTrendSpBO bo = new JczqTrendSpBO();
//		bo.setId(1L);
//		bo.setWdf(new String[]{"t1","t2","t5"});
//		List<JczqTrendSpBO> list = new ArrayList<JczqTrendSpBO>();
//		list.add(bo);
//		messageData.setList(list);
//		amqpTemplate.convertAndSend("football_sp_test",messageData);
	}
	
	@Test
	public void sendOrderAddEndNotice() {
//		//追号结束提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(15);//节点Id
//		vo.setNodeData("JZ17101415205417200039");//追号编号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendRecommendOrderPrizeMsg() {
//		//提成提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(7);//节点Id
//		vo.setNodeData("D17112819450917300022");//推荐方案编号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendIpUpdateNotice() {
//		//换IP地址登录时发送
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(11);//节点Id
//		vo.setNodeData("2");//用户ID
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendMobileUpdateNotice() {
//		//换手机登录时发送
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(12);//节点Id
//		vo.setNodeData("2");//用户ID
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendOrderAddStopNotice() {
//		//追号停追提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(10);//节点Id
//		vo.setNodeData("D17101709544017300008");//追号编号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendFollowUserMsg() {
//		//关注提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(16);//节点Id
//		vo.setNodeData("31;1");//编号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
	@Test
	public void sendRecommendOrderMsg() {
//		//推荐订单提醒
//		OperateNodeMsg vo = new OperateNodeMsg();
//		vo.setNodeId(17);//节点Id
//		vo.setNodeData("2;D17122716144717300015");//追号编号
//		MessageModel message = new MessageModel();
//	    message.setMessage(vo);
//	    message.setMessageSource(Constants.CMS);
//	    message.setKey(com.hhly.skeleton.base.constants.Constants.MSG_NODE_RESEND);
//	    mqProducer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(message));
	}
	
//	@Autowired
//	private ILotteryMgrService iLotteryMgrService;
//	@Autowired
//	private SendMessage sendMessage;
//	
//	@Test
//	public void sendDrawResult() {
//		IssueLottBO lastestIssue = iLotteryMgrService.findIssueByParam(233, "20170717011",0);
//		DrawResultData drawResultData = new DrawResultData();
//		drawResultData.setList(Arrays.asList(lastestIssue));
//		sendMessage.sendLastestIssue(drawResultData);
//		
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
}
