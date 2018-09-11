package com.hhly.cms.taskmgr.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;

import com.hhly.skeleton.base.valid.NotNull;

/**
 * @desc 定时任务参数
 * @author jiangwei
 * @date 2017年4月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TaskJobBO {
	/**
	 * 任务名
	 */
	@NotNull(msg="任务名")
	private String name;
	/**
	 * 分组
	 */
	@NotNull(msg="分组")
	private String group;
	/**
	 * 处理类
	 */
	@NotNull(msg="处理类")
	private Class<? extends Job> jobClass;
	/**
	 * 参数
	 */
	private Map<String, String> params = new HashMap<>();
	/**
	 * 表达式
	 */
	private String cron;
	/**
	 * 任务开始时间
	 */
	private Date startDate;
	/**
	 * 任务结束时间
	 */
	private Date endDate;
	/**
	 * 是否立即执行
	 */
	private boolean startNow;
	

	public boolean isStartNow() {
		return startNow;
	}

	public void setStartNow(boolean startNow) {
		this.startNow = startNow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Class<? extends Job> getJobClass() {
		return jobClass;
	}

	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}


	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
