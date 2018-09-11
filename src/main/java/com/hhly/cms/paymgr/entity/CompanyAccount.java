package com.hhly.cms.paymgr.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.vo.PageVO;

import java.util.Date;


/**
 * <p>
 * 支付账号
 * </p>
 *
 * @author xiongjingang
 * @since 2018-08-16 18:33:30
 */
public class CompanyAccount extends PageVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer id;
    /**
     * 商户编号
     */

    private String companyCode;
    /**
     * app登陆密码（md5加密）
     */
    private String loginPwd;
    /**
     * 收款账号
     */

    private String getAccount;
    /**
     * 收款密码
     */

    private String getPasswd;
    /**
     * 收款手机号
     */
    private String phone;
    /**
     * 收款人姓名
     */

    private String getName;
    /**
     * 当天交易次数限制
     */

    private Integer timesLimit;
    /**
     * 当天交易总金额限制
     */

    private Double totalLimit;
    /**
     * 单笔金额限制
     */
    private Double singleLimit;
    /**
     * 启用状态:0未启用;1已启用
     */
    private Short status;
    /**
     * 交易类型:1：支付宝；2：微信
     */
    private Short type;
    /**
     * 支付密码
     */
    private String payPasswd;
    /**
     * 提款银行卡号
     */
    private String cardCode;
    /**
     * 提款银行名称
     */
    private String bankName;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getGetAccount() {
        return getAccount;
    }

    public void setGetAccount(String getAccount) {
        this.getAccount = getAccount;
    }

    public String getGetPasswd() {
        return getPasswd;
    }

    public void setGetPasswd(String getPasswd) {
        this.getPasswd = getPasswd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGetName() {
        return getName;
    }

    public void setGetName(String getName) {
        this.getName = getName;
    }

    public Integer getTimesLimit() {
        return timesLimit;
    }

    public void setTimesLimit(Integer timesLimit) {
        this.timesLimit = timesLimit;
    }

    public Double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Double getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(Double singleLimit) {
        this.singleLimit = singleLimit;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getPayPasswd() {
        return payPasswd;
    }

    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }


    @Override
    public String toString() {
        return "CompanyAccount{" + "id=" + id + ", companyCode=" + companyCode + ", getAccount=" + getAccount + ", getPasswd=" + getPasswd + ", phone=" + phone + ", getName=" + getName + ", timesLimit=" + timesLimit + ", totalLimit=" + totalLimit
                + ", singleLimit=" + singleLimit + ", status=" + status + ", type=" + type + ", payPasswd=" + payPasswd + ", cardCode=" + cardCode + ", bankName=" + bankName + ", remark=" + remark + ", createTime=" + createTime + ", modifyTime="
                + modifyTime + ", modifyBy=" + modifyBy + "}";
    }
}
