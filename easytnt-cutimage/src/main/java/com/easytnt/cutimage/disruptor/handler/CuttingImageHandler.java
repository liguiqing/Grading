/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.utils.CuttingImage;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingImageHandler
		implements WorkHandler<StudentTestPaperAnswerCardEvent>, EventHandler<StudentTestPaperAnswerCardEvent> {
	private static Logger log = LoggerFactory.getLogger(CuttingImageHandler.class);

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event) throws Exception {
		log.debug("开始切割....");
		CuttingImage cuttingImage = new CuttingImage(event);
		cuttingImage.cutting();
		log.debug("且过完成.");
	}

}
