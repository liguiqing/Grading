/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.dispatcher.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.easytnt.grading.dispatcher.PinciQueue;
import com.easytnt.grading.dispatcher.PinciQueueListener;
import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 评次队列
 * </pre>
 * 
 * @author 李贵庆 2015年10月14日
 * @version 1.0
 **/
public class PinciQueueImpl implements PinciQueue {
	
	private ConcurrentLinkedDeque<ImgCuttings> queue = new ConcurrentLinkedDeque<ImgCuttings>();
	
	private AtomicInteger queueSize = new AtomicInteger(0);
	
	@Override
	public void put(Collection<ImgCuttings> cuttingses) {
		this.queueSize.addAndGet(cuttingses.size());
		this.queue.addAll(cuttingses);
	}
	
	@Override
	public void add(ImgCuttings cuttings) {
		this.queue.add(cuttings);
		this.queueSize.incrementAndGet();
	}
	
	@Override
	public ImgCuttings get() {
		ImgCuttings cuttings = this.queue.poll();
		this.queueSize.decrementAndGet();
		return cuttings;
	}
	
	@Override
	public void clear() {
		this.queue.clear();
		this.queueSize.set(0);
	}

	@Override
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	@Override
	public int size() {
		return this.queueSize.get();
	}
	
	@Override
	public void addListenner(PinciQueueListener listenner) {
		listenner.on(this);
	}
	
}


