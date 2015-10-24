/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 试卷中的小题，可以进行作答的题目
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Item implements ValueObject<Item> {

	private String title;

	private String caption;

	private String scoreDot;

	private Area answerArea;

	private Float fullScore;
	
	public Item(String title,Float fullScore) {
		this.title = title;
		this.fullScore = fullScore;
	}
	

	public boolean isEffectiveScore(Float score) {
		return score.compareTo(this.getMinPoint()) >= 0
				&& score.compareTo(getMaxPoint()) <= 0;
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
		return new HashCodeBuilder()
				.append(this.title).append(this.fullScore).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Item))
			return false;
		
		Item other = (Item) o;
		return new EqualsBuilder().append(this.title, other.title)
				.append(this.fullScore, other.fullScore).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.title).append(this.fullScore).build();
	}

	@Override
	public boolean sameValueAs(Item other) {
		return this.equals(other);
	}
	
	public static class Builder{
		private Item item;
		
		public Builder(String title) {
			this.item = new Item();
			this.item.title = title;
		}
		
		public Builder caption(String caption) {
			this.item.caption = caption;
			return this;
		}
		
		public Builder scoreDot(String scoreDot) {
			this.item.scoreDot = scoreDot;
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

	//以下功能为ORM或者自动构造使用，非此慎用
	public Item() {
		
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

	public String getScoreDot() {
		return scoreDot;
	}

	public void setScoreDot(String scoreDot) {
		this.scoreDot = scoreDot;
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

}
