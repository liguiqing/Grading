/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OmrResult {
	private Long paperId;
	private Long studentId;
	private String omrStr;
	private String omrScore;
	private float kgScore;

	public Long getPaperId() {
		return paperId;
	}

	public OmrResult setPaperId(Long paperId) {
		this.paperId = paperId;
		return this;
	}

	public Long getStudentId() {
		return studentId;
	}

	public OmrResult setStudentId(Long studentId) {
		this.studentId = studentId;
		return this;
	}

	public String getOmrStr() {
		return omrStr;
	}

	public OmrResult setOmrStr(String omrStr) {
		this.omrStr = omrStr;
		return this;
	}

	public String getOmrScore() {
		return omrScore;
	}

	public OmrResult setOmrScore(String omrScore) {
		this.omrScore = omrScore;
		return this;
	}

	public float getKgScore() {
		return kgScore;
	}

	public OmrResult setKgScore(float kgScore) {
		this.kgScore = kgScore;
		return this;
	}

	@Override
	public String toString() {

		return new ToStringBuilder(this).append("paperId:", paperId).append("studentId:", studentId)
				.append("kgScore:" + kgScore).append("omrStr:", omrStr).append("omrScore:", omrScore).build();
	}

}
