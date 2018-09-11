package com.hhly.cms.taskmgr.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.quartz.JobDataMap;

import com.hhly.cms.taskmgr.entity.TaskJobBO;
import com.hhly.skeleton.base.common.TaskEnum.JobAutoRun;
import com.hhly.skeleton.base.common.TaskEnum.JobRunWay;
import com.hhly.skeleton.base.common.TaskEnum.JobStatus;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.taskmgr.bo.TaskBO;

public class TaskHandler {
	
	/**
	 * 任务名前缀
	 */
	private static String PRE_NAME = "JOB_";
	/**
	 * 任务组
	 */
	public static String GROUP = "GROUP_DEFAULT";
	
	public static final String TASK_ID = "taskId";
	
	public static final String URL = "http_task_url";
	
	public static final String RUN_WAY = "runWay";
	
	/**
	 * 判断是否需要添加定时任务状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午4:15:19
	 * @param status
	 * @param jobAutorun
	 * @return
	 */
	public static boolean isAddJob(int status,int jobAutorun){
		return status == JobStatus.VALID.getValue() && jobAutorun == JobAutoRun.YES.getValue();
	}
	
	/**
	 * 数据封装
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午4:20:14
	 * @param taskBO
	 * @return
	 */
	public static TaskJobBO geTaskJobBO(TaskBO taskBO){
		TaskJobBO taskJobBO = new TaskJobBO();
		taskJobBO.setName(getTaskName(taskBO.getId()));
		taskJobBO.setGroup(GROUP);
		taskJobBO.setJobClass(JobWayEnum.values()[taskBO.getJobWay()].getJobClass());
		taskJobBO.setCron(taskBO.getJobCronExpression());
		taskJobBO.getParams().putAll(getParams(taskBO));
		taskJobBO.getParams().put(TASK_ID,taskBO.getId().toString());
		taskJobBO.getParams().put(URL,taskBO.getJobManualUrl());
		if(Objects.equals(taskBO.getRestartRun(), 1)){
			taskJobBO.setStartNow(true);
		}
		return taskJobBO;
	}
	
	/**
	 * 获取定时任务ID
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午3:58:27
	 * @param id
	 * @return
	 */
	public static String getTaskName(int id){
		return PRE_NAME+ id;
	}
	/**
	 * 立即执行
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 下午6:07:49
	 * @param taskBO
	 */
	public static void runNow(TaskBO taskBO,Map<String,String> other){
		TaskJobBO  bo = geTaskJobBO(taskBO);
		JobDataMap map = new JobDataMap(); 
		map.put(TaskHandler.RUN_WAY, JobRunWay.MANUAL.getValue());
		if(other !=null){
			map.putAll(other);
		}
		boolean exist = JobUtil.runJob(bo.getName(),bo.getGroup(),map);
		if(!exist){
			bo.getParams().put(TaskHandler.RUN_WAY,String.valueOf(JobRunWay.MANUAL.getValue()));
			if(other !=null){
				bo.getParams().putAll(other);
			}
			bo.setStartNow(true);
			bo.setCron(null);
			bo.setStartDate(null);
			JobUtil.addJob(bo);
		}
	}
	/**
	 * targetTask 对象参数封装
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月21日 下午4:06:26
	 * @param targetTask
	 * @return
	 */
	public  static Map<String, String> getParams(TaskBO targetTask) {
		Map<String, String> params = new HashMap<String, String>();
		String paramKey1 = targetTask.getParamKey1();
		if (!StringUtil.isBlank(paramKey1)) {
			params.put(paramKey1, targetTask.getParamValue1());
		}
		String paramKey2 = targetTask.getParamKey2();
		if (!StringUtil.isBlank(paramKey2)) {
			params.put(paramKey2, targetTask.getParamValue2());
		}
		String paramKey3 = targetTask.getParamKey3();
		if (!StringUtil.isBlank(paramKey3)) {
			params.put(paramKey3, targetTask.getParamValue3());
		}
		return params;
	}
}
