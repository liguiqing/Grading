package com.easytnt.grading.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.repository.ExamineeRepository;

@Repository
public class ExamineeRepositoryHibernateImpl extends HibernateRepository<Examinee,Long> implements ExamineeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String querySql = "SELECT ex.examinne_name,ex.examinne_uuid,st.student_number,ex.clazz_name,ex.clazz_code FROM examinne ex LEFT JOIN student st ON ex.student_id=st.student_id  where 1=1 limit ?,?";
	
	private String queryCountSql = "SELECT COUNT(ex.examinne_id) total FROM examinne ex LEFT JOIN student st ON ex.student_id=st.student_id";
	
	@Override
	public int insertImports(ListDataSourceMapper mapper, 
			ListDataSourceReader reader) throws Exception {
		new ExamineeDataImpoirtor(this.jdbcTemplate,mapper,reader).doImport();
		return 0;
	}
	
	@Override
	public void query(com.easytnt.commons.entity.cqrs.Query query) {
		int rowsTotal = jdbcTemplate.query(queryCountSql, new ResultSetExtractor<Integer>(){
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if(rs.next()) {
					return rs.getInt("total");
				}
				return 0;
			}
		});
		
		List<HashMap<String,?>> result = jdbcTemplate.query(querySql,new Object[] {query.getStartRow(),query.getPageSize()},
				new RowMapper<HashMap<String,?>>() {

			@SuppressWarnings("rawtypes")
			@Override
			public HashMap<String,?> mapRow(ResultSet rs, int index)
					throws SQLException {
				HashMap map = new HashMap();
				map.put("examinneName", rs.getString("examinne_name"));
				map.put("examinneUuid", rs.getString("examinne_uuid"));
				map.put("studentNumber", rs.getString("student_number"));
				map.put("clazzName", rs.getString("clazz_name"));
				map.put("clazzCode", rs.getString("clazz_code"));
				return map;
			}
		});
		
		query.rows(rowsTotal);
		query.result(result);
	}
	
	@Override
	protected Class<Examinee> getEntityClass() {
		return Examinee.class;
	}
}
