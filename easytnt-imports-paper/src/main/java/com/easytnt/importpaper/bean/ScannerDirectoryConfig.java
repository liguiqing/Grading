/**
 * 
 */
package com.easytnt.importpaper.bean;

import java.util.List;

/**
 * @author liuyu
 *
 */
public class ScannerDirectoryConfig {
	private String uuId;
	private int testId;
	private String rootUrl;
	private String fileDir;
	private List<DirectoryMapping> directoryMappings;

	public String getRootUrl() {
		return rootUrl;
	}

	public int getTestId() {
		return testId;
	}

	public ScannerDirectoryConfig setTestId(int testId) {
		this.testId = testId;
		return this;
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

	public String getUuId() {
		return uuId;
	}

	public ScannerDirectoryConfig setUuId(String uuId) {
		this.uuId = uuId;
		return this;
	}

}
