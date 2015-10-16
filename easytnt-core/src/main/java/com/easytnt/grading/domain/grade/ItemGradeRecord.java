/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import com.easytnt.grading.domain.paper.Item;

/** 
 * <pre>
 * 小题评分记录
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class ItemGradeRecord {
	
	private Referees referees;
	
	private Item gradeFor;

	public int hashCode() {
		//TODO 
		return 0;
	}
	
	public boolean equals() {
		//TODO 
		return false;
	}
	
	public String toString() {
		//TODO  
		return this.getClass().getName();
	}
}

