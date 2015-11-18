/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io.dbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.commons.util.Closer;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

/** 
 * <pre>
 * DBF文件数据源读取器
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class DbfDataSourceReader implements ListDataSourceReader {

	private InputStream is;
	
	private DBFReader reader;
	
	private ArrayList<String[]> dataList;
	
	private String[] fields = new String[0];
	
	public DbfDataSourceReader(File dbfFile) throws FileNotFoundException  {
		this.is = new FileInputStream(dbfFile);
	}

	//打开数据源
	@Override
	public void open() throws Exception {
		reader = new DBFReader(is); 
		dataList = new ArrayList<String[]>();
	}

	//获取某行数据
	@Override
	public String[] get(int row) throws Exception {
		if(reader.getRecordCount() <= row){
			throw new IndexOutOfBoundsException();
		}
		
		if(row/100!=0){
			dataList.clear();
		}
		
		int size = row%100-1;
		
		while(dataList.size() <= size){
			Object[] records = reader.nextRecord();
			String[] str = new String[records.length];
			int i = 0;
			for(Object o:records) {
				str[i++] = o + "";
			}
			dataList.add(str);
		}
		return dataList.get(size);
	}
	
	//获取某行某列数据
	@Override
	public String get(int row, int col) throws Exception {
		String[] str = get(row);
		if(str.length <= col){
			throw new IndexOutOfBoundsException();
		}
		return str[col];
	}
	
	@Override
	public String[] getFields() throws Exception {
		if(this.fields.length > 0)
			return this.fields;
		
		int fieldsCount = reader.getFieldCount();
		this.fields = new String[fieldsCount];
		// 取出字段信息
		for (int i = 0; i < fieldsCount; i++) {
			DBFField field = reader.getField(i);
			this.fields[i] = field.getName();
		}
		return this.fields;
	}

	@Override
	public void close() throws Exception {
		Closer.close(is);
	}

}


