/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.paper.AnswerArea;
import com.easytnt.grading.domain.paper.Section;

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
	
	private CuttingsArea area;
	
	public List<Section> getSections(){
		return this.area.getSections();
	}

	public String getSubjectName() {
		//TODO
		return "数学";
	}
	
	public int getRefereesTotal() {
		return 4450;
	}
	
	public int getTotal() {
		//TODO
		return 12223;
	}

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

	public Referees getReferees() {
		return referees;
	}

	public void setReferees(Referees referees) {
		this.referees = referees;
	}

	public CuttingsArea getArea() {
		return area;
	}

	public void setArea(CuttingsArea area) {
		this.area = area;
	}
	
	
}

