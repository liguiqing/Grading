/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.share.Area;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingImageService {
	private Logger log = LoggerFactory.getLogger(CuttingImageService.class);
	private StudentTestPaperAnswerCardEvent event;

	private int diquId;// 地区ID
	private long kcId;// 考场ID
	private String studentName;//

	private String cuttingRootPath;// 切割块存放的位置
	private List<BufferedImage> bufferedImages;

	public CuttingImageService(StudentTestPaperAnswerCardEvent event) {
		this.event = event;
		init();
	}

	private void init() {
		setDqIdAndKcId();
		setCuttingRootPath();
		bufferedImages = event.getBufferedImages();
	}

	private void setDqIdAndKcId() {
		Path rootDir = Paths.get(event.getRootDir());
		Path filePath = Paths.get(event.getFilePaths().get(0));
		int rootDirNum = rootDir.getNameCount();
		String dqIdStr = filePath.getName(rootDirNum).toString();
		String kcIdStr = filePath.getName(rootDirNum + 1).toString();
		try {
			this.diquId = Integer.parseInt(dqIdStr);
		} catch (Exception e) {
			this.diquId = 1;
		}
		try {
			this.kcId = Long.parseLong(kcIdStr);
		} catch (Exception e) {
			this.kcId = 0L;
		}

		studentName = filePath.getFileName().toString();
		int idx = studentName.indexOf(".");
		studentName = idx == -1 ? studentName : studentName.substring(0, idx);
	}

	private void setCuttingRootPath() {
		cuttingRootPath = event.getCuttingsSolution().getDesignFor().getCuttingRootPath();
	}

	public void cutting() throws Exception {
		List<CuttingsArea> cutTo = event.getCuttingsSolution().getCutTo();
		for (CuttingsArea cuttingsArea : cutTo) {
			String savePath = createSaveFilePath(cuttingsArea.getId());
			BufferedImage bufferedImage = this.bufferedImages.get(cuttingsArea.getAnswerCardImageIdx());
			Area areaInPaper = cuttingsArea.getAreaInPaper();
			cutting(savePath, bufferedImage, areaInPaper);
		}

	}

	private String createSaveFilePath(Long itemId) throws Exception {
		StringBuffer saveFilePath = new StringBuffer();
		saveFilePath.append(cuttingRootPath).append(event.getCuttingsSolution().getDesignFor().getPaperId()).append("/")
				.append(diquId).append("/").append(kcId).append("/").append(itemId).append("/").append(studentName)
				.append(".png");
		Path path = Paths.get(saveFilePath.toString());
		if (!Files.exists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		return saveFilePath.toString();
	}

	private void cutting(String savePath, BufferedImage bufferImage, Area area) throws Exception {
		Thumbnails.of(bufferImage).sourceRegion(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight())
				.size(area.getWidth(), area.getHeight()).toFile(savePath);
	}

}
