package com.hzjs.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import utils.mybatis.MybatisUtil;

import com.hzjs.dao.TestTypeMapper;
import com.hzjs.domain.TestType;

public class TestTypeDao {

	private SqlSession sqlSession = null;
	private TestTypeMapper mapper = null;

	public TestTypeDao() {
		sqlSession = MybatisUtil.getSqlSession();
		mapper = sqlSession.getMapper(TestTypeMapper.class);
	}

	/**
	 * 
	 * @return 所有的testType
	 * @throws SQLException
	 */
	public List<TestType> findAllTestTypes1() {
		return mapper.getTestTypes();
	}

	/**
	 * 根据类别查询类型
	 * 
	 * @param testType
	 * @return testCodeString
	 * @throws SQLException
	 */
	public String[] findTestCodeByTestType1(int Id) {
		String str = mapper.getTestCodeString(Id);
		return str.split(",");
	}

	/**
	 * 优化查询后
	 * 
	 * @param testTypeName
	 * @return
	 */
	public List<Integer> findTestCodeByName(String testTypeName) {
		String str = mapper.getTestCode(testTypeName);
		List<Integer> list = new ArrayList<>();
		String[] strArr = str.split(",");
		if (strArr == null) {
			list.add(-1);
			return list;
		}
		
		for (String s : strArr) {
			if (!Pattern.matches("\\d*", s)) {
				list.add(-1);
			}else {
				list.add(Integer.valueOf(s));
			}
		}
		return list;
	}

}
