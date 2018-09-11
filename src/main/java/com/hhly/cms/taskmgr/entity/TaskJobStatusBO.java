package com.hhly.cms.taskmgr.entity;

import java.util.Date;

import org.quartz.Trigger.TriggerState;

/**
 * @desc 定时任务状态
 * @author jiangwei
 * @date 2017年4月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TaskJobStatusBO {
	/**
	 * 定时器状态
	 */
	private TriggerState state;
	/**
	 * 下次执行时间
	 */
	private Date nextFireTime;
	/**
	 * 上传执行时间
	 */
	private Date previousFireTime;
	
	public TriggerState getState() {
		return state;
	}
	public void setState(TriggerState state) {
		this.state = state;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public Date getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	
	
}
