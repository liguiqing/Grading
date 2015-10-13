/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.easytnt.grading.dispatcher.Block;
import com.easytnt.grading.dispatcher.BlockFetcher;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class LockBlockFetcherProxy implements BlockFetcher {

	private  ReentrantLock lock = new ReentrantLock();
	
	private BlockFetcher principal ;
	
	public LockBlockFetcherProxy(BlockFetcher retcher) {
		this.principal = retcher;
	}
	
	@Override
	public List<Block> fetch(int amount) {
		lock.lock();
		try {
			return this.principal.fetch(amount);
		}finally {
			lock.unlock();
		}
	}

}

