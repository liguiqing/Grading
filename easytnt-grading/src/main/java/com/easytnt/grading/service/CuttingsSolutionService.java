/**
 * 
 */
package com.easytnt.grading.service;

import java.util.List;

import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.paper.ExamPaper;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface CuttingsSolutionService {

	public void saveCuttingDefines(CuttingSolution cuttingsSolution);

	public CuttingSolution getCuttingDefines(Long paperId);

	public void saveCuttingAreaes(ExamPaper paper, List<CuttingBlock> cuttingBlocks);

	public List<CuttingsArea> getCuttingAreaes(Long paperId);
}
