package com.hzjs.domain;

import java.util.Date;

public class TaskInfo {

	public TaskInfo() {
	}

	private int taskId;
	private int taskSId;
	private String taskName;
	private int testCode;
	/**
	 * 执行方式
	 * 1 立即；2定时；3 循环
	 */
	private char executeMode;
	private char sendStatus;
	/**
	 * 任务状态
	 * 0 无效；1 等待执行；2 正在执行；3 完成；4 失败；5 暂停；6 中止；7 恢复执行；8 挂起；
	 */
	private char taskStatus;
	/**
	 * 计划执行时间
	 */
	private Date planTime;
	private Date lastPlanTime;
	private Date firstExecuteTime;
	private Date lastExecuteTime;
	private String caller;
	private String called;
	private int duration;
	private int interVal;
	/**
	 * 测试总次数
	 */
	private int testCount;
	/**
	 * 路由
	 */
	private String router;
	private String gateWay;
	private String textInfo;
	/**
	 * 循环输入的单位
	 * 0 不循环；1 分钟；2 小时；3 天；4 周；5 月；
	 */
	private int cycleUnit;
	/**
	 * 循环输入 单位不确定
	 */
	private int cycleInput;
	/**
	 * 循环周期换算值  单位：分钟
	 */
	private int cycleValue;
	private char alarmFlag;
	/**
	 * 执行次数
	 */
	private int executeNum;
	private int errorNum;
	private int redo;
	private char deleteFlag;
	private Date deleteDate;
	private int sapId;
	private int userId;
	private int operator;
	private int areaId;
	private String outneName;
	private String toneName1;
	private String toneName2;
	private String toneName3;
	private String outCode;
	private String inneCode;
	private String toneCode1;
	private String toneCode2;
	private String toneCode3;
	private String remark1;
	private String remark2;
	private Date istDate;
	private Date uptDate;
	private String incFlag;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskSId() {
		return taskSId;
	}

