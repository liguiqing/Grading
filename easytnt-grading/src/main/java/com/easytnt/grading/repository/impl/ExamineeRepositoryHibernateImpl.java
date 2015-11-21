package com.easytnt.grading.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.repository.ExamineeRepository;

@Repository
public class ExamineeRepositoryHibernateImpl extends HibernateRepository<Examinee,Long> implements ExamineeRepository {

	@Override
	public int insertImports(JdbcTemplate jdbcTemplate,ListDataSourceMapper mapper, 
			ListDataSourceReader reader) throws Exception {
		new ExamineeDataImpoirtor(jdbcTemplate,mapper,reader).doImport();
		return 0;
	}
	
	@Override
	protected Class<Examinee> getEntityClass() {
		return Examinee.class;
	}
}
