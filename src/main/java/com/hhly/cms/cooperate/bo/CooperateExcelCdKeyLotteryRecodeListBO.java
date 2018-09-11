package com.hhly.cms.cooperate.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhly.skeleton.base.bo.BaseBO;
import com.hhly.skeleton.base.excel.ExcelHeader;
import com.hhly.skeleton.base.excel.ReplaceAttribute;
import com.hhly.skeleton.base.model.DicDataEnum;

import java.util.Date;

/**
 * @author lgs on
 * @version 1.0
 * @desc 彩种库存
 * @date 2018/3/12.
 * @company 益彩网络科技有限公司
 */
public class CooperateExcelCdKeyLotteryRecodeListBO extends BaseBO {

    /**
     * 主键
     */
    @ExcelHeader("序号")
    private Integer id;

    /**
     * 官方兑换码
     */
    @ExcelHeader("官方兑换码")
    private String lottoCdkey;


    /**
     * 本站兑换码
     */
    @ExcelHeader("本站兑换码")
    private String myCdKey;


    /**
     * 兑换码来源
     */
    @ExcelHeader("兑换码来源")
    private String exchangeChannel;

    /**
     * 兑换状态
     */
    @ExcelHeader("兑换状态")
    @ReplaceAttribute(DicDataEnum.CDKEY_STATUS)
    private String stauts;

    /**
     * 彩种
     */
    @ExcelHeader("彩种名称")
    private String lotteryCode;

    /**
     * 兑换渠道id
     */
    @ExcelHeader("兑换渠道")
    private String channelId;

    /**
     * userid
     */
    @ExcelHeader("兑换用户ID")
    private String userId;

    /**
     * 账号名称
     */
    @ExcelHeader("账号名称")
    private String account;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelHeader("创建时间")
    private Date createTime;

    /**
     * 兑换时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelHeader("兑换时间")
    private Date exchangeRecordTime;


    /**
     * 兑换截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelHeader("兑换截止时间")
    private Date exchangeOverTime;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelHeader("修改时间")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLottoCdkey() {
        return lottoCdkey;
    }

    public void setLottoCdkey(String lottoCdkey) {
        this.lottoCdkey = lottoCdkey;
    }

    public String getMyCdKey() {
        return myCdKey;
    }

    public void setMyCdKey(String myCdKey) {
        this.myCdKey = myCdKey;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getExchangeChannel() {
        return exchangeChannel;
    }

    public void setExchangeChannel(String exchangeChannel) {
        this.exchangeChannel = exchangeChannel;
    }

    public Date getExchangeRecordTime() {
        return exchangeRecordTime;
    }

    public void setExchangeRecordTime(Date exchangeRecordTime) {
        this.exchangeRecordTime = exchangeRecordTime;
    }

    public Date getExchangeOverTime() {
        return exchangeOverTime;
    }

    public void setExchangeOverTime(Date exchangeOverTime) {
        this.exchangeOverTime = exchangeOverTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
