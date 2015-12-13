/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent.StudentTestPaperAnswerCardEventTranslator;
import com.easytnt.cutimage.disruptor.handler.CountDownLatchHandler;
import com.easytnt.cutimage.disruptor.handler.CuttingImageHandler;
import com.easytnt.cutimage.disruptor.handler.LogHandlerException;
import com.easytnt.cutimage.disruptor.handler.SaveCuttingBlockToDBHandler;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
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
	private CuttingSolution cuttingsSolution;
	private CountContainer<Integer> countContainer;
	private SaveCutImageInfoToDatabaseService saveService;

	public StartCuttingTestpaper(CuttingSolution cuttingsSolution, DataSource ds) {
		this.cuttingsSolution = cuttingsSolution;
		createContainer();
		createSaveDatabaseService(ds);
	}

	private void createContainer() {
		countContainer = new CountContainer<>(0);
		String key = "paperId:" + this.cuttingsSolution.getPaper().getPaperId();
		CountContainerMgr.getInstance().put(key, countContainer);

	}

	private void createSaveDatabaseService(DataSource ds) {
		SaveCutImageInfoToDatabaseServiceImpl saveServiceImpl = new SaveCutImageInfoToDatabaseServiceImpl();
		saveServiceImpl.setDatasource(ds);
		this.saveService = saveServiceImpl;
	}

	@Override
	public void run() {
		long b = System.currentTimeMillis();
		CountDownLatch studentCountDownLatch = createStudentCountDownLatch();
		Disruptor<StudentTestPaperAnswerCardEvent> cuttingDisruptor = createCuttingDisruptor(studentCountDownLatch);
		try {
			publishTask(cuttingDisruptor);
			studentCountDownLatch.await();
			cuttingDisruptor.shutdown();
			saveService.clear();
			countContainer.setIsOver(true);
			long e = System.currentTimeMillis();
			log.debug("[" + cuttingsSolution.getPaper().getPaperId() + "]且过完毕花费" + ((e - b) * 1.0 / 1000) + "秒!");
			;
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
				.getDirectoryScanner(cuttingsSolution.getPaper().getStudentAnserCardRootPath());
		try {
			directoryScanner.scan(new VisitorFile() {
				private ArrayList<String> paths = new ArrayList<>();

				@Override
				public void visit(FileInfo fileInfo) {
					if (isDatFile(fileInfo)) {
						try {
							counter.add(FileUtils.readLines(fileInfo.getFilePath().toFile()).size());
						} catch (Exception e) {
							log.error(ThrowableParser.toString(e));
							e.printStackTrace();
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}

		return counter.getCounter();
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
		final String rootDir = cuttingsSolution.getPaper().getStudentAnserCardRootPath();
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(rootDir);
		directoryScanner.scan(new VisitorFile() {
			@Override
			public void visit(FileInfo fileInfo) {
				if (isDatFile(fileInfo)) {
					List<String> studentInfos = null;
					try {
						studentInfos = FileUtils.readLines(fileInfo.getFilePath().toFile());
					} catch (Exception e) {
						log.error(ThrowableParser.toString(e));
						e.printStackTrace();
					}
					for (String studentInfo : studentInfos) {
						StudentTestPaperAnswerCardEvent event = createStudentTestPaperAnswerCardEvent(rootDir,
								fileInfo.getFilePath().getParent(), studentInfo);
						cuttingDisruptor.publishEvent(new StudentTestPaperAnswerCardEventTranslator(event));
					}
				}
			}
		});
	}

	private StudentTestPaperAnswerCardEvent createStudentTestPaperAnswerCardEvent(String rootDir, Path parentPath,
			String studentInfo) {
		StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
		event.setFilePaths(createSourceImagePath(parentPath, studentInfo)).setScanSourceImageRootDir(rootDir)
				.setCuttingsSolution(cuttingsSolution).setStudentId(getStudentId(studentInfo));
		return event;
	}

	private List<String> createSourceImagePath(Path parentPath, String studentInfo) {
		// 海云天的文件解析通过这个完成
		ArrayList<String> paths = new ArrayList<>();
		String[] infos = studentInfo.split(";");
		int imageNum = Integer.parseInt(infos[0]);
		int size = imageNum + 15;
		for (int i = 15; i < size; i++) {
			paths.add(parentPath.resolve(Paths.get(infos[i])).toString());
		}
		return paths;
	}

	private String getStudentId(String studentInfo) {
		String[] infos = studentInfo.split(";");
		return infos[1];
	}

	private boolean isDatFile(FileInfo fileInfo) {
		return fileInfo.getFilePath().toString().toLowerCase().endsWith(".dat");
	}

	private class Counter {
		private int num = 0;

		public void add(int num) {
			this.num += num;
		}

		public int getCounter() {
			return num;
		}
	}
}
