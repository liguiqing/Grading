/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.commons.web.view.Progress;
import com.easytnt.commons.web.view.ProgressListener;
import com.easytnt.grading.service.ScannerDirectoryService;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.util.StartScanDirectoryUtil;

/**
 * @author liuyu
 *
 */
@Service("ScannerDirectoryService")
public class ScannerDirectoryServiceImpl implements ScannerDirectoryService, ProgressListener {

	@Override
	public void scanDirectory(ScannerDirectoryConfig scannerDirectoryConfig, boolean saveToDb) {
		DataSource ds = null;
		if (saveToDb) {
			ds = SpringContextUtil.getBean("ds");
		}
		StartScanDirectoryUtil.start(scannerDirectoryConfig, ds);
	}

	@Override
	public Progress on(Map<String, String> params) {
		String uuid = params.get("uuId");
		String type = params.get("type");
		int fileSize = Integer.parseInt(params.get("fileSize"));
		int completed = Integer.parseInt(params.get("completed"));
		int total = 100;
		CountContainer<CutImageInfo> container = CountContainerMgr.getInstance().get(uuid);
		String text = container.getFileNumber() + "";
		if (type.equals("1")) {
			text = "已扫描" + text + "张图片";
			completed++;
			if (container.getIsOver()) {
				completed = 100;
			} else if (completed >= 100) {
				completed = 90;
			}
		} else {
			text = "已导入" + text + "张图片";
			completed = container.getFileNumber();
			total = fileSize;
		}

		Progress progress = new Progress(completed, total, text);
		return progress;
	}

}
