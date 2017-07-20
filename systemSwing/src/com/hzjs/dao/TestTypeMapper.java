package com.hzjs.dao;

import java.util.List;

import com.hzjs.domain.TestCode;
import com.hzjs.domain.TestType;

public interface TestTypeMapper {

	/**
	 * 获取全部的类别
	 * @return 所有的测试类别
	 */
	public List<TestType> getTestTypes();
	
	/**
	 * 根据测试类别名称 获取测试类型
	 * @param testTypeName
	 * @return Type下的TestCode String
	 */
	public String getTestCode(String testTypeName);

	/**
	 * 根据类别的Id 获取其所属的code字符串
	 * @param ID
	 * @return Type下的TestCode String
	 */
	public String getTestCodeString(int ID);

	/**
	 * 根据code 获取其所属的测试类型
	 * @param str
	 * @return 测试类型
	 */
	public List<TestCode> findByTestCode1(List<Integer> codes);
	
	public int findCodeIntByName(String testName);


}
