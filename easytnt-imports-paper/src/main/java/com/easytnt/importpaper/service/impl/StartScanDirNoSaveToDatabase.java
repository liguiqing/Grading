/**
 * 
 */
package com.easytnt.importpaper.service.impl;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.easytnt.importpaper.disruptor.handler.StatisticScanFile;
import com.easytnt.importpaper.disruptor.produce.ScanDirProduce;
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
public class StartScanDirNoSaveToDatabase implements Runnable {
	private final Logger log = LoggerFactory.getLogger(StartScanDirNoSaveToDatabase.class);
	private ScannerDirectoryConfig config;

	public StartScanDirNoSaveToDatabase(ScannerDirectoryConfig config) {
		this.config = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Disruptor<CutImageEvent> disruptor = createScanDirDisruptor();
		disruptor.start();
		ScanDirProduce scanDirProduce = new ScanDirProduce(disruptor, config);
		EasytntExecutor.instance().getExecutorService().submit(scanDirProduce);
		CountDownLatch countDownLatch = scanDirProduce.getCountDownLatch();
		try {
			countDownLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
	}

	private Disruptor<CutImageEvent> createScanDirDisruptor() {
		int bufferSize = Util.ceilingNextPowerOfTwo(1024);
		Disruptor<CutImageEvent> disruptor = new Disruptor<>(CutImageEvent.FACTORY, bufferSize,
				EasytntExecutor.instance().getExecutorService(), ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleEventsWithWorkerPool(new StatisticScanFile(config));
		return disruptor;
	}
}
