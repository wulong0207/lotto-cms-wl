package com.hhly.cms.taskmgr.tasks;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;

import com.hhly.cms.taskmgr.tasks.job.DefaultJob;
import com.hhly.cms.taskmgr.tasks.job.LotteryIssueJob;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.cms.taskmgr.vo.TaskVO;

/**
 * @desc 任务执行方式
 * @author jiangwei
 * @date 2017年4月27日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public enum JobWayEnum {
	/**
	 * 默认任务执行方式
	 */
	DEFAULT("默认方式",DefaultJob.class){
		@Override
		public void valid(TaskVO taskVO) {
			if(StringUtils.isNotBlank(taskVO.getJobCronExpression())
					&& !JobUtil.isValidExpression(taskVO.getJobCronExpression())){
				throw new ServiceRuntimeException("目标任务的时间表达式是非法的！");
			}
		}
		
	},
	/**
	 * 彩期切换
	 */
	LOTTERY_ISSUE_JOB("彩期切换",LotteryIssueJob.class){
		@Override
		public void valid(TaskVO taskVO) {
			if(StringUtils.isNotBlank(taskVO.getJobCronExpression())){
				throw new ServiceRuntimeException("不能添加时间表达式");
			}
			int count = 0;
			if(Objects.equals(LotteryIssueJob.PARAM_NAME,taskVO.getParamKey1())){
				count ++;
			}
			if(Objects.equals(LotteryIssueJob.PARAM_NAME,taskVO.getParamKey2())){
				count ++;	
			}
			if(Objects.equals(LotteryIssueJob.PARAM_NAME,taskVO.getParamKey3())){
				count ++;
			}
			if(count != 1){
				throw new ServiceRuntimeException("必须只能包含一个"+LotteryIssueJob.PARAM_NAME+"参数");
			}
		}
		
	},
	;
	JobWayEnum(String name ,Class<? extends Job> jobClass) {
		this.name = name;
		this.jobClass = jobClass;
	}
	private String name;
	
	private Class<? extends Job> jobClass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<? extends Job> getJobClass() {
		return jobClass;
	}

	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

	public abstract void valid(TaskVO taskVO);
	

}
