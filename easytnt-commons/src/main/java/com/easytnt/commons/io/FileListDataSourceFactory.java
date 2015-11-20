/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.easytnt.commons.io.dbf.DbfDataSourceReader;
import com.easytnt.commons.io.excel.ExcleSourceReader;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class FileListDataSourceFactory implements ListDataSourceFactory {

	private File dataSource;
	
	private ListDataSourceReader reader;
	
	public FileListDataSourceFactory(File dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public ListDataSourceReader getReader() throws Exception{
		parseBody();
		return this.reader;
	}

	private void parseBody() throws Exception {
		if(this.reader == null) {
			String fileName = this.dataSource.getName();
			if(fileName.endsWith("xls") || fileName.endsWith("xlsx") || fileName.endsWith(".csv")) {
				this.reader = new ExcleSourceReader(this.dataSource);
			}else if(fileName.endsWith("dbf")) {
				this.reader = new DbfDataSourceReader(this.dataSource);
			}
		}
	}

}


