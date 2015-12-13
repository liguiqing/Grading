/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service;

import java.util.List;

import com.easytnt.commons.ui.ichart.IchartData;
import com.easytnt.commons.ui.ichart.ResultData;
import com.easytnt.grading.domain.grade.Teacher;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月10日
 * @version 1.0
 **/
public interface MonitorService {

	List<String> statusOfTeacherWorking();
	
	/**
	 * 科目监控
	 * @return
	 */
	IchartData subjectsMonitor(Long testId);
	
	/**
	 * 同组监控
	 * @param teacher
	 * @return
	 */
	IchartData sameTeamMonitor(Teacher teacher);
	
	/**
	 * 
	 * @param teacher
	 * @return
	 */
	IchartData teamMonitorOfWorking(Teacher teacher);
	
	IchartData teamMonitorOfStabled(Teacher teacher);
}


