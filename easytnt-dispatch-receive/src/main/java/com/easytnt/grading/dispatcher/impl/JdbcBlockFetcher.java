/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.grading.dispatcher.Block;
import com.easytnt.grading.dispatcher.BlockFetcher;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class JdbcBlockFetcher implements BlockFetcher {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcBlockFetcher() {
		jdbcTemplate = SpringContextUtil.getBean(JdbcTemplate.class);
	}
	
	@Override
	public List<Block> fetch(int amount) {
		// TODO Auto-generated method stub
		// impl jdbcTemplate
		return null;
	}

}

