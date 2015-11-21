/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
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

	private int start = 0;
	
	private JdbcTemplate jdbcTemplate;
	
	private CuttingsArea fetchFor;
	
	public JdbcFetcher(CuttingsArea fetchFor) {
		this.fetchFor = fetchFor;
	}

	@Override
	public List<CuttingsImage> fetch(int amount) {
		Object[] args = new Object[] {fetchFor.getId(),start,amount};
		List<CuttingsImage> pcs = jdbcTemplate.query("select imagepath from paperimport where getmark is null and itemid=? limit ?, ?  ", args, new RowMapper<CuttingsImage>() {

			@Override
			public CuttingsImage mapRow(ResultSet rs, int arg1)
					throws SQLException {
				String imgPath = rs.getString("imagepath");
				CuttingsImage cuttings = new CuttingsImage(fetchFor);
				cuttings.setImgPath(imgPath);
				cuttings.setUuid(imgPath);
				return cuttings;
			}
			
		});
		this.start = amount;
		return pcs;
	}
	
	@Override
	public void destroy() {
		this.fetchFor = null;
		this.jdbcTemplate = null;
	}
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

