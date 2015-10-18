/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mgt;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.Referees;

/** 
 * <pre>
 * 评卷控制器
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
public interface GradingManager {

	PieceCuttings getPieceCuttingsFor(Referees referees);
	
	PieceCuttings getPieceCuttingsFor(CuttingsArea area);
	
}


