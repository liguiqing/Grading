/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.easytnt.grading.domain.grade.PieceGradeRecord;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.room.ExamineePaper;
import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 考生试卷的切割块
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class PieceCuttings{

	private ExamineePaper cutFrom;
	
	private Set<PieceGradeRecord> records;
	
	private String pinci = "0000";
	
	private String imgPath;
	
	public void addRecord(Referees referees) {
		if(this.records == null) {
			this.records = new HashSet<>();
		}
		
		this.records.add(new PieceGradeRecord(referees,this));
	}
	
	public boolean recordedBy(Referees referees) {
		if(this.records == null)
			return false;
		for(PieceGradeRecord record:this.records) {
			if(record.recordOf(referees)) {
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		//TODO 
		return 0;
	}
	
	public boolean equals() {
		//TODO 
		return false;
	}
	
	public String toString() {
		//TODO  
		return this.getClass().getName();
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

	public ExamineePaper getCutFrom() {
		return cutFrom;
	}

	public void setCutFrom(ExamineePaper cutFrom) {
		this.cutFrom = cutFrom;
	}

	public Set<PieceGradeRecord> getRecords() {
		return records;
	}

	public void setRecords(Set<PieceGradeRecord> records) {
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

