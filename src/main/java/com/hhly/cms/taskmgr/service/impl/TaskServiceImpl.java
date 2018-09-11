package com.hhly.cms.taskmgr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.taskmgr.tasks.JobWayEnum;
import com.hhly.cms.taskmgr.tasks.TaskHandler;
import com.hhly.cmscore.cms.remote.service.ITaskMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TaskEnum.FailSucFlag;
import com.hhly.skeleton.base.common.TaskEnum.JobRunWay;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.cms.taskmgr.bo.TaskBO;
import com.hhly.skeleton.cms.taskmgr.bo.TaskInfoBO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskInfoVO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskVO;

/**
 * @author huangb
 *
 * @Date 2017年1月5日
 *
 * @Desc 调度服务实现
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {
	/**
	 * 日志对象
	 */
	private static Logger logger = Logger.getLogger(TaskServiceImpl.class);
	
	/**
	 * task远程服务
	 */
	@Autowired
	private ITaskMgrService taskMgrService;

	@Override
	public PagingBO<TaskBO> findPaging(TaskVO taskVO) {
		return taskMgrService.findPaging(taskVO);
	}

	@Override
	public TaskBO findSingle(TaskVO taskVO) {
		return taskMgrService.findSingle(taskVO);
	}

	@Override
	public List<TaskBO> findMultiple(TaskVO taskVO) {
		return taskMgrService.findMultiple(taskVO);
	}

	@Override
	public ResultBO<Integer> addTask(TaskVO taskVO) {
		return taskMgrService.addTask(taskVO);
	}

	@Override
	public int updTask(TaskVO taskVO) {
		return taskMgrService.updTask(taskVO);
	}

	@Override
	public int delTaskByIds(String ids) {
		return taskMgrService.delTaskByIds(ids);
	}

	/************************* 以下是任务记录表的操作 ***************************/

	@Override
	public PagingBO<TaskInfoBO> findPagingTaskInfo(TaskInfoVO taskInfoVO) {
		return taskMgrService.findPagingTaskInfo(taskInfoVO);
	}

	@Override
	public TaskInfoBO findSingleTaskInfo(TaskInfoVO taskInfoVO) {
		return taskMgrService.findSingleTaskInfo(taskInfoVO);
	}

	@Override
	public List<TaskInfoBO> findMultipleTaskInfo(TaskInfoVO taskInfoVO) {
		return taskMgrService.findMultipleTaskInfo(taskInfoVO);
	}

	@Override
	public ResultBO<Long> addTaskInfo(TaskInfoVO taskInfoVO) {
		return taskMgrService.addTaskInfo(taskInfoVO);
	}

	@Override
	public int updTaskInfo(TaskInfoVO taskInfoVO) {
		return taskMgrService.updTaskInfo(taskInfoVO);
	}

	@Override
	public List<DictionaryBO> listJobWay() {
		JobWayEnum[] ways = JobWayEnum.values();
		List<DictionaryBO> list = new ArrayList<>();
		for (JobWayEnum way : ways) {
			DictionaryBO bo = new DictionaryBO();
			bo.setId(String.valueOf(way.ordinal()));
			bo.setText(way.getName());
			list.add(bo);
		}
		return list;
	}

	@Override
	public void runTask(String jobId, Map<String, String> param) {
		TaskVO vo = new TaskVO();
		vo.setJobId(jobId);
		TaskBO bo = taskMgrService.findSingle(vo);
		if(bo == null){
			throw new ServiceRuntimeException("任务不存在！");
		}
		TaskHandler.runNow(bo, param);
	}

	@Override
	public ResultBO<?> runTaskSync(String jobId, Map<String, String> params) {
		TaskVO vo = new TaskVO();
		vo.setJobId(jobId);
		TaskBO taskBO = taskMgrService.findSingle(vo);
		Assert.notNull(taskBO, "30100");
		// 任务配置参数集
		Map<String, String> paramMap = TaskHandler.getParams(taskBO);
		if (params != null) {
			paramMap.putAll(params);
		}
		// 调用URL
		String url = taskBO.getJobManualUrl();
		Assert.hasText(url, "30101");
		// 日志记录
		TaskInfoVO taskInfo = new TaskInfoVO();
		taskInfo.setRunWay(JobRunWay.MANUAL.getValue());
		taskInfo.setTimedTaskId(taskBO.getId());
		taskInfo.setStatus(FailSucFlag.SUCCESS.getValue());
		taskInfo.setRunTime(new Date());
		taskInfo.setStartTime(taskInfo.getRunTime());
		ResultBO<?> resultBO = null;
		taskInfo.setRemark(JsonUtil.object2Json(paramMap));
		try {
			// 请求
			String result = HttpUtil.doPost(url, paramMap);
			resultBO = (ResultBO<?>) JsonUtil.json2Object(result, ResultBO.class);
		} catch (Exception e) {
			logger.error("请求任务执行异常", e);
			taskInfo.setStatus(FailSucFlag.FAIL.getValue());
			resultBO = ResultBO.err("30107");
		}
		taskInfo.setEndTime(new Date());
		int spendTime = (int) (taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime());
		taskInfo.setSpendTime(spendTime);
		if (resultBO != null && Objects.equals(resultBO.getErrorCode(), ResultBO.SUCCESS_CODE)) {
			taskInfo.setResult(FailSucFlag.SUCCESS.getValue());
		} else {
			taskInfo.setResult(FailSucFlag.FAIL.getValue());
			if(resultBO != null){
				taskInfo.setRemark(resultBO.getMessage()+taskInfo.getRemark());
			}
		}
		// 添加记录
		addTaskInfo(taskInfo);
		return resultBO;
	}

	@Override
	public void runTaskByGroup(String groupId, Map<String, String> param) {
		TaskVO vo = new TaskVO();
		vo.setJobGroup(groupId);
		vo.setPageIndex(0);
		vo.setPageSize(30);
		PagingBO<TaskBO> page = findPaging(vo);
		if(page.getTotal() <= 0){
			return;
		}
		for (TaskBO bo : page.getData()) {
			if(bo.getJobStatus().intValue() == 1){
				runTask(bo.getJobId(), param);
			}
		}
	}
}
