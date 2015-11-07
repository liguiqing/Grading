/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/** 
 * <pre>
 * 小题的评分标准
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class ScoreStandard implements ValueObject<ScoreStandard>{
	
	private Long id;
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).append(this.id).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ScoreStandard))
			return false;
		ScoreStandard other = (ScoreStandard) o;

		return new EqualsBuilder().append(this.id, other.id)
				.append(this.id, other.id).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.id).build();
	}

	@Override
	public boolean sameValueAs(ScoreStandard other) {
		return this.equals(other);
	}
}

