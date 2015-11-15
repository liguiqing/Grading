/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent.StudentTestPaperAnswerCardEventTranslator;
import com.easytnt.cutimage.disruptor.handler.CountDownLatchHandler;
import com.easytnt.cutimage.disruptor.handler.CuttingImageHandler;
import com.easytnt.cutimage.disruptor.handler.LogHandlerException;
import com.easytnt.cutimage.disruptor.handler.SaveCuttingBlockToDBHandler;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScanner;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScannerFactory;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.io.scanfiledir.VisitorFile;
import com.easytnt.importpaper.service.SaveCutImageInfoToDatabaseService;
import com.easytnt.importpaper.service.impl.SaveCutImageInfoToDatabaseServiceImpl;
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
public class StartCuttingTestpaper implements Runnable {
	private Logger log = LoggerFactory.getLogger(StartCuttingTestpaper.class);
	private CuttingsSolution cuttingsSolution;
	private CountContainer<Integer> countContainer;
	private SaveCutImageInfoToDatabaseService saveService;

	public StartCuttingTestpaper(CuttingsSolution cuttingsSolution, DataSource ds) {
		this.cuttingsSolution = cuttingsSolution;
		createContainer();
		createSaveDatabaseService(ds);
	}

	private void createContainer() {
		countContainer = new CountContainer<>(0);
		String key = "paperId:" + this.cuttingsSolution.getDesignFor().getPaperId();
		CountContainerMgr.getInstance().put(key, countContainer);

	}

	private void createSaveDatabaseService(DataSource ds) {
		SaveCutImageInfoToDatabaseServiceImpl saveServiceImpl = new SaveCutImageInfoToDatabaseServiceImpl();
		saveServiceImpl.setDatasource(ds);
		this.saveService = saveServiceImpl;
	}

	@Override
	public void run() {

		CountDownLatch studentCountDownLatch = createStudentCountDownLatch();
		Disruptor<StudentTestPaperAnswerCardEvent> cuttingDisruptor = createCuttingDisruptor(studentCountDownLatch);
		try {
			publishTask(cuttingDisruptor);
			studentCountDownLatch.await();
			cuttingDisruptor.shutdown();
			saveService.clear();
			countContainer.setIsOver(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
	}

	private CountDownLatch createStudentCountDownLatch() {
		int studentNum = getStudentCount();
		countContainer.setTotalNum(studentNum);
		CountDownLatch countDownLatch = new CountDownLatch(studentNum);
		return countDownLatch;
	}

	private int getStudentCount() {
		final Counter counter = new Counter();
		DirectoryScanner directoryScanner = DirectoryScannerFactory
				.getDirectoryScanner(cuttingsSolution.getDesignFor().getStudentAnserCardRootPath());
		try {
			directoryScanner.scan(new VisitorFile() {
				private ArrayList<String> paths = new ArrayList<>();

				@Override
				public void visit(FileInfo fileInfo) {
					if (isTiff(fileInfo)) {
						counter.add();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}

		return counter.getCounter() / cuttingsSolution.getDesignFor().getAnswerCardImageNum();
	}

	private Disruptor<StudentTestPaperAnswerCardEvent> createCuttingDisruptor(CountDownLatch countDownLatch) {
		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		final Disruptor<StudentTestPaperAnswerCardEvent> disruptor = new Disruptor<>(
				StudentTestPaperAnswerCardEvent.FACTORY, bufferSize, EasytntExecutor.instance().getExecutorService(),
				ProducerType.SINGLE, new YieldingWaitStrategy());

		disruptor.handleExceptionsWith(new LogHandlerException());

		disruptor
				.handleEventsWithWorkerPool(new CuttingImageHandler(), new CuttingImageHandler(),
						new CuttingImageHandler(), new CuttingImageHandler())
				.handleEventsWithWorkerPool(new SaveCuttingBlockToDBHandler(saveService))
				.handleEventsWith(new CountDownLatchHandler(countDownLatch, countContainer));
		disruptor.start();
		return disruptor;
	}

	private void publishTask(final Disruptor<StudentTestPaperAnswerCardEvent> cuttingDisruptor) throws Exception {
		final int temlateNum = cuttingsSolution.getDesignFor().getAnswerCardImageNum();
		final String rootDir = cuttingsSolution.getDesignFor().getStudentAnserCardRootPath();
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(rootDir);
		directoryScanner.scan(new VisitorFile() {
			private ArrayList<String> paths = new ArrayList<>();

			@Override
			public void visit(FileInfo fileInfo) {
				if (isTiff(fileInfo)) {
					paths.add(fileInfo.getFilePath().toString());
				}

				if (paths.size() == temlateNum) {
					StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
					event.setFilePaths(paths).setRootDir(rootDir).setCuttingsSolution(cuttingsSolution);
					cuttingDisruptor.publishEvent(new StudentTestPaperAnswerCardEventTranslator(event));
					paths = new ArrayList<>();
				}
			}
		});
	}

	private boolean isTiff(FileInfo fileInfo) {
		return fileInfo.getFilePath().toString().toLowerCase().endsWith(".tif");
	}

	private class Counter {
		private int num = 0;

		public void add() {
			num++;
		}

		public int getCounter() {
			return num;
		}
	}
}
