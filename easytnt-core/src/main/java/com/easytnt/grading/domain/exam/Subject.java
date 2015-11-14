/**
 * 
 * 
 **/

package com.easytnt.grading.domain.exam;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/**
 * <pre>
 * 考试的科目， 并非教学中的科目，就是指一类试卷 
 * 如七年级语文（只包含教学中的七年级语文），九年级科学（可能包含九年级物理、化学、生物等几个教学中的科目）
 * </pre>
 * 
 * @author 李贵庆2015年10月19日
 * @version 1.0
 **/
public class Subject implements ValueObject<Subject> {

	private String name;

	private int subjectCode;
	
	public Subject(String name,int subjectCode) {
		this.name  = name;
		this.subjectCode = subjectCode;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.subjectCode).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Subject))
			return false;
		Subject other = (Subject) o;

		return new EqualsBuilder().append(this.subjectCode, other.subjectCode).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.name).append(this.subjectCode).build();
	}

	@Override
	public boolean sameValueAs(Subject other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public Subject() {
	}
	
	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(int subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
