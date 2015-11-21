/**
 * 
 */
package com.easytnt.importpaper.util;

import java.util.HashMap;

/**
 * <pre>
 *暂时使用，实力恶化一个科目ID
 * </pre>
 * 
 * @author liuyu
 *
 */
public class GetSubjectId {
	private static final HashMap<String, Integer> subjectMap = new HashMap<>();

	static {
		subjectMap.put("yuwen", 1);
		subjectMap.put("shuxue", 2);
		subjectMap.put("lishu", 3);
		subjectMap.put("wenshu", 4);
		subjectMap.put("yingyu", 5);
		subjectMap.put("lizong", 6);
		subjectMap.put("wenzong", 7);
		subjectMap.put("wuli", 8);
		subjectMap.put("huaxue", 9);
		subjectMap.put("shengwu", 10);
		subjectMap.put("zhengzhi", 11);
		subjectMap.put("lishi", 12);
		subjectMap.put("dili", 13);
	}

	public static Integer get(String subjectCode) {
		return subjectMap.get(subjectCode);
	}
}
