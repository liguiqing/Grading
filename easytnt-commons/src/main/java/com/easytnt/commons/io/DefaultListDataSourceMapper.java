/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io;


/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class DefaultListDataSourceMapper implements ListDataSourceMapper {

	private String[] heads = new String[0];
	
	public DefaultListDataSourceMapper(String[] heads) {
		this.heads = heads;
	}
	
	@Override
	public int getColIndex(String targetName) throws Exception {
		for(int i = 0;i<heads.length;i++) {
			String head = this.heads[i];
			if(head.equalsIgnoreCase(targetName)) {
				return i+1;
			}
		}
		return -1;
	}

}


