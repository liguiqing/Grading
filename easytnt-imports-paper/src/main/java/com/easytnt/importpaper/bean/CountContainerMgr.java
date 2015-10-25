/**
 * 
 */
package com.easytnt.importpaper.bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CountContainerMgr {
	private ConcurrentHashMap<String, CountContainer> containerMgr = new ConcurrentHashMap<>();
	private static CountContainerMgr countContainerMgr;
	private static ReentrantLock lock = new ReentrantLock();

	private CountContainerMgr() {
	}

	public void put(String key, CountContainer countContainer) {
		containerMgr.put(key, countContainer);
	}

	public <T> CountContainer<T> get(String key) {
		return containerMgr.get(key);
	}

	public void remove(String key) {
		containerMgr.remove(key);
	}

	public void removeAll() {
		containerMgr.clear();
	}

	public boolean isEmpty() {
		return containerMgr.isEmpty();
	}

	public static CountContainerMgr getInstance() {
		lock.lock();
		try {
			if (countContainerMgr == null) {
				countContainerMgr = new CountContainerMgr();
			}
		} finally {
			lock.unlock();
		}
		return countContainerMgr;
	}
}
