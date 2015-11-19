/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent.StudentTestPaperAnswerCardEventTranslator;
import com.easytnt.cutimage.disruptor.handler.CountDownLatchHandler;
import com.easytnt.cutimage.disruptor.handler.CuttingImageHandler;
import com.easytnt.cutimage.disruptor.handler.LogHandlerException;
import com.easytnt.cutimage.disruptor.handler.ReadImageHandler;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.share.Area;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScanner;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScannerFactory;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.io.scanfiledir.VisitorFile;
import com.easytnt.thread.EasytntExecutor;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.Util;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingImageTest {

	@Test
	public void test() throws Exception {
		long b = System.currentTimeMillis();
		StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
		event.setRootDir("E:/tif/lizong").setFilePaths(filePaths()).setCuttingsSolution(cuttingsSolution());

		// ConvertImageHandler convertHandler = new ConvertImageHandler();
		// convertHandler.onEvent(event);

		CuttingImageHandler cuttingImageHandler = new CuttingImageHandler();
		cuttingImageHandler.onEvent(event);
		long e = System.currentTimeMillis();
		System.out.println((e - b) + "ms");

	}

	@Test
	public void testMitl() throws Exception {
		long b = System.currentTimeMillis();

		CountDownLatch countDownLatch = new CountDownLatch(60);
		CountContainer<Integer> countContainer = new CountContainer<>(0);

		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		final Disruptor<StudentTestPaperAnswerCardEvent> disruptor = new Disruptor<>(
				StudentTestPaperAnswerCardEvent.FACTORY, bufferSize, EasytntExecutor.instance().getExecutorService(),
				ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleExceptionsWith(new LogHandlerException());
		disruptor
				.handleEventsWithWorkerPool(new CuttingImageHandler(), new CuttingImageHandler(),
						new CuttingImageHandler(), new CuttingImageHandler())
				.handleEventsWith(new CountDownLatchHandler(countDownLatch, countContainer));
		disruptor.start();

		final CuttingsSolution solution = cuttingsSolution();
		final String rootDir = "E:/tif/lizong";
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(rootDir);
		try {
			directoryScanner.scan(new VisitorFile() {
				private ArrayList<String> paths = new ArrayList<>();

				@Override
				public void visit(FileInfo fileInfo) {
					if (fileInfo.getFilePath().toString().toLowerCase().endsWith(".tif")) {
						paths.add(fileInfo.getFilePath().toString());
					}

					if (paths.size() == 2) {
						StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
						event.setFilePaths(paths).setRootDir(rootDir).setCuttingsSolution(solution);
						disruptor.publishEvent(new StudentTestPaperAnswerCardEventTranslator(event));
						paths = new ArrayList<>();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		countDownLatch.await();
		long e = System.currentTimeMillis();
		System.out.println(((e - b) * 1.0 / 1000) + "s");

	}

	@Test
	public void testMitl2() throws Exception {
		long b = System.currentTimeMillis();

		CountDownLatch countDownLatch = new CountDownLatch(60);
		CountContainer<Integer> countContainer = new CountContainer<>(0);
		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		final Disruptor<StudentTestPaperAnswerCardEvent> disruptor = new Disruptor<>(
				StudentTestPaperAnswerCardEvent.FACTORY, bufferSize, EasytntExecutor.instance().getExecutorService(),
				ProducerType.SINGLE, new YieldingWaitStrategy());

		disruptor.handleExceptionsWith(new LogHandlerException());

		disruptor.handleEventsWithWorkerPool(new ReadImageHandler(), new ReadImageHandler(), new ReadImageHandler())
				.handleEventsWithWorkerPool(new CuttingImageHandler(), new CuttingImageHandler(),
						new CuttingImageHandler(), new CuttingImageHandler())
				.handleEventsWith(new CountDownLatchHandler(countDownLatch, countContainer));
		disruptor.start();

		final CuttingsSolution solution = cuttingsSolution();
		final String rootDir = "E:/tif/lizong";
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(rootDir);
		try {
			directoryScanner.scan(new VisitorFile() {
				private ArrayList<String> paths = new ArrayList<>();

				@Override
				public void visit(FileInfo fileInfo) {
					if (fileInfo.getFilePath().toString().toLowerCase().endsWith(".tif")) {
						paths.add(fileInfo.getFilePath().toString());
					}

					if (paths.size() == 2) {
						StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
						event.setFilePaths(paths).setRootDir(rootDir).setCuttingsSolution(solution);
						disruptor.publishEvent(new StudentTestPaperAnswerCardEventTranslator(event));
						paths = new ArrayList<>();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		countDownLatch.await();
		long e = System.currentTimeMillis();
		System.out.println(((e - b) * 1.0 / 1000) + "s");

	}

	private List<String> filePaths() {
		ArrayList<String> filePaths = new ArrayList<>();
		filePaths.add("E:/tif/lizong/01/222000002/00000001.Tif");
		filePaths.add("E:/tif/lizong/01/222000002/00000002.Tif");
		return filePaths;
	}

	private CuttingsSolution cuttingsSolution() {
		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		ExamPaper examPaper = ExamPaper();
		cuttingsSolution.setDesignFor(examPaper);

		cuttingsSolution.newCuttingsDefines(CuttingsArea(1L, 0, 132, 1178, 1049, 230));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(2L, 0, 122, 1350, 1069, 722));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(3L, 0, 1164, 191, 1025, 272));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(4L, 0, 1173, 431, 1010, 735));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(5L, 0, 1165, 1113, 1021, 1005));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(6L, 0, 2194, 1035, 1023, 528));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(7L, 0, 2190, 1592, 1018, 516));

		cuttingsSolution.newCuttingsDefines(CuttingsArea(8L, 1, 78, 193, 1033, 547));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(9L, 1, 72, 715, 1054, 523));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(10L, 1, 80, 1181, 1049, 526));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(11L, 1, 79, 1641, 1037, 512));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(12L, 1, 1125, 200, 1009, 771));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(13L, 1, 1121, 940, 1027, 1219));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(14L, 1, 2146, 185, 1035, 991));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(15L, 1, 2151, 1153, 1029, 1012));
		return cuttingsSolution;
	}

	private ExamPaper ExamPaper() {
		List<AnswerCardCuttingTemplate> answerCardCuttingTemplates = new ArrayList<>();
		AnswerCardCuttingTemplate template = new AnswerCardCuttingTemplate();
		template.setIndex(0).setRotate(-90);
		answerCardCuttingTemplates.add(template);
		template = new AnswerCardCuttingTemplate();
		template.setIndex(0).setRotate(90);
		answerCardCuttingTemplates.add(template);

		ExamPaper examPaper = new ExamPaper();
		examPaper.setAnswerCardCuttingTemplates(answerCardCuttingTemplates);
		examPaper.setAnswerCardImageNum(2);
		examPaper.setPaperId(1000L);
		examPaper.setCuttingRootPath("E:/test/cuttingImage");
		examPaper.setStudentAnserCardRootPath("E:/tif/lizong");

		return examPaper;
	}

	private CuttingsArea CuttingsArea(Long id, int answerCardImageIdx, int left, int top, int width, int height) {
		CuttingsArea area = new CuttingsArea();
		area.setId(id);
		area.setAnswerCardImageIdx(answerCardImageIdx);
		area.setAreaInPaper(new Area(left, top, width, height));
		return area;
	}
}
