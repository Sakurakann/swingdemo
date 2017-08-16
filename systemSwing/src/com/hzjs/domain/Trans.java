package com.hzjs.domain;


/**
 * 用以传递Sid 单例类
 * @author Administrator
 *
 */
public class Trans {

	private String SID;
	
	private Trans(){}
	
	private static Trans transInstance = new Trans();
	
	public static Trans getInstance(){
		return transInstance;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}
	
	

}
