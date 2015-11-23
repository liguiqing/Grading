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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger =  LoggerFactory.getLogger(ExcleSourceReader.class);
	
	private  static String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private InputStream is;
	
	private Workbook workBook = null;
	
	private Sheet sheet = null;
	
	public ExcleSourceReader(File excelFile) throws FileNotFoundException {
		this.is = new FileInputStream(excelFile);
	}

	//打开数据源
	@Override
	public void open() throws Exception {
		if(this.sheet != null)
			return;
		// 判断能否正确解析文件，是否是想要的文件解析内容
		if (!is.markSupported()) {
			is = new PushbackInputStream(is, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(is)) {// 2003
			workBook = new HSSFWorkbook(is);
		} else if (POIXMLDocument.hasOOXMLHeader(is)) {// 2007
			// 各种出错，所以手动进行装箱操作
			XSSFWorkbook temp = new XSSFWorkbook(is);
			workBook = temp;
		}
		sheet = workBook.getSheetAt(0);
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
		logger.info("Get From [{},{}]",row,col);
		if(col == -1)
			return null;
		
		if(sheet.getPhysicalNumberOfRows() <= row){
			throw new IndexOutOfBoundsException();
		}
		
		//第一行是表头，所以应该从第二行开始读取
		Row thisRow = sheet.getRow(row);
		//POI对excel列计数是从零开始的，所以col要-1
		if(thisRow.getPhysicalNumberOfCells() < col - 1){
			throw new IndexOutOfBoundsException();
		}
		Cell cell = thisRow.getCell(col-1);
		if(cell == null)
			return null;
		
		return getCellValue(cell);
	}
	
	private String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		case Cell.CELL_TYPE_FORMULA:
			Workbook wb = cell.getSheet().getWorkbook();
			CreationHelper crateHelper = wb.getCreationHelper();
			FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
			ret = getCellValue(evaluator.evaluateInCell(cell));
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date theDate = cell.getDateCellValue();
				ret = new SimpleDateFormat().format(theDate);
			} else {
				ret = NumberToTextConverter.toText(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			ret = cell.getRichStringCellValue().getString();
			break;
		default:
			ret = null;
		}

		return ret; // 有必要自行trim
	}


	//关闭数据源
	@Override
	public void close() throws Exception {
		Closer.close(is);
	}

	@Override
	public String[] getFields() throws Exception {
		return this.get(0);
	}

}


