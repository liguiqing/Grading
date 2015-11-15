/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller.formbean;

import com.easytnt.grading.domain.exam.SubjectExam;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月15日
 * @version 1.0
 **/
public class SubjectExamFormBean {

	private String subjectName;
	
	private Float fullScore;
	
	private Float objectivityScore;
	
	private Float subjectivityScore;
	
	private Long testId;
	
	public SubjectExam toSubjectExam(int subjectCode) {
		SubjectExam subjectExam = SubjectExam.newSubjectExam(this.subjectName, subjectCode, this.fullScore, 
				this.objectivityScore, this.subjectivityScore);
		return subjectExam;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Float getFullScore() {
		return fullScore;
	}
	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}
	public Float getObjectivityScore() {
		return objectivityScore;
	}
	public void setObjectivityScore(Float objectivityScore) {
		this.objectivityScore = objectivityScore;
	}
	public Float getSubjectivityScore() {
		return subjectivityScore;
	}
	public void setSubjectivityScore(Float subjectivityScore) {
		this.subjectivityScore = subjectivityScore;
	}
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
	
}
