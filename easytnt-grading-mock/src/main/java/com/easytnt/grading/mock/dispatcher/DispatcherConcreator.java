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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.dispatcher.impl.DispatcherImpl;
import com.easytnt.grading.dispatcher.impl.JdbcFetcher;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;
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
	
	private DispatcherStrategy dispatcherStrategy;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private List<CuttingsArea> cuttingsDefineds;
	
	private ExamPaper cutFrom;
	
	public DispatcherConcreator() {
		cutFrom = new ExamPaper();
		cutFrom.setFullScore(150f);
		cutFrom.setPaperId(10000010l);
	}
	
	public void initMethod()throws Exception {
		creatorMockDispatcher();
	}

	private void creatorMockDispatcher() throws Exception {
		logger.debug("Mock Dispatcher Concreat");
	
		this.cuttingsDefineds = getCuttingsArea();
		if(this.cuttingsDefineds != null) {
			for(CuttingsArea area:this.cuttingsDefineds) {
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
	
	public List<CuttingsArea> getCuttingsDefineds(){
		return this.cuttingsDefineds;
	}

	private Fetcher getFetherFor(final CuttingsArea area) {
		JdbcFetcher fetcher = new JdbcFetcher(area);
		fetcher.setJdbcTemplate(jdbcTemplate);
		return fetcher;
	}
	
	private List<CuttingsArea> getCuttingsArea(){
		Object[] args = new Object[] {this.cutFrom.getPaperId()};
		final Subject subject  = new Subject();
		subject.setId(100l);
		
		return jdbcTemplate.query("select * from paperiteminfo where paperid=?", args, new RowMapper<CuttingsArea>() {

			@Override
			public CuttingsArea mapRow(ResultSet rs, int arg1)
					throws SQLException {

				Area areaInPaper = new Area(rs.getInt("left"),rs.getInt("top"),rs.getInt("right"),rs.getInt("height"));
				CuttingsArea area = new CuttingsArea(cutFrom,areaInPaper);
				
				area.setId(rs.getLong("itemid"));
				area.setMaxerror(rs.getFloat("maxerror"));
				Section section = new Section();
				section.setSubject(subject);
				section.setSectionId(rs.getLong("itemid"));
				section.setPaper(cutFrom);
				section.setTitle(rs.getString("itemname"));
				section.setCaption(rs.getString("itemcaption"));
				section.setFullScore(rs.getFloat("fullscore"));				
				//area.bindSection(section);
				
				setSectionItems(section);
				
				return area;
			}
			
		});
	}
	
	private void setSectionItems(final Section section) {
		Object[] args = new Object[] {section.getSectionId()};
		List<Item> items = jdbcTemplate.query("select * from papersubiteminfo where itemid=?", args, new RowMapper<Item>() {

			@Override
			public Item mapRow(ResultSet rs, int arg1)
					throws SQLException {
				Item item = new Item();
				Area area = new Area(rs.getInt("left"),rs.getInt("top"),rs.getInt("right"),rs.getInt("height"));
				item.setAnswerArea(area);
				item.setItemId(rs.getLong("itemid"));
				item.setTitle(rs.getString("subitemname"));
				item.setCaption(rs.getString("subitemcaption"));
				item.setFullScore(rs.getFloat("fullscore"));
				String validscoredot  = rs.getString("validscoredot");
				item.genValidValues(validscoredot);				
				return item;
			}
			
		});
		
		section.addAllItems(items);
	}

}


