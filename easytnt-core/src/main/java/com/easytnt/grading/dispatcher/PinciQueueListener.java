/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher;
/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface PinciQueueListener {

	void on(PinciQueue queue);
	
	void off();
}

