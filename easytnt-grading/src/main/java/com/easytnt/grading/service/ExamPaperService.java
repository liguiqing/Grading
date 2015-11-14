package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.domain.paper.Section;

public interface ExamPaperService extends EntityService<ExamPaper, Long> {

	void deleteSectionFor(Long paperId, Section section);

	void updateSectionFor(Long paperId, Section section,Integer position);

	void addSectionFor(Long paperId, Section section);
	
	void addPaperCardFor(Long paperId, PaperCard paperCard);
	
	void deletePaperCardFor(Long paperId, PaperCard paperCard);
	
}
