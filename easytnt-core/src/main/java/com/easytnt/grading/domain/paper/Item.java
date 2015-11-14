/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.NumberUtils;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 给分点
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Item implements ValueObject<Item> {

	private String title;
	
	private Long itemOid;

	private String caption;

	private Area answerArea;

	private Float fullScore;

	private Float[] validValues;
	
	private boolean seriesScore;// 是否连续给分
	
	private double interval;// 如果连续给分的给分区间

	public Item(Float fullScore,String title,String caption) {
		this.fullScore = fullScore;
		this.title = title;
		this.caption = caption;
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
		Float[] allPoints = getAllPoints();
		return allPoints[0];
	}

	public Float getMaxPoint() {
		return fullScore;
	}


	public Float[] getAllPoints() {
		// TODO
		return new Float[] { 0f, 1f, 2f };
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.itemId).append(this.title).append(this.fullScore).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Item))
			return false;

		Item other = (Item) o;
		return new EqualsBuilder().append(this.itemId, other.itemId).append(this.title, other.title)
				.append(this.fullScore, other.fullScore).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.title).append(this.fullScore).build();
	}

	@Override
	public boolean sameValueAs(Item other) {
		return this.equals(other);
	}

	public static class Builder {
		private Item item;

		public Builder(String title) {
			this.item = new Item();
			this.item.title = title;
		}

		public Builder caption(String caption) {
			this.item.caption = caption;
			return this;
		}

		public Builder validValues(Float[] validValues) {
			this.item.validValues = validValues;
			return this;
		}

		public Builder fullScore(Float fullScore) {
			this.item.fullScore = fullScore;
			return this;
		}

		public Builder answerArea(Area answerArea) {
			this.item.answerArea = answerArea;
			return this;
		}

		public Item create() {
			return this.item;
		}
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public Item() {

	}

	private Long itemId;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Float[] getValidValues() {
		return validValues;
	}

	public void setValidValues(Float[] validValues) {
		this.validValues = validValues;
	}

	public Area getAnswerArea() {
		return answerArea;
	}

	public void setAnswerArea(Area answerArea) {
		this.answerArea = answerArea;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
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

	public Long getItemOid() {
		return itemOid;
	}

	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}
	
	public String getValidscoredot() {
		return genValidscoredot(this.validValues);
	}

	public void setValidscoredot(String validscoredot) {
		genValidValues(validscoredot);
	}
}
