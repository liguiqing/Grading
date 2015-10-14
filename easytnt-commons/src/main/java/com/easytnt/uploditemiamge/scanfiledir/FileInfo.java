/**
 * 
 */
package com.easytnt.uploditemiamge.scanfiledir;

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

	public FileInfo setRelativelyPath(String relativelyPath) {
		this.relativelyPath = relativelyPath;
		return this;
	}

}
