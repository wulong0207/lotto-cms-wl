package com.hhly.cms.burypoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BpPointVO extends PageVO {

	private Integer id;

    private Integer code;

    private String name;

    private Integer pageId;

    private Integer modeId;

    private Integer buttonId;

    private Integer delStatic;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    private String modifyBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
	
    private List<Integer> ids;

    private List<Integer> codes;

    private Integer newDelStatic;

    private String codeOrNameLike;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getModeId() {
		return modeId;
	}

	public void setModeId(Integer modeId) {
		this.modeId = modeId;
	}

	public Integer getButtonId() {
		return buttonId;
	}

	public void setButtonId(Integer buttonId) {
		this.buttonId = buttonId;
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

	public List<Integer> getCodes() {
		return codes;
	}

	public void setCodes(List<Integer> codes) {
		this.codes = codes;
	}

	public String getCodeOrNameLike() {
		return codeOrNameLike;
	}

	public void setCodeOrNameLike(String codeOrNameLike) {
		this.codeOrNameLike = codeOrNameLike;
	}
}
