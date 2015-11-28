/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.dispatcher.impl.DispatcherImpl;
import com.easytnt.grading.dispatcher.impl.JdbcFetcher;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.fetch.Fetcher;
import com.easytnt.grading.repository.ExamPaperRepository;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/

public class DispatcherConcreator {
	private static Logger logger = LoggerFactory.getLogger(DispatcherConcreator.class);
	
	private DispathcerManager dispathcerManager;
	
	private DispatcherStrategy dispatcherStrategy;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExamPaperRepository examPaperRepository;
	
	
	public DispatcherConcreator() {
	}
	
	
	public void start(List<CuttingsArea> cuttingsDefineds) throws Exception{
		if(cuttingsDefineds != null) {
			for(CuttingsArea area:cuttingsDefineds) {
				Fetcher fetcher = getFetherFor(area);
				Dispatcher dispatcher = new DispatcherImpl(dispatcherStrategy,fetcher,1);
				dispathcerManager.registerDispatcher(area, dispatcher);
				logger.debug("Dispatcher for {} Registered",area);
			}
		}
	}

	public void setDispathcerManager(DispathcerManager dispathcerManager) {
		this.dispathcerManager = dispathcerManager;
	}

	public void setDispatcherStrategy(DispatcherStrategy dispatcherStrategy) {
		this.dispatcherStrategy = dispatcherStrategy;
	}
	

	private Fetcher getFetherFor(final CuttingsArea area) {
		JdbcFetcher fetcher = new JdbcFetcher(area);
		fetcher.setJdbcTemplate(jdbcTemplate);
		return fetcher;
	}
	
}


