/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

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

	private int PINCI_MAX = 1;
	
	private int BUFFER_SIZE = 1024;
	
	private List<Queue<Block>> pinci;
	
	private DispatcherStrategy dispatcherStrategy;
	
	private BlockFetcher blockFetcher;
	
	public DefaultBlockDispatcher() {
		this(new SinglePaperPriorDispatcherStrategy(1));
	}
	
	public DefaultBlockDispatcher(DispatcherStrategy dispatcherStrategy) {
		this(dispatcherStrategy,new JdbcBlockFetcher());
	}
	
	public DefaultBlockDispatcher(DispatcherStrategy dispatcherStrategy,BlockFetcher blockFetcher) {
		this.pinci = new ArrayList<Queue<Block>>();
		this.blockFetcher = new LockBlockFetcherProxy(blockFetcher);
		Queue<Block> queue = new ConcurrentLinkedDeque<Block>();
		putBlocksTo(queue);
		this.pinci.add(queue);
		dispatcherStrategy = new SinglePaperPriorDispatcherStrategy(PINCI_MAX);
	}
	
	private void putBlocksTo(Queue<Block> queue) {
		List<Block> blocks = this.blockFetcher.fetch(10000);
		queue.addAll(blocks);
	}

	@Override
	public Block get() {
		Queue<Block> queue = getPinciQueue();
		Block block =  queue.poll();
		moveToNext(block);
		return block;
	}

	/**
	 * 移动到下一评
	 * @param block
	 */
	private void moveToNext(Block block) {
		int curPinci = block.getCurrentPinci();
		Queue<Block> next = pinci.get(curPinci);
		if(next != null)
			next.add(block);
	}

	private Queue<Block> getPinciQueue() {
		return this.dispatcherStrategy.getDispatcherQueue(this.pinci);
	}

}

