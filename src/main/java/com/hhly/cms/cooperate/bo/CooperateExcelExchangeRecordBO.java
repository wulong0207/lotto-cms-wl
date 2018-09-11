package com.hhly.cms.cooperate.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.bo.BaseBO;
import com.hhly.skeleton.base.excel.ExcelHeader;
import com.hhly.skeleton.base.excel.ReplaceAttribute;
import com.hhly.skeleton.base.model.DicDataEnum;

import java.util.Date;

public class CooperateExcelExchangeRecordBO extends BaseBO {
    /**
     * 主键
     */
    @ExcelHeader("序号")
    private Integer id;

    /**
     * 渠道名称
     */
    @ExcelHeader("渠道名称")
    private String channelName;

    /**
     * 流水号
     */
    @ExcelHeader("流水号")
    private String serialNumber;

    /**
     * 交易类型,1,资金充值，2，兑换
     */
    @ExcelHeader("交易类型")
    @ReplaceAttribute(DicDataEnum.PAY_ClASS)
    private String payClass;

    /**
     * 交易金额
     */
    @ExcelHeader("交易金额")
    private Double payAmount;

    /**
     * 订单信息
     */
    @ExcelHeader("订单信息")
    private String orderInfo;

    /**
     * 渠道余额。
     */
    @ExcelHeader("渠道余额")
    private Float channelBalance;


    /**
     * 兑换账户
     */
    @ExcelHeader("兑换账户")
    private String account;


    /**
     * 兑换用户手机号码
     */
    @ExcelHeader("兑换用户手机号码")
    private String phoneNum;

    /**
     * 兑换时间
     */
    @ExcelHeader("兑换时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date exchangeRecordTime;

    /**
     * 兑换订单编号
     */
    @ExcelHeader("兑换订单编号")
    private String orderCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayClass() {
        return payClass;
    }

    public void setPayClass(String payClass) {
        this.payClass = payClass;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Float getChannelBalance() {
        return channelBalance;
    }

    public void setChannelBalance(Float channelBalance) {
        this.channelBalance = channelBalance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getExchangeRecordTime() {
        return exchangeRecordTime;
    }

    public void setExchangeRecordTime(Date exchangeRecordTime) {
        this.exchangeRecordTime = exchangeRecordTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}