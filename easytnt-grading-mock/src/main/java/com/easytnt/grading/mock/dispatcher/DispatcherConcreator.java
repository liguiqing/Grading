/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mock.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.dispatcher.impl.DispatcherImpl;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.fetch.Fetcher;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class DispatcherConcreator implements ApplicationListener<ContextRefreshedEvent>{
	private static Logger logger = LoggerFactory.getLogger(DispatcherConcreator.class);
	
	private DispathcerManager dispathcerManager;
	
	public DispatcherConcreator() {
		
	}

	public void creatorMockDispatcher() throws Exception {
		logger.debug("Mock Dispatcher Concreat");
		DispatcherStrategy dispatcherStrategy = SpringContextUtil.getBean("mockDispatcherStrategy");
		Fetcher fetcher = SpringContextUtil.getBean("mockFetcher");
		Dispatcher dispatcher = new DispatcherImpl(dispatcherStrategy,fetcher,1);
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		dispathcerManager.registerDispatcher(area, dispatcher);
		logger.debug("Mock Dispatcher Registered");
	}

	public void setDispathcerManager(DispathcerManager dispathcerManager) {
		this.dispathcerManager = dispathcerManager;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			try {
				creatorMockDispatcher();
			} catch (Exception e) {
				logger.debug(ThrowableParser.toString(e));
			}
	      }
	}
}


