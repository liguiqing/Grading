/**
 * 
 * 
 **/

package com.easytnt.grading.domain.room;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
import com.easytnt.grading.domain.paper.ExamPaper;

/**
 * <pre>
 * 考生考卷
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class ExamineePaper implements Entity<ExamineePaper> {

	private String uuid;

	private Examinee examinee;

	private ExamPaper examPaper;

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.uuid).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExamineePaper))
			return false;
		ExamineePaper other = (ExamineePaper) o;

		return new EqualsBuilder().append(this.uuid, other.uuid).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.examinee == null ? "" : this.examinee.getUuid()).build();
	}

	@Override
	public boolean sameIdentityAs(ExamineePaper other) {
		return this.equals(other);
	}
	
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public ExamineePaper() {
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Examinee getExaminee() {
		return examinee;
	}

	public void setExaminee(Examinee examinee) {
		this.examinee = examinee;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	
	
}
