/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.GradeTask;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月25日
 * @version 1.0
 **/
public interface CuttingsImageGradeRecordRepository extends
		Repository<CuttingsImageGradeRecord, Long> {

	void saveForFetching(CuttingsImageGradeRecord record);
	
	void saveForScoring(CuttingsImageGradeRecord record);

	CuttingsImageGradeRecord findUndoRecordOf(GradeTask task);


	void saveForBlank(CuttingsImageGradeRecord imageGradeRecord);

	void saveForError(CuttingsImageGradeRecord imageGradeRecord);

	void saveLastScore(CuttingsImageGradeRecord record,Float score);

}
