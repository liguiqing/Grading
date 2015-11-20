/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.commons.util.SpringContextUtil;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月21日
 * @version 1.0
 **/
public class ExamineeDataImpoirtorTest {
	
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void before()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath*:applicationContext-ds.xml"});
		SpringContextUtil sp = new SpringContextUtil();
		sp.setApplicationContext(context);
		jdbcTemplate = SpringContextUtil.getBean("jdbcTemplate");
		jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
	}
	
	@Test
	public void testDoImports() throws Exception{
		ListDataSourceMapper mapper = mock(ListDataSourceMapper.class);
		when(mapper.getColIndex("district_name")).thenReturn(1);
		when(mapper.getColIndex("district_number")).thenReturn(2);
		ListDataSourceReader reader = mock(ListDataSourceReader.class);
		when(reader.get(1, 1)).thenReturn("1区");
		when(reader.get(1, 2)).thenReturn("1");
		when(reader.get(2, 1)).thenThrow(IndexOutOfBoundsException.class);
		ExamineeDataImpoirtor importor = new ExamineeDataImpoirtor(jdbcTemplate,null,mapper,reader);
		expectedException.expect(IndexOutOfBoundsException.class);
		importor.doImport();
		System.out.print("+++++++++++++++++++++++++++++++++++++++");
	}
}


