/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;

/**
 * <pre>
 * 切割块评分记录
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class PieceGradeRecord implements ValueObject<PieceGradeRecord> {

	private Referees referees;

	private PieceCuttings recordFor;

	private Date startTime;

	private Date finishTime;

	private String pinci;

	public PieceGradeRecord(Referees referees, PieceCuttings recordFor) {
		this.referees = referees;
		this.recordFor = recordFor;
		this.startTime = Calendar.getInstance().getTime();
	}

	/**
	 * 给切割块中的小题目打分
	 * 
	 * @param scores
	 * @return Collection<ItemGradeRecord>
	 * @throws IllegalArgumentException 当小题分值不在有效范围内时抛出
	 */
	public Collection<ItemGradeRecord> scoringForItems(Float[] scores) {
		List<Section> sections = recordFor.getSections();
		ArrayList<ItemGradeRecord> itemRecords = new ArrayList<>();
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
		return itemRecords;
	}

	public void finish() {
		this.finishTime = Calendar.getInstance().getTime();
	}

	public boolean recordOf(Referees referees) {
		return this.referees.equals(referees);
	}

	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.referees).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PieceGradeRecord))
			return false;
		PieceGradeRecord other = (PieceGradeRecord) o;

		return new EqualsBuilder().append(this.referees, other.referees)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.referees)
				.append(this.recordFor).append(this.recordFor).build();
	}

	@Override
	public boolean sameValueAs(PieceGradeRecord other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public PieceGradeRecord() {

	}

	public Referees getReferees() {
		return referees;
	}

	public void setReferees(Referees referees) {
		this.referees = referees;
	}

	public PieceCuttings getRecordFor() {
		return recordFor;
	}

	public void setRecordFor(PieceCuttings recordFor) {
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

}
