/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.Calendar;
import java.util.Date;

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
	
	private Date startTime;
	
	private Date finishTime;
	
	public PieceGradeRecord(Referees referees,PieceCuttings recordFor) {
		this.referees = referees;
		this.recordFor = recordFor;
		this.startTime = Calendar.getInstance().getTime();
	}
	
	public PieceGradeRecord() {
		
	}

	public void finish() {
		this.finishTime = Calendar.getInstance().getTime();
	}
	
	public boolean recordOf(Referees referees) {
		return this.referees.equals(referees);
	}
	
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
}

