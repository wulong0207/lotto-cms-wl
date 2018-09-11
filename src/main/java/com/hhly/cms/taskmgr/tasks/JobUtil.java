package com.hhly.cms.taskmgr.tasks;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.ContextLoader;

import com.hhly.cms.taskmgr.entity.TaskJobBO;
import com.hhly.cms.taskmgr.entity.TaskJobStatusBO;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.util.ParamUtil;

/**
 * @desc 定时任务工具类
 * @author jiangwei
 * @date 2017年4月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JobUtil {

	private static final Logger logger = Logger.getLogger(JobUtil.class);
	
	/**
	 * 调度计划（所有有效的配置任务都会加入其中）
	 */
	private static SchedulerFactoryBean factoryBean;
	

	/**
	 * 添加定时任务
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 上午9:31:26
	 * @param jobBO
	 */
	public static void addJob(TaskJobBO jobBO){
		//验证参数
		ParamUtil.validation(jobBO);
		//构建具体执行任务
		JobDetail jobDetail = JobBuilder.newJob(jobBO.getJobClass()).withIdentity(jobBO.getName(), jobBO.getGroup()).build();
		jobDetail.getJobDataMap().putAll(jobBO.getParams());
		//构建Trigger
		TriggerBuilder<Trigger> builder = getTriggerBuilder(jobBO);
		//加入调度计划
		try {
			getScheduler().scheduleJob(jobDetail, builder.build());
		} catch (SchedulerException e) {
			logger.error("定时任务添加失败", e);
			throw new ServiceRuntimeException("定时任务添加失败"+e.getMessage());
		}
	}

	
	/**
	 * 修改定时任务
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午2:21:04
	 * @param jobBO
	 */
	public static void modifyJobTime(TaskJobBO jobBO) {
		ParamUtil.validation(jobBO);
		TriggerKey triggerKey = getTriggerKey(jobBO.getName(),jobBO.getGroup());
		try {
			if(getScheduler().getTrigger(triggerKey) == null){
				throw new  ServiceRuntimeException("定时任务不存在，不能修改");
			}
		} catch (SchedulerException e1) {
			throw new ServiceRuntimeException("修改定时任务失败", e1);
		}
		TriggerBuilder<Trigger> builder = getTriggerBuilder(jobBO);
		try {
			getScheduler().rescheduleJob(triggerKey, builder.build());
		} catch (SchedulerException e) {
			logger.error("定时任务修改失败", e);
			throw new ServiceRuntimeException("定时任务修改失败"+e.getMessage());
		}
	}
	/**
	 * 修改定时任务执行时间
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月27日 上午11:13:37
	 * @param triggerKey
	 * @param runDate
	 */
	public static void modifyJobTime(TriggerKey triggerKey,Date runDate){
		TriggerBuilder<Trigger> builder =  TriggerBuilder.newTrigger().withIdentity(triggerKey);
		builder.startAt(runDate);
		try {
			getScheduler().rescheduleJob(triggerKey, builder.build());
		} catch (SchedulerException e) {
			logger.error("定时任务修改失败", e);
			throw new ServiceRuntimeException("定时任务修改失败"+e.getMessage());
		}
	}
	/**
	 * 修改定时任务（删除原有的添加新的）
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午2:58:36
	 * @param jobBO
	 */
	public static void updateJob(TaskJobBO jobBO){
		ParamUtil.validation(jobBO);
		delJob(jobBO.getName(), jobBO.getGroup());
		addJob(jobBO);
	}
	
	/**
	 * @param name
	 *            任务名
	 * @param group
	 *            任务组
	 * @return 返回任务执行触发器
	 * @Desc 返回任务执行触发器
	 */
	public static TriggerKey getTriggerKey(String name, String group) {
		return TriggerKey.triggerKey(name, group);
	}

	/**
	 * @param triggerKey
	 *            任务执行触发器
	 * @return 调度计划中是否存在指定任务,存在则返回任务所在的触发器，无则返回空
	 * @throws SchedulerException
	 * @Desc 判断调度计划中是否存在指定任务(任务是跟任务触发器对应的)
	 */
	public static Trigger existTrigger(TriggerKey triggerKey) throws SchedulerException {
		return getScheduler().getTrigger(triggerKey);
	}


	/**
	 * @param name
	 *            名
	 * @param group
	 *            组
	 * @throws SchedulerException
	 * @Desc 删除任务
	 */
	public static void delJob(String name, String group){
		try {
			TriggerKey triggerKey = getTriggerKey(name, group);
			Trigger Trigger = existTrigger(triggerKey);
			// 任务执行触发器存在，则删除对应的job
			if (Trigger != null) {
				getScheduler().pauseTrigger(triggerKey);
				getScheduler().deleteJob(JobKey.jobKey(name, group));
			}
		} catch (SchedulerException e) {
			throw new ServiceRuntimeException("删除定时任务失败！",e);
		}
	}

	

	/**
	 * @param name
	 *            名
	 * @param group
	 *            组
	 * @throws SchedulerException
	 * @Desc 删除任务
	 */
	public static void delJobs(String[] names, String group) throws SchedulerException {
		if (names == null || names.length == 0) {
			return;
		}
		for (String name : names) {
			delJob(name, group);
		}
	}

	/**
	 * @param cronExpression
	 *            时间表达式
	 * @return 时间表达式是否合法 true/false
	 * @Desc 验证时间表达式是否合法
	 */
	public static boolean isValidExpression(String cronExpression) {
		if (StringUtil.isBlank(cronExpression)) {
			return false;
		}
		return CronExpression.isValidExpression(cronExpression);
	}
	
	/**
	 * 立即执行
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午2:27:48
	 * @param jobBO
	 */
	public static boolean runJob(String name,String group,JobDataMap map){
		 try {
			 Trigger trigger =  getScheduler().getTrigger(getTriggerKey(name, group));
			 if(trigger == null){
				 return false;
			 } 
			 JobKey jobKey = JobKey.jobKey(name,group);
			getScheduler().triggerJob(jobKey,map);
		} catch (SchedulerException e) {
			throw new ServiceRuntimeException("立即运行任务调度中的定时任务异常！",e);
		}
		return true;
	}
	
	/**
	 * 获取定时任务状态 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午3:24:51
	 * @param name
	 * @param group
	 * @return 对应枚举 TriggerState 类
	 */
	public static TaskJobStatusBO getJobStatus(String name, String group){
		TaskJobStatusBO bo = new TaskJobStatusBO();
		try {
			TriggerKey key = getTriggerKey(name, group);
			TriggerState state = getScheduler().getTriggerState(key);
			bo.setState(state);
			Trigger trigger = getScheduler().getTrigger(key);
			if(trigger != null){
				bo.setNextFireTime(trigger.getNextFireTime());
				bo.setPreviousFireTime(trigger.getPreviousFireTime());
			}
		} catch (SchedulerException e) {
			throw new ServiceRuntimeException("获取定时任务状态失败", e);
		}
		return bo;
	}
	/**
	 * 获取处理类
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 上午10:54:32
	 * @return
	 */
	private static Scheduler getScheduler(){
		if(factoryBean == null){
			factoryBean = ContextLoader.getCurrentWebApplicationContext().getBean(SchedulerFactoryBean.class);
		}
		return factoryBean.getObject();
	}
	/**
	 * 获取时间表达构建器
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 上午11:30:26
	 * @param cron
	 * @return
	 */
	private static CronScheduleBuilder getCronScheduleBuilder(String cron){
		if (StringUtils.isNotBlank( cron)) {
			if(JobUtil.isValidExpression( cron)){
				return CronScheduleBuilder.cronSchedule( cron);
			}else{
				throw new ServiceRuntimeException("定时任务时间表达式错误："+ cron);
			}
		}
		return null;
	}
	/**
	 * 获取时间处理器
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 上午11:52:06
	 * @param jobBO
	 * @return
	 */
	private static TriggerBuilder<Trigger> getTriggerBuilder(TaskJobBO jobBO) {
		TriggerBuilder<Trigger> builder =  TriggerBuilder.newTrigger().withIdentity(jobBO.getName(), jobBO.getGroup());
		boolean isRight  = false;
		//时间表达式判断
	    CronScheduleBuilder schedBuilder = getCronScheduleBuilder(jobBO.getCron());
	    
		if(schedBuilder != null){
			isRight  = true;
			builder.withSchedule(schedBuilder);
		}
		if(jobBO.getStartDate() != null){
			isRight = true;
			builder.startAt(jobBO.getStartDate());
		}
		if(jobBO.getEndDate() != null){
			builder.endAt(jobBO.getEndDate());
		}
		if(jobBO.isStartNow()){
			isRight = true;
			builder.startNow();
		}
		if(!isRight){
			throw new ServiceRuntimeException("未指定定时任务启动方式");
		}
		return builder;
	}
}
