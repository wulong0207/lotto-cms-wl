package com.hhly.cms.taskmgr.tasks.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.web.context.ContextLoader;

import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.taskmgr.service.impl.TaskServiceImpl;
import com.hhly.cms.taskmgr.tasks.TaskHandler;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TaskEnum.FailSucFlag;
import com.hhly.skeleton.base.common.TaskEnum.JobRunWay;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.cms.taskmgr.vo.TaskInfoVO;
/**
 * @desc 默认定时任务处理类
 * @author jiangwei
 * @date 2017年4月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@PersistJobDataAfterExecution
public class DefaultJob implements Job{
   
	private static TaskService taskService;
	
	private static final Logger logger = Logger.getLogger(DefaultJob.class);
	
	private void init(){
		if(taskService == null){
			taskService = ContextLoader.getCurrentWebApplicationContext().getBean(TaskServiceImpl.class);
		}
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		init();
		Map<String,String> param = new HashMap<>();
		for (Map.Entry<String, Object> entry : context.getMergedJobDataMap().entrySet()) {
			if(entry.getValue() !=null){
				param.put(entry.getKey(),entry.getValue().toString());
			}
			
		}
		String url = param.get(TaskHandler.URL);
		param.remove(TaskHandler.URL);
		String taskId = param.get(TaskHandler.TASK_ID);
		//日志记录
		TaskInfoVO taskInfo = new TaskInfoVO();
		String runWay = param.get(TaskHandler.RUN_WAY);
		if(StringUtils.isBlank(runWay)){
			taskInfo.setRunWay(JobRunWay.AUTO.getValue());
		}else{
			taskInfo.setRunWay(Short.parseShort(runWay));
		}
		taskInfo.setTimedTaskId(Integer.parseInt(taskId));
		taskInfo.setStatus(FailSucFlag.SUCCESS.getValue());
		taskInfo.setRunTime(new Date());
		taskInfo.setStartTime(taskInfo.getRunTime());
		ResultBO<?> resultBO = null;
		taskInfo.setRemark(JsonUtil.object2Json(param));
		String result ="";
		try {
			//请求
			result = HttpUtil.doPost(url, param);
			resultBO = (ResultBO<?>) JsonUtil.json2Object(result, ResultBO.class);
		} catch (Exception e) {
			logger.error("定时任务执行异常,url:" + url + ",参数：" + JsonUtil.objectToJcakJson(param)+"结果："+result, e);
			taskInfo.setStatus(FailSucFlag.FAIL.getValue());
		}
		taskInfo.setEndTime(new Date());
		int spendTime = (int) (taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime());
		taskInfo.setSpendTime(spendTime);
		if(resultBO !=null 
				&& Objects.equals(resultBO.getErrorCode(),ResultBO.SUCCESS_CODE)){
			taskInfo.setResult(FailSucFlag.SUCCESS.getValue());
		}else{
			taskInfo.setResult(FailSucFlag.FAIL.getValue());
			if(resultBO != null){
				taskInfo.setRemark(resultBO.getMessage()+taskInfo.getRemark());
			}
		}
		after(resultBO,context,taskInfo.getRunWay());
		taskService.addTaskInfo(taskInfo);
	}
	/**
	 * 请求后处理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 下午2:48:38
	 * @param resultBO
	 */
	protected void after(ResultBO<?> resultBO,JobExecutionContext context,short runWay){}
}
