package com.hhly.cms.paymgr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.bo.BaseBO;
import com.hhly.skeleton.base.vo.PageVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付日志信息
 * </p>
 *
 * @author xiongjingang
 * @since 2018-08-16 18:33:30
 */
public class TransLog extends PageVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer id;
    /**
     * 收款账号id
     */
    private Integer accountId;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 商户名称
     */
    private String company;

    /**
     * 商户编号
     */
    private String companyCode;
    /**
     * 交易流水编号
     */
    private String transCode;
    /**
     * 商户订单编号
     */
    private String orderCode;
    /**
     * 第三方交易会员姓名
     */
    private String thirdName;
    /**
     * 第三方交易流水号
     */
    private String thirdCode;
    /**
     * 第三方交易流水状态:1成功,2失败
     */
    private Integer thirdStatus;
    /**
     * 第三方交易支付金额
     */
    private Double thirdAmount;
    /**
     * 第三方交易类型:1收款,2提款
     */
    private Integer thirdType;
    /**
     * 回调次数
     */
    private Integer returnTimes;
    /**
     * 回调支付状态:1未推送;2推送失败;3推送成功
     */
    private Integer returnStatus;
    /**
     * 预计下次推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pushNextTime;
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


    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public Integer getThirdStatus() {
        return thirdStatus;
    }

    public void setThirdStatus(Integer thirdStatus) {
        this.thirdStatus = thirdStatus;
    }

    public Double getThirdAmount() {
        return thirdAmount;
    }

    public void setThirdAmount(Double thirdAmount) {
        this.thirdAmount = thirdAmount;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public Integer getReturnTimes() {
        return returnTimes;
    }

    public void setReturnTimes(Integer returnTimes) {
        this.returnTimes = returnTimes;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Date getPushNextTime() {
        return pushNextTime;
    }

    public void setPushNextTime(Date pushNextTime) {
        this.pushNextTime = pushNextTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "TransLog{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", companyCode=" + companyCode +
                ", transCode=" + transCode +
                ", orderCode=" + orderCode +
                ", thirdName=" + thirdName +
                ", thirdCode=" + thirdCode +
                ", thirdStatus=" + thirdStatus +
                ", thirdAmount=" + thirdAmount +
                ", thirdType=" + thirdType +
                ", returnTimes=" + returnTimes +
                ", returnStatus=" + returnStatus +
                ", pushNextTime=" + pushNextTime +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", modifyBy=" + modifyBy +
                "}";
    }
}
