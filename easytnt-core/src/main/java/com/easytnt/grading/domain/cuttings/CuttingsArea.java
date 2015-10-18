/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.cuttings;

import java.util.List;

import com.easytnt.grading.domain.paper.Section;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月18日
 * @version 1.0
 **/
public class CuttingsArea {

	private List<Section> sections;
	
	public List<Section> getSections() {
		// TODO Auto-generated method stub
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
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


