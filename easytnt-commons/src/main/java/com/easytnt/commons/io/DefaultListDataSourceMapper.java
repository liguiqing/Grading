/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class DefaultListDataSourceMapper implements ListDataSourceMapper {

	private HashMap<String,ListDataSourceMapperBean> heads = new HashMap<>();
	
	public DefaultListDataSourceMapper() {

	}
	
	public DefaultListDataSourceMapper(String... headStrs) {
		if(headStrs != null) {
			int index = 1;
			for(String s:headStrs) {
				addMapper(s,index++);
			}
		}
	}
	
	public DefaultListDataSourceMapper(Map<String,String> headMap) {
		if(headMap != null) {
			int index = 1;
			Iterator<String> keys = headMap.keySet().iterator();
			while(keys.hasNext()) {
				addMapper(keys.next(),index++);
			}
		}
	}
	
	public void addMapper(String targetName,int seq) {
		heads.put(targetName,new ListDataSourceMapperBean(targetName,seq));
	}
	
	public void addMapper(ListDataSourceMapperBean mapperBean) {
		heads.put(mapperBean.getTargetName(),mapperBean);
	}
	
	@Override
	public int getColIndex(String targetName){
		ListDataSourceMapperBean mapperBean = this.heads.get(targetName);
		if(mapperBean != null)
			return mapperBean.getSeq();
		return -1;
	}

}


