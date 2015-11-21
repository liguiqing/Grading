package com.easytnt.grading.repository.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;

import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.District;
import com.easytnt.grading.domain.room.School;

public class ExamineeDataImpoirtor {

	private ListDataSourceMapper mapper;
	private ListDataSourceReader reader;
	private Session session;
	private StringBuffer sql = new StringBuffer();
	private int colindex = 0;
	private Map<String, Long> longMap = new HashMap<String, Long>();
	private Map<Integer, String[]> errorDatas = new HashMap<Integer, String[]>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");;
	private JdbcTemplate jdbcTemplate;

	private District curDistrict;

	private School curSchool;

	public ExamineeDataImpoirtor(JdbcTemplate jdbcTemplate, Session session, ListDataSourceMapper mapper,
			ListDataSourceReader reader) {
		this.session = session;
		this.mapper = mapper;
		this.reader = reader;
		this.jdbcTemplate = jdbcTemplate;
	}

	public void doImport() throws Exception {
		reader.open();
		try {
			for (int i = 1;; i++) {
				Map<String, Object> paramMap = getParam(i, mapper, reader);
				// this.row = reader.get(i);
				// importDistrict(i);
				importSchool(session, paramMap);
				importRoom(session, paramMap);
				importStudentAndExaminne(false, session, paramMap);
				if (examinneIsNull(paramMap)) {
					errorDatas.put(i, reader.get(i));
				}
			}
		} catch (IndexOutOfBoundsException e) {
			importStudentAndExaminne(true, null, null);
			throw new IndexOutOfBoundsException();
		} finally {
			reader.close();
		}

	}

	private void clearSql() {
		sql = sql.delete(0, sql.length());

	}

	/**
	 * 插入并得到区的ID
	 * 
	 * @param session
	 * @param paramMap
	 * @return
	 */
	private Long importDistrict() {
		if (districtIsNull()) {
			return null;
		}

		// String key = paramMap.get("district_number") + "_" +
		// paramMap.get("district_name");
		// if (longMap.get(key) == null) {
		// getDistrict(paramMap, key);
		// }
		// return longMap.get(key);
		return null;
	}

	/**
	 * 查询内存或数据库中是否存在，否则进行插入
	 * 
	 * @param paramMap
	 */
	private void getDistrict(int index) {
		// clearSql();
		// sql.append(" select district_id from district where district_name = ?
		// and district_number = ? ");
		// String str = sql.toString();
		// List list = session.createSQLQuery(str).setString(0,
		// reader.get(index, mapper.getColIndex("district_name")))
		// .setString(1, reader.get(index,
		// mapper.getColIndex("district_number"))).list();
		// clearSql();
		// if (list.size() > 0) {
		// longMap.put(key, ((BigInteger) list.get(0)).longValue());
		// } else {
		// clearSql();
		// sql.append(" insert into district(district_name,district_number)
		// values (?,?);");
		// session.createSQLQuery(sql.toString()).setString(0, (String)
		// paramMap.get("district_name"))
		// .setString(1, (String)
		// paramMap.get("district_number")).executeUpdate();
		// list = session.createSQLQuery(str).setString(0, (String)
		// paramMap.get("district_name"))
		// .setString(1, (String) paramMap.get("district_number")).list();
		// longMap.put(key, ((BigInteger) list.get(0)).longValue());
		// clearSql();
		// }
	}

	private Long importSchool(Session session, Map<String, Object> paramMap) {
		if (schoolIsNull(paramMap)) {
			return null;
		}
		Long districtId = importDistrict();
		String key = districtId + "_" + paramMap.get("school_code") + "_" + paramMap.get("school_name");
		if (longMap.get(key) == null) {
			getSchool(paramMap, districtId, key);
		}
		return longMap.get(key);
	}

	/**
	 * 查询内存或数据库中是否存在，否则进行插入
	 * 
	 * @param paramMap
	 */
	private void getSchool(Map<String, Object> paramMap, Long districtId, String key) {
		clearSql();
		sql.append(" select school_id from school where school_name =?  and school_code =? ");
		if (districtId == null) {
			sql.append(" and district_id is ? ");
		} else {
			sql.append(" and district_id = ? ");
		}
		String str = sql.toString();
		List list = session.createSQLQuery(str).setString(0, (String) paramMap.get("school_name"))
				.setString(1, (String) paramMap.get("school_code")).setBigInteger(2, longToBigInteger(districtId))
				.list();
		clearSql();
		if (list.size() > 0) {
			longMap.put(key, ((BigInteger) list.get(0)).longValue());
		} else {
			clearSql();
			sql.append(" insert into school(school_name,school_code,district_id) values (?,?,?);");
			session.createSQLQuery(sql.toString()).setString(0, (String) paramMap.get("school_name"))
					.setString(1, (String) paramMap.get("school_code")).setBigInteger(2, longToBigInteger(districtId))
					.executeUpdate();
			list = session.createSQLQuery(str).setString(0, (String) paramMap.get("school_name"))
					.setString(1, (String) paramMap.get("school_code")).setBigInteger(2, longToBigInteger(districtId))
					.list();
			longMap.put(key, ((BigInteger) list.get(0)).longValue());
			clearSql();
		}
	}

