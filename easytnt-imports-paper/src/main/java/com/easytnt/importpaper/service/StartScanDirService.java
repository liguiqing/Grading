/**
 * 
 */
package com.easytnt.importpaper.service;

import javax.sql.DataSource;

import com.easytnt.importpaper.bean.ScannerDirectoryConfig;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface StartScanDirService {
	public void start(ScannerDirectoryConfig config, DataSource ds);
}
