package com.easytnt.grading.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;


/** 
 * <pre>
 * 导入考生信息
 * </pre>
 * 
 * @author 李贵庆 2015年11月20日
 * @version 1.0
 **/
public class ExamineeDataImpoirtor{
    
	private ListDataSourceMapper mapper;
	
	private ListDataSourceReader reader;

	private Map<Integer,String[]> errorDatas = new HashMap<Integer,String[]>();

	private JdbcTemplate jdbcTemplate;
	
	private DistrictTable curDistrict;
	
	private SchoolTable curSchool;
	
	private RoomTable curRoom;
	
	private ArrayList<Object[]> students = new ArrayList<>();
	
	private ArrayList<Object[]> examinnes = new ArrayList<>();
	
	public ExamineeDataImpoirtor(JdbcTemplate jdbcTemplate,ListDataSourceMapper mapper, ListDataSourceReader reader){
		this.mapper = mapper;
		this.reader = reader;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Map<Integer, String[]> getErrorDatas() {
		return errorDatas;
	}
	
	public void doImport() throws Exception{
		reader.open();
		try{
			for(int i=1;;i++){
				importDistrict(i);
				importSchool(i);
				importRoom(i);
				importStudentAndExaminne(i);		
			}
		}catch(IndexOutOfBoundsException e){
			
		}finally{
			reader.close();
		}
		executeInsertStudentAndExaminee(students.size());
	}

	private void  importDistrict(int index)throws Exception{
		if(!districtIsMapped()){
			return ;
		}
		
		String district_name =  reader.get(index, mapper.getColIndex("district_name"));
		String district_number = reader.get(index, mapper.getColIndex("district_number"));
		if("".equals(district_name) && "".equals(district_number)){
			return ;
		}
		
		if(this.curDistrict == null) {
			Long district_Id = getDistrictFromDb(district_name, district_number);			
			if(district_Id != null) {
				newCurDistrict(district_Id,district_name,district_number);
			}else {
				district_Id = insertDistrictTable(district_name,district_number);
				newCurDistrict(district_Id,district_name,district_number);
			}
			
		}else {
			if(!districtExists(district_name,district_number)) {
				Long district_Id = insertDistrictTable(district_name,district_number);
				newCurDistrict(district_Id,district_name,district_number);
			}		
		}
	}
	
	private boolean districtIsMapped(){
		return mapper.getColIndex("district_number") > 0 || mapper.getColIndex("district_name") > 0;
	}
	
	private boolean districtExists(String district_name, String district_number) {
		if(this.curDistrict != null && this.curDistrict.district_name.equals(district_name) && this.curDistrict.district_number.equals(district_number)) {
			return true;
		}
		return false;
	}
	
	private Long getDistrictFromDb(String district_name,String district_number) {
		return selectIdFrom(DistrictTable.selectSql,"district_Id",district_name,district_number);
	}
	
	private Long insertDistrictTable(String district_name,String district_number) {
		return insertInto(DistrictTable.insertSql,district_name,district_number);
	}
	
	private void newCurDistrict(Long district_id,String district_name,String district_number) {
		this.curDistrict = new DistrictTable();
		this.curDistrict.district_id = district_id;
		this.curDistrict.district_name = district_name;
		this.curDistrict.district_number = district_number;
	}
	
	private void importSchool(int index) throws Exception{
		if(!schoolIsMapped()){
			return ;
		}
		
		String school_code =  reader.get(index, mapper.getColIndex("school_code"));
		String school_name = reader.get(index, mapper.getColIndex("school_name"));
		if("".equals(school_name) && "".equals(school_code)){
			return ;
		}
		
		if(this.curSchool == null) {
			Long school_id = getSchoolFromDb(school_name, school_code);			
			if(school_id != null) {
				newCurSchool(school_id,school_name,school_code);
			}else {
				school_id = insertSchoolTable(school_name,school_code,this.curDistrict==null?null:this.curDistrict.district_id);
				newCurSchool(school_id,school_name,school_code);
			}
			
		}else {
			if(!schoolExists(school_name,school_code)) {
				Long school_id = insertSchoolTable(school_name,school_code,this.curDistrict==null?null:this.curDistrict.district_id);
				newCurSchool(school_id,school_name,school_code);
			}		
		}
	}
	
	
	private boolean schoolIsMapped(){
		return mapper.getColIndex("school_name") > 0 || mapper.getColIndex("school_code") > 0;
	}
	
	private boolean schoolExists(String school_name, String school_code) {
		if(this.curSchool != null && this.curSchool.school_name.equals(school_name) && this.curSchool.school_code.equals(school_code)) {
			return true;
		}
		return false;
	}
	
	private Long getSchoolFromDb(String district_name,String district_number) {
		if(this.curDistrict != null) {
			return selectIdFrom(SchoolTable.selectSql + " and district_id = ?","school_Id",district_name,district_number,this.curDistrict.district_id);
		}else {
			return selectIdFrom(SchoolTable.selectSql + " and distirct_id is null","school_Id",district_name,district_number);
		}		
	}
	
	private Long insertSchoolTable(String district_name,String district_number,Long district_id) {
		return insertInto(SchoolTable.insertSql,district_name,district_number,district_id);
	}
	
	private void newCurSchool(Long school_id,String school_name,String school_code) {
		this.curSchool = new SchoolTable();
		this.curSchool.school_id = school_id;
		this.curSchool.school_name = school_name;
		this.curSchool.school_code = school_code;
	}
	
	private void importRoom(int index) throws Exception{
		if(!roomIsMapped()){
			return ;
		}
		
		String room_number =  reader.get(index, mapper.getColIndex("room_number"));
		if("".equals(room_number) ){
			return ;
		}
		
		if(this.curRoom == null) {
			Long room_id = getRoomFromDb(room_number);			
			if(room_id != null) {
				newCurRoom(room_id,room_number);
			}else {
				room_id = insertRoomTable(room_number);
				newCurRoom(room_id,room_number);
			}
			
		}else {
			if(!roomExists(room_number)) {
				Long room_id = insertRoomTable(room_number);
				newCurRoom(room_id,room_number);
			}		
		}
	}
	
	private boolean roomIsMapped(){
		return mapper.getColIndex("room_number") > 0;
	}
	
	private boolean roomExists(String room_number) {
		if(this.curRoom != null && this.curRoom.room_number.equals(room_number)){
			return true;
		}
		return false;
	}
	
	private Long getRoomFromDb(String room_number) {
		return selectIdFrom(RoomTable.selectSql,"room_id",Integer.valueOf(room_number));
	}
	
	private Long insertRoomTable(String room_number) {
		return insertInto(RoomTable.insertSql,Integer.valueOf(room_number));
	}
	
	private void newCurRoom(Long room_id,String room_number) {
		this.curRoom = new RoomTable();
		this.curRoom.room_id = room_id;
		this.curRoom.room_number = room_number;
	}
	
	private void importStudentAndExaminne(int index) throws Exception{
		//目前先按一个学生，一个考生规则处理
		String student_name = this.reader.get(index, this.mapper.getColIndex("student_name"));
		String student_number = this.reader.get(index, this.mapper.getColIndex("student_number"));
		String gender = this.reader.get(index, this.mapper.getColIndex("gender"));
		String nation = this.reader.get(index, this.mapper.getColIndex("nation"));
		String birthday = this.reader.get(index, this.mapper.getColIndex("birthday"));
		
		String examinne_uuid = this.reader.get(index, this.mapper.getColIndex("examinne_uuid"));
		String examinne_name = this.reader.get(index, this.mapper.getColIndex("examinne_name"));
		String seating_number = this.reader.get(index, this.mapper.getColIndex("seating_number"));
		String arts = this.reader.get(index, this.mapper.getColIndex("arts"));
		String clazz_name = this.reader.get(index, this.mapper.getColIndex("clazz_name"));
		String clazz_code = this.reader.get(index, this.mapper.getColIndex("clazz_code"));
		
		if("".equals(student_name) || student_name == null) {
			student_name = examinne_name;
		}else {
			examinne_name = student_name;
		}
		
		if("".equals(examinne_uuid) || examinne_uuid == null) {
			examinne_uuid = student_number;		
		}
		if(examinne_name == null || examinne_name.length() == 0 || examinne_uuid.length()==0) {
			errorDatas.put(index,  this.reader.get(index));
			return;
		}
		
		this.students.add(new Object[] {student_number,student_name,gender,nation,birthday});
		this.examinnes.add(new Object[] {this.curSchool == null?null:this.curSchool.school_id,
				this.curRoom == null?null:this.curRoom.room_id,seating_number,examinne_name,examinne_uuid
				,"",arts,clazz_name,clazz_code,"-1"});
		executeInsertStudentAndExaminee(50);
		
	}
	
	private void executeInsertStudentAndExaminee(int size) {
		if(this.students.size() == size) {
			this.jdbcTemplate.batchUpdate(StudentTable.insertSql, this.students);
			this.students.clear();
		}
		
		if(this.examinnes.size() == size) {
			this.jdbcTemplate.batchUpdate(ExaminneTable.insertSql, this.examinnes);
			this.examinnes.clear();
		}
	}
	
	private Long selectIdFrom(final String sql,final String idField,final Object... params) {
		return this.jdbcTemplate.query(sql,params, new ResultSetExtractor<Long>() {
			@Override
			public Long extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				if(rs.next()) {
					return rs.getLong(idField);
				}
				return null;
			}				
		});		
	}
	
