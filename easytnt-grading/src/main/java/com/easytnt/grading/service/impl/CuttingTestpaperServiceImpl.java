/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.commons.web.view.Progress;
import com.easytnt.commons.web.view.ProgressListener;
import com.easytnt.cutimage.utils.CuttingBlockBuilder;
import com.easytnt.cutimage.utils.StartCuttingTestpaper;
import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
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
	@Transactional(readOnly = false)
	public void cutting(CuttingSolution cuttingSolution) {
		DataSource ds = SpringContextUtil.getBean("ds");
		CuttingBlockBuilder blockBuilder = new CuttingBlockBuilder(cuttingSolution);
		List<CuttingBlock> cuttingBlocks = blockBuilder.toBuild();
		cuttingsSolutionService.saveCuttingAreaes(cuttingSolution.getPaper(), cuttingBlocks);
		cuttingSolution.setCuttingBlocks(cuttingBlocks);

		StartCuttingTestpaper cuttingService = new StartCuttingTestpaper(cuttingSolution, ds);
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
