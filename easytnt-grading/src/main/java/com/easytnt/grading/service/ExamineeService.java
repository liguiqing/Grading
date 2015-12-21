package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月18日
 * @version 1.0
 **/
public interface ExamineeService extends EntityService<Examinee, Long>{
	
	void imports(ListDataSourceMapper mapper,ListDataSourceReader reader);
	
	String calFinalScore(String examineeuuid,Long testId);
}
