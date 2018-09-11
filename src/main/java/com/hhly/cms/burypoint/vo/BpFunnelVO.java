package com.hhly.cms.burypoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BpFunnelVO extends PageVO {

    private Integer id;
    
    private List<Integer> ids;

	private String name;
	/**
     * 点集合信息(多个)
     */
    private String bpCode;
    /**
     * 渠道id(支持多个)
     */
    private String channelsId;
    /**
     * 平台id(支持多个)
     */
    private String platformsId;
    /**
     * 版本id(支持多个)
     */
    private String versionsId;

    private String bpCodeText;
    private String channelsIdText;
    private String platformsIdText;
    private String versionsIdText;
    
	/**
	 * 排序类型
	 */
	private Integer orderType;
	/**
	 * 转化时间(天)
	 */
	private Integer dayNumber;
	/**
	 * 删除状态
	 */
	private Integer delStatic;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyTime;
	
    private String modifyBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	private Integer newDelStatic;

	private String nameLike;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBpCode() {
		return bpCode;
	}

	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}

	public String getChannelsId() {
		return channelsId;
	}

	public void setChannelsId(String channelsId) {
		this.channelsId = channelsId;
	}

	public String getPlatformsId() {
		return platformsId;
	}

	public void setPlatformsId(String platformsId) {
		this.platformsId = platformsId;
	}

	public String getVersionsId() {
		return versionsId;
	}

	public void setVersionsId(String versionsId) {
		this.versionsId = versionsId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Integer getDelStatic() {
		return delStatic;
	}

	public void setDelStatic(Integer delStatic) {
		this.delStatic = delStatic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getNewDelStatic() {
		return newDelStatic;
	}

	public void setNewDelStatic(Integer newDelStatic) {
		this.newDelStatic = newDelStatic;
	}

	public String getBpCodeText() {
		return bpCodeText;
	}

	public void setBpCodeText(String bpCodeText) {
		this.bpCodeText = bpCodeText;
	}

	public String getChannelsIdText() {
		return channelsIdText;
	}

	public void setChannelsIdText(String channelsIdText) {
		this.channelsIdText = channelsIdText;
	}

	public String getPlatformsIdText() {
		return platformsIdText;
	}

	public void setPlatformsIdText(String platformsIdText) {
		this.platformsIdText = platformsIdText;
	}

	public String getVersionsIdText() {
		return versionsIdText;
	}

	public void setVersionsIdText(String versionsIdText) {
		this.versionsIdText = versionsIdText;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
}
