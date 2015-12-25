/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.paper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月24日
 * @version 1.0
 **/
public class PaperType implements ValueObject<PaperType>{

	private int typeValue;
	
	private String typeName;
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.typeValue).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PaperType))
			return false;
		
		PaperType other = (PaperType) o;
		return new EqualsBuilder().append(this.typeValue, other.typeValue).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.typeName).append(this.typeValue).build();
	}
	
	@Override
	public boolean sameValueAs(PaperType other) {
		return this.equals(other);
	}
	
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public PaperType() {
		
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}


