/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.commons.util.Closer;

/** 
 * <pre>
 * Excel数据源读取器
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class ExcleSourceReader implements ListDataSourceReader {

	private InputStream is;
	
	private Workbook workBook = null;
	
	private Sheet sheet = null;
	
	public ExcleSourceReader(File excelFile) throws FileNotFoundException {
		this.is = new FileInputStream(excelFile);
	}

	//打开数据源
	@Override
	public void open() throws Exception {
		//判断能否正确解析文件，是否是想要的文件解析内容
		if(!is.markSupported()){
			is = new PushbackInputStream(is,8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(is)) {//2003  
			workBook = new HSSFWorkbook(is);  
       }else if (POIXMLDocument.hasOOXMLHeader(is)) {//2007  
    	   //各种出错，所以手动进行装箱操作
    	   XSSFWorkbook temp = new XSSFWorkbook(is);  
    	   workBook = temp;
    	   sheet = workBook.getSheetAt(0);
       }
	}

	//获取某行数据
	@Override
	public String[] get(int row) throws Exception {
		if(sheet.getPhysicalNumberOfRows()<=row){
			throw new IndexOutOfBoundsException();
		}
		List<String> result = new ArrayList<String>();
		Row thisRow = sheet.getRow(row);
		Iterator<Cell> cells =  thisRow.iterator();
		while(cells.hasNext()){
			Cell cell = cells.next();
			result.add(cell.getStringCellValue());
		}
		String[] str=new String[]{};
		str = result.toArray(str);
		return str;
	}

	//获取某行某列数据
	@Override
	public String get(int row, int col) throws Exception {
		if(sheet.getPhysicalNumberOfRows() <= row +1 ){
			throw new IndexOutOfBoundsException();
		}
		
		//第一行是表头，所以应该从第二行开始读取
		Row thisRow = sheet.getRow(row + 1);
		if(thisRow.getPhysicalNumberOfCells() <= col){
			throw new IndexOutOfBoundsException();
		}
		Cell cell = thisRow.getCell(col);
		return cell.getStringCellValue();
	}

	//关闭数据源
	@Override
	public void close() throws Exception {
		Closer.close(is);
	}

	@Override
	public String[] getFields() throws Exception {
		return this.get(1);
	}

}


