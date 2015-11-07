/**
 * 
 */
package com.easytnt.importpaper.service;

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

	public void clear();
}
