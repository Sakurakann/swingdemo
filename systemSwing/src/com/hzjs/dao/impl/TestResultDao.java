package com.hzjs.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import utils.mybatis.MybatisUtil;
import utils.page.PagePOJO;
import utils.page.QueryPOJO;

import com.hzjs.dao.TaskInfoMapper;
import com.hzjs.domain.TaskQuery;

public class TestResultDao {

	protected SqlSession sqlSession = null;
	protected TaskInfoMapper mapper = null;

	public TestResultDao() {
		this.sqlSession = MybatisUtil.getSqlSession(true);
		this.mapper = sqlSession.getMapper(TaskInfoMapper.class);
	}

	public List<TaskQuery> findTestResults(TaskQuery taskQuery) {
		List<TaskQuery> results = mapper.findTaskTestAll(taskQuery);

		for (TaskQuery task : results) {
			int taskId = task.getTaskId();
			TaskQuery query = mapper.findRate(taskId);
			task.setSuccessNum(query.getSuccessNum());
			task.setFailureNum(query.getFailureNum());
			task.setTestNum(query.getTestNum());
		}
		return results;
	}

	public PagePOJO<TaskQuery> findTestResults1(QueryPOJO queryPOJO) {

		List<TaskQuery> beanList = mapper.findTaskTestAll1(queryPOJO);
		int totleResult = mapper.findTaskTestAll2(queryPOJO);

		PagePOJO<TaskQuery> pagePOJO = new PagePOJO<>();
		pagePOJO.setBeanList(beanList);

		if (totleResult != 0) {
			pagePOJO.setCurrPage(queryPOJO.getCurrPage());
		}
		pagePOJO.setPageSize(queryPOJO.getPageSize());
		pagePOJO.setTotleResult(totleResult);

		return pagePOJO;
	}

	//daleixia xiaolei
	public PagePOJO<TaskQuery> findTestResults1(QueryPOJO queryPOJO,List<Integer> codes) {

		
		int totleResult = 0;
		List<TaskQuery> beanList = new ArrayList<>();
		for (Integer code : codes) {
			queryPOJO.setTestCode(code);
			beanList.addAll(mapper.findTaskTestAll1(queryPOJO));
			totleResult += mapper.findTaskTestAll2(queryPOJO);
		}

		PagePOJO<TaskQuery> pagePOJO = new PagePOJO<>();
		pagePOJO.setBeanList(beanList);

		if (totleResult != 0) {
			pagePOJO.setCurrPage(queryPOJO.getCurrPage());
		}
		pagePOJO.setPageSize(queryPOJO.getPageSize());
		pagePOJO.setTotleResult(totleResult);

		return pagePOJO;
	}

}
