/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
import com.easytnt.commons.math.CombineIterator;
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
	
	private Float score = 0f;
	
	//private List<Referees> refereedBy = new ArrayList<>();
	
	private List<CuttingsImageGradeRecord> refereedBy = new ArrayList<>();
	
	public CuttingsImage(CuttingsArea definedOf) {
		this.definedOf = definedOf;
	}
	
	public CuttingsImageGradeRecord createRecord(Referees referees) {
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,this);
		this.refereedBy.add(record);
		return record;
	}
	
	public boolean hasRefereedBy(Referees referees) {
		if(this.refereedBy == null)
			return false;
		for(CuttingsImageGradeRecord record:this.refereedBy){
			if(record.recordBy(referees))
				return true;
		}
		return false;
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
	
	public boolean isFinish() {
		return this.pinci == this.definedOf.getRequiredPinci();
	}
	
	//返回-1表示计算不成功
	public Float calScore() {
		//这里最好抽出一个接口来实现
		if(this.isFinish()) {
			//只有一评时直接返回
			if(this.definedOf.getRequiredPinci() == 1)
				return this.refereedBy.get(0).calScore();
			
			Float[] s = new Float[this.refereedBy.size()];
			int i = 0;
			for(CuttingsImageGradeRecord record:this.refereedBy) {
				s[i++] = record.calScore();
			}
			CombineIterator cit = new CombineIterator(s,2);
			ArrayList<Float[]> ss = new ArrayList<>();
			while(cit.hasNext()) {
				Float[] f = cit.next();
				if(Math.abs(f[0] - f[1]) <= this.definedOf.getMaxerror()) {
					ss.add(f);
				}
			}
			
			//取高分的一组来计算最后得分
			if(ss.size() > 0) {
				Float t = 0f;
				int a = 0;
				for(i=0;i<ss.size();i++) {
					Float[] ts = ss.get(i);
					if(t<ts[0]+ts[1]) {
						t = ts[0]+ts[1];
						a = i;
					}
				}
				Float[] fs = ss.get(a);
				DecimalFormat df = new DecimalFormat("0.00");
				return new Float(df.format((fs[0]+fs[1])/2));
			}
		}
		return -1f;
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

