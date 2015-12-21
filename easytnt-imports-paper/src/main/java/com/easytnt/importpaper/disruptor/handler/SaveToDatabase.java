/**
 * 
 */
package com.easytnt.importpaper.disruptor.handler;

import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.easytnt.importpaper.service.SaveCutImageInfoToDBService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SaveToDatabase implements EventHandler<CutImageEvent>, WorkHandler<CutImageEvent> {
	private SaveCutImageInfoToDBService saveService;

	public SaveToDatabase(SaveCutImageInfoToDBService saveService) {
		this.saveService = saveService;
	}

	@Override
	public void onEvent(CutImageEvent event) throws Exception {
		saveService.save(event.getCutImageInfo());

	}

	@Override
	public void onEvent(CutImageEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

}
