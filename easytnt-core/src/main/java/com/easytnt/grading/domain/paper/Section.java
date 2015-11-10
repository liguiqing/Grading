/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.exam.Subject;

/**
 * <pre>
 * 试卷中的大题，如：一、选择题；二、填空题等
 * 在试卷中，不能直接作答的题目，都定义为大题
 * 如语文卷中，
 * 一、选择题；
 * 二、填空题
 * 三、阅读题目
 *   1、古诗词
 *   2、文言文阅读 
 *   3、现代文阅读
 *   3.1 请阅读下方
 *       ......
 *   3.1.1 本文主要内容是什么？
 *   3.1.2 
 *   
 * 其中,一、二、三，1、 2、3，3.1均定义为大题
 * 3.1.1、3.1.2不是大题，是小题{@link Item}}
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Section implements ValueObject<Section>{

	private ExamPaper paper;

	private Section parentSection;

	private List<Section> subSection;
	
	private Subject subject;

	private String title;

	private String caption;
	
	private Float fullScore;

	private List<Item> items;

	public void addItem(Item item) {
		init();
		this.items.add(item);
	}
	
	public void addAllItems(Collection<Item> items) {
		init();
		this.items.addAll(items);
	}
	
	private void init() {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.title)
				.append(this.paper)
				.append(this.parentSection)
				.toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Section))
			return false;
		Section other = (Section) o;

		return new EqualsBuilder().append(this.title, other.title)
				.append(this.paper,other.paper)
				.append(this.parentSection, other.parentSection)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.paper)
				.append(this.title).build();
	}
	
	@Override
	public boolean sameValueAs(Section other) {
		return this.equals(other);
	}

	//以下功能为ORM或者自动构造使用，非此慎用
	public Section() {
		
	}
	
	private Long sectionId;
	
	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public ExamPaper getPaper() {
		return paper;
	}

	public void setPaper(ExamPaper paper) {
		this.paper = paper;
	}

	public Section getParentSection() {
		return parentSection;
	}

	public void setParentSection(Section parentSection) {
		this.parentSection = parentSection;
	}

	public List<Section> getSubSection() {
		return subSection;
	}

	public void setSubSection(List<Section> subSection) {
		this.subSection = subSection;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
}
