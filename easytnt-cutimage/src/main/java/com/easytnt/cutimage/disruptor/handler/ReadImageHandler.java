/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.utils.ReadImageService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ReadImageHandler
		implements WorkHandler<StudentTestPaperAnswerCardEvent>, EventHandler<StudentTestPaperAnswerCardEvent> {
	private static Logger log = LoggerFactory.getLogger(ReadImageHandler.class);

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	@Override
	public void onEvent(StudentTestPaperAnswerCardEvent event) throws Exception {
		log.debug("开始读取图片....");
		ReadImageService service = new ReadImageService(event);
		try {
			service.read();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
		log.debug("且过完成读取图片完毕.");
	}

}
