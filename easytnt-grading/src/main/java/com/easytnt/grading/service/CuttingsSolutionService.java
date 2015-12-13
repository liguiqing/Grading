/**
 * 
 */
package com.easytnt.grading.service;

import com.easytnt.grading.domain.cuttings.CuttingSolution;

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

	public void saveCuttingAreaes(CuttingSolution cuttingsSolution);

	public CuttingSolution getCuttingAreaes(Long paperId);
}
