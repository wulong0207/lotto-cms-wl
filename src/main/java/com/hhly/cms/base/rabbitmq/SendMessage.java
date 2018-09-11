package com.hhly.cms.base.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.mq.msg.DrawResultData;
import com.hhly.skeleton.base.util.JsonUtil;

import net.sf.json.JSONObject;
/**
 * @desc 发送消息
 * @author jiangwei
 * @date 2017年6月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class SendMessage {
	//短信消息列队
	@Value("${msg_queue_name}")
	private  String  msg;
	
	@Value("${draw_result_queue_name}")
	private  String  drawResultQueueName;
	/**
	 * 短信邮箱列队
	 */
	private String messageQueue = "send_queue";
	
	@Autowired
	private MQProducer producer;
	/**
	 * 开奖号码通知
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月6日 下午3:21:15
	 * @param orders
	 */
	public void sendDrawCodeMesssage(int lotteryCode,String issueCode,String drawCode){
		JSONObject message = new JSONObject();
		message.put("nodeId", 6);
		message.put("nodeData",lotteryCode+SymbolConstants.SEMICOLON+issueCode+SymbolConstants.SEMICOLON+drawCode);
		JSONObject json = new JSONObject();
		json.put("message", message);
		json.put("key", "nodeMsgSend");
		json.put("messageSource", "cms");
		producer.sendDataToQueue(msg, json.toString());
	}
	
	public void sendLastestIssue(String jsonInString) {
		producer.sendDataToQueue(drawResultQueueName, jsonInString);
	}

	public void sendLastestIssue(DrawResultData drawResultData) {
		producer.sendDataToQueue(drawResultQueueName, drawResultData);
	}
	/**
	 * 发送彩期状态更新
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月15日 上午11:14:00
	 * @param lotteryCode
	 * @param updateDataType
	 */
	public void sendUpdateNotice(int lotteryCode,int updateDataType){
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("lotteryCode",lotteryCode);
        jsonObject.put("updateDataType",updateDataType);
        producer.sendMessage("update_notice", jsonObject.toString());
	}

	/**
	 * @desc   发送消息到msg_queue_name
	 * @author Tony Wang
	 * @create 2017年8月30日
	 * @param msgData 
	 */
	public void sendToMsgQueue(Object msgData) {
		producer.sendDataToQueue(msg, JsonUtil.objectToJson(msgData));
	}
	
	/**
	 * 用户发送邮箱信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午3:27:41
	 * @param account
	 * @param content
	 */
	public void sendQueueEmail(String account,String content){
		sendQueue(account, "5", content);
	}
	
	/**
	 * 用户发送手机短信
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午3:27:30
	 * @param account
	 * @param content
	 */
	public void sendQueueMobile(String account,String content) {
		sendQueue(account, "2", content);
	}
	/**
	 * 发送信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午3:27:16
	 * @param account
	 * @param type
	 * @param content
	 */
	private void sendQueue(String account,String type,String content) {
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("account",account);
        jsonObject.put("type",type);
        jsonObject.put("content",content);
		producer.sendDataToQueue(messageQueue, jsonObject.toString());
	}
}
