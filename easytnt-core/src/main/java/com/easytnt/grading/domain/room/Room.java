/**
 * 
 * 
 **/

package com.easytnt.grading.domain.room;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.grade.ItemGradeRecord;
import com.easytnt.grading.domain.share.Area;

/** 
 * <pre>
 * 考场
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Room implements ValueObject<Room>{

	private int number;
	
	private District district;
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.number).append(this.district).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Room))
			return false;
		Room other = (Room) o;

		return new EqualsBuilder().append(this.number, other.number)
				.append(this.district, other.district).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.district).append(this.number).build();
	}

	@Override
	public boolean sameValueAs(Room other) {
		return this.equals(other);
	}
}

