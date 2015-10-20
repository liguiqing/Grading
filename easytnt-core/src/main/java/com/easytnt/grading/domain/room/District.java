/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.room;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/** 
 * <pre>
 * 考区
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class District implements ValueObject<District> {

	private int number;
	
	private District parent;

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.number).append(this.parent).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof District))
			return false;
		District other = (District) o;

		return new EqualsBuilder().append(this.number, other.number)
				.append(this.parent, other.parent).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.parent).append(this.number).build();
	}

	@Override
	public boolean sameValueAs(District other) {
		return this.equals(other);
	}
}


