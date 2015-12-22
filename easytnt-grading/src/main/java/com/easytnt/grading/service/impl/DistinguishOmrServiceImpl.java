/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.easytnt.commons.web.view.Progress;
import com.easytnt.commons.web.view.ProgressListener;
import com.easytnt.grading.service.DistinguishOmrService;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Service("distinguishOmrService")
public class DistinguishOmrServiceImpl implements DistinguishOmrService, ProgressListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.easytnt.grading.service.DistinguishOmrService#startDistinguishOmr(
	 * java.lang.Long)
	 */
	@Override
	public void startDistinguishOmr(Long paperId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Progress on(Map<String, String> params) {
		String paperId = params.get("paperId");
		String key = "OMR:paperId:" + paperId;
		CountContainer<Integer> counterContainer = CountContainerMgr.getInstance().get(key);
		int totalNum = counterContainer.getTotalNum();
		int complateNum = counterContainer.getFileNumber();
		boolean isOver = counterContainer.getIsOver();
		String text = "正在扫描切割目录...";
		if (!isOver && totalNum == 0) {
			totalNum = 100;
			complateNum = 20;
		} else {
			text = "正在识别 " + complateNum + "/" + totalNum;
		}

		if (isOver) {
			CountContainerMgr.getInstance().remove(key);
		}
		Progress progress = new Progress(complateNum, totalNum, text);
		return progress;
	}

}
