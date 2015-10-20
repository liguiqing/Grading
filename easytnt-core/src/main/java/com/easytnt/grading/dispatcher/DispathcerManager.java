/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher;

import com.easytnt.grading.domain.cuttings.CuttingsArea;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface DispathcerManager {
	
	public Dispatcher getDispatcherFor(CuttingsArea area);

	void registerDispatcher(CuttingsArea area, Dispatcher dispathcer) throws Exception;

	void removeDispatcher(CuttingsArea area)throws Exception;
	
}

