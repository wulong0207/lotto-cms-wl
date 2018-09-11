package com.hhly.cms.burypoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;

@SuppressWarnings("serial")
public class BpModeVO extends PageVO {

    private Integer id;

    private Integer pageId;

    private String name;

    /**
     * 删除状态:	1:启用;0:已删除;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
