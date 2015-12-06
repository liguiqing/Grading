/**
 * <p><b>© 1997-2013 深圳市海云天教育测评有限公司 TEL: (86)755 - 86024188</b></p>
 * 
 **/

package com.easytnt.grading;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.easytnt.commons.util.SpringContextUtil;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
@ContextConfiguration(locations= {"classpath:applicationContext.xml","classpath*:applicationContext-ds.xml"
		,"classpath*:applicationContext-tx.xml","classpath*:applicationContext-app.xml"})
public class ConfigurationTest extends AbstractJUnit4SpringContextTests {

	@Value("${easytnt.img.server}")
	private String imgServer="http://localhost:8888";
	
	@Test
	public void test()throws Exception{
		JdbcTemplate jt = SpringContextUtil.getBean("jdbcTemplate");
		assertNotNull(jt);
		
		DataSource ds  = SpringContextUtil.getBean("ds");
		
		assertNotNull(ds);
		assertNotEquals("${easytnt.img.server}",imgServer);
	}
}

