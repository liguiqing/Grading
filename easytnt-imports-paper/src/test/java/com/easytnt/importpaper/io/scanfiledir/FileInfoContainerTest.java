/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;

/**
 * @author liuyu
 *
 */
public class FileInfoContainerTest {

	private Logger log = LoggerFactory.getLogger(FileInfoContainerTest.class);

	@Test
	public void testAdd() throws Exception {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

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

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>end");
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
