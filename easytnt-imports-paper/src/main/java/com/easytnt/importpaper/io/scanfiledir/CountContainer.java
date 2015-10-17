/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuyu
 *
 */
public class CountContainer<T> {
	private CopyOnWriteArrayList<T> container = new CopyOnWriteArrayList<>();
	private AtomicLong fileNumber = new AtomicLong();
	private final int capacity;

	public CountContainer(int capacity) {
		this.capacity = capacity;
	}

	public void add(T itemImageFile) {
		if (capacity > fileNumber.intValue()) {
			container.add(itemImageFile);
		}
		count();
	}

	public void count() {
		fileNumber.incrementAndGet();
	}

	public int getFileNumber() {
		return fileNumber.intValue();
	}

	public List<T> getContainer() {
		return container;
	}
}
