/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
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
public class SaveCuttingBlockToDBHandler
		implements WorkHandler<StudentTestPaperAnswerCardEvent>, EventHandler<StudentTestPaperAnswerCardEvent> {
	private static Logger log = LoggerFactory.getLogger(SaveCuttingBlockToDBHandler.class);
	private SaveCutImageInfoToDBService saveService;

	public SaveCuttingBlockToDBHandler(SaveCutImageInfoToDBService saveService) {
		this.saveService = saveService;
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event) throws Exception {
		log.debug("保存到数据库....");
		saveService.save(event.getCutImageInfos());
	}

}
