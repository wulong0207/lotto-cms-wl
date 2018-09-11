package com.hhly.cms.paymgr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.bo.BaseBO;
import com.hhly.skeleton.base.vo.PageVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商户支付请求信息
 * </p>
 *
 * @author xiongjingang
 * @since 2018-08-16 18:33:30
 */
public class RequestLog extends PageVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    private Integer id;
    /**
     * 交易流水编号
     */
    private String transCode;
    /**
     * 商户编号
     */
    private String companyCode;
    /**
     * 商户订单编号
     */
    private String orderCode;
    /**
     * 订单金额
     */
    private Double orderAmount;
    /**
     * 支付类型:1：支付宝；2：微信
     */
    private Integer payType;
    /**
     * 支付平台:1：Web,2:Wap(h5);3:Android;4:IOS
     */
    private Integer platform;
    /**
     * 同步地址
     */
    private String syncUrl;
    /**
     * 异步地址
     */
    private String asyncUrl;
    /**
     * 签名串
     */
    private String paySign;
    /**
     * 请求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSyncUrl() {
        return syncUrl;
    }

    public void setSyncUrl(String syncUrl) {
        this.syncUrl = syncUrl;
    }

    public String getAsyncUrl() {
        return asyncUrl;
    }

    public void setAsyncUrl(String asyncUrl) {
        this.asyncUrl = asyncUrl;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "RequestLog{" +
                "id=" + id +
                ", transCode=" + transCode +
                ", companyCode=" + companyCode +
                ", orderCode=" + orderCode +
                ", orderAmount=" + orderAmount +
                ", payType=" + payType +
                ", platform=" + platform +
                ", syncUrl=" + syncUrl +
                ", asyncUrl=" + asyncUrl +
                ", paySign=" + paySign +
                ", createTime=" + createTime +
                "}";
    }
}
