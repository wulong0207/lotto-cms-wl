package com.hhly.cms.taskmgr.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.taskmgr.entity.TaskJobBO;
import com.hhly.cms.taskmgr.entity.TaskJobStatusBO;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.taskmgr.tasks.JobUtil;
import com.hhly.cms.taskmgr.tasks.JobWayEnum;
import com.hhly.cms.taskmgr.tasks.TaskHandler;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.RequestUtils;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.cache.sport.SportCacheEnum.SportCmsFBCacheEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.taskmgr.bo.TaskBO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskInfoVO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskVO;

/**
 * @author huangb
 *
 * @Date 2017年1月4日
 *
 * @Desc 定时任务控制器
 */
@Controller
@RequestMapping("/taskmgr/job")
public class TaskController extends BaseController {
	@Autowired
	private TaskService taskService;

	/**
	 * @return
	 * @Desc 跳转到任务管理页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "taskmgr/job_manager";
	}

	/**
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @param taskVO
	 *            参数对象
	 * @return 分页列表
	 * @throws SchedulerException 
	 * @Desc 分页查询
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(TaskVO taskVO){
		PagingBO<TaskBO> result = taskService.findPaging(taskVO);
		for (TaskBO bo : result.getData()) {
			TaskJobStatusBO jobStatusBO =  JobUtil.getJobStatus(TaskHandler.getTaskName(bo.getId()), TaskHandler.GROUP);
			bo.setQuartzStatus(jobStatusBO.getState().ordinal());
			bo.setNextFireTime(jobStatusBO.getNextFireTime());
			bo.setPreviousFireTime(jobStatusBO.getPreviousFireTime());
		}
		return result;
	}

	/**
	 * @param id
	 *            任务id
	 * @return 任务详情
	 * @throws SchedulerException 
	 * @Desc 查询任务详情
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object detail(@PathVariable(value = "id") Integer id){
		return getResultSuccess(taskService.findSingle(new TaskVO(id)));
	}

	/**
	 * @param taskVO
	 *            参数对象
	 * @return 操作结果
	 * @Desc 添加任务
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public Object add(@Valid(GroupValue.ADD) TaskVO taskVO, HttpSession session) {
		// 时间表达式是否有效
		JobWayEnum.values()[taskVO.getJobWay()].valid(taskVO);
		String userName = getUserName(session);
		taskVO.setCreateBy(userName);
		taskVO.setModifyBy(userName);
		ResultBO<Integer> result = taskService.addTask(taskVO);
		// 入库失败
		if (result.getSuccess() == ResultBO.getErr()) {
			return getResult(false);
		}
		// 入库成功，加入调度计划(针对状态为有效，且自动运行)
		if(TaskHandler.isAddJob(taskVO.getJobStatus(), taskVO.getJobAutorun())){
			TaskBO taskBO = taskService.findSingle(new TaskVO(result.getData()));
			TaskJobBO bo = TaskHandler.geTaskJobBO(taskBO);
			JobUtil.addJob(bo);
		}
		return getResult(true);
	}

	/**
	 * @param taskVO
	 *            参数对象
	 * @return 操作结果
	 * @Desc 修改任务
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	public Object update(@Valid(GroupValue.UPD) TaskVO taskVO, HttpSession session) {
		// 时间表达式是否有效
		JobWayEnum.values()[taskVO.getJobWay()].valid(taskVO);
		taskVO.setModifyBy(getUserName(session));
		int num = taskService.updTask(taskVO);
		// 入库失败
		if (num <= Constants.NUM_0) {
			return getResult(false);
		}
		// 入库成功，修改调度计划(针对状态为有效，且自动运行;可能是添加，修改，删除的任何一种)
		if(TaskHandler.isAddJob(taskVO.getJobStatus(), taskVO.getJobAutorun())){
			TaskBO taskBO = taskService.findSingle(new TaskVO(taskVO.getId()));
			TaskJobBO bo = TaskHandler.geTaskJobBO(taskBO);
			JobUtil.updateJob(bo);
		}else{
			JobUtil.delJob(TaskHandler.getTaskName(taskVO.getId()),TaskHandler.GROUP);
		}
		return getResult(true);
	}

	/**
	 * @param ids
	 *            id列表 (逗号分隔)
	 * @return 操作结果
	 * @Desc 删除任务
	 */
	@RequestMapping(value = "/del")
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public Object delete(@RequestParam("ids") String ids) {
		int num = taskService.delTaskByIds(ids);
		// 删除失败
		if (num <= Constants.NUM_0) {
			return getResult(false);
		}
		// 删除成功，修改调度计划
		for (String temp : ids.split(SymbolConstants.COMMA)) {
			int id = Integer.parseInt(temp);
			JobUtil.delJob(TaskHandler.getTaskName(id), TaskHandler.GROUP);
		}
		return getResult(true);
	}

	/**
	 * @param id
	 *            任务id
	 * @return 操作结果
	 * @Desc 手动执行任务
	 */
	@RequestMapping(value = "/manual/{id}", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	public Object manualExeTask(@PathVariable(value = "id") Integer id) {
		TaskBO targetTask = taskService.findSingle(new TaskVO(id));
		TaskHandler.runNow(targetTask,null);
		return getResult(true);
	}

	/******************* 任务执行记录相关操作 ********************/
	/**
	 * @return
	 * @Desc 跳转到任务管理页
	 */
	@RequestMapping("/exe")
	@Authority(privilege = AuthEnum.SEARCH)
	public String jobExeIndex() {
		return "taskmgr/job_execute";
	}

	/**
	 * @param taskInfoVO
	 *            任务执行参数对象
	 * @return 任务执行列表
	 * @Desc 分页查询任务执行列表
	 */
	@RequestMapping(value = "/exe/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object jobExeList(TaskInfoVO taskInfoVO) {
		return taskService.findPagingTaskInfo(taskInfoVO);
	}
	
	/**
	 * @return
	 * @Desc 任务执行方式
	 */
	@RequestMapping("/way")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object jobWay() {
		return getResultSuccess(taskService.listJobWay());
	}
	
	/**
	 * 任务重启
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月2日 上午10:27:10
	 * @param id
	 * @return
	 */
	@RequestMapping(value="restart/{id}",method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.UPD)
	@ResponseBody
	public Object restart(@PathVariable("id")Integer id) {
		TaskBO taskBO = taskService.findSingle(new TaskVO(id));
		if(TaskHandler.isAddJob(taskBO.getJobStatus(), taskBO.getJobAutorun())){
			TaskJobBO bo = TaskHandler.geTaskJobBO(taskBO);
			JobUtil.updateJob(bo);
			return getResultSuccess("操作成功！");
		}
		throw new ServiceRuntimeException("任务状态不正确，不能启动");
	}
	
    /**
     * 调度不同的任务入口
	 * @author cheng.chen
     * @return
     */
    @RequestMapping(value = "/runTask", method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    @DeleteBatchAssignCache(GetCacheEnumService = SportCmsFBCacheEnum.class)
    public Object runTask(HttpServletRequest request) {
    	Map<String, String> param = RequestUtils.converMap(request.getQueryString());
    	ResultBO<?> resultBo = getResult(false);
    	if(MapUtils.isNotEmpty(param)){
            try {
            	String jobId = param.get("jobId") == null ? null 
            			: param.get("jobId").toString();
        		resultBo = taskService.runTaskSync(jobId, param);
            } catch (Exception e) {
            	throw new ServiceRuntimeException("按钮执行任务异常!!!");
            }
    	}
    	 return resultBo;    		
    }
	
}
