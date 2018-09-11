package com.hhly.cms.burypoint.vo;

import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BpUserVO extends PageVO {

	private Integer id;
	private Integer userId;
	/**
	 * IMEI编号
	 */
	private String imei;
	/**
	 * 最后版本id
	 */
	private Integer versionId;
	/**
	 * 注册渠道id
	 */
	private Integer registerChannelId;
	
    private Date registerTime;
	
	/**
	 * 最后登录时间
	 */
	private Date loginLastTime;
	
	/**
	 * 最后下单时间
	 */
	private Date orderLastTime;
	
	/**
	 * 最后开始支付时间
	 */
	private Date payBeginTime;
	
	/**
	 * 最后支付完成时间
	 */
	private Date payLastTime;
	
	/**
	 * 最后登录渠道id
	 */
	private Integer channelId;
	
	private Integer ip;
	
	/**
	 * 显示隐藏开关
	 */
	private Integer isShow;
	
	private Date modifyTime;
	
	private Date createTime;
	
    private String modifyBy;

	private List<Long> userIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getRegisterChannelId() {
		return registerChannelId;
	}

	public void setRegisterChannelId(Integer registerChannelId) {
		this.registerChannelId = registerChannelId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLoginLastTime() {
		return loginLastTime;
	}

	public void setLoginLastTime(Date loginLastTime) {
		this.loginLastTime = loginLastTime;
	}

	public Date getOrderLastTime() {
		return orderLastTime;
	}

	public void setOrderLastTime(Date orderLastTime) {
		this.orderLastTime = orderLastTime;
	}

	public Date getPayBeginTime() {
		return payBeginTime;
	}

	public void setPayBeginTime(Date payBeginTime) {
		this.payBeginTime = payBeginTime;
	}

	public Date getPayLastTime() {
		return payLastTime;
	}

	public void setPayLastTime(Date payLastTime) {
		this.payLastTime = payLastTime;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getIp() {
		return ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}


	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
}
