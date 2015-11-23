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
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.share.Area;
import com.easytnt.importpaper.bean.CutImageInfo;

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
public class CuttingImage extends BaseCuttingImage {
	private Logger log = LoggerFactory.getLogger(CuttingImage.class);

	private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();

	public CuttingImage(StudentTestPaperAnswerCardEvent event) {
		super(event);
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

	@Override
	public void cutting() throws Exception {

		createBufferedImages();// 读取图片buffer
		rotateImages();// 转换图片

		ArrayList<CutImageInfo> cutImageInfos = new ArrayList<>();
		List<CuttingsArea> cutTo = event.getCuttingsSolution().getCutTo();
		for (CuttingsArea cuttingsArea : cutTo) {
			String savePath = createSaveFilePath(cuttingsArea.getId());
			BufferedImage bufferedImage = this.bufferedImages.get(cuttingsArea.getAnswerCardImageIdx());
			Area areaInPaper = cuttingsArea.getAreaInPaper();
			cutting(savePath, bufferedImage, areaInPaper);
			cutImageInfos.add(createCutImageInfo(cuttingsArea.getId()));
		}
		event.setCutImageInfos(cutImageInfos);
	}

	private void cutting(String savePath, BufferedImage bufferImage, Area area) throws Exception {
		Thumbnails.of(bufferImage).sourceRegion(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight())
				.size(area.getWidth(), area.getHeight()).toFile(savePath);
	}

}
