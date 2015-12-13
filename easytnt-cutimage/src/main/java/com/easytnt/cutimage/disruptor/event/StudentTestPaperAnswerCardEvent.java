/**
 * 
 */
package com.easytnt.cutimage.disruptor.event;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.CuttingSolution;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StudentTestPaperAnswerCardEvent {

	private String studentId;
	private CuttingSolution cuttingsSolution;

	private String scanSourceImageRootDir;

	private List<String> filePaths = new ArrayList<>();

	private List<BufferedImage> bufferedImages;

	private List<CutImageInfo> cutImageInfos;

	public List<BufferedImage> getBufferedImages() {
		return bufferedImages;
	}

	public StudentTestPaperAnswerCardEvent setBufferedImages(List<BufferedImage> bufferedImages) {
		this.bufferedImages = bufferedImages;
		return this;
	}

	public CuttingSolution getCuttingsSolution() {
		return cuttingsSolution;
	}

	public StudentTestPaperAnswerCardEvent setCuttingsSolution(CuttingSolution cuttingsSolution) {
		this.cuttingsSolution = cuttingsSolution;
		return this;
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public StudentTestPaperAnswerCardEvent setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
		return this;
	}

	public String getScanSourceImageRootDir() {
		return scanSourceImageRootDir;
	}

	public String getStudentId() {
		return studentId;
	}

	public StudentTestPaperAnswerCardEvent setStudentId(String studentId) {
		this.studentId = studentId;
		return this;
	}

	public StudentTestPaperAnswerCardEvent setScanSourceImageRootDir(String scanSourceImageRootDir) {
		if (!scanSourceImageRootDir.endsWith("/") && !scanSourceImageRootDir.endsWith("\\")) {
			scanSourceImageRootDir += "/";
		}
		this.scanSourceImageRootDir = scanSourceImageRootDir;

		return this;
	}

	public List<CutImageInfo> getCutImageInfos() {
		return cutImageInfos;
	}

	public StudentTestPaperAnswerCardEvent setCutImageInfos(List<CutImageInfo> cutImageInfos) {
		this.cutImageInfos = cutImageInfos;
		return this;
	}

	public void clone(StudentTestPaperAnswerCardEvent obj) {
		this.filePaths = obj.filePaths;
		this.scanSourceImageRootDir = obj.scanSourceImageRootDir;
		this.cuttingsSolution = obj.cuttingsSolution;
		this.studentId = obj.studentId;
	}

	public static final EventFactory<StudentTestPaperAnswerCardEvent> FACTORY = new EventFactory<StudentTestPaperAnswerCardEvent>() {
		@Override
		public StudentTestPaperAnswerCardEvent newInstance() {
			return new StudentTestPaperAnswerCardEvent();
		}
	};

	public static class StudentTestPaperAnswerCardEventTranslator
			implements EventTranslator<StudentTestPaperAnswerCardEvent> {
		private StudentTestPaperAnswerCardEvent event;

		public StudentTestPaperAnswerCardEventTranslator(StudentTestPaperAnswerCardEvent event) {
			this.event = event;
		}

		@Override
		public void translateTo(StudentTestPaperAnswerCardEvent event, long sequence) {
			event.clone(this.event);
		}

	}
}
