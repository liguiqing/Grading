/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.dispatcher;

import java.util.Collection;

import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.Referees;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月14日
 * @version 1.0
 **/
public interface PinciQueue {
	
	void put(Collection<CuttingsImage> cuttingses);
	
	void add(CuttingsImage cuttings);
	
	CuttingsImage get(Referees referees);
	
	int size();
	
	boolean isEmpty();
	
	void clear();
	
	void addListenner(PinciQueueListener listenner);
	
}


