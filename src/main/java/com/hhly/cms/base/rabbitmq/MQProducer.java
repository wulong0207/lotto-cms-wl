package com.hhly.cms.base.rabbitmq;

public interface MQProducer {
	/**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
    public void sendDataToQueue(String queueKey, String message);
    
    /**
     * 
     * @Description 发送消息到指定队列
     * @author HouXiangBao289
     * @param queueKey 队列
     * @param message 发送数据
     * @param messageSource 发送所属系统
     * @param typeKey 消息类型key
     */
    public void sendDataToQueue(String queueKey, Object message,String typeKey);
    
    /**
     * @desc   发送消息到指定队列
     * @author Tony Wang
     * @create 2017年7月11日
     * @param queueKey
     * @param message 
     */
    void sendDataToQueue(String queueKey, Object message);
    
    /**
     * 发送广播信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年3月27日 下午2:17:38
     * @param exchange
     * @param object
     */
    public void sendDataToFanout(String exchange,String message);
    
    /**
     * 发送消息
     * @param queueKey 队列名
     * @param message  消息
     */
    public void sendMessage(String queueKey,Object message);
}
