package com.easytnt.grading.repository.impl;

import java.util.HashMap;

import com.easytnt.commons.io.ListDataSourceMapper;

public class ListDataMapperMocker implements ListDataSourceMapper {
	HashMap<String,Integer> mapper = new HashMap<>();
	
	public ListDataMapperMocker() {
		mapper.put("district_name", 0);
		mapper.put("district_number", 1);
		mapper.put("school_name", 2);
		mapper.put("school_code", 3);
		mapper.put("student_number", 4);
		mapper.put("student_name", 5);
		mapper.put("gender", 6);
		mapper.put("nation", 7);
		mapper.put("birthday", 8);
		mapper.put("room_number", 9);
		mapper.put("seating_number", 10);
		mapper.put("examinne_name", 11);
		mapper.put("examinne_uuid", 12);
		mapper.put("uuid_type", 13);
		mapper.put("arts", 14);
		mapper.put("clazz_name", 15);
		mapper.put("clazz_code", 16);
		mapper.put("absence", 17);
		mapper.put("total_score", 18);
		mapper.put("term_test_name", 19);
		mapper.put("term_test_from", 20);
		mapper.put("term_test_to", 21);
	}
	@Override
	public int getColIndex(String targetName){
		return mapper.get(targetName);
	}
}
