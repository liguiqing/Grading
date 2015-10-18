/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.grade.Referees;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
public interface RefereesService  extends EntityService<Referees, Long>{

	/**
	 * 得到当前登录的评卷员
	 * @return
	 */
	Referees getCurrentReferees();
}


