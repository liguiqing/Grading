/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.easytnt.commons.util.SpringContextUtil;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月20日
 * @version 1.0
 **/
public class ExamineeFinalScoreCalculatorTest {

	private JdbcTemplate jdbcTemplate;
	
	private SessionFactory sessionFactroy;
	
	@Before
	public void before()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath*:applicationContext-ds.xml"});
		SpringContextUtil sp = new SpringContextUtil();
		sp.setApplicationContext(context);
		jdbcTemplate = SpringContextUtil.getBean("jdbcTemplate");
		jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
		sessionFactroy = SpringContextUtil.getBean(SessionFactory.class);
	}
	
	@Test
	public void testOutputFinalScoreOf() throws Exception{
		assertNotNull(jdbcTemplate);
		assertNotNull(sessionFactroy);
		final ExamineeFinalScoreCalculator calculator = ExamineeFinalScoreCalculator.newCalculator(1l);
		calculator.setJdbcTemplate(jdbcTemplate);
		calculator.setSessionFactory(sessionFactroy);
		jdbcTemplate.query("SELECT examinne_uuid FROM examinne ", new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while(rs.next())
					calculator.calScore(rs.getString(1));
				return null;
			}
		});
		calculator.ranking();
		calculator.output();
	}
	
}
