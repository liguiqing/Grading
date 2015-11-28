/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 切割块定义
 * </pre>
 * 
 * @author 李贵庆 2015年10月18日
 * @version 1.0
 **/
public class CuttingsArea implements ValueObject<CuttingsArea> {

	private Long id;

	private String name;

	private ExamPaper paper;

	private Area areaInPaper;

	private List<PositionOfItemInArea> itemAreas;

	private List<Item> items;

	private int requiredPinci = 1; // 必须执行的评判次数

	private Float maxerror = 0f; // 最大误差值

	private int answerCardImageIdx = 0;// 答题卡图片位置

	private Float fullScore = 0f;

	public Float getFullScore() {
		return fullScore;
	}

	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}

	public CuttingsArea(ExamPaper paper, Area areaInPaper) {
		this.paper = paper;
		this.areaInPaper = areaInPaper;
	}

	public void addItemDefinition(PositionOfItemInArea itemArea) {
		if (this.itemAreas == null)
			this.itemAreas = new ArrayList<>();
		this.itemAreas.add(itemArea);
	}

	public Subject subjectOf() {
		List<Section> sections = this.getSections();
		if (sections != null && sections.size() > 0)
			return sections.get(0).getSubject();
		return null;
	}

	public List<Section> getSections() {
		if (this.itemAreas != null) {
			//临时实现方式
			ArrayList<Section> sections = new ArrayList<>();
			Section section = new Section();
			section.setCaption(this.name);
			sections.add(section);
//			for (PositionOfItemInArea area : itemAreas) {
//				if (!sections.contains(area.getSection())) {
//					sections.add(area.getSection());
//				}
//			}
			return sections;
		}
		return null;
	}

	public void validate() {
		Iterator<PositionOfItemInArea> iterItem = itemAreas.iterator();
		float itemFullScores = 0;
		while (iterItem.hasNext()) {
			PositionOfItemInArea item = iterItem.next();
			itemFullScores += item.getFullScore();
		}
		if (this.fullScore == null) {
			throw new UnsupportedOperationException("试题分数为空");
		}
		if (itemFullScores > this.fullScore) {
			throw new UnsupportedOperationException("给分点大于试题分数");
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CuttingsArea))
			return false;
		CuttingsArea other = (CuttingsArea) o;

		return new EqualsBuilder().append(this.id, other.id).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.paper).append(this.areaInPaper).build();
	}

	@Override
	public boolean sameValueAs(CuttingsArea other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsArea() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExamPaper getPaper() {
		return paper;
	}

	public void setPaper(ExamPaper paper) {
		this.paper = paper;
	}

	public Area getAreaInPaper() {
		return areaInPaper;
	}

	public void setAreaInPaper(Area areaInPaper) {
		this.areaInPaper = areaInPaper;
	}

	public int getRequiredPinci() {
		return requiredPinci;
	}

	public void setRequiredPinci(int requiredPinci) {
		this.requiredPinci = requiredPinci;
	}

	public Float getMaxerror() {
		return maxerror;
	}

	public void setMaxerror(Float maxerror) {
		this.maxerror = maxerror;
	}

	public int getAnswerCardImageIdx() {
		return answerCardImageIdx;
	}

	public void setAnswerCardImageIdx(int answerCardImageIdx) {
		this.answerCardImageIdx = answerCardImageIdx;
	}

	public List<PositionOfItemInArea> getItemAreas() {
		return itemAreas;
	}

	public void setItemAreas(List<PositionOfItemInArea> itemAreas) {
		this.itemAreas = itemAreas;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
