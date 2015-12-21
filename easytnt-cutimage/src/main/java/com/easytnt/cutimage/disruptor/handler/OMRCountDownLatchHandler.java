/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import java.util.concurrent.CountDownLatch;

import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.importpaper.bean.CountContainer;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OMRCountDownLatchHandler implements EventHandler<DistinguishOMREvent>, WorkHandler<DistinguishOMREvent> {
	private CountDownLatch countDownLatch;
	private CountContainer<Integer> countContainer;

	public OMRCountDownLatchHandler(CountDownLatch countDownLatch, CountContainer<Integer> countContainer) {
		this.countDownLatch = countDownLatch;
		this.countContainer = countContainer;
	}

	@Override
	public void onEvent(DistinguishOMREvent event) throws Exception {
		countContainer.add(1);
		countDownLatch.countDown();
	}

	@Override
	public void onEvent(DistinguishOMREvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

}
