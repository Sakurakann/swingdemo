package com.hzjs.domain;

import java.util.Date;

public class TaskQuery {

	public TaskQuery() {
	}
	
	@Override
	public String toString() {
		return "TaskQuery [taskId=" + taskId + ", testCode=" + testCode
				+ ", taskName=" + taskName + ", executeMode=" + executeMode
				+ ", cycleValue=" + cycleValue + ", taskStatus=" + taskStatus
				+ ", planTime=" + planTime + ", successNum=" + successNum
				+ ", successRate=" + successRate + ", failureNum=" + failureNum
				+ ", failureRate=" + failureRate + ", testCount=" + testCount
				+ "]";
	}

	private int taskSId;
	private int taskId;
	private int testCode;
	private String taskName;
	/**
	 * 执行方式 1 立即；2定时；3 循环
	 */
	private int executeNum;
	private char executeMode;
	
	private int cycleInput;
	private int cycleUnit;
	/**
	 * 循环周期换算值 单位：分钟
	 */
	private int cycleValue;
	/**
	 * 任务状态 0 无效；1 等待执行；2 正在执行；3 完成；4 失败；5 暂停；6 中止；7 恢复执行；8 挂起；
	 */
	private char taskStatus;
	/**
	 * 计划执行时间
	 */

	private Date planTime;
	
	private int successNum = 0;
	private double successRate = 0;
	private int failureNum = 0;
	private double failureRate = 0;
	private int testCount = 0;
	private int testNum = 0;
	private String remark1;
	/**
	 * 通话时长
	 */
	private int duration;
	
	/**
	 * 呼叫间隔
	 */
	private int interVal;
 
	public int getTestNum() {
		return testNum;
	}

	public void setTestNum(int testNum) {
		this.testNum = testNum;
	}

	public int getTaskId() {
		return taskId;
	}

	public int getTaskSId() {
		return taskSId;
	}

	public void setTaskSId(int taskSId) {
		this.taskSId = taskSId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public char getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(char executeMode) {
		this.executeMode = executeMode;
	}

	public int getExecuteNum() {
		return executeNum;
	}

	public void setExecuteNum(int executeNum) {
		this.executeNum = executeNum;
	}

	public int getCycleValue() {
		switch (this.cycleUnit) {
		case 0:
			return 0;
		case 1:
			return this.cycleInput;
		case 2:
			return this.cycleInput*60;
		case 3:
			return this.cycleInput*60*24;
		case 4:
			return this.cycleInput*60*24*7;
		case 5:
			return this.cycleInput*60*24*30;
		default:
			return -1;
		}
	}

	public void setCycleValue(int cycleValue) {
		this.cycleValue = cycleValue;
	}

	public int getCycleInput() {
		return cycleInput;
	}

	public void setCycleInput(int cycleInput) {
		this.cycleInput = cycleInput;
	}

	public int getCycleUnit() {
		return cycleUnit;
	}

	public void setCycleUnit(int cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	public char getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(char taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

	public double getSuccessRate() {
		//避免除零异常 或使数据格式化异常
		if (testNum <= 0) {
			return 0;
		}
		successRate = (double) successNum / testNum;
		return successRate;
	}

	public int getFailureNum() {
		return failureNum;
	}

	public void setFailureNum(int failureNum) {
		this.failureNum = failureNum;
	}

	public double getFailureRate() {
		if (testNum <= 0) {
			return 0;
		}
		failureRate = (double) failureNum / testNum;
		return failureRate;
	}

	public int getTestCount() {
		return testCount;
	}

	public int getTestCode() {
		return testCode;
	}

	public void setTestCode(int testCode) {
		this.testCode = testCode;
	}

	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getInterVal() {
		return interVal;
	}

	public void setInterVal(int interVal) {
		this.interVal = interVal;
	}

}
