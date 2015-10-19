/**
 * 
 * 
 **/

package com.easytnt.grading.domain.exam;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/**
 * <pre>
 * 考试特征对象
 * </pre>
 * 
 * @author 李贵庆2015年10月19日
 * @version 1.0
 **/
public class ExamDesc implements ValueObject<ExamDesc> {

	private String name;

	private Date from;

	private Date to;

	public ExamDesc(String name) {
		this.name = name;
		this.from = Calendar.getInstance().getTime();
		this.to = Calendar.getInstance().getTime();
	}
	
	public ExamDesc(String name,Date from,Date to) {
		this.name = name;
		this.from = from;
		this.to = to;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name).append(this.from).append(this.to).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExamDesc))
			return false;
		ExamDesc other = (ExamDesc) o;

		return new EqualsBuilder().append(this.name, other.name).append(this.from, this.from).append(this.to, this.to).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.name).append(this.from).append(this.to).build();
	}

	@Override
	public boolean sameValueAs(ExamDesc other) {
		return this.equals(other);
	}

	//以下功能为ORM或者自动构造使用，非此勿用
	public ExamDesc() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
