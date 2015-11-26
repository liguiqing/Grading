/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.commons.web.view.Progress;
import com.easytnt.commons.web.view.ProgressListener;
import com.easytnt.cutimage.utils.StartCuttingTestpaper;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.service.CuttingTestpaperService;
import com.easytnt.grading.service.CuttingsSolutionService;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.thread.EasytntExecutor;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Service("CuttingTestpaperService")
public class CuttingTestpaperServiceImpl implements CuttingTestpaperService, ProgressListener {

	@Autowired(required = false)
	private CuttingsSolutionService cuttingsSolutionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.grading.service.CuttingTestpaperService#cutting(long)
	 */
	@Override
	public void cutting(long paperId) {
		DataSource ds = SpringContextUtil.getBean("ds");
		CuttingsSolution cuttingsSolution = cuttingsSolutionService.getCuttingsSolutionWithPaperId(paperId);
		StartCuttingTestpaper cuttingService = new StartCuttingTestpaper(cuttingsSolution, ds);
		EasytntExecutor.instance().getExecutorService().submit(cuttingService);
	}

	@Override
	public Progress on(Map<String, String> params) {
		String paperId = params.get("paperId");
		String key = "paperId:" + paperId;
		CountContainer<Integer> counterContainer = CountContainerMgr.getInstance().get(key);
		int totalNum = counterContainer.getTotalNum();
		int complateNum = counterContainer.getFileNumber();
		boolean isOver = counterContainer.getIsOver();
		String text = "正在扫描切割目录...";
		if (!isOver && totalNum == 0) {
			totalNum = 100;
			complateNum = 20;
		} else {
			text = "正在切割 " + complateNum + "/" + totalNum;
		}

		if (isOver) {
			CountContainerMgr.getInstance().remove(key);
		}
		// int completed = Integer.parseInt(params.get("completed"));

		Progress progress = new Progress(complateNum, totalNum, text);
		return progress;
	}

}
