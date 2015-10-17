/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.Set;

import com.easytnt.grading.domain.cuttings.PieceCuttings;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class GradeTask {
	
	private Referees referees;

	private Set<PieceCuttings> tasks;

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

