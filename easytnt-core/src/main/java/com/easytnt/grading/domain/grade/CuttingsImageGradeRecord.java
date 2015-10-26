/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.room.Examinee;

/**
 * <pre>
 * 切割块评分记录
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class CuttingsImageGradeRecord implements ValueObject<CuttingsImageGradeRecord> {

	private Referees referees;

	private CuttingsImage recordFor;
	
	private Set<ItemGradeRecord> itemRecords;

	private Date startTime;

	private Date finishTime;

	private int pinci;
	
	private boolean valid = Boolean.TRUE;

	public CuttingsImageGradeRecord(Referees referees, CuttingsImage recordFor) {
		this.referees = referees;
		this.recordFor = recordFor;
		this.startTime = Calendar.getInstance().getTime();
	}

	public void invalid() {
		this.valid = Boolean.FALSE;
	}
	
	/**
	 * 计算得分
	 * @return
	 */
	public Float calScore() {
		Float score = 0f;
		if(this.isFinished()) {
			for(ItemGradeRecord itemRecord:itemRecords) {
				score += itemRecord.getScored();
			}
		}
		return score;
	}
	
	/**
	 * 给切割块中的给分点打分
	 * 
	 * @param scores
	 * @return Collection<ItemGradeRecord>
	 * @throws IllegalArgumentException 当小题分值不在有效范围内时抛出
	 */
	public void scoringForItems(Float[] scores) {
		if(this.isFinished())
			throw new UnsupportedOperationException(this.toString() + "已经完结");
		List<Section> sections = recordFor.getSections();
		this.itemRecords = new HashSet<>();
		int i = 0;
		for (Section section : sections) {
			List<Item> items = section.getItems();
			for (Item item : items) {
				Float score = 0f;
				if( i >scores.length)
					score = scores[i++];
				if (item.isEffectiveScore(score)) {
					ItemGradeRecord igr = new ItemGradeRecord(item,score);
					itemRecords.add(igr);
				} else {
					throw new IllegalArgumentException("无效的分值，"
							+ section.getTitle() + " " + item.getTitle()
							+ " 的有效分范围是[" + item.getMinPoint() + ","
							+ item.getMaxPoint() + "]");
				}
			}

		}
	}

	public void finish() {
		this.finishTime = Calendar.getInstance().getTime();
	}
	
	public boolean isFinished() {
		return this.finishTime != null;
	}

	public boolean recordBy(Referees referees) {
		return this.referees.equals(referees);
	}
	
	public Examinee recordOf() {
		return this.recordFor.getCutFrom().getExaminee();
	}

	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.referees).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CuttingsImageGradeRecord))
			return false;
		CuttingsImageGradeRecord other = (CuttingsImageGradeRecord) o;

		return new EqualsBuilder().append(this.referees, other.referees)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.referees)
				.append(this.recordFor).append(this.recordFor).build();
	}

	@Override
	public boolean sameValueAs(CuttingsImageGradeRecord other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsImageGradeRecord() {

	}

	public Referees getReferees() {
		return referees;
	}

	public void setReferees(Referees referees) {
		this.referees = referees;
	}

	public CuttingsImage getRecordFor() {
		return recordFor;
	}

	public void setRecordFor(CuttingsImage recordFor) {
		this.recordFor = recordFor;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Set<ItemGradeRecord> getItemRecords() {
		return itemRecords;
	}

	public void setItemRecords(Set<ItemGradeRecord> itemRecords) {
		this.itemRecords = itemRecords;
	}

	public int getPinci() {
		return pinci;
	}

	public void setPinci(int pinci) {
		this.pinci = pinci;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
