/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.ThreadExcutor;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.fetch.Fetcher;
import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class DispatcherImpl implements Dispatcher {
	private static Logger logger = LoggerFactory.getLogger(DispatcherImpl.class);
	
	private int BUFFER_SIZE = 100;
	
	private ArrayList<Queue<ImgCuttings>> pinci;
	
	private DispatcherStrategy dispatcherStrategy;
	
	private Fetcher fetcher;
	
	private  QueueListener queueListener;
	
	private Dispatcher topPatcher;
	
	
	public DispatcherImpl() {
		this(new SinglePaperPriorDispatcherStrategy(1));
	}
	
	public DispatcherImpl(DispatcherStrategy dispatcherStrategy) {
		this(dispatcherStrategy,new JdbcFetcher());
	}
	
	public DispatcherImpl(Dispatcher topPatcher,DispatcherStrategy dispatcherStrategy,Fetcher fetcher) {
		this(dispatcherStrategy,fetcher);
		this.topPatcher = topPatcher;
	}
	
	public DispatcherImpl(DispatcherStrategy dispatcherStrategy,Fetcher fetcher) {
		this.dispatcherStrategy = dispatcherStrategy;
		this.pinci = new ArrayList<Queue<ImgCuttings>>();
		this.fetcher = new LockBlockFetcherProxy(fetcher);
		this.queueListener = new QueueListener();
		Queue<ImgCuttings> queue = new ConcurrentLinkedDeque<ImgCuttings>();
		this.pinci.add(queue);
		this.queueListener .start(queue);
	}

	@Override
	public ImgCuttings get() {
		ImgCuttings cuttings = getCuttingsFromTop();
		if(cuttings != null)
			return cuttings;
			
		Queue<ImgCuttings> queue = getPinciQueue();
		cuttings =  queue.poll();
		if(cuttings == null)
			return null;
		moveToNext(cuttings);
		logger.debug(cuttings.toString());
		return cuttings;
	}
	
	@Override
	public void stop() {
		for(Queue<ImgCuttings> queue:this.pinci) {
			queue.clear();
		}
		this.pinci.clear();
		this.queueListener.stop();
	}

	private ImgCuttings getCuttingsFromTop() {
		if(this.topPatcher != null) {
			return this.topPatcher.get();
		}
		return null;
	}
	
	/**
	 * 移动到下一评
	 * @param cuttings
	 */
	private void moveToNext(ImgCuttings cuttings) {
		int curPinci = cuttings.getCurrentPinci();
		if(pinci.size() < curPinci) {
			Queue<ImgCuttings> next = pinci.get(curPinci);
			if(next != null) {
				cuttings.nextPinci();
				next.add(cuttings);
			}
		}
	}

	private Queue<ImgCuttings> getPinciQueue() {
		return this.dispatcherStrategy.getDispatcherQueue(this.pinci);
	}
	
	private  class QueueListener implements Runnable{
		private Queue<ImgCuttings> queue ;
		
		private volatile boolean stop = Boolean.FALSE;
		
		@Override
		public void run() {
			logger.debug("QueueListener running....");
			 while(!stop) {
				 try {					 
					 if(this.queue.size() < BUFFER_SIZE) {
						 List<ImgCuttings> blocks = fetcher.fetch(BUFFER_SIZE * 10);						
						 this.queue.addAll(blocks);
					 }
					 Thread.sleep(5000);
				 }catch(Exception e) {
					 logger.debug(ThrowableParser.toString(e));
				 }
			 }
		}
		
		private void start(Queue<ImgCuttings> queue) {
			this.queue = queue;
			ThreadExcutor.getInstance().submit(this);
		}
		
		private void stop() {
			this.stop = Boolean.TRUE;
		}
		
	}

}

