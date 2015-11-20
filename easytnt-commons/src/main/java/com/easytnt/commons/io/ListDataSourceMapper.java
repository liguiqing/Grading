package com.easytnt.commons.io;


/** 
 * <pre>
 * 行列数据源映射配置
 * </pre>
 * 
 * @author 李贵庆 2015年11月17日
 * @version 1.0
 **/
public interface ListDataSourceMapper {

	/**
	 * 读取数据源到目标数据列映射
	 * @param targetName
	 * @param sourceIndex
	 * @return 大于０的整数，找不到，返回-1;
	 * 
	 */
	public int getColIndex(String targetName); 
	 
}
