/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.cutimage.utils.DistinguishOmr;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OMRDistinguishHandler implements WorkHandler<DistinguishOMREvent>, EventHandler<DistinguishOMREvent> {
	private static Logger log = LoggerFactory.getLogger(OMRDistinguishHandler.class);

	@Override
	public void onEvent(DistinguishOMREvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(DistinguishOMREvent event) throws Exception {
		log.debug("开始识别OMR....");
		DistinguishOmr distinguishOmr = new DistinguishOmr(event);
		distinguishOmr.distinguish();
		log.debug("识别完成.");
	}

}