	public void setTaskSId(int taskSId) {
		this.taskSId = taskSId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTestCode() {
		return testCode;
	}

	public void setTestCode(int testCode) {
		this.testCode = testCode;
	}

	public char getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(char executeMode) {
		this.executeMode = executeMode;
	}

	public char getsendStatus() {
		return sendStatus;
	}

	public void setsendStatus(char sendStatus) {
		this.sendStatus = sendStatus;
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

	public Date getLastPlanTime() {
		return lastPlanTime;
	}

	public void setLastPlanTime(Date lastPlanTime) {
		this.lastPlanTime = lastPlanTime;
	}

	public Date getFirstExecuteTime() {
		return firstExecuteTime;
	}

	public void setFirstExecuteTime(Date firstExecuteTime) {
		this.firstExecuteTime = firstExecuteTime;
	}

	public Date getLastExecuteTime() {
		return lastExecuteTime;
	}

	public void setLastExecuteTime(Date lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	

	public int getTestCount() {
		return testCount;
	}

	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getGateWay() {
		return gateWay;
	}

	public void setGateWay(String gatWay) {
		this.gateWay = gatWay;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public int getCycleUnit() {
		return cycleUnit;
	}

	public void setCycleUnit(int cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	public int getCycleInput() {
		return cycleInput;
	}

	public void setCycleInput(int cycleInput) {
		this.cycleInput = cycleInput;
	}

	
	/**
	 * 循环输入的单位
	 * 0 不循环；1 分钟；2 小时；3 天；4 周；5 月；
	 * cycleUnit
	 */
	public int getCycleValue() {
		return cycleValue;
	}
	public void setCycleValue(int cycleValue){
		this.cycleValue = cycleValue;
	}

	public char getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(char alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public int getExecuteNum() {
		return executeNum;
	}

	public void setExecuteNum(int executeNum) {
		this.executeNum = executeNum;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public int getRedo() {
		return redo;
	}

	public void setRedo(int redo) {
		this.redo = redo;
	}

	public char getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(char deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public int getSapId() {
		return sapId;
	}

	public void setSapId(int sapId) {
		this.sapId = sapId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getOutneName() {
		return outneName;
	}

	public void setOutneName(String outneName) {
		this.outneName = outneName;
	}

	public String getToneName1() {
		return toneName1;
	}

	public void setToneName1(String toneName1) {
		this.toneName1 = toneName1;
	}

	public String getToneName2() {
		return toneName2;
	}

	public void setToneName2(String toneName2) {
		this.toneName2 = toneName2;
	}

	public String getToneName3() {
		return toneName3;
	}

	public void setToneName3(String toneName3) {
		this.toneName3 = toneName3;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getInneCode() {
		return inneCode;
	}

	public void setInneCode(String inneCode) {
		this.inneCode = inneCode;
	}

	public String getToneCode1() {
		return toneCode1;
	}

	public void setToneCode1(String toneCode1) {
		this.toneCode1 = toneCode1;
	}

	public String getToneCode2() {
		return toneCode2;
	}

	public void setToneCode2(String toneCode2) {
		this.toneCode2 = toneCode2;
	}

	public String getToneCode3() {
		return toneCode3;
	}

	public void setToneCode3(String toneCode3) {
		this.toneCode3 = toneCode3;
	}

	public Date getIstDate() {
		return istDate;
	}

	public void setIstDate(Date istDate) {
		this.istDate = istDate;
	}

	public Date getUptDate() {
		return uptDate;
	}

	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}

	public String getIncFlag() {
		return incFlag;
	}

	public void setIncFlag(String incFlag) {
		this.incFlag = incFlag;
	}

	@Override
	public String toString() {
		return "TaskInfo [taskId=" + taskId + ", taskSId=" + taskSId
				+ ", taskName=" + taskName + ", testCode=" + testCode
				+ ", executeMode=" + executeMode + ", sendStatus=" + sendStatus
				+ ", taskStatus=" + taskStatus + ", planTime=" + planTime
				+ ", lastPlanTime=" + lastPlanTime + ", firstExecuteTime="
				+ firstExecuteTime + ", lastExecuteTime=" + lastExecuteTime
				+ ", caller=" + caller + ", called=" + called + ", duration="
				+ duration + ", interval=" + interVal + ", testCount="
				+ testCount + ", router=" + router + ", gateWay=" + gateWay
				+ ", textInfo=" + textInfo + ", cycleUnit=" + cycleUnit
				+ ", cycleInput=" + cycleInput + ", cycleValue=" + cycleValue
				+ ", alarmFlag=" + alarmFlag + ", executeNum=" + executeNum
				+ ", errorNum=" + errorNum + ", redo=" + redo + ", deleteFlag="
				+ deleteFlag + ", deleteDate=" + deleteDate + ", sapId="
				+ sapId + ", userId=" + userId + ", operator=" + operator
				+ ", areaId=" + areaId + ", outneName=" + outneName
				+ ", toneName1=" + toneName1 + ", toneName2=" + toneName2
				+ ", toneName3=" + toneName3 + ", outCode=" + outCode
				+ ", inneCode=" + inneCode + ", toneCode1=" + toneCode1
				+ ", toneCode2=" + toneCode2 + ", toneCode3=" + toneCode3
				+ ", remake1=" + remark1 + ", remake2=" + remark2
				+ ", istDate=" + istDate + ", uptDate=" + uptDate
				+ ", incFlag=" + incFlag + ", getTaskId()=" + getTaskId()
				+ ", getTaskSId()=" + getTaskSId() + ", getTaskName()="
				+ getTaskName() + ", getTestCode()=" + getTestCode()
				+ ", getExecuteMode()=" + getExecuteMode()
				+ ", getsendStatus()=" + getsendStatus() + ", getTaskStatus()="
				+ getTaskStatus() + ", getPlanTime()=" + getPlanTime()
				+ ", getLastPlanTime()=" + getLastPlanTime()
				+ ", getFirstExecuteTime()=" + getFirstExecuteTime()
				+ ", getLastExecuteTime()=" + getLastExecuteTime()
				+ ", getCaller()=" + getCaller() + ", getCalled()="
				+ getCalled() + ", getDuration()=" + getDuration()
				+ ", getInterval()=" + getInterVal() + ", getTestCount()="
				+ getTestCount() + ", getRouter()=" + getRouter()
				+ ", getGateWay()=" + getGateWay() + ", getTextInfo()="
				+ getTextInfo() + ", getCycleUnit()=" + getCycleUnit()
				+ ", getCycleInput()=" + getCycleInput() + ", getCycleValue()="
				+ getCycleValue() + ", getAlarmFlag()=" + getAlarmFlag()
				+ ", getExecuteNum()=" + getExecuteNum() + ", getErrorNum()="
				+ getErrorNum() + ", getRedo()=" + getRedo()
				+ ", getDeleteFlag()=" + getDeleteFlag() + ", getDeleteDate()="
				+ getDeleteDate() + ", getSapId()=" + getSapId()
				+ ", getUserId()=" + getUserId() + ", getOperator()="
				+ getOperator() + ", getAreaId()=" + getAreaId()
				+ ", getOutneName()=" + getOutneName() + ", getToneName1()="
				+ getToneName1() + ", getToneName2()=" + getToneName2()
				+ ", getToneName3()=" + getToneName3() + ", getOutCode()="
				+ getOutCode() + ", getInneCode()=" + getInneCode()
				+ ", getToneCode1()=" + getToneCode1() + ", getToneCode2()="
				+ getToneCode2() + ", getToneCode3()=" + getToneCode3()
				+ ", getRemake1()=" + getRemark1() + ", getRemake2()="
				+ getRemark2() + ", getIstDate()=" + getIstDate()
				+ ", getUptDate()=" + getUptDate() + ", getIncFlag()="
				+ getIncFlag() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public int getInterVal() {
		return interVal;
	}

	public void setInterVal(int interVal) {
		this.interVal = interVal;
	}
}
