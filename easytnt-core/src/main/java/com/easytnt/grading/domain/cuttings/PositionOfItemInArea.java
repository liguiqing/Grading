/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.cuttings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.NumberUtils;

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
	
	private Float[] validValues;
	
	private Float fullScore;
	
	private boolean seriesScore;// 是否连续给分
	
	private double interval;// 如果连续给分的给分区间
	
	public PositionOfItemInArea(Item item,Section section,Area areaIn) {
		this.areaIn = areaIn;
		this.section = section;
		this.areaIn = areaIn;
	}
	
	public PositionOfItemInArea(Item item,Section section,Area areaIn,Float[] validValues) {
		this.areaIn = areaIn;
		this.section = section;
		this.areaIn = areaIn;
		this.validValues = validValues;
	}
	
	public boolean isEffectiveScore(Float score) {
		return score.compareTo(this.getMinPoint()) >= 0
				&& score.compareTo(getMaxPoint()) <= 0;
	}
	
	public void genValidValues(String validscoredot) {
		String[] values = validscoredot.split(",");
		if(values.length > 0) {
			Float[] scores = new Float[values.length];
			int i = 0;
			for(String value:values) {
				scores[i++] = NumberUtils.parseNumber(value, Float.class);
			}
			if(!this.isEffectiveScore(scores[scores.length-1])) {
				throw new IllegalArgumentException(validscoredot + "不在有效分范围内");
			}
			
			this.validValues = scores;
		}
	}
	public String genValidscoredot(Float[] validValues) {
		if(validValues.length > 0) {
			StringBuffer sb = new StringBuffer();
			for(Float value:validValues) {
				sb.append(value).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		return null;
	}

	public Float getMinPoint() {
		return this.item.getMinPoint();
	}

	public Float getMaxPoint() {
		return this.item.getMaxPoint();
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

	public Float[] getValidValues() {
		return validValues;
	}

	public void setValidValues(Float[] validValues) {
		this.validValues = validValues;
	}

	public boolean isSeriesScore() {
		return seriesScore;
	}

	public void setSeriesScore(boolean seriesScore) {
		this.seriesScore = seriesScore;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}

}
