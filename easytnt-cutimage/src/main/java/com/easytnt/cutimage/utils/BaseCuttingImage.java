/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.importpaper.bean.CutImageInfo;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public abstract class BaseCuttingImage {
	private Logger log = LoggerFactory.getLogger(BaseCuttingImage.class);

	protected StudentTestPaperAnswerCardEvent event;
	protected long diquId;// 地区ID
	protected long kcId;// 考场ID
	protected String studentId;//
	protected String cuttingRootPath;// 切割块存放的位置

	protected BaseCuttingImage(StudentTestPaperAnswerCardEvent event) {
		this.event = event;
		init();
	}

	private void init() {
		this.studentId = event.getStudentId();
		setDqIdAndKcId();
		setCuttingRootPath();
	}

	private void setDqIdAndKcId() {
		Path rootDir = Paths.get(event.getScanSourceImageRootDir());
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
	}

	private void setCuttingRootPath() {
		cuttingRootPath = event.getCuttingsSolution().getPaper().getCuttingRootPath();
	}

	public abstract void cutting() throws Exception;

	protected String createSaveFilePath(Long itemId) throws Exception {
		StringBuffer saveFilePath = new StringBuffer();
		saveFilePath.append(cuttingRootPath).append(event.getCuttingsSolution().getPaper().getPaperId()).append("/")
				.append(diquId).append("/").append(kcId).append("/").append(itemId).append("/").append(studentId)
				.append(".png");
		Path path = Paths.get(saveFilePath.toString());
		if (!Files.exists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		return saveFilePath.toString();
	}

	protected CutImageInfo createCutImageInfo(Long itemId) {
		StringBuffer imagePath = new StringBuffer();
		imagePath.append(event.getCuttingsSolution().getPaper().getPaperId()).append("/").append(diquId).append("/")
				.append(kcId).append("/").append(itemId).append("/").append(studentId).append(".png");
		CutImageInfo cutImageInfo = new CutImageInfo();
		cutImageInfo.setPaperId(event.getCuttingsSolution().getPaper().getPaperId()).setDiquId(diquId).setRoomId(kcId)
				.setVirtualroomId(kcId).setItemId(itemId).setImagePath(imagePath.toString())
				.setStudentId(Long.parseLong(studentId));
		return cutImageInfo;
	}
}
