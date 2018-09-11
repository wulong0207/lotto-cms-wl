package com.hhly.cms.paymgr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.bo.BaseBO;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;

/**
 * <p>
 * 商户信息
 * </p>
 *
 * @author xiongjingang
 * @since 2018-08-16 18:33:30
 */
public class Merchant extends PageVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer id;
    /**
     * 商户编号
     */
    private String code;
    /**
     * 商户名称
     */
    private String company;
    /**
     * 支付秘钥
     */
    private String passwd;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 商户地址
     */
    private String address;
    /**
     * 启用状态:0未启用;1已启用
     */
    private Short status;
    /**
     * 回调地址
     */
    private String returnUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
    /**
     * 修改人
     */
    private String modifyBy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", code=" + code +
                ", company=" + company +
                ", passwd=" + passwd +
                ", name=" + name +
                ", phone=" + phone +
                ", address=" + address +
                ", status=" + status +
                ", returnUrl=" + returnUrl +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", modifyBy=" + modifyBy +
                "}";
    }
}
