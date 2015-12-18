/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.ui.ichart.IchartData;
import com.easytnt.commons.ui.ichart.ResultData;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.service.MonitorService;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月12日
 * @version 1.0
 **/
@Service
public class MonitorServiceImpl implements MonitorService {

	@Autowired(required=false)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<String> statusOfTeacherWorking() {
		
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public IchartData subjectsMonitor(Long testId) {
		//这个实现过程将来可以考虑用 ListDataSourceReader来实现
		String sql1 = "SELECT COUNT(a.itemid) total,b.paper_name,COUNT(d.studentoid) finish "
				+ "FROM paperimport a INNER JOIN paper_info b ON b.paper_id=a.paperid "
				+ "LEFT JOIN test_used_paper c ON a.paperid=c.paper_id AND c.test_id=? "
				+ "LEFT JOIN lastscore d ON d.paperid = a.paperid AND d.studentoid=a.studentoid "
				+ "GROUP BY a.paperid;";
		final IchartData data = new IchartData();
		jdbcTemplate.query(sql1, new Object[] {testId}, new RowMapper<IchartData>() {

			@Override
			public IchartData mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				data.addRow(rs.getString("paper_name"),rs.getFloat("total"),rs.getFloat("finish"));
				return null;
			}
			
		});
		
		return data;
	}

	@Override
	@Transactional(readOnly=true)
	public IchartData sameTeamMonitor(Teacher teacher) {
		String sql1 = "SELECT e.teacher_name,e.teacher_account,COUNT(d.teacheroid) total, "
				+ "MAX(d.score) _max,MIN(d.score) _min,ROUND(AVG(d.score),2) _avg,ROUND(AVG(d.spenttime)/1000,2) spenttime "
				+ "FROM grade_task a INNER JOIN teacher_info b ON b.teacher_id = a.teacher_id AND b.teacher_account=? "
				+ "INNER JOIN grade_task c ON c.item_id=a.item_id "
				+ "INNER JOIN teacher_info e ON e.teacher_id=c.teacher_id "
				+ "LEFT JOIN scoreinfolog d ON d.teacheroid=c.teacher_id AND d.delmark=0 "
				+ "GROUP BY e.teacher_id";
		final IchartData data = new IchartData();
		jdbcTemplate.query(sql1, new Object[] {teacher.getTeacherAccount()}, new RowMapper<IchartData>() {

			@Override
			public IchartData mapRow(ResultSet rs, int rowNum) throws SQLException {
				//IchartData data = new IchartData();
				data.addRow(rs.getString("teacher_name"),rs.getString("teacher_account"),
						rs.getInt("total"),rs.getFloat("_max"),rs.getFloat("_min"),rs.getFloat("_avg")
						,rs.getInt("spenttime"));
				return null;
			}
			
		});
		
		return data;
	}

	@Override
	@Transactional(readOnly=true)
	public IchartData teamMonitorOfWorking(Teacher teacher) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public IchartData teamMonitorOfStabled(Teacher teacher) {
		// TODO Auto-generated method stub
		return null;
	}
	

}


