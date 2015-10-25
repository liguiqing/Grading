/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;

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
}


