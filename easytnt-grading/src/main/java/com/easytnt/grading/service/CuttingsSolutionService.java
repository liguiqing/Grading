/**
 * 
 */
package com.easytnt.grading.service;

import com.easytnt.grading.domain.cuttings.CuttingsSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface CuttingsSolutionService {

	public void saveCuttingDefines(CuttingsSolution cuttingsSolution);

	public CuttingsSolution getCuttingDefines(Long paperId);

	public void saveCuttingAreaes(CuttingsSolution cuttingsSolution);

	public CuttingsSolution getCuttingAreaes(Long paperId);
}
