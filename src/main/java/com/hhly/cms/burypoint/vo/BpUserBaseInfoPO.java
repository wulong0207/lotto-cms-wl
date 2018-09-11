package com.hhly.cms.burypoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lgs on
 * @version 1.0
 * @desc 用户信息
 * @date 2018/1/8.
 * @company 益彩网络科技有限公司
 */
public class BpUserBaseInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*** 主键ID */
    private Long id;

    /*** 主键ID */
    private Long userId;

    /*** IMEI编号 */
    private String imei;

    /*** 最后版本id */
    private String versionId;

    /*** 注册渠道id */
    private Integer registerChannelId;

    /*** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;

    /*** 最后登录渠道id */
    private Integer channelId;

    /*** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginLastTime;

    /*** 最后下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderLastTime;

    /*** 最后开始支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payBeginTime;

    /*** 最后支付完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payLastTime;

    /*** 最近请求IP地址 */
    private String ip;

    /*** 显示隐藏开关:默认为 1：显示，0：隐藏 */
    private Integer isShow;

    /*** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /*** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    /*** 后台操作员ID */
    private String modifyBy;

    /*** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /*** 埋点编号 */
    private Integer bpCode;


    /*** 手机或者平板设备型号 */
    private String terminal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public Integer getRegisterChannelId() {
        return registerChannelId;
    }

    public void setRegisterChannelId(Integer registerChannelId) {
        this.registerChannelId = registerChannelId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Date getLoginLastTime() {
        return loginLastTime;
    }

    public void setLoginLastTime(Date loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    public Date getOrderLastTime() {
        return orderLastTime;
    }

    public void setOrderLastTime(Date orderLastTime) {
        this.orderLastTime = orderLastTime;
    }

    public Date getPayBeginTime() {
        return payBeginTime;
    }

    public void setPayBeginTime(Date payBeginTime) {
        this.payBeginTime = payBeginTime;
    }

    public Date getPayLastTime() {
        return payLastTime;
    }

    public void setPayLastTime(Date payLastTime) {
        this.payLastTime = payLastTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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

    public Integer getBpCode() {
        return bpCode;
    }

    public void setBpCode(Integer bpCode) {
        this.bpCode = bpCode;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
