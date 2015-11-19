/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;

import ij.ImagePlus;
import ij.io.Opener;
import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ReadImageService {
	private Logger log = LoggerFactory.getLogger(ReadImageService.class);
	private StudentTestPaperAnswerCardEvent event;

	private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();

	public ReadImageService(StudentTestPaperAnswerCardEvent event) {
		this.event = event;
	}

	private void createBufferedImages() throws Exception {
		List<String> filePaths = event.getFilePaths();
		for (String filePath : filePaths) {
			BufferedImage bufferImage = getBufferedImage(filePath);
			bufferedImages.add(bufferImage);
		}
	}

	private BufferedImage getBufferedImage(String filePath) throws Exception {
		if (isTifFile(filePath)) {
			Path path = Paths.get(filePath);
			Opener opener = new Opener();
			ImagePlus imagePlus = opener.openTiff(path.getParent().toString(), path.getFileName().toString());
			return imagePlus.getBufferedImage();
		} else {
			return ImageIO.read(new File(filePath));
		}
	}

	private boolean isTifFile(String filePath) {
		if (filePath.toLowerCase().endsWith(".tif")) {
			return true;
		} else {
			return false;
		}
	}

	private void rotateImages() throws Exception {
		List<AnswerCardCuttingTemplate> templateInfos = event.getCuttingsSolution().getDesignFor()
				.getAnswerCardCuttingTemplates();
		ArrayList<BufferedImage> bufferedImages = new ArrayList<>();
		for (AnswerCardCuttingTemplate templateInfo : templateInfos) {
			BufferedImage bufferedImage = this.bufferedImages.get(templateInfo.getIndex());
			bufferedImage = rotateImage(templateInfo, bufferedImage);
			bufferedImages.add(bufferedImage);
		}
		this.bufferedImages = bufferedImages;
	}

	private BufferedImage rotateImage(AnswerCardCuttingTemplate templateInfo, BufferedImage bufferedImage)
			throws Exception {
		if (templateInfo.getRotate() == 0) {
			return bufferedImage;
		} else {
			return Thumbnails.of(bufferedImage).rotate(templateInfo.getRotate())
					.size(bufferedImage.getWidth(), bufferedImage.getHeight()).asBufferedImage();
		}
	}

	public void read() throws Exception {
		createBufferedImages();// 读取图片buffer
		rotateImages();// 转换图片
		event.setBufferedImages(bufferedImages);
	}
}
