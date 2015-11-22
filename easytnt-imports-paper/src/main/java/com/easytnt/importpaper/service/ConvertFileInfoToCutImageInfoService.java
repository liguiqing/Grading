/**
 * 
 */
package com.easytnt.importpaper.service;

import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;

/**
 * <pre>
 * 转换文件为切割图片信息
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface ConvertFileInfoToCutImageInfoService {
	public CutImageInfo convert(ScannerDirectoryConfig config, FileInfo fileInfo);
}
