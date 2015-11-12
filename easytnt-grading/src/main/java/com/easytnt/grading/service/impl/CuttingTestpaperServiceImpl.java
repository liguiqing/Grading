/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.Map;

import com.easytnt.commons.web.view.Progress;
import com.easytnt.commons.web.view.ProgressListener;
import com.easytnt.grading.service.CuttingTestpaperService;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingTestpaperServiceImpl implements CuttingTestpaperService, ProgressListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.grading.service.CuttingTestpaperService#cutting(long)
	 */
	@Override
	public void cutting(long paperId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Progress on(Map<String, String> params) {
		Long paperId = Long.parseLong(params.get("paperId"));
		return null;
	}

}
