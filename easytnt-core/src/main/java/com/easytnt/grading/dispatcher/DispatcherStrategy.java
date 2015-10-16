/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher;

import java.util.List;
import java.util.Queue;

import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 派发策略
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface DispatcherStrategy {

	/**
	 * 
	 * @param pinci
	 * @return
	 * @throws 当pince为null时 throws 
	 */
	PinciQueue getDispatcherQueue(List<PinciQueue> pincis);
}

