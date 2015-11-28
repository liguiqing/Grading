/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.dispatcher.impl;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class DispatcherManagerImpl implements DispathcerManager {
	private static Logger logger = LoggerFactory.getLogger(DispatcherManagerImpl.class);
	
	private  ConcurrentHashMap<CuttingsArea,Dispatcher> dispatcherPool = new ConcurrentHashMap<>();
	
	@Override
	public void registerDispatcher(CuttingsArea area,Dispatcher dispatcher) throws Exception{
		logger.debug("Register Dispatcher For {} ",area.toString());
		
		if(dispatcherPool.containsKey(area)) {
			return;
		}
		
		dispatcherPool.put(area,dispatcher);
		dispatcher.start();
	}
	
	@Override
	public void removeDispatcher(CuttingsArea area) throws Exception{
		Dispatcher dispatcher = dispatcherPool.get(area);
		dispatcher.destroy();
		dispatcherPool.remove(area);
	}
	
	@Override
	public void destroy() {
		Enumeration<CuttingsArea> keys = dispatcherPool.keys();
		while(keys.hasMoreElements()) {
			CuttingsArea key = keys.nextElement();
			Dispatcher dispatcher = dispatcherPool.get(key);
			try {
				dispatcher.destroy();
				
			} catch (Exception e) {
				logger.error(ThrowableParser.toString(e));
			}
		}
		dispatcherPool.clear();
	}
	
	public Dispatcher getDispatcherFor(CuttingsArea area) {
		return dispatcherPool.get(area);
	}
	
	public Dispatcher getDispatcherFor(GradeTask task) {
		return dispatcherPool.get(task.getArea());
	}
	
}


