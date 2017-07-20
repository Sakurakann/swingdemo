package com.hzjs.dao;

import java.util.List;

import utils.page.QueryPOJO;

import com.hzjs.domain.TaskInfo;
import com.hzjs.domain.TaskQuery;

public interface TaskInfoMapper {

	/**
	 * 根据查询条件 查询任务模拟信息
	 * @param taskQuery
	 * @return 任务信息 taskinfo
	 */
	public List<TaskQuery> findTaskInfo(TaskQuery taskQuery);

	/**
	 * 根据查询条件 查询任务测试结果
	 * @param query
	 * @return 任务执行结果 takstest
	 */
	public List<TaskQuery> taskTest(TaskQuery query);

	/**
	 * 导入任务 Import
	 * @param taskInfos
	 */
	public int addTask(List<TaskInfo> taskInfos);

	/**
	 * 查询所有符合条件的测试结果
	 * @param taskQuery
	 * @return tasktest
	 */
	public List<TaskQuery> findTaskTestAll(TaskQuery taskQuery);

	
	/**
	 * 查询刚加入的任务组ID
	 * @return
	 */
	public int findTaskSID();
	
	public List<Integer> findTaskIdBySId(int taskSId);

	public List<TaskInfo> getTemplate(); 
	
	public TaskQuery findRate(int taskId);

	/**
	 * 分页 testResult
	 * @param queryPOJO
	 * @return
	 */
	public List<TaskQuery> findTaskTestAll1(QueryPOJO queryPOJO);
	public int findTaskTestAll2(QueryPOJO queryPOJO);

	/**
	 * 分页 taskinfo
	 * @param queryPOJO
	 * @return
	 */
	public List<TaskQuery> findTaskInfo1(QueryPOJO queryPOJO);
	public int findTaskInfo2(QueryPOJO queryPOJO);
}
