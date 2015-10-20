/**
 * 
 */
package com.easytnt.importpaper.disruptor.produce;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.easytnt.importpaper.disruptor.event.CutImageEvent.CutImageEventTranslator;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScanner;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScannerFactory;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.io.scanfiledir.VisitorFile;
import com.easytnt.importpaper.service.ConvertFileInfoToCutImageInfoService;
import com.easytnt.importpaper.service.impl.ConvertFileInfoToCutImageInfoServiceImpl;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * <pre>
 * 开始烧苗图片
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StartScanDir implements Runnable {
	private final Logger log = LoggerFactory.getLogger(StartScanDir.class);

	private Disruptor<CutImageEvent> disruptor;
	private ScannerDirectoryConfig config;
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public StartScanDir(Disruptor<CutImageEvent> disruptor, ScannerDirectoryConfig config) {
		this.disruptor = disruptor;
		this.config = config;
	}

	public CountDownLatch getCountDownLatch() {
		return this.countDownLatch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		final ConvertFileInfoToCutImageInfoService service = new ConvertFileInfoToCutImageInfoServiceImpl();
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(config.getFileDir());
		try {
			directoryScanner.scan(new VisitorFile() {

				@Override
				public void visit(FileInfo fileInfo) {
					CutImageInfo cutImageInfo = service.convert(config, fileInfo);
					CutImageEvent event = new CutImageEvent();
					event.setCutImageInfo(cutImageInfo).setOver(false);
					disruptor.publishEvent(new CutImageEventTranslator(event));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		}
		disruptor.shutdown();
		countDownLatch.countDown();
	}

}