	// 插入考场表数据room
	private Long importRoom(Session session, Map<String, Object> paramMap) {
		if (roomIsNull(paramMap)) {
			return null;
		}
		if (paramMap.get("room_number") == null || (Integer.valueOf((String) paramMap.get("room_number")) == 0)) {
			return null;
		}
		String key = "room_" + paramMap.get("room_number");
		if (longMap.get(key) == null) {
			getRoom(paramMap, key);
		}
		return longMap.get(key);
	}

	/**
	 * 查询内存或数据库中是否存在，否则进行插入
	 * 
	 * @param paramMap
	 */
	private void getRoom(Map<String, Object> paramMap, String key) {
		clearSql();
		sql.append(" select room_id from room where room_number = ? ");
		String str = sql.toString();
		List list = session.createSQLQuery(str).setString(0, (String) paramMap.get("room_number")).list();
		clearSql();
		if (list.size() > 0) {
			longMap.put(key, ((BigInteger) list.get(0)).longValue());
		} else {
			clearSql();
			sql.append(" insert into room (room_number) values (?)");
			session.createSQLQuery(sql.toString()).setString(0, (String) paramMap.get("room_number")).executeUpdate();
			list = session.createSQLQuery(str).setString(0, (String) paramMap.get("room_number")).list();
			longMap.put(key, ((BigInteger) list.get(0)).longValue());
			clearSql();
		}
	}

	List<Map<String, Object>> studentlist = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> examinnelist = new ArrayList<Map<String, Object>>();

	private void importStudentAndExaminne(boolean isLast, Session session, Map<String, Object> paramMap)
			throws SQLException {
		if ((studentlist.size() != 0 && examinnelist.size() != 0) && (isLast || examinnelist.size() % 50 == 0)) {
			addStudentAndExaminne(studentlist, examinnelist);
			examinnelist.clear();
			studentlist.clear();
		}
		if (paramMap == null) {
			return;
		}
		if (examinneIsNull(paramMap)) {
			return;
		}
		if (studentIsNull(paramMap)) {
			return;
		}
		Map<String, Object> examinnemap = new LinkedHashMap<String, Object>();
		examinnemap.put("school_id", longToBigInteger(importSchool(session, paramMap)));
		examinnemap.put("room_id", longToBigInteger(importRoom(session, paramMap)));
		examinnemap.put("seating_number", paramMap.get("seating_number"));
		examinnemap.put("examinne_name", paramMap.get("examinne_name"));
		examinnemap.put("examinne_uuid", paramMap.get("examinne_uuid"));
		examinnemap.put("uuid_type", paramMap.get("uuid_type"));
		examinnemap.put("arts", paramMap.get("arts"));
		examinnemap.put("clazz_name", paramMap.get("clazz_name"));
		examinnemap.put("clazz_code", paramMap.get("clazz_code"));
		examinnelist.add(examinnemap);
		Map<String, Object> studentmap = new LinkedHashMap<String, Object>();
		studentmap.put("student_number", paramMap.get("student_number"));
		studentmap.put("student_name", paramMap.get("student_name"));
		studentmap.put("gender", paramMap.get("gender"));
		studentmap.put("nation", paramMap.get("nation"));
		studentmap.put("birthday", paramMap.get("birthday"));
		studentlist.add(studentmap);

	}

	public void addStudentAndExaminne(List<Map<String, Object>> studentList, List<Map<String, Object>> examinneList)
			throws SQLException {
		String sql = " insert into student(student_number,student_name,gender,nation,birthday) values (?,?,?,?,?) ";
		List<Integer> studentIdList = addBean(sql, studentList);
		for (int i = 0; i < examinneList.size(); i++) {
			examinneList.get(i).put("student_id", studentIdList.get(i));
		}
		sql = " insert into examinne(school_id,term_test_id,room_id,seating_number,examinne_name,examinne_uuid,uuid_type,arts,clazz_name,clazz_code,student_id) "
				+ " values (?,1,?,?,?,?,?,?,?,?,?,) ";
		addBean(sql, examinneList);
	}

	private BigInteger longToBigInteger(Long lo) {
		if (lo == null) {
			return null;
		} else {
			return BigInteger.valueOf(lo);
		}
	}

