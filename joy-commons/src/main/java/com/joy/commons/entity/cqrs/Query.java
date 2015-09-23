/**
 * <p><b></b></p>
 * 
 **/

package com.joy.commons.entity.cqrs;

import java.util.List;
import java.util.Map;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public interface Query<T> {

	public void parametersWith(Map<String, String[]> parameters);
	
	public <E>E parameterOf(String name,Class<E> clazz);
	
	public void result(List<T> list) ;
	
	public void totalRows(int totalRows);
		
	public boolean hasNext();

	public boolean hasPrev();
	
	public void first();

	public void prev();

	public void next();

	public void last();

	public int getCurPage();
	
	public int getPageSize();
	
	public int getTotalRows();
	
	public int getTotalPage();

	public int getStartRow();

	public Map<String, String[]> getParameters();
	
	public List<T> getResults();

}

