/**
 * 
 */
package com.easytnt.importpaper.disruptor.handler;

import java.util.concurrent.CountDownLatch;

import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CountDownLatchHandler implements EventHandler<CutImageEvent>, WorkHandler<CutImageEvent> {
	private CountDownLatch countDownLatch;

	public CountDownLatchHandler(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void onEvent(CutImageEvent event) throws Exception {
		countDownLatch.countDown();
	}

	@Override
	public void onEvent(CutImageEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

}
