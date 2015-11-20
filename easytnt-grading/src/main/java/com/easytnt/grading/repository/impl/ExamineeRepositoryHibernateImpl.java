package com.easytnt.grading.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.repository.ExamineeRepository;

@SuppressWarnings("rawtypes")
@Repository
public class ExamineeRepositoryHibernateImpl extends HibernateRepository<Examinee,Long> implements ExamineeRepository {
	
	
	
	//读取数据
	@Override
	public int insertImports(JdbcTemplate jdbcTemplate,ListDataSourceMapper mapper, ListDataSourceReader reader) throws Exception {
		new ExamineeDataImpoirtor(jdbcTemplate,this.getCurrentSession(),mapper,reader).doImport();
		return 0;
	}
	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return Examinee.class;
	}
}
