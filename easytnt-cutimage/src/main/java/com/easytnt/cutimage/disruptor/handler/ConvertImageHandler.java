/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.utils.ConvertImageTifToPng;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ConvertImageHandler
		implements WorkHandler<StudentTestPaperAnswerCardEvent>, EventHandler<StudentTestPaperAnswerCardEvent> {
	private static Logger log = LoggerFactory.getLogger(ConvertImageHandler.class);

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event) throws Exception {
		ConvertImageTifToPng convertImage = new ConvertImageTifToPng(event);
		convertImage.convert();
	}

}
