/**
 * 
 */
package com.easytnt.cutimage.disruptor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.utils.ImageJCuttingImage;
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
		// CuttingImage cuttingImage = new CuttingImage(event);
		// CuttingImageService service = new CuttingImageService(event);
		ImageJCuttingImage cuttingImage = new ImageJCuttingImage(event);
		try {
			cuttingImage.cutting();
			// service.cutting();
			event.setBufferedImages(null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
		log.debug("且过完成.");
	}

}
