/**
 * 
 */
package com.easytnt.importpaper.service.impl;

import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.easytnt.importpaper.disruptor.handler.CountDownLatchHandler;
import com.easytnt.importpaper.disruptor.handler.SaveToDatabase;
import com.easytnt.importpaper.disruptor.handler.StatisticScanFile;
import com.easytnt.importpaper.disruptor.produce.ScanDirProduce;
import com.easytnt.importpaper.service.SaveCutImageInfoToDatabaseService;
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
public class StartScanDirSaveToDatabase implements Runnable {
	private final Logger log = LoggerFactory.getLogger(StartScanDirSaveToDatabase.class);
	private ScannerDirectoryConfig config;
	private SaveCutImageInfoToDatabaseService saveService;

	public StartScanDirSaveToDatabase(ScannerDirectoryConfig config, DataSource ds) {
		this.config = config;
		SaveCutImageInfoToDatabaseServiceImpl saveService = new SaveCutImageInfoToDatabaseServiceImpl();
		saveService.setDatasource(ds);
		this.saveService = saveService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		CountDownLatch countDownLatch = new CountDownLatch(config.getFileSize());

		Disruptor<CutImageEvent> disruptor = createScanDirDisruptor(countDownLatch);
		disruptor.start();
		ScanDirProduce scanDirProduce = new ScanDirProduce(disruptor, config);
		EasytntExecutor.instance().getExecutorService().submit(scanDirProduce);
		scanDirProduce.getCountDownLatch();
		try {
			countDownLatch.await();
			saveService.clear();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
	}

	private Disruptor<CutImageEvent> createScanDirDisruptor(CountDownLatch countDownLatch) {
		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		Disruptor<CutImageEvent> disruptor = new Disruptor<>(CutImageEvent.FACTORY, bufferSize,
				EasytntExecutor.instance().getExecutorService(), ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleEventsWithWorkerPool(new SaveToDatabase(saveService), new SaveToDatabase(saveService))
				.handleEventsWithWorkerPool(new StatisticScanFile(config))
				.handleEventsWithWorkerPool(new CountDownLatchHandler(countDownLatch));
		return disruptor;
	}
}
