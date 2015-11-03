/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.room.ExamineePaper;

/** 
 * <pre>
 * 考生试卷扫描切割成的图片
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class CuttingsImage implements Entity<CuttingsImage>{

	private String uuid;
	
	private CuttingsArea definedOf;
	
	private ExamineePaper cutFrom;
	
	private int pinci = 0;
	
	private String imgPath;
	
	public CuttingsImage(CuttingsArea definedOf) {
		this.definedOf = definedOf;
	}
	
	public CuttingsImageGradeRecord createRecord(Referees referees) {
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,this);
		return record;
	}
	
	public CuttingsArea definedOf() {
		return this.definedOf;
	}
	
	public Subject subjectOf() {
		return this.definedOf.subjectOf();
	}
	
	public List<Section> getSections(){
		return this.definedOf.getSections();
	}
	
	public int getCurrentPinci() {
		return this.pinci;
	}

	public void nextPinci() {
		this.pinci++;
	}

	public int incrementPinciAndGet() {
		this.pinci++;
		return this.pinci;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.uuid).toHashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof CuttingsImage))
			return false;
		CuttingsImage other = (CuttingsImage)o;
		return new EqualsBuilder().append(this.uuid,other.uuid).isEquals();
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this).append(this.imgPath).build();
	}
    
	@Override
	public boolean sameIdentityAs(CuttingsImage other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsImage () {}
	
	private Long imageId;
	
	public ExamineePaper getCutFrom() {
		return cutFrom;
	}

	public void setCutFrom(ExamineePaper cutFrom) {
		this.cutFrom = cutFrom;
	}

	public int getPinci() {
		return pinci;
	}

	public void setPinci(int pinci) {
		this.pinci = pinci;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
}

