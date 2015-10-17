/**
 * 
 */
package com.easytnt.bean;

import java.util.List;

/**
 * @author liuyu
 *
 */
public class ScannerDirectoryConfig {
	private String rootUrl;
	private String fileDir;
	private List<DirectoryMapping> directoryMappings;

	public String getRootUrl() {
		return rootUrl;
	}

	public ScannerDirectoryConfig setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
		if (!this.rootUrl.endsWith("/")) {
			this.rootUrl += "/";
		}
		return this;
	}

	public String getFileDir() {
		return fileDir;
	}

	public ScannerDirectoryConfig setFileDir(String fileDir) {
		this.fileDir = fileDir;
		return this;
	}

	public List<DirectoryMapping> getDirectoryMappings() {
		return directoryMappings;
	}

	public ScannerDirectoryConfig setDirectoryMappings(List<DirectoryMapping> directoryMappings) {
		this.directoryMappings = directoryMappings;
		return this;
	}

}
