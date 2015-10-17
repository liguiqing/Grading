/**
 * 
 */
package com.easytnt.importpaper.util;

import java.util.List;

import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.DirectoryMapping;
import com.easytnt.importpaper.bean.MappingName;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CutImageUtil {

	public static CutImageInfo convertFileInfo(ScannerDirectoryConfig config, FileInfo fileInfo) {
		CutImageInfo cutImageInfo = new CutImageInfo();
		cutImageInfo.setPaperId(config.getPaperId()).setStudentId(Long.parseLong(fileInfo.getFileName()))
				.setImagePath(config.getRootUrl() + fileInfo.getRelativelyURL());

		List<DirectoryMapping> dirMappings = config.getDirectoryMappings();

		for (DirectoryMapping dirMapping : dirMappings) {
			if (dirMapping.getMappingName().equals(MappingName.KM)) {
				cutImageInfo.setKemuId(getSubjectId(dirMapping, fileInfo));
			} else if (dirMapping.getMappingName().equals(MappingName.KC)) {
				cutImageInfo.setVirtualroomId(getMappingNameValueLog(dirMapping, fileInfo));
			} else if (dirMapping.getMappingName().equals(MappingName.DQ)) {

			} else if (dirMapping.getMappingName().equals(MappingName.TH)) {
				cutImageInfo.setItemId(getMappingNameValueInt(dirMapping, fileInfo));
			}
		}
		return cutImageInfo;
	}

	private static int getMappingNameValueInt(DirectoryMapping dirMapping, FileInfo fileInfo) {
		String value = fileInfo.getName(dirMapping.getPlace());
		return Integer.parseInt(value);
	}

	private static Long getMappingNameValueLog(DirectoryMapping dirMapping, FileInfo fileInfo) {
		String value = fileInfo.getName(dirMapping.getPlace());
		return Long.parseLong(value);
	}

	private static int getSubjectId(DirectoryMapping dirMapping, FileInfo fileInfo) {
		String value = fileInfo.getName(dirMapping.getPlace());
		// todo
		return 1;
	}
}
