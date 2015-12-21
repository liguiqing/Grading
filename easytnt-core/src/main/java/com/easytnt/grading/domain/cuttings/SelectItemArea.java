/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SelectItemArea extends Area {
	private Long id;
	private String selectOption;
	private SelectItemDefine selectItemDefine;

	public SelectItemDefine getSelectItemDefine() {
		return selectItemDefine;
	}

	public void setSelectItemDefine(SelectItemDefine selectItemDefine) {
		this.selectItemDefine = selectItemDefine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelectOption() {
		return selectOption;
	}

	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}

}
