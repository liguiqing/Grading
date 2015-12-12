/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.paper.ExamPaper;

/**
 * <pre>
 * 切割方案，即一个考卷切割成多少份
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class CuttingsSolution implements ValueObject<CuttingsSolution> {

	private ExamPaper paper;
	private List<CuttingDefine> cuttingDefines;
	private List<CuttingBlock> cuttingBlocks;

	public ExamPaper getPaper() {
		return paper;
	}

	public CuttingsSolution setPaper(ExamPaper paper) {
		this.paper = paper;
		return this;
	}

	public List<CuttingDefine> getCuttingDefines() {
		return cuttingDefines;
	}

	public CuttingsSolution setCuttingDefines(List<CuttingDefine> cuttingDefines) {
		this.cuttingDefines = cuttingDefines;
		return this;
	}

	public List<CuttingBlock> getCuttingBlocks() {
		return cuttingBlocks;
	}

	public CuttingsSolution setCuttingBlocks(List<CuttingBlock> cuttingBlocks) {
		this.cuttingBlocks = cuttingBlocks;
		return this;
	}

	public void addCuttingDefine(CuttingDefine cuttingDefine) {
		if (cuttingDefines == null) {
			cuttingDefines = new ArrayList<>();
		}
		cuttingDefines.add(cuttingDefine);
	}

	@Override
	public boolean sameValueAs(CuttingsSolution other) {
		return false;
	}

}
