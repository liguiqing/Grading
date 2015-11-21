/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ConvertImageTifToPng {
	private Logger log = LoggerFactory.getLogger(ConvertImageTifToPng.class);
	private StudentTestPaperAnswerCardEvent event;

	public ConvertImageTifToPng(StudentTestPaperAnswerCardEvent event) {
		this.event = event;
	}

	public void convert() throws Exception {
		List<String> filePaths = event.getFilePaths();
		List<AnswerCardCuttingTemplate> answerCardCuttingTemplates = event.getCuttingsSolution().getDesignFor()
				.getAnswerCardCuttingTemplates();
		ArrayList<String> pngFilePath = new ArrayList<>();
		int idx = 0;
		for (String filePath : filePaths) {
			AnswerCardCuttingTemplate template = answerCardCuttingTemplates.get(idx++);
			String path = filePath;
			if (isConvert(path)) {
				path = convert(path);
			}
			rotate(path, template);
			pngFilePath.add(path);
		}
		event.setFilePaths(pngFilePath);
	}

	private boolean isConvert(String filePath) {
		filePath = filePath.toLowerCase();
		return filePath.endsWith(".tif");
	}

	private String convert(String path) throws Exception {
		log.debug("进行图片转换" + path);
		String from = path;
		String to = from + ".png";
		// RenderedOp src2 = JAI.create("fileload", from);
		// OutputStream os2 = new FileOutputStream(to);
		// JPEGEncodeParam param2 = new JPEGEncodeParam();
		// // 指定格式类型，jpg 属于 JPEG 类型
		// ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2,
		// param2);
		// enc2.encode(src2);
		// os2.close();
		log.debug("转换完毕" + path.toString());
		return to;
	}

	private void rotate(String path, AnswerCardCuttingTemplate template) throws Exception {
		if (template.getRotate() != 0) {
			BufferedImage bufImage = ImageIO.read(new File(path));
			Thumbnails.of(bufImage).rotate(template.getRotate()).size(bufImage.getWidth(), bufImage.getHeight())
					.toFile(path);
		}
	}
}
