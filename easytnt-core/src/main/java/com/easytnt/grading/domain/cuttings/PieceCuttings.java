/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.List;

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
public class PieceCuttings implements ImgCuttings{

	private ExamineePaper cutFrom;
	
	private List<ItemCuttings> items;
	
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

	@Override
	public int getCurrentPinci() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void nextPinci() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int incrementPinciAndGet() {
		// TODO Auto-generated method stub
		return 0;
	}
}

