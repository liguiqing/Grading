/**
 * 
 * 
 **/

package com.easytnt.grading.fetch;

import java.util.List;

import com.easytnt.grading.domain.cuttings.CuttingsImage;


/** 
 * <pre>
 * 取卷器，负责从卷库中取出考卷({@link ExamPaper})的一个切割块
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public interface Fetcher {

	List<CuttingsImage> fetch(int amount);
	
	void destroy();
}

