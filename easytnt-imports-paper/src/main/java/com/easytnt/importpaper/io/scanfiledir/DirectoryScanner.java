/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.io.IOException;

import com.easytnt.importpaper.bean.CountContainer;

/**
 * @author T440P
 *
 */
public interface DirectoryScanner {
	/**
	 * 扫描文件路径
	 * 
	 * @return
	 * @throws IOException
	 */
	public CountContainer<FileInfo> scan() throws IOException;

	/**
	 * 扫描文件路径
	 * 
	 * @param visitorFile
	 *            回调函数
	 * @throws IOException
	 */
	public void scan(VisitorFile visitorFile) throws IOException;
}
