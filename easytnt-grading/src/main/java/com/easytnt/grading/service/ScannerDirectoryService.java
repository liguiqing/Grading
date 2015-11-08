/**
 * 
 */
package com.easytnt.grading.service;

import com.easytnt.importpaper.bean.ScannerDirectoryConfig;

/**
 * @author liuyu
 *
 */
public interface ScannerDirectoryService {
	/**
	 * <pre>
	 * 扫描屠屏
	 * </pre>
	 *
	 * @return void
	 * @autor liuyu
	 * @datatime 2015年10月17日 上午10:57:07
	 * @param scannerDirectoryConfig
	 */
	public void scanDirectory(ScannerDirectoryConfig scannerDirectoryConfig, boolean saveToDb);
}
