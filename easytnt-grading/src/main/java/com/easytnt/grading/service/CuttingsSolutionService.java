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

	public void saveCuttingsSolution(CuttingsSolution cuttingsSolution);

	public CuttingsSolution getCuttingsSolutionWithPaperId(Long paperId);
}
