/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.room.Examinee;

/** 
 * <pre>
 * 小题评分记录
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class ItemGradeRecord implements ValueObject<ItemGradeRecord>{
	
	private Referees referees;
	
	private Item gradeFor;
	
	private Float scored;
	
	public ItemGradeRecord(Item gradeFor,Float scored) {
		this.gradeFor = gradeFor;
		this.scored = scored;
	}

	public void recordedBy(Referees referees) {
		this.referees = referees;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.referees).append(this.gradeFor).append(this.scored).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ItemGradeRecord))
			return false;
		ItemGradeRecord other = (ItemGradeRecord) o;

		return new EqualsBuilder().append(this.referees, other.referees)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.referees)
				.append(this.gradeFor).append(this.scored).build();
	}

	@Override
	public boolean sameValueAs(ItemGradeRecord other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public ItemGradeRecord() {
		
	}

	public Referees getReferees() {
		return referees;
	}

	public void setReferees(Referees referees) {
		this.referees = referees;
	}

	public Item getGradeFor() {
		return gradeFor;
	}

	public void setGradeFor(Item gradeFor) {
		this.gradeFor = gradeFor;
	}

	public Float getScored() {
		return scored;
	}

	public void setScored(Float scored) {
		this.scored = scored;
	}
		
}

