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
		ListDataSourceReader reader = mock(ListDataSourceReader.class);
		
		when(mapper.getColIndex("district_name")).thenReturn(1);
		when(mapper.getColIndex("district_number")).thenReturn(2);
		when(reader.get(1, 1)).thenReturn("1区");
		when(reader.get(1, 2)).thenReturn("1");
		
		when(mapper.getColIndex("school_name")).thenReturn(3);
		when(mapper.getColIndex("school_code")).thenReturn(4);
		when(reader.get(1, 3)).thenReturn("学校１");
		when(reader.get(1, 4)).thenReturn("school_code1");
		
		when(mapper.getColIndex("room_number")).thenReturn(5);
		when(reader.get(1, 5)).thenReturn("1");
		
		when(mapper.getColIndex("student_number")).thenReturn(6);
		when(mapper.getColIndex("examinne_uuid")).thenReturn(6);
		when(reader.get(1, 6)).thenReturn("1111111");
		when(mapper.getColIndex("student_name")).thenReturn(7);
		when(mapper.getColIndex("examinne_name")).thenReturn(7);
		when(reader.get(1, 7)).thenReturn("Jim Paul");
		when(mapper.getColIndex("gender")).thenReturn(8);
		when(reader.get(1, 8)).thenReturn("male");
		when(mapper.getColIndex("birthday")).thenReturn(9);
		when(reader.get(1, 9)).thenReturn("2015-01-05");
		when(mapper.getColIndex("arts")).thenReturn(10);
		when(reader.get(1, 10)).thenReturn("0");
		when(mapper.getColIndex("clazz_name")).thenReturn(11);
		when(reader.get(1, 11)).thenReturn("1班");
		when(mapper.getColIndex("clazz_code")).thenReturn(12);
		when(reader.get(1, 12)).thenReturn("051");
		when(mapper.getColIndex("seating_number")).thenReturn(13);
		when(reader.get(1, 13)).thenReturn("12");
		
		
		when(reader.get(2, 1)).thenThrow(IndexOutOfBoundsException.class);
		ExamineeDataImpoirtor importor = new ExamineeDataImpoirtor(jdbcTemplate,mapper,reader);
		//expectedException.expect(IndexOutOfBoundsException.class);
		importor.doImport();
		System.out.print("+++++++++++++++++++++++++++++++++++++++");
	}
}