	/**
	 * 获取文件映射字段的内容
	 * 
	 * @param i
	 * @param mapper
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getParam(int i, ListDataSourceMapper mapper, ListDataSourceReader reader)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 获取district表字段列
		colindex = mapper.getColIndex("district_number");
		paramMap.put("district_number", reader.get(i, colindex));
		colindex = mapper.getColIndex("district_name");
		paramMap.put("district_name", reader.get(i, colindex));

		// 获取school表字段列
		colindex = mapper.getColIndex("school_name");
		paramMap.put("school_name", reader.get(i, colindex));
		colindex = mapper.getColIndex("school_code");
		paramMap.put("school_code", reader.get(i, colindex));

		// 获取student表字段列
		colindex = mapper.getColIndex("student_number");
		paramMap.put("student_number", reader.get(i, colindex));
		colindex = mapper.getColIndex("student_name");
		paramMap.put("student_name", reader.get(i, colindex));
		colindex = mapper.getColIndex("gender");
		paramMap.put("gender", reader.get(i, colindex));
		colindex = mapper.getColIndex("nation");
		paramMap.put("nation", reader.get(i, colindex));
		colindex = mapper.getColIndex("birthday");
		paramMap.put("birthday", reader.get(i, colindex));

		// 获取room表字段列
		colindex = mapper.getColIndex("room_number");
		paramMap.put("room_number", reader.get(i, colindex));

		// 获取examinne表字段列
		colindex = mapper.getColIndex("seating_number");
		paramMap.put("seating_number", reader.get(i, colindex));
		colindex = mapper.getColIndex("examinne_name");
		paramMap.put("examinne_name", reader.get(i, colindex));
		colindex = mapper.getColIndex("examinne_uuid");
		paramMap.put("examinne_uuid", reader.get(i, colindex));
		colindex = mapper.getColIndex("uuid_type");
		paramMap.put("uuid_type", reader.get(i, colindex));
		colindex = mapper.getColIndex("arts");
		paramMap.put("arts", reader.get(i, colindex));
		colindex = mapper.getColIndex("clazz_name");
		paramMap.put("clazz_name", reader.get(i, colindex));
		colindex = mapper.getColIndex("clazz_code");
		paramMap.put("clazz_code", reader.get(i, colindex));
		colindex = mapper.getColIndex("absence");
		paramMap.put("absence", reader.get(i, colindex));
		colindex = mapper.getColIndex("total_score");

		if (paramMap.get("examinne_name") == null && paramMap.get("student_name") != null) {
			paramMap.put("examinne_name", paramMap.get("student_name"));
		} else {
			paramMap.put("student_name", paramMap.get("examinne_name"));
		}
		// 获取term_test表字段列
		// colindex = mapper.getColIndex("term_test_name");
		// paramMap.put("term_test_name", reader.get(i, colindex));
		// colindex = mapper.getColIndex("term_test_from");
		// paramMap.put("term_test_from", reader.get(i, colindex));
		// colindex = mapper.getColIndex("term_test_to");
		// paramMap.put("term_test_to", reader.get(i, colindex));
		return paramMap;
	}

	private boolean districtIsNull() {
		return mapper.getColIndex("district_number") > 0 || mapper.getColIndex("district_name") > 0;
	}

	private boolean schoolIsNull(Map<String, Object> paramMap) {
		boolean isTrue = true;
		isTrue = isTrue && paramMap.get("school_name") == null;
		isTrue = isTrue && paramMap.get("school_code") == null;
		return isTrue;
	}

	private boolean studentIsNull(Map<String, Object> paramMap) {
		boolean isTrue = true;
		isTrue = isTrue && (paramMap.get("student_number") == null || paramMap.get("student_name") == null);
		return isTrue;
	}

	private boolean roomIsNull(Map<String, Object> paramMap) {
		boolean isTrue = true;
		isTrue = isTrue && paramMap.get("room_number") == null;
		return isTrue;
	}

	private boolean examinneIsNull(Map<String, Object> paramMap) {
		boolean isTrue = true;
		isTrue = isTrue && (paramMap.get("examinne_name") == null || paramMap.get("examinne_uuid") == null);
		return isTrue;
	}

	public Map<Integer, String[]> getErrorDatas() {
		return errorDatas;
	}

	public List<Integer> addBean(String sql, List<Map<String, Object>> expList) throws SQLException {
		final List<Map<String, Object>> tempexpList = expList;
		Connection con = jdbcTemplate.getDataSource().getConnection();
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		List<String> keyList = new ArrayList<String>();
		for (Map<String, Object> map : tempexpList) {
			keyList.clear();
			keyList.addAll(map.keySet());
			for (int i = 0; i < keyList.size(); i++) {
				pstmt.setObject(i + 1, map.get(keyList.get(i)));
			}
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		con.commit();
		ResultSet rs = pstmt.getGeneratedKeys(); // 获取结果
		List<Integer> list = new ArrayList<Integer>();
		while (rs.next()) {
			list.add(rs.getInt(1));// 取得ID
		}
		con.close();
		pstmt.close();
		rs.close();

		return list;

	}

}
