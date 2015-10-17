/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import com.easytnt.grading.domain.cuttings.PieceCuttings;


/** 
 * <pre>
 * 切割块评分记录
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class PieceGradeRecord {

	private Referees referees;
	
	private PieceCuttings recordFor;

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

