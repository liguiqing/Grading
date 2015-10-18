/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;
/** 
 * <pre>
 * 试卷中的小题，可以进行作答的题目
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Item {
	
	private Section section;
	
	private String title;
	
	private String caption;
	
	private String scoreDot;
	
	private AnswerArea area;
	
	public int getMinPoint() {
		//TODO
		int[] allPoints = getAllPoints();
		return allPoints[0];
	}
	
	public int getMaxPoint() {
		//TODO
		int[] allPoints = getAllPoints();
		return allPoints[allPoints.length-1];
	}
	
	public int[] getAllPoints() {
		//TODO
		return new int[] {0,1,2};
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getScoreDot() {
		return scoreDot;
	}

	public void setScoreDot(String scoreDot) {
		this.scoreDot = scoreDot;
	}

	public AnswerArea getArea() {
		return area;
	}

	public void setArea(AnswerArea area) {
		this.area = area;
	}
	
}

