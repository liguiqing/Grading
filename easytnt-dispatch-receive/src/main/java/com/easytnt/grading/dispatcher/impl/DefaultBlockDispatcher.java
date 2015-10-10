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
import com.easytnt.grading.dispatcher.Block;
import com.easytnt.grading.dispatcher.BlockDispatcher;
import com.easytnt.grading.dispatcher.BlockFetcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class DefaultBlockDispatcher implements BlockDispatcher {
	private static Logger logger = LoggerFactory.getLogger(DefaultBlockDispatcher.class);
	
	private int BUFFER_SIZE = 100;
	
	private ArrayList<Queue<Block>> pinci;
	
	private DispatcherStrategy dispatcherStrategy;
	
	private BlockFetcher blockFetcher;
	
	private  QueueListener queueListener;
	
	public DefaultBlockDispatcher() {
		this(new SinglePaperPriorDispatcherStrategy(1));
	}
	
	public DefaultBlockDispatcher(DispatcherStrategy dispatcherStrategy) {
		this(dispatcherStrategy,new JdbcBlockFetcher());
	}
	
	public DefaultBlockDispatcher(DispatcherStrategy dispatcherStrategy,BlockFetcher blockFetcher) {
		this.dispatcherStrategy = dispatcherStrategy;
		this.pinci = new ArrayList<Queue<Block>>();
		this.blockFetcher = new LockBlockFetcherProxy(blockFetcher);
		this.queueListener = new QueueListener();
		Queue<Block> queue = new ConcurrentLinkedDeque<Block>();
		this.pinci.add(queue);
		this.queueListener .start(queue);
	}

	@Override
	public Block get() {
		Queue<Block> queue = getPinciQueue();
		Block block =  queue.poll();
		if(block == null)
			return null;
		moveToNext(block);
		logger.debug(block.toString());
		return block;
	}
	
	@Override
	public void stop() {
		for(Queue<Block> queue:this.pinci) {
			queue.clear();
		}
		this.pinci.clear();
		this.queueListener.stop();
	}

	/**
	 * 移动到下一评
	 * @param block
	 */
	private void moveToNext(Block block) {
		int curPinci = block.getCurrentPinci();
		if(pinci.size() < curPinci) {
			Queue<Block> next = pinci.get(curPinci);
			if(next != null) {
				block.nextPinci();
				next.add(block);
			}
		}
	}

	private Queue<Block> getPinciQueue() {
		return this.dispatcherStrategy.getDispatcherQueue(this.pinci);
	}
	
	private  class QueueListener implements Runnable{
		private Queue<Block> queue ;
		
		private volatile boolean stop = Boolean.FALSE;
		
		@Override
		public void run() {
			logger.debug("QueueListener running....");
			 while(!stop) {
				 try {					 
					 if(this.queue.size() < BUFFER_SIZE) {
						 List<Block> blocks = blockFetcher.fetch(BUFFER_SIZE * 10);						
						 this.queue.addAll(blocks);
					 }
					 Thread.sleep(5000);
				 }catch(Exception e) {
					 logger.debug(ThrowableParser.toString(e));
				 }
			 }
		}
		
		private void start(Queue<Block> queue) {
			this.queue = queue;
			ThreadExcutor.getInstance().submit(this);
		}
		
		private void stop() {
			this.stop = Boolean.TRUE;
		}
		
	}

}

