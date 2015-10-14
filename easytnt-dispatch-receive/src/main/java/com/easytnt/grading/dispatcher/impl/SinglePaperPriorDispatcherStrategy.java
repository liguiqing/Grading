/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;
import java.util.Queue;



import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 单卷优先出成绩派发策略
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class SinglePaperPriorDispatcherStrategy implements DispatcherStrategy {

	//评次
	private int maxPinci = 1;
	
	public SinglePaperPriorDispatcherStrategy() {
		
	}
	
	public SinglePaperPriorDispatcherStrategy(int maxPinci) {
		this.maxPinci = maxPinci;
	}
	
	@Override
	public Queue<ImgCuttings> getDispatcherQueue(List<Queue<ImgCuttings>> pinci) {
		if(pinci == null)
			throw new IllegalArgumentException("评卷队列为空！");
		//从最高评次开始取
		for(int index = this.maxPinci-1; index >= 0; index--) {
			Queue<ImgCuttings> queue = pinci.get(index);
			if(!queue.isEmpty()) {
				return queue;
			}
		}
		return pinci.get(maxPinci-1);
	}

}

