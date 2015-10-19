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

	PieceCuttings get(Referees referees)throws Exception;
	
	void put(Collection<PieceCuttings> cuttingses) throws Exception;
	
	void recover(Collection<PieceCuttings> cuttingses)throws Exception;
	
	void start()throws Exception;
	
	void destroy()throws Exception;
	
	boolean isWorking();

}