	private Long insertInto(final String sql,final Object... params) {
		KeyHolder keyHolder  = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				
				PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		        for(int index = 0;index<params.length;index++) {
		        	ps.setObject(index+1, params[index]);
		        }
				return ps;
			}
			
		}, keyHolder );

		return keyHolder.getKey().longValue();
	}

	
	public static class DistrictTable{
		private Long district_id;
		private String district_number;
		private String district_name;
		private static String insertSql = "insert into district(district_name,district_number) values (?,?)";
		private static String selectSql = "select district_id from district where district_name = ? and district_number = ?";
	}
	
	public static class SchoolTable{
		private Long school_id;
		private String school_code;
		private String school_name;
		private static String insertSql = "insert into school(school_name,school_code,district_id) values (?,?,?)";
		private static String selectSql = " select school_id from school where school_name =?  and school_code =? ";
	}
	
	public static class RoomTable{
		private Long room_id;
		private String room_number;
		private static String insertSql = " insert into room (room_number) values (?)";
		private static String selectSql = "select room_id from room where room_number = ?";
	}
	
	public static class StudentTable{
		private Long student_id;
		private static String insertSql = "insert into student(student_number,student_name,gender,nation,birthday) values (?,?,?,?,?)";
	}
	
	public static class ExaminneTable{
		private Long examinne_id;
		private static String insertSql = "insert into examinne(school_id,room_id,seating_number,examinne_name,examinne_uuid,uuid_type,arts,clazz_name,clazz_code,student_id) values (?,?,?,?,?,?,?,?,?,?) ";
	}
	
}
