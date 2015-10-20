/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.dispatcher.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.domain.cuttings.CuttingsArea;

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
	public void registerDispatcher(CuttingsArea area,Dispatcher dispathcer) throws Exception{
		logger.debug("Register Dispatcher For {} ",area.toString());
		//TODO
		//DispatcherStrategy dispatcherStrategy = SpringContextUtil.getBean("singlePaperPriorDispatcherStrategy2pin");
		//Fetcher fetcher = SpringContextUtil.getBean("jdbcFetcher");
		//DispatcherImpl dispather = new DispatcherImpl(dispatcherStrategy,fetcher);
		dispatcherPool.put(area,dispathcer);
		dispathcer.start();
	}
	
	@Override
	public void removeDispatcher(CuttingsArea area) throws Exception{
		Dispatcher dispatcher = dispatcherPool.get(area);
		dispatcher.destroy();
		dispatcherPool.remove(area);
	}
	
	public Dispatcher getDispatcherFor(CuttingsArea area) {
		return dispatcherPool.get(area);
	}
	
}


