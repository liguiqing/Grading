/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import java.util.List;

import com.easytnt.grading.domain.cuttings.SelectItemDefine;
import com.easytnt.grading.domain.paper.ExamPaper;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OmrDefine {
	private ExamPaper paper;
	private List<SelectItemDefine> selectItemDefines;

	public ExamPaper getPaper() {
		return paper;
	}

	public OmrDefine setPaper(ExamPaper paper) {
		this.paper = paper;
		return this;
	}

	public List<SelectItemDefine> getSelectItemDefines() {
		return selectItemDefines;
	}

	public OmrDefine setSelectItemDefines(List<SelectItemDefine> selectItemDefines) {
		this.selectItemDefines = selectItemDefines;
		return this;
	}

}
