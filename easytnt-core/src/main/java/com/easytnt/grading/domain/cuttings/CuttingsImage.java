/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
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
	
	private Set<CuttingsImageGradeRecord> records;
	
	private String pinci = "0000";
	
	private String imgPath;
	
	public CuttingsImage(CuttingsArea definedOf) {
		this.definedOf = definedOf;
	}
	
	public CuttingsImageGradeRecord addRecord(Referees referees) {
		if(this.records == null) {
			this.records = new HashSet<>();
		}
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,this);
		this.records.add(record);
		return record;
	}
	
	public boolean recordedBy(Referees referees) {
		if(this.records == null)
			return false;
		for(CuttingsImageGradeRecord record:this.records) {
			if(record.recordBy(referees)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Section> getSections(){
		return this.definedOf.getSections();
	}
	
	

	public int getCurrentPinci() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void nextPinci() {
		// TODO Auto-generated method stub
		
	}

	public int incrementPinciAndGet() {
		// TODO Auto-generated method stub
		return 0;
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
	
	public ExamineePaper getCutFrom() {
		return cutFrom;
	}

	public void setCutFrom(ExamineePaper cutFrom) {
		this.cutFrom = cutFrom;
	}

	public Set<CuttingsImageGradeRecord> getRecords() {
		return records;
	}

	public void setRecords(Set<CuttingsImageGradeRecord> records) {
		this.records = records;
	}

	public String getPinci() {
		return pinci;
	}

	public void setPinci(String pinci) {
		this.pinci = pinci;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}

