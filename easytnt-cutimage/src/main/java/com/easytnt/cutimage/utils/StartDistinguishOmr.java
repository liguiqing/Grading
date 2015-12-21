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
import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent.DistinguishOMREventTranslator;
import com.easytnt.cutimage.disruptor.handler.OMRCountDownLatchHandler;
import com.easytnt.cutimage.disruptor.handler.OMRDistinguishHandler;
import com.easytnt.cutimage.disruptor.handler.OMRLogHandlerException;
import com.easytnt.cutimage.disruptor.handler.OMRResultToDBHandler;
import com.easytnt.grading.domain.cuttings.OmrDefine;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScanner;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScannerFactory;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.io.scanfiledir.VisitorFile;
import com.easytnt.importpaper.service.SaveOmrResultToDBService;
import com.easytnt.importpaper.service.impl.SaveOmrResultToDBServiceImpl;
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
public class StartDistinguishOmr implements Runnable {
	private Logger log = LoggerFactory.getLogger(StartDistinguishOmr.class);
	private OmrDefine omrDefine;
	private CountContainer<Integer> countContainer;
	private SaveOmrResultToDBService saveService;

	public StartDistinguishOmr(OmrDefine omrDefine, DataSource ds) {
		this.omrDefine = omrDefine;
		createContainer();
		createSaveDatabaseService(ds);
	}

	private void createContainer() {
		countContainer = new CountContainer<>(0);
		String key = "OMR:paperId:" + omrDefine.getPaper().getPaperId();
		CountContainerMgr.getInstance().put(key, countContainer);

	}

	private void createSaveDatabaseService(DataSource ds) {
		SaveOmrResultToDBServiceImpl saveServiceImpl = new SaveOmrResultToDBServiceImpl();
		saveServiceImpl.setDatasource(ds);
		this.saveService = saveServiceImpl;
	}

	@Override
	public void run() {
		long b = System.currentTimeMillis();
		CountDownLatch studentCountDownLatch = createStudentCountDownLatch();
		Disruptor<DistinguishOMREvent> disruptor = createDisruptor(studentCountDownLatch);
		try {
			publishTask(disruptor);
			studentCountDownLatch.await();
			disruptor.shutdown();
			saveService.clear();
			countContainer.setIsOver(true);
			long e = System.currentTimeMillis();
			log.debug("[" + omrDefine.getPaper().getPaperId() + "]且过完毕花费" + ((e - b) * 1.0 / 1000) + "秒!");
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
				.getDirectoryScanner(omrDefine.getPaper().getStudentAnserCardRootPath());
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

	private Disruptor<DistinguishOMREvent> createDisruptor(CountDownLatch countDownLatch) {
		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		final Disruptor<DistinguishOMREvent> disruptor = new Disruptor<>(DistinguishOMREvent.FACTORY, bufferSize,
				EasytntExecutor.instance().getExecutorService(), ProducerType.SINGLE, new YieldingWaitStrategy());

		disruptor.handleExceptionsWith(new OMRLogHandlerException());

		disruptor
				.handleEventsWithWorkerPool(new OMRDistinguishHandler(), new OMRDistinguishHandler(),
						new OMRDistinguishHandler(), new OMRDistinguishHandler())
				.handleEventsWithWorkerPool(new OMRResultToDBHandler(saveService))
				.handleEventsWith(new OMRCountDownLatchHandler(countDownLatch, countContainer));
		disruptor.start();
		return disruptor;
	}

	private void publishTask(final Disruptor<DistinguishOMREvent> disruptor) throws Exception {
		final String rootDir = omrDefine.getPaper().getStudentAnserCardRootPath();
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
						DistinguishOMREvent event = createDistinguishOMREvent(rootDir,
								fileInfo.getFilePath().getParent(), studentInfo);
						disruptor.publishEvent(new DistinguishOMREventTranslator(event));
					}
				}
			}
		});
	}

	private DistinguishOMREvent createDistinguishOMREvent(String rootDir, Path parentPath, String studentInfo) {
		DistinguishOMREvent event = new DistinguishOMREvent();
		event.setFilePaths(createSourceImagePath(parentPath, studentInfo)).setOmrDefine(omrDefine)
				.setStudentId(getStudentId(studentInfo));
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
