/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.grade;

/** 
 * <pre>
 * 评卷任务类型
 * TryTask:试评
 * Official:正评
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public enum TaskType {

	TryTask(1),Official(9);
	
	private int value;
	
	private TaskType(int value) {
		this.value = value;
	}
	
	public boolean typeOf(TaskType other) {
		return this.value == other.value;
	}
	
}


