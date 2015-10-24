/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mock.dispatcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.dispatcher.impl.DispatcherImpl;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.room.ExamineePaper;
import com.easytnt.grading.fetch.Fetcher;

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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private ExamineePaper cutFrom;
	
	public DispatcherConcreator() {
		
	}
	
	public void initMethod()throws Exception {
		creatorMockDispatcher();
	}

	private void creatorMockDispatcher() throws Exception {
		logger.debug("Mock Dispatcher Concreat");
		cutFrom = new ExamineePaper();
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		area.bindSection(section);
		section.addItem(new Item("13",2f));
		section.addItem(new Item("14",2f));
		section.addItem(new Item("15",2f));
		section.addItem(new Item("16",2f));
		
		DispatcherStrategy dispatcherStrategy = SpringContextUtil.getBean("mockDispatcherStrategy");
		Fetcher fetcher = getFetherFor(area);//SpringContextUtil.getBean("mockFetcher");
		Dispatcher dispatcher = new DispatcherImpl(dispatcherStrategy,fetcher,1);
		
		dispathcerManager.registerDispatcher(area, dispatcher);
		logger.debug("Mock Dispatcher Registered");
	}

	public void setDispathcerManager(DispathcerManager dispathcerManager) {
		this.dispathcerManager = dispathcerManager;
	}
	
	private Fetcher getFetherFor(final CuttingsArea area) {
		return new Fetcher() {

			@Override
			public List<CuttingsImage> fetch(int amount) {
				Object[] args = new Object[] {1,amount};
				List<CuttingsImage> pcs = jdbcTemplate.query("select * from getpaper limit ?, ? ", args, new RowMapper<CuttingsImage>() {

					@Override
					public CuttingsImage mapRow(ResultSet rs, int arg1)
							throws SQLException {
						String imgPath = rs.getString("imagepath");
						CuttingsImage cuttings = new CuttingsImage(cutFrom,area);
						cuttings.setImgPath(imgPath);
						
						return cuttings;
					}
					
				});
				return pcs;
			}
		};
	}

}


