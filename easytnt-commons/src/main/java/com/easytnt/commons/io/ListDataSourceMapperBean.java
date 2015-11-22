/**
 * 
 * 
 **/

package com.easytnt.commons.io;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月20日
 * @version 1.0
 **/
public class ListDataSourceMapperBean {

	private String targetName;
	
	private String targetAlias;
	
	private int seq;
	
	public ListDataSourceMapperBean() {
		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.targetName).toHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ListDataSourceMapperBean))
			return false;
		ListDataSourceMapperBean other = (ListDataSourceMapperBean)o;
		
		return new EqualsBuilder().append(this.targetName,other.targetName).isEquals();
	}
	
	public ListDataSourceMapperBean(String targetName, String targetAlias, int seq) {
		super();
		this.targetName = targetName;
		this.targetAlias = targetAlias;
		this.seq = seq;
	}
	public ListDataSourceMapperBean(String targetName, int seq) {
		super();
		this.targetName = targetName;
		this.seq = seq;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTargetAlias() {
		return targetAlias;
	}
	public void setTargetAlias(String targetAlias) {
		this.targetAlias = targetAlias;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}

