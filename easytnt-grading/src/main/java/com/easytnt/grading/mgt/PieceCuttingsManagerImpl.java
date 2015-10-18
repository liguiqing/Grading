/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mgt;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.impl.DispatcherImpl;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.paper.AnswerArea;
import com.easytnt.grading.fetch.Fetcher;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
@Service
public class PieceCuttingsManagerImpl implements PieceCuttingsManager {
	
	HashMap<CuttingsArea,Dispatcher> dispatchers = new HashMap<>();
	
	
	public PieceCuttingsManagerImpl() {
		
	}
	
	@Override
	public void registerDispatcher(CuttingsArea area,Dispatcher dispathcer) {
		//TODO
		//DispatcherStrategy dispatcherStrategy = SpringContextUtil.getBean("singlePaperPriorDispatcherStrategy2pin");
		//Fetcher fetcher = SpringContextUtil.getBean("jdbcFetcher");
		//DispatcherImpl dispather = new DispatcherImpl(dispatcherStrategy,fetcher);
		dispatchers.put(area,dispathcer);
		dispathcer.start();
	}
	
	@Override
	public void removeDispatcher(CuttingsArea area) {
		Dispatcher dispatcher = dispatchers.get(area);
		dispatcher.destroy();
		dispatchers.remove(area);
	}
	
	public Dispatcher getDispatcherFor(CuttingsArea area) {
		return dispatchers.get(area);
	}
	
}


