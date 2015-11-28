/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller.formbean;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public class DataSourceMapperFormBean {
	
	public DataSourceMapperFormBean() {
		
	}
	
	public DataSourceMapperFormBean(String fieldName,String fieldAlias) {
		this.fieldAlias = fieldAlias;
		this.fieldName = fieldName;
	}
	
	private String fieldName;
	
	private String fieldAlias;
	
	private String targetName;
	
	private int seq;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getFieldAlias() {
		return fieldAlias;
	}

	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}


