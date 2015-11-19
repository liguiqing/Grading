/**
 * 
 * 
 **/

package com.easytnt.grading.domain.exam;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;

/** 
 * <pre>
 * 考试
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Exam implements Entity<Exam> {
	
	private Long id;
	
    private Long oid;
    
    private ExamDesc desc;
    
	
	public Exam(ExamDesc desc,Long oid) {
		this.desc = desc;
		this.oid = oid;
	}

	public void genOid(){
		this.oid = System.currentTimeMillis();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.oid).toHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Exam))
			return false;
		Exam other = (Exam)o;
		
		return new EqualsBuilder().append(this.oid,other.oid).isEquals();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.desc).build();
	}

	@Override
	public boolean sameIdentityAs(Exam other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public Exam() {
		
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}