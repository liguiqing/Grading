package com.easytnt.commons.io;

/** 
 * <pre>
 * 行列数据源读取接口
 * </pre>
 * 
 * @author 李贵庆 2015年11月17日
 * @version 1.0
 **/
public interface ListDataSourceReader {

	/**
	 * 打开数据源
	 * @throws Exception
	 */
	public void open()throws Exception;
	
	/**
	 * 读取数据源某行数据
	 * @param row
	 * @return
	 * @throws Exception 如果行号 throw IndexOutOfBoundException
	 */
	public String[] get(int row) throws Exception;
	
	/**
	 * 读取数据源某行某列数据
	 * @param row
	 * @param col
	 * @return
	 * @throws Exception 如果行号或者列号超出范围，throw IndexOutOfBoundException
	 */
	public String get(int row,int col) throws Exception;
	
	/**
	 * 读取数据源字段名称
	 * @return
	 * @throws Exception 
	 */
	public String[] getFields() throws Exception;
	
	/**
	 * 关闭数据源
	 * @throws Exception
	 */
	public void close() throws Exception;
	
}
