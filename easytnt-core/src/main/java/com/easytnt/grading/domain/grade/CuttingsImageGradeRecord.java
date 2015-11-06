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

import com.easytnt.commons.entity.share.Entity;
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
public class CuttingsImageGradeRecord implements Entity<CuttingsImageGradeRecord> {

	private Referees referees;

	private CuttingsImage recordFor;
	
	private Set<ItemGradeRecord> itemRecords;

	private Date startTime;

	private Date finishTime;

	private int pinci;
	
	private boolean valid = Boolean.TRUE;
	
	private String scorestr;

	public CuttingsImageGradeRecord(Referees referees, CuttingsImage recordFor) {
		this.referees = referees;
		this.recordFor = recordFor;
		this.startTime = Calendar.getInstance().getTime();
		this.pinci = recordFor.getCurrentPinci();
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
			for(ItemGradeRecord itemRecord:this.itemRecords) {
				score += itemRecord.getScored();
			}
		}
		return score;
	}
	
	public int spendTime() {
		if(this.finishTime == null)
			return -1;
		return new Long(this.getFinishTime().getTime() - this.getStartTime().getTime()).intValue();
	}

	
	/**
	 * 给切割块中的给分点打分
	 * 
	 * @param scores
	 * @return Collection<ItemGradeRecord>
	 * @throws IllegalArgumentException 当小题分值不在有效范围内时抛出
	 */
	public void scoringForItems(Float[] scores) {
		List<Section> sections = recordFor.getSections();
		this.itemRecords = new HashSet<>();
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for (Section section : sections) {
			List<Item> items = section.getItems();
			for (Item item : items) {
				Float score = 0f;
				if( i < scores.length)
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
				if(score <= score.intValue()) {
					sb.append(score.intValue());
				}else {
					sb.append(score);
				}
				sb.append(",");
			}

		}
		sb.deleteCharAt(sb.length()-1);
		this.scorestr = sb.toString();
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
	

	public boolean recordedFor(CuttingsImage cuttingsimage) {
		return this.recordFor == null?false:this.recordFor.equals(cuttingsimage);
	}

	
	public Examinee recordOf() {
		return this.recordFor.getCutFrom().getExaminee();
	}

	public Long genId() {
		this.id = new Double(Math.random()).longValue();
		return this.id;
	}
	
	/**
	 * 记录为空白
	 */
	public void blanks() {
		this.invalid();//TODO
	}

	/**
	 * 记录为异常
	 */
	public void errors() {
		this.invalid();//TODO
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.referees).append(this.recordFor).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CuttingsImageGradeRecord))
			return false;
		CuttingsImageGradeRecord other = (CuttingsImageGradeRecord) o;

		return new EqualsBuilder().append(this.referees, other.referees)
				.append(this.recordFor,other.recordFor).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.referees)
				.append(this.recordFor).build();
	}

	@Override
	public boolean sameIdentityAs(CuttingsImageGradeRecord other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsImageGradeRecord() {

	}

	private Long id;
	
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

	public String getScorestr() {
		return scorestr;
	}

	public void setScorestr(String scorestr) {
		this.scorestr = scorestr;
	}

}
