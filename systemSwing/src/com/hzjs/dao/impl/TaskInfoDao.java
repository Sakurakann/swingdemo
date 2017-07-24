package com.hzjs.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import utils.mybatis.MybatisUtil;
import utils.page.PagePOJO;
import utils.page.QueryPOJO;

import com.hzjs.dao.TaskInfoMapper;
import com.hzjs.domain.TaskInfo;
import com.hzjs.domain.TaskQuery;

public class TaskInfoDao {

	private SqlSession sqlSession = null;
	private TaskInfoMapper mapper = null;

	protected String IPPattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
			+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	public TaskInfoDao() {
		this.sqlSession = MybatisUtil.getSqlSession(true);
		this.mapper = sqlSession.getMapper(TaskInfoMapper.class);
	}

	/**
	 * 导入任务 附excel解析后的list<list<object>>
	 * 
	 * @param listobj
	 * @param taskQuery
	 * @throws Exception
	 */
	public int addTasks(List<List<Object>> listobj, TaskQuery taskQuery)
			throws Exception {
		List<TaskInfo> taskInfos = putListObjToTaskInfos(listobj, taskQuery);
		// System.out.println(taskInfos.get(0).toString());
		return mapper.addTask(taskInfos);
		// sqlSession.insert(arg0, arg1)
	}

	public List<TaskQuery> findTaskInfo(TaskQuery taskQuery) {
		return mapper.findTaskInfo(taskQuery);
	}

	public PagePOJO<TaskQuery> findTaskInfo1(QueryPOJO queryPOJO) {
		List<TaskQuery> beanList = mapper.findTaskInfo1(queryPOJO);
		int totleResult = mapper.findTaskInfo2(queryPOJO);

		PagePOJO<TaskQuery> pagePOJO = new PagePOJO<TaskQuery>();

		pagePOJO.setBeanList(beanList);
		pagePOJO.setTotleResult(totleResult);
		pagePOJO.setPageSize(queryPOJO.getPageSize());

		if (totleResult != 0) {
			pagePOJO.setCurrPage(queryPOJO.getCurrPage());
		}
		return pagePOJO;
	}

	public PagePOJO<TaskQuery> findTaskInfo1(QueryPOJO queryPOJO,
			List<Integer> codes) {
		List<TaskQuery> beanList = new ArrayList<TaskQuery>();
		int totleResult = 0;

		for (Integer code : codes) {
			queryPOJO.setTestCode(code);
			beanList.addAll(mapper.findTaskInfo1(queryPOJO));
			totleResult += mapper.findTaskInfo2(queryPOJO);
		}

		beanList = mapper.findTaskInfo1(queryPOJO);
		totleResult = mapper.findTaskInfo2(queryPOJO);

		PagePOJO<TaskQuery> pagePOJO = new PagePOJO<TaskQuery>();

		pagePOJO.setBeanList(beanList);
		pagePOJO.setTotleResult(totleResult);
		pagePOJO.setPageSize(queryPOJO.getPageSize());

		if (totleResult != 0) {
			pagePOJO.setCurrPage(queryPOJO.getCurrPage());
		}
		return pagePOJO;
	}

	/**
	 * 封装数据
	 * 
	 * @param listobj
	 * @param taskQuery
	 * @return
	 * @throws Exception
	 */
	private List<TaskInfo> putListObjToTaskInfos(List<List<Object>> listobj,
			TaskQuery taskQuery) throws Exception {
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		for (int i = 1; i < listobj.size(); i++) {
			TaskInfo info = new TaskInfo();

			// 填入excel表中的数据
			// 按照表的顺序 taskname testcode caller called router textInfo GateWay
			// ToneCode1 ToneCode3 ToneName1
			List<Object> lo = listobj.get(i);
			info.setTaskName((String) lo.get(0));
			info.setTestCode(Integer.parseInt((String) lo.get(1)));
			// TODO
			if (Pattern.matches("((\\+86)|(86))?[0-9$]+|(" + IPPattern + ")",
					String.valueOf(lo.get(2)))) {
				info.setCaller(String.valueOf(lo.get(2)));
			} else {
				throw new Exception("模板中存在Caller格式错误");
			}

			info.setCalled(String.valueOf(lo.get(3)));
			info.setRouter(String.valueOf(lo.get(4)));
			info.setTextInfo(String.valueOf(lo.get(5)));
			info.setGateWay(String.valueOf(lo.get(6)));
			info.setToneCode1(String.valueOf(lo.get(7)));
			info.setToneCode3(String.valueOf(lo.get(8)));
			info.setToneName1(String.valueOf(lo.get(9)));

			// 将统一的填入
			info.setExecuteMode(taskQuery.getExecuteMode());
			info.setExecuteNum(taskQuery.getExecuteNum());
			info.setCycleInput(taskQuery.getCycleInput());
			info.setCycleUnit(taskQuery.getCycleUnit());
			info.setCycleValue(taskQuery.getCycleValue());
			info.setPlanTime(taskQuery.getPlanTime());
			info.setDuration(taskQuery.getDuration());
			info.setInterVal(taskQuery.getInterVal());
			info.setRemark1(taskQuery.getRemark1());

			taskInfos.add(info);
		}
		return taskInfos;
	}

	public int findTaskSID() {
		return mapper.findTaskSID();
	}

	public List<Integer> findTaskIdBySId(int sid) {
		return mapper.findTaskIdBySId(sid);
	}

	public List<TaskInfo> getTemplate() {
		List<Integer> codes = mapper.getTemplateTestCode();
//		return mapper.getTemplate();
		return mapper.getTemplateOrderBy(codes);
	}

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		TaskInfoMapper mapper = null;

		sqlSession = MybatisUtil.getSqlSession(true);
		mapper = sqlSession.getMapper(TaskInfoMapper.class);
		
		List<Integer> codes = mapper.getTemplateTestCode();
		
		List<TaskInfo> taskInfos = mapper.getTemplateOrderBy(codes);
		System.out.println(codes);
		System.out.println(taskInfos);
	}

}
