package com.hhly.cms.taskmgr.tasks;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hhly.cms.base.common.Constants;
import com.hhly.cms.taskmgr.entity.TaskJobBO;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.skeleton.base.common.TaskEnum.JobAutoRun;
import com.hhly.skeleton.base.common.TaskEnum.JobStatus;
import com.hhly.skeleton.base.util.PropertyUtil;
import com.hhly.skeleton.cms.taskmgr.bo.TaskBO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskVO;

@Component
public class JobInit implements ApplicationListener<ContextRefreshedEvent>{
	
	private static final Logger logger = Logger.getLogger(JobInit.class);
	
	@Autowired
	private TaskService taskService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					for(;;){
						try {
							init();		
						} catch (Exception e) {
							logger.info("定时任务启动异常，等待30秒后再执行", e);
							try {
								Thread.sleep(30000);
							} catch (InterruptedException e1) {
								logger.error("线程异常",e);
							}
							continue;
						}
						break;
					}
				}
			}, 30000);
		}		
	}
	
	private void init(){
		String taskOn = PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"task_on");
		//判断重启是否开启定时任务
		if(!"1".equals(taskOn)){
			logger.info("定时任务重启启动开关未打开");
			return;
		}
		logger.info("初始化所有配置任务  begin");
		// 查询所有有效的配置任务
		List<TaskBO> taskList = taskService.findMultiple(new TaskVO(JobStatus.VALID.getValue(), JobAutoRun.YES.getValue()));
		// 加载指定的任务列表
		for (TaskBO taskBO : taskList) {
			if(TaskHandler.isAddJob(taskBO.getJobStatus(), taskBO.getJobAutorun())){
				TaskJobBO taskJobBO = TaskHandler.geTaskJobBO(taskBO);
				JobUtil.updateJob(taskJobBO);
			}
		}
		logger.info("初始化所有配置任务  end");
	}
	
}
