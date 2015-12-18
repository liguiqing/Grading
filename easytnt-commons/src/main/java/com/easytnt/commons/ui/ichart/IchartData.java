/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.ui.ichart;

import java.util.ArrayList;
import java.util.List;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月12日
 * @version 1.0
 **/
public class IchartData {
	
	private List<List<Object>> rows;
	
	public IchartData() {
		this.rows = new ArrayList<>();
	}
	
	public void addRow(Object... datas) {
		if(datas == null || datas.length == 0)
			return ;
		ArrayList<Object> row = new ArrayList<>();
		for(Object data:datas) {
			row.add(data);
		}
		this.rows.add(row);
	}
	
	public List<List<Object>> getRows() {
		return this.rows;
	}
	
}


