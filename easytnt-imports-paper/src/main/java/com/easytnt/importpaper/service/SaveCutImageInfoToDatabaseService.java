/**
 * 
 */
package com.easytnt.importpaper.service;

import java.util.List;

import com.easytnt.importpaper.bean.CutImageInfo;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface SaveCutImageInfoToDatabaseService {
	public void save(CutImageInfo cutImageInfo);

	public void save(List<CutImageInfo> cutImageInfos);

	public void clear();
}
