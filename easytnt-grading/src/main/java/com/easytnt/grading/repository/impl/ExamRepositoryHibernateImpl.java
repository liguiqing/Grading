package com.easytnt.grading.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.repository.ExamRepository;

@Repository
public class ExamRepositoryHibernateImpl extends HibernateRepository<Exam,Long> implements ExamRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	protected Class<Exam> getEntityClass() {
		return Exam.class;
	}

	@Override
	public ExamineeFinalScoreCalculator createFinalScoreCalculator(Long examId) {
		final ExamineeFinalScoreCalculator calculator = ExamineeFinalScoreCalculator.newCalculator(1l);
		calculator.setJdbcTemplate(this.jdbcTemplate);
		calculator.setSessionFactory(this.getCurrentSession().getSessionFactory());
			jdbcTemplate.query("SELECT examinne_uuid FROM examinne ", new ResultSetExtractor() {

				@Override
				public Object extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					while(rs.next())
						calculator.calScore(rs.getString(1));
					return null;
				}
			});
		return calculator;
	}

}
