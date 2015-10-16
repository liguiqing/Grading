/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.ThreadExcutor;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.dispatcher.PinciQueue;
import com.easytnt.grading.dispatcher.PinciQueueListener;
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
	
	private ArrayList<PinciQueue> pinci;
	
	private DispatcherStrategy dispatcherStrategy;
	
	private Fetcher fetcher;
	
	private Dispatcher topPatcher;
	
	private  QueueListener queueListener;
	
	public DispatcherImpl() {
		this(new SinglePaperPriorDispatcherStrategy(1));
	}
	
	public DispatcherImpl(DispatcherStrategy dispatcherStrategy) {
		this(dispatcherStrategy,new JdbcFetcher());
	}
	
	
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
		this.startListened();
	}

	@Override
	public ImgCuttings get() {
		ImgCuttings cuttings = getCuttingsFromTop();
		if(cuttings != null)
			return cuttings;
			
		PinciQueue queue = getPinciQueue();
		cuttings =  queue.get();
		
		if(cuttings == null)
			return null;
		try {
		cuttings.incrementPinciAndGet();
		logger.debug(cuttings.toString());
		moveToNext(cuttings);
		return cuttings;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void put(Collection<ImgCuttings> cuttingses) {
		this.getFirstQueue().put(cuttingses);
	}
	
	@Override
	public void stop() {
		queueListener.off();
		for(PinciQueue queue:this.pinci) {
			queue.clear();
		}
		this.pinci.clear();
		
	}
	
	private PinciQueue getFirstQueue() {
		return this.pinci.get(0);
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
		
		private volatile boolean stop = Boolean.FALSE;
		
		@Override
		public void run() {
			logger.debug("QueueListener running....");
			while (!stop) {
				try {
					if (this.queue.size() < BUFFER_SIZE) {
						List<ImgCuttings> cuttings = fetcher.fetch(BUFFER_SIZE * 10);
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

