/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;
/** 
 * <pre>
 * 小题在试卷中的答题区域
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class AnswerArea {
	
	private int left;
	
	private int top;
	
	private int width;
	
	private int height;

	public int hashCode() {
		//TODO 
		return 0;
	}
	
	public boolean equals(Object o) {
		//TODO 
		return false;
	}
	
	public String toString() {
		//TODO  
		return this.getClass().getName();
	}

	public boolean sameAsId(Long areaId) {
		// TODO Auto-generated method stub
		return true;
	}
}
