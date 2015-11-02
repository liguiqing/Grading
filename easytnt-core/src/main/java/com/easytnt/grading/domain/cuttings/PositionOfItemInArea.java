/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.cuttings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 给分点在切割区的位置
 * </pre>
 * 
 * @author 李贵庆 2015年10月24日
 * @version 1.0
 **/
public class PositionOfItemInArea implements ValueObject<PositionOfItemInArea>{
	
	private Item item;
	
	private Section section;
	
	private Area areaIn;
	
	public PositionOfItemInArea(Item item,Section section,Area areaIn) {
		this.areaIn = areaIn;
		this.section = section;
		this.areaIn = areaIn;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.item).append(this.section).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PositionOfItemInArea))
			return false;
		PositionOfItemInArea other = (PositionOfItemInArea) o;

		return new EqualsBuilder().append(this.item, other.item).append(this.section, other.section).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.section).append(this.item).append(this.areaIn).build();
	}
	
	@Override
	public boolean sameValueAs(PositionOfItemInArea other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public PositionOfItemInArea() {
		
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Area getAreaIn() {
		return areaIn;
	}

	public void setAreaIn(Area areaIn) {
		this.areaIn = areaIn;
	}

}
