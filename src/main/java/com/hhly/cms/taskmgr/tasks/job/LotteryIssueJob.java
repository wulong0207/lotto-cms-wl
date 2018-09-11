package com.hhly.cms.taskmgr.tasks.job;

import java.util.Date;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.TriggerKey;

import com.hhly.cms.taskmgr.tasks.JobUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TaskEnum.JobRunWay;

public class LotteryIssueJob extends DefaultJob {
	
	public static final String PARAM_NAME = "lotteryCode";
	/**
	 * @see 彩期自动切换，根据切换成功返回数据启动下一次定时任务执行时间，
	 * 如果切换失败，延迟1分钟继续执行
	 */
	@Override
	protected void after(ResultBO<?> resultBO, JobExecutionContext context,short runWay) {
		TriggerKey triggerKey = context.getTrigger().getKey();
		if(runWay == JobRunWay.AUTO.getValue()){
			if(resultBO !=null 
					&& Objects.equals(resultBO.getErrorCode(),ResultBO.SUCCESS_CODE)){
				long time = Long.parseLong(resultBO.getData().toString());
				if(time < System.currentTimeMillis() ){
					time = System.currentTimeMillis() + 10000;
				}
				JobUtil.modifyJobTime(triggerKey,new Date(time));
			}else{
				JobUtil.modifyJobTime(triggerKey,new Date(System.currentTimeMillis()+60000));
			}
			
		}
	}

}
