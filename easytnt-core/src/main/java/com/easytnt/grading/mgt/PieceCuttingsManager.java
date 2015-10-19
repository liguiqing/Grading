/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mgt;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.CuttingsArea;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
public interface PieceCuttingsManager {

	public Dispatcher getDispatcherFor(CuttingsArea area);

	void registerDispatcher(CuttingsArea area, Dispatcher dispathcer) throws Exception;

	void removeDispatcher(CuttingsArea area)throws Exception;
}


