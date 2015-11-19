/**
 * 
 */
package com.easytnt.importpaper.bean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuyu
 *
 */
public class CountContainer<T> {
	private CopyOnWriteArrayList<T> container = new CopyOnWriteArrayList<>();
	private int totalNum = 0;
	private AtomicLong fileNumber = new AtomicLong();
	private final int capacity;
	private AtomicBoolean isOver = new AtomicBoolean(false);

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

	public boolean getIsOver() {
		return isOver.get();
	}

	public void setIsOver(boolean isOver) {
		this.isOver.set(isOver);
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

}
