/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.lmax.disruptor.ExceptionHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class LogHandlerException implements ExceptionHandler<StudentTestPaperAnswerCardEvent> {
	private Logger log = LoggerFactory.getLogger(LogHandlerException.class);

	@Override
	public void handleEventException(Throwable ex, long sequence, StudentTestPaperAnswerCardEvent event) {
		log.error(ThrowableParser.toString(ex));
	}

	@Override
	public void handleOnStartException(Throwable ex) {
		log.error(ThrowableParser.toString(ex));
	}

	@Override
	public void handleOnShutdownException(Throwable ex) {
		log.error(ThrowableParser.toString(ex));
	}

}
