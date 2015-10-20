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
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月19日
 * @version 1.0
 **/
public class Subject implements ValueObject<Subject>{

	private String name;
	
	private String code;
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name).append(this.code).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Subject))
			return false;
		Subject other = (Subject) o;

		return new EqualsBuilder().append(this.name, other.name).append(this.code, other.code).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.name).append(this.code).build();
	}
	
	@Override
	public boolean sameValueAs(Subject other) {
		return this.equals(other);
	}

	//以下功能为ORM或者自动构造使用，非此慎用
	public Subject() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

