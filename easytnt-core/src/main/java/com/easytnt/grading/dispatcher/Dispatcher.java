/**
 * <p><b></b></p>
 * 
 **/

package com.easytnt.grading.dispatcher;

import java.util.Collection;

import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.fetch.Fetcher;

/** 
 * <pre>
 * 试卷（其实是切割块图片）分发器
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface Dispatcher {

	/**
	 * 为
	 * @param referees
	 * @return
	 * @throws Exception
	 */
	PieceCuttings getFor(Referees referees)throws Exception;
	
	void put(Collection<PieceCuttings> cuttingses) throws Exception;
	
	void recover(Collection<PieceCuttings> cuttingses)throws Exception;
	
	void start()throws Exception;
	
	void stop()throws Exception;
	
	void destroy()throws Exception;
	
	void useNewFetcher(Fetcher fetcher,boolean mustCleanData) throws Exception;

}

