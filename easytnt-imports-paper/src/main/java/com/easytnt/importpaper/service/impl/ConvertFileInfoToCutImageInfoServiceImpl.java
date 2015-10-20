/**
 * 
 */
package com.easytnt.importpaper.service.impl;

import java.util.List;

import com.easytnt.commons.util.StringUtil;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.DirectoryMapping;
import com.easytnt.importpaper.bean.MappingName;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.service.ConvertFileInfoToCutImageInfoService;
import com.easytnt.importpaper.util.GetSubjectId;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ConvertFileInfoToCutImageInfoServiceImpl implements ConvertFileInfoToCutImageInfoService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.easytnt.importpaper.service.ConvertFileInfoToCutImageInfoService#
	 * convert(com.easytnt.importpaper.bean.ScannerDirectoryConfig,
	 * com.easytnt.importpaper.io.scanfiledir.FileInfo)
	 */
	@Override
	public CutImageInfo convert(ScannerDirectoryConfig config, FileInfo fileInfo) {
		int testId = config.getTestId();
		int paperId = 1;
		int roomType = 1;
		Long studentId = StringUtil.toLong(fileInfo.getFileName());
		String imagePath = fileInfo.getRelativelyURL();

		int kemuId = 0;
		long roomId = 0;
		long virtualroomId = 0;
		int itemId = 0;
		int diquId = 0;

		List<DirectoryMapping> dirMappings = config.getDirectoryMappings();
		for (DirectoryMapping dirMapping : dirMappings) {
			if (dirMapping.getMappingName().equals(MappingName.KM)) {
				kemuId = GetSubjectId.get(fileInfo.getName(dirMapping.getPlace()));
			} else if (dirMapping.getMappingName().equals(MappingName.KC)) {
				roomId = virtualroomId = StringUtil.toLong(fileInfo.getName(dirMapping.getPlace()));
			} else if (dirMapping.getMappingName().equals(MappingName.DQ)) {
				diquId = StringUtil.toInt(fileInfo.getName(dirMapping.getPlace()));
			} else if (dirMapping.getMappingName().equals(MappingName.TH)) {
				itemId = StringUtil.toInt(fileInfo.getName(dirMapping.getPlace()));
			}
		}

		CutImageInfo cutImageInfo = new CutImageInfo();
		cutImageInfo.setTestId(config.getTestId()).setPaperId(1).setRoomType(1).setKemuId(kemuId).setRoomId(roomId)
				.setVirtualroomId(virtualroomId).setItemId(itemId).setDiquId(diquId).setImagePath(imagePath);

		return cutImageInfo;
	}

}
