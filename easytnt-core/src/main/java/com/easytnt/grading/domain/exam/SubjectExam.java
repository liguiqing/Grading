/**
 * 
 * 
 **/

package com.easytnt.grading.domain.exam;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;

/** 
 * <pre>
 * 科目（或者称学科）考试,如，语文考试、数学考试
 * 科目考试可以独立进行，也可以是一次多学科考试{@link Exam}的组部分，
 * 如九年上学期期末考试包含有语文考试、数学考试、英语考试等
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class SubjectExam implements Entity<SubjectExam>{
    
    private Long oid;
    
    private ExamDesc desc;
    
    private Subject subject;
    
    public SubjectExam(ExamDesc desc,Subject subject ,Long oid) {
		this.desc = desc;
		this.subject = subject;
		this.oid = oid;
	}

    @Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.oid).toHashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof SubjectExam))
			return false;
		SubjectExam other = (SubjectExam)o;
		
		return new EqualsBuilder().append(this.oid,other.oid).isEquals();
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this).append(this.desc).build();
	}

	@Override
	public boolean sameIdentityAs(SubjectExam other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此勿用
	public SubjectExam() {
		
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public ExamDesc getDesc() {
		return desc;
	}

	public void setDesc(ExamDesc desc) {
		this.desc = desc;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
}

