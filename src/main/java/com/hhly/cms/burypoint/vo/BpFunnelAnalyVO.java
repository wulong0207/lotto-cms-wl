package com.hhly.cms.burypoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BpFunnelAnalyVO extends PageVO {

    private Integer id;
    private Integer channelId;
    private Integer platform;
	private Integer versionId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
	private String bpCode;
	private List<FunnelCriteria> criterions;
    private Date funnelTime;

    /**
     * 排序类型 1,无序,2有序. 默认:1
     */
    private String orderType;

    /**
     * 上一个code
     */
    private String[] prevBpCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<FunnelCriteria> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<FunnelCriteria> criterions) {
		this.criterions = criterions;
	}

	public String getBpCode() {
		return bpCode;
	}

	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}

    public Date getFunnelTime() {
        return funnelTime;
    }

    public void setFunnelTime(Date funnelTime) {
        this.funnelTime = funnelTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String[] getPrevBpCode() {
        return prevBpCode;
    }

    public void setPrevBpCode(String[] prevBpCode) {
        this.prevBpCode = prevBpCode;
    }
}
