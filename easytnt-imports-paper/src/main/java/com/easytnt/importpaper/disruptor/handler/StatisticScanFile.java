/**
 * 
 */
package com.easytnt.importpaper.disruptor.handler;

import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.disruptor.event.CutImageEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StatisticScanFile implements EventHandler<CutImageEvent>, WorkHandler<CutImageEvent> {
	private ScannerDirectoryConfig config;

	public StatisticScanFile(ScannerDirectoryConfig config) {
		this.config = config;
	}

	@Override
	public void onEvent(CutImageEvent event) throws Exception {
		String uuId = config.getUuId();
		CountContainer<CutImageInfo> container = CountContainerMgr.getInstance().get(uuId);
		container.add(event.getCutImageInfo());
	}

	@Override
	public void onEvent(CutImageEvent event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

}
