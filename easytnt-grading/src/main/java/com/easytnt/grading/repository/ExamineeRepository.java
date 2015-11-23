package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public interface ExamineeRepository extends Repository<Examinee,Long>{
	int insertImports(ListDataSourceMapper mapper, ListDataSourceReader reader) throws Exception;
}