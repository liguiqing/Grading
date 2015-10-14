/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.fetch.Fetcher;
import com.easytnt.grading.share.ImgCuttings;


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
	
	public JdbcFetcher() {
		jdbcTemplate = SpringContextUtil.getBean(JdbcTemplate.class);
	}

	@Override
	public List<ImgCuttings> fetch(int amount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

