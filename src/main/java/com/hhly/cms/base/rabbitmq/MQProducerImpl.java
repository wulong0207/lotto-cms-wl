package com.hhly.cms.base.rabbitmq;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.common.Constants;
import com.hhly.skeleton.base.mq.msg.MessageModel;
import com.hhly.skeleton.base.util.JsonUtil;

@Service("producer")
public class MQProducerImpl implements MQProducer {
	private static final Logger logger = Logger.getLogger(MQProducerImpl.class);
    @Autowired
	private AmqpTemplate amqpTemplate;
    
	@Override
	public void sendDataToQueue(String queueKey, String message) {
		byte [] body= message.getBytes();
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		Message message2 = new Message(body,properties );
		amqpTemplate.send(queueKey,message2);
	}

	@Override
	public void sendDataToFanout(String exchange, String message) {
		amqpTemplate.convertAndSend(exchange, "", message);
	}

	@Override
	public void sendDataToQueue(String queueKey, Object message, String typeKey) {
		MessageModel messageModel = new MessageModel();
		messageModel.setMessage(message);
		messageModel.setMessageSource(Constants.CMS);
		messageModel.setKey(typeKey);
        // MQ推送
        String data = JsonUtil.object2Json(messageModel);
        byte [] body= data.getBytes();
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		Message msg = new Message(body,properties );
		amqpTemplate.send(queueKey,msg);
		
	}
	/**
	 * 向消息队列中发送消息
	 * @param queueKey 队列名
	 * @param message  消息
	 */
	@Override
	public void sendMessage(String queueKey, Object message) {
		String jsonStr = "";
		if (message instanceof String) {
			jsonStr = (String) message;
		} else {
			jsonStr = JSON.toJSONString(message);
		}
		try {
			byte[] body = jsonStr.getBytes();
			MessageProperties properties = new MessageProperties();
			properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
			properties.setPriority(new Random().nextInt(10));
			Message message2 = new Message(body, properties);
			amqpTemplate.send(queueKey, message2);
		} catch (Exception e) {
			logger.error("发送消息异常！queueKey：" + queueKey + "，消息内容：" + jsonStr, e);
		}
		logger.info("发送消息成功！queueKey：" + queueKey + "，消息内容：" + jsonStr);
	}
	@Override
	public void sendDataToQueue(String queueKey, Object message) {
		amqpTemplate.convertAndSend(queueKey, message);
	}

}
