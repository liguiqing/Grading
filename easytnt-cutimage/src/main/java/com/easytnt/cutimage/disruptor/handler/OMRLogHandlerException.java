/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.lmax.disruptor.ExceptionHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OMRLogHandlerException implements ExceptionHandler<DistinguishOMREvent> {
	private Logger log = LoggerFactory.getLogger(OMRLogHandlerException.class);

	@Override
	public void handleEventException(Throwable ex, long sequence, DistinguishOMREvent event) {
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
