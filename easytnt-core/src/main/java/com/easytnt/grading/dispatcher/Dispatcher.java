/**
 * <p><b></b></p>
 * 
 **/

package com.easytnt.grading.dispatcher;

import java.util.Collection;

import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.Referees;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface Dispatcher {

	PieceCuttings get(Referees referees);
	
	void put(Collection<PieceCuttings> cuttingses);
	
	void recover(Collection<PieceCuttings> cuttingses);
	
	void start();
	
	void destroy();

}

