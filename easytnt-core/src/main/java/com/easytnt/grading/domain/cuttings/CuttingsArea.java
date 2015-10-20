/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.cuttings;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.grade.ItemGradeRecord;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;


/** 
 * <pre>
 * 切割块定义，一个切割块至少有一个大题
 * </pre>
 * 
 * @author 李贵庆 2015年10月18日
 * @version 1.0
 **/
public class CuttingsArea implements ValueObject<CuttingsArea>{

	private Long id;
	
	private ExamPaper paper;
	
	private Area areaInPaper;
	
	private List<Section> sections;
	
	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
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
		return new ToStringBuilder(this).append(this.id).build();
	}
	
	@Override
	public boolean sameValueAs(CuttingsArea other) {
		return this.equals(other);
	}
	
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsArea() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}


