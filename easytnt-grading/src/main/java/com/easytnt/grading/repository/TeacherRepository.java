package com.easytnt.grading.repository;
import java.util.List;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.grade.Teacher;

/**
 * 科目的具体操作接口类
 * 
 * @author 钟水林 20151109
 *
 */
public interface TeacherRepository extends Repository<Teacher, Long> {
	
	//根据科目名称查询教师信息
	public List<Teacher> getTeacherSname(Long subject_id);

	int selectMaxSeqOf(Long subjectId, int leader);
	
}
