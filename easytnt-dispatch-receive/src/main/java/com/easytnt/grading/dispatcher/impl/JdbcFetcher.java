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
		Object[] args = new Object[] {1,amount};
		List<PieceCuttings> pcs = jdbcTemplate.query("select * from getpaper limit ?, ? ", args, new RowMapper<PieceCuttings>() {

			@Override
			public PieceCuttings mapRow(ResultSet rs, int arg1)
					throws SQLException {
				String imgPath = rs.getString("imagepath");
				PieceCuttings cuttings = new PieceCuttings();
				cuttings.setImgPath(imgPath);
				
				return cuttings;
			}
			
		});
		return pcs;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

