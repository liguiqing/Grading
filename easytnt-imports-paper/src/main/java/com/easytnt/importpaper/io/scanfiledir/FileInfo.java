/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

/**
 * @author liuyu
 *
 */
public class FileInfo {

	private String rootDir;
	private String relativelyPath;

	public String getRootDir() {
		return rootDir;
	}

	public FileInfo setRootDir(String rootDir) {
		this.rootDir = rootDir;
		return this;
	}

	public String getRelativelyPath() {
		return relativelyPath;
	}

	public String getRelativelyURL() {
		return relativelyPath.replace("\\", "/");
	}

	public FileInfo setRelativelyPath(String relativelyPath) {
		this.relativelyPath = relativelyPath;
		return this;
	}

}
