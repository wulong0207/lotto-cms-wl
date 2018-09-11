package com.hhly.cms.taskmgr.service;

import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.taskmgr.bo.TaskBO;
import com.hhly.skeleton.cms.taskmgr.bo.TaskInfoBO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskInfoVO;
import com.hhly.skeleton.cms.taskmgr.vo.TaskVO;

/**
 * @author huangb
 *
 * @Date 2017年1月4日
 *
 * @Desc 调度管理服务
 */
public interface TaskService {

	/**
	 * @param taskVO
	 *            查询参数
	 * @return 分页对象
	 * @Desc 查询调度任务分页
	 */
	PagingBO<TaskBO> findPaging(TaskVO taskVO);

	/**
	 * @param taskVO
	 *            参数对象
	 * @return 单个查询对象
	 * @desc 查询唯一的数据记录
	 */
	TaskBO findSingle(TaskVO taskVO);

	/**
	 * @param taskVO
	 *            参数对象
	 * @return 多个查询对象（列表）
	 * @desc 查询多条数据记录（列表）
	 */
	List<TaskBO> findMultiple(TaskVO taskVO);

	/**
	 * @param taskVO
	 *            参数
	 * @return 新增结果
	 * @Desc 新增调度任务
	 */
	ResultBO<Integer> addTask(TaskVO taskVO);

	/**
	 * @param taskVO
	 *            参数
	 * @return 修改结果
	 * @Desc 修改调度任务
	 */
	int updTask(TaskVO taskVO);

	/**
	 * @param ids
	 *            参数
	 * @return 删除结果
	 * @Desc 删除调度任务 (标识删除)
	 */
	int delTaskByIds(String ids);

	/************************* 以下是任务记录表的操作 ***************************/
	/**
	 * @param taskInfoVO
	 *            查询参数
	 * @return 分页对象
	 * @Desc 查询调度任务执行记录分页
	 */
	PagingBO<TaskInfoBO> findPagingTaskInfo(TaskInfoVO taskInfoVO);

	/**
	 * @param taskInfoVO
	 *            参数对象
	 * @return 单个查询对象
	 * @desc 查询唯一的数据记录
	 */
	TaskInfoBO findSingleTaskInfo(TaskInfoVO taskInfoVO);

	/**
	 * @param taskInfoVO
	 *            参数对象
	 * @return 多个查询对象（列表）
	 * @desc 查询多条数据记录（列表）
	 */
	List<TaskInfoBO> findMultipleTaskInfo(TaskInfoVO taskInfoVO);

	/**
	 * @param taskInfoVO
	 *            参数
	 * @return 新增结果
	 * @Desc 新增调度任务执行记录
	 */
	ResultBO<Long> addTaskInfo(TaskInfoVO taskInfoVO);

	/**
	 * @param taskInfoVO
	 *            参数
	 * @return 修改结果
	 * @Desc 修改调度任务执行记录
	 */
	int updTaskInfo(TaskInfoVO taskInfoVO);

	/**
	 * 获取任务执行方式
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月27日 上午10:26:25
	 * @return
	 */
	List<DictionaryBO> listJobWay();
	/**
	 * 根据任务ID执行任务
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月2日 下午5:30:54
	 * @param jobId 任务job
	 * @param param 任务调度参数
	 */
	void runTask(String jobId,Map<String, String> param);
	
	/**
	 * @desc 根据任务ID执行任务(同步返回)
	 * @author huangb
	 * @date 2017年5月13日
	 * @param jobId 任务job
	 * @param param 任务调度参数
	 * @return 根据任务ID执行任务(同步返回)
	 */
	ResultBO<?> runTaskSync(String jobId, Map<String, String> param);
	/**
	 * 通过分组执行
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月11日 上午10:55:00
	 * @param groupId
	 * @param param
	 */
	void  runTaskByGroup(String groupId, Map<String, String> param);
}
