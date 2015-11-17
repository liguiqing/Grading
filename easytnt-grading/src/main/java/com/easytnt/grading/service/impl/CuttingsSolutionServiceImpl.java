/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.repository.CuttingsAreaRepository;
import com.easytnt.grading.service.CuttingsSolutionService;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Service
public class CuttingsSolutionServiceImpl implements CuttingsSolutionService {
	@Autowired(required = false)
	private CuttingsAreaRepository cuttingsAreaRepository;

	@Override
	public void saveCuttingsSolution(CuttingsSolution cuttingsSolution) {
		ExamPaper paper = cuttingsSolution.getDesignFor();
		List<CuttingsArea> cuttingsAreas = cuttingsSolution.getCutTo();
		for (CuttingsArea cuttingsArea : cuttingsAreas) {
			cuttingsArea.setPaper(paper);
			cuttingsAreaRepository.saveOrUpdate(cuttingsArea);
		}
	}

}
