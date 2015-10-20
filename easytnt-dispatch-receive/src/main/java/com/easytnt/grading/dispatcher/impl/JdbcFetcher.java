/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.fetch.Fetcher;


/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class JdbcFetcher implements Fetcher {

	
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<PieceCuttings> fetch(int amount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

