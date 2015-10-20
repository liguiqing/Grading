/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.grade;

/** 
 * <pre>
 * 任务状态
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public enum TaskStatus {
	Ready(0),Start(1),Play(2),Pause(8),Finish(9),Stop(-1);
	private int value;
	
	private TaskStatus(int value) {
		this.value = value;
	}
}


