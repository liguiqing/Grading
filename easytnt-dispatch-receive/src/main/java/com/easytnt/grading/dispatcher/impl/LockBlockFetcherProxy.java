/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.fetch.Fetcher;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class LockBlockFetcherProxy implements Fetcher {

	private  ReentrantLock lock = new ReentrantLock();
	
	private Fetcher principal ;
	
	public LockBlockFetcherProxy(Fetcher retcher) {
		this.principal = retcher;
	}
	
	@Override
	public List<CuttingsImage> fetch(int amount) {
		lock.lock();
		try {
			return this.principal.fetch(amount);
		}finally {
			lock.unlock();
		}
	}
	
	public void destroy() {
		principal.destroy();
	}

}

