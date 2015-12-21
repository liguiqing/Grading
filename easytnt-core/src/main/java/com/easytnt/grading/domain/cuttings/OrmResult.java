/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OrmResult {
	private Long paperId;
	private Long studentId;
	private String omrStr;
	private String omrScore;
	private float kgScore;

	public Long getPaperId() {
		return paperId;
	}

	public OrmResult setPaperId(Long paperId) {
		this.paperId = paperId;
		return this;
	}

	public Long getStudentId() {
		return studentId;
	}

	public OrmResult setStudentId(Long studentId) {
		this.studentId = studentId;
		return this;
	}

	public String getOmrStr() {
		return omrStr;
	}

	public OrmResult setOmrStr(String omrStr) {
		this.omrStr = omrStr;
		return this;
	}

	public String getOmrScore() {
		return omrScore;
	}

	public OrmResult setOmrScore(String omrScore) {
		this.omrScore = omrScore;
		return this;
	}

	public float getKgScore() {
		return kgScore;
	}

	public OrmResult setKgScore(float kgScore) {
		this.kgScore = kgScore;
		return this;
	}

}
