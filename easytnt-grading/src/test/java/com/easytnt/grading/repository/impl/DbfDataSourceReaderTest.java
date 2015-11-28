/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.io.dbf.DbfDataSourceReader;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月28日
 * @version 1.0
 **/
public class DbfDataSourceReaderTest {
	private static Logger logger = LoggerFactory.getLogger(DbfDataSourceReaderTest.class);
	@Test
	public void testReadDbf()throws Exception{
		File root = new File(this.getClass().getResource("").getPath());
		File dbf = new File(root.toPath() + "\\bmk.dbf");
		assertNotNull(dbf);
		DbfDataSourceReader reader = new DbfDataSourceReader(dbf);
		reader.open();
		String[] fields = reader.getFields();
		for(int i=0;i<fields.length;i++) {
			System.out.print(fields[i]+"  ");
		}
		System.out.println();
		for(int j=1;j<10;j++) {
			
			for(int i=0;i<fields.length;i++) {
				System.out.print(reader.get(j,i+1));
			}
			System.out.println();
		}
		reader.close();
	}
}


