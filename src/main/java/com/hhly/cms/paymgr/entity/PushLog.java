package com.hhly.cms.paymgr.entity;

import com.hhly.skeleton.base.bo.BaseBO;

import java.util.Date;

/**
 * <p>
 * 推送日志
 * </p>
 *
 * @author xiongjingang
 * @since 2018-08-16 18:33:30
 */
public class PushLog extends BaseBO {

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
     * 推送时间
     */
    private Date pushTime;
    /**
     * 回调支付状态:1未推送;2推送失败;3推送成功
     */
    private Integer returnStatus;
    /**
     * 返回结果
     */
    private String returnResult;
    /**
     * 商户编号
     */
    private String companyCode;


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

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public String toString() {
        return "PushLog{" +
                "id=" + id +
                ", transCode=" + transCode +
                ", pushTime=" + pushTime +
                ", returnStatus=" + returnStatus +
                ", returnResult=" + returnResult +
                ", companyCode=" + companyCode +
                "}";
    }
}
