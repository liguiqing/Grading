/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import java.util.concurrent.CountDownLatch;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CountDownLatchHandler
		implements EventHandler<StudentTestPaperAnswerCardEvent>, WorkHandler<StudentTestPaperAnswerCardEvent> {
	private CountDownLatch countDownLatch;

	public CountDownLatchHandler(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event) throws Exception {
		countDownLatch.countDown();
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

}
