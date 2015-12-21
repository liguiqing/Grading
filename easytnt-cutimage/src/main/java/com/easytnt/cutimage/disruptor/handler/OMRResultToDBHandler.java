/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.importpaper.service.SaveOmrResultToDBService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class OMRResultToDBHandler implements WorkHandler<DistinguishOMREvent>, EventHandler<DistinguishOMREvent> {
	private static Logger log = LoggerFactory.getLogger(OMRResultToDBHandler.class);
	private SaveOmrResultToDBService saveService;

	public OMRResultToDBHandler(SaveOmrResultToDBService saveService) {
		this.saveService = saveService;
	}

	@Override
	public void onEvent(DistinguishOMREvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(DistinguishOMREvent event) throws Exception {
		log.debug("保存OMR识别结果到数据库....");
		saveService.save(event.getOrmResult());
	}

}
