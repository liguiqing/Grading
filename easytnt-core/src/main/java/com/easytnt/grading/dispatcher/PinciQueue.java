/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.dispatcher;

import java.util.Collection;

import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月14日
 * @version 1.0
 **/
public interface PinciQueue {
	
	void put(Collection<ImgCuttings> cuttingses);
	
	void add(ImgCuttings cuttings);
	
	ImgCuttings get();
	
	int size();
	
	boolean isEmpty();
	
	void clear();
	
	void addListenner(PinciQueueListener listenner);
	
}


