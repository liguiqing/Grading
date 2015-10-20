/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/** 
 * <pre>
 * 切割方案，即一个考卷切割成多少份
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class CuttingsSolution implements ValueObject<CuttingsSolution>{
	
	private Long id;
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CuttingsSolution))
			return false;
		CuttingsSolution other = (CuttingsSolution) o;

		return new EqualsBuilder().append(this.id, other.id).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.id).build();
	}
	
	@Override
	public boolean sameValueAs(CuttingsSolution other) {
		return this.equals(other);
	}

	
	//以下功能为ORM或者自动构造使用，非此慎用
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}

