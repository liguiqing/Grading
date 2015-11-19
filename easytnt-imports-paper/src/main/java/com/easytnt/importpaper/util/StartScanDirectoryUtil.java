/**
 * 
 */
package com.easytnt.importpaper.util;

import java.util.UUID;

import javax.sql.DataSource;

import com.easytnt.commons.util.StringUtil;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.service.impl.StartScanDirNoSaveToDatabase;
import com.easytnt.importpaper.service.impl.StartScanDirSaveToDatabase;
import com.easytnt.thread.EasytntExecutor;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StartScanDirectoryUtil {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.importpaper.service.StartScanDirService#start(javax.sql.
	 * DataSource)
	 */
	public static void start(ScannerDirectoryConfig config, DataSource ds) {
		if (StringUtil.isEmpty(config.getUuId())) {
			String uuId = UUID.randomUUID().toString();
			config.setUuId(uuId);
			CountContainer<CutImageInfo> container = new CountContainer<>(10);
			CountContainerMgr.getInstance().put(uuId, container);
		}
		if (ds == null) {
			EasytntExecutor.instance().getExecutorService().submit(new StartScanDirNoSaveToDatabase(config));
		} else {
			EasytntExecutor.instance().getExecutorService().submit(new StartScanDirSaveToDatabase(config, ds));
		}
	}

}
