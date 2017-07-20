package com.hzjs.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import utils.mybatis.MybatisUtil;

import com.hzjs.dao.TestTypeMapper;
import com.hzjs.domain.TestCode;


public class TestCodeDao {
	
	private SqlSession sqlSession = null;
	private TestTypeMapper mapper = null;
	
	public TestCodeDao() {
		sqlSession = MybatisUtil.getSqlSession();
		mapper = sqlSession.getMapper(TestTypeMapper.class);
	}
	
	/**
	 * mybatis-c3p0
	 * @param testCodes
	 * @return
	 */
	public List<TestCode> findTestCodesByTestType(List<Integer> codes){
		return mapper.findByTestCode1(codes);
	}
	
	public int findCodeIntByName(String testName){
		return mapper.findCodeIntByName(testName);
	}

}
