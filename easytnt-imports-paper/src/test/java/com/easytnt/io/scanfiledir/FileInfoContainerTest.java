/**
 * 
 */
package com.easytnt.io.scanfiledir;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liuyu
 *
 */
public class FileInfoContainerTest {

	@Test
	public void testAdd() throws Exception {
		final int SIZE = 200;
		final int capacity = 10;
		CountContainer<FileInfo> container = new CountContainer<>(capacity);
		CountDownLatch countDownLatch = new CountDownLatch(SIZE);

		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) {
			Thread thread = new Thread(new AddThread(container, countDownLatch));
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}

		countDownLatch.await();

		Assert.assertTrue(container.getContainer().size() == capacity);
		Assert.assertTrue(container.getFileNumber() == SIZE);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>end");
	}

	class AddThread implements Runnable {
		private CountContainer<FileInfo> container;
		private CountDownLatch countDownLatch;

		public AddThread(CountContainer<FileInfo> container, CountDownLatch countDownLatch) {
			this.container = container;
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			container.add(new FileInfo());
			countDownLatch.countDown();
		}
	}
}
