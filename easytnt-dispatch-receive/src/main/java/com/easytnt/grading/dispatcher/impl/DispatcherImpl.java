/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.ThreadExcutor;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.PinciQueue;
import com.easytnt.grading.dispatcher.PinciQueueListener;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.fetch.Fetcher;

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
	
	private ArrayList<PinciQueue> pinci;
	
	private DispatcherStrategy dispatcherStrategy;
	
	private Fetcher fetcher;
	
	private Dispatcher topPatcher;
	
	private  QueueListener queueListener;
	
	private boolean working = Boolean.FALSE;
	
	
	public DispatcherImpl(DispatcherStrategy dispatcherStrategy,Fetcher fetcher) {
		this(null,dispatcherStrategy,fetcher);
	}
	
	/**
	 * 默认两评构造器
	 * @param topPatcher
	 * @param dispatcherStrategy
	 * @param fetcher
	 */
	public DispatcherImpl(Dispatcher topPatcher,DispatcherStrategy dispatcherStrategy,Fetcher fetcher) {
		this(dispatcherStrategy,fetcher,2);
		this.topPatcher = topPatcher;
	}

	
	public DispatcherImpl(DispatcherStrategy dispatcherStrategy,Fetcher fetcher,int pinci) {
		if(pinci <= 0) {
			pinci = 2;
		}
		this.dispatcherStrategy = dispatcherStrategy;
		this.pinci = new ArrayList<>();
		for(int i=0;i<pinci;i++) {
			this.pinci.add(new PinciQueueImpl());
		}
		this.fetcher = new LockBlockFetcherProxy(fetcher);
		
	}

	@Override
	public CuttingsImage getFor(Referees referees) throws Exception{
		checkWorking();
		
		CuttingsImage cuttings = getCuttingsFromTop(referees);
		if(cuttings != null)
			return cuttings;
			
		PinciQueue queue = getPinciQueue();
		cuttings =  queue.get(referees);
		
		if(cuttings == null)
			return null;

		cuttings.incrementPinciAndGet();
		
		moveToNext(cuttings);
		
		logger.debug(cuttings.toString());
		
		return cuttings;

	}
	
	@Override
	public void put(Collection<CuttingsImage> cuttingses) throws Exception{
		checkWorking();
		this.getFirstQueue().put(cuttingses);
	}
	

	@Override
	public void recover(Collection<CuttingsImage> cuttingses) throws Exception{
		// TODO Auto-generated method stub
		checkWorking();
	}
	
	
	@Override
	public void useNewFetcher(Fetcher fetcher,boolean mustCleanData)throws Exception {
		this.stop();
		this.fetcher = fetcher;
		if(mustCleanData) {
			clearPinciQueue();
		}
		this.start();
	}
	
	@Override
	public void start() throws Exception{
		this.working = Boolean.TRUE;
		this.startListened();
	}
	
	@Override
	public void stop() throws Exception{
		this.working = Boolean.FALSE;
		queueListener.off();
	}
	
	@Override
	public void destroy() throws Exception{
		this.stop();
		clearPinciQueue();
		this.pinci.clear();	
		if(this.topPatcher != null)
			this.topPatcher.destroy();
	}
	
	private void checkWorking() throws IllegalAccessException{
		if(!this.working)
		    throw new IllegalAccessException("试卷分发器未启动或者已经被停止");
	}
	
	private void clearPinciQueue() {
		for(PinciQueue queue:this.pinci) {
			queue.clear();
		}
	}
	
	private PinciQueue getFirstQueue() {
		return this.pinci.get(0);
	}

	private CuttingsImage getCuttingsFromTop(Referees referees) throws Exception{
		if(this.topPatcher != null) {
			return this.topPatcher.getFor(referees);
		}
		return null;
	}
	
	/**
	 * 移动到下一评
	 * @param cuttings
	 */
	private void moveToNext(CuttingsImage cuttings) {
		int curPinci = cuttings.getCurrentPinci();
		if(pinci.size() > curPinci) {
			PinciQueue next = pinci.get(curPinci);
			if(next != null) {
				//cuttings.nextPinci();
				next.add(cuttings);
			}
		}
	}

	private PinciQueue getPinciQueue() {
		return this.dispatcherStrategy.getDispatcherQueue(this.pinci);
	}
	
	private void startListened() {
		this.queueListener = new QueueListener();
		this.queueListener.on(getFirstQueue());
	}
	
	private  class QueueListener implements Runnable,PinciQueueListener{
		private PinciQueue queue;
		
		private  boolean stop = Boolean.FALSE;
		
		@Override
		public void run() {
			logger.debug("QueueListener running....");
			while (!stop) {
				try {
					if (this.queue.size() < BUFFER_SIZE) {
						List<CuttingsImage> cuttings = fetcher.fetch(BUFFER_SIZE * 10);
						logger.debug("Add {} cuttings",cuttings.size());
						this.queue.put(cuttings);
					}
					Thread.sleep(5000);
				} catch (Exception e) {
					logger.debug(ThrowableParser.toString(e));
				}
			}
		}
		

		@Override
		public void on(PinciQueue queue) {
			this.queue = queue;
			ThreadExcutor.getInstance().submit(this);
		}

		@Override
		public void off() {
			this.stop = Boolean.TRUE;
			this.queue = null;
		}
		
	}

}

