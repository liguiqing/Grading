/**
 * 
 */
package com.easytnt.uploditemiamge.scanfiledir;

/**
 * @author liuyu
 *
 */
public class ItemImageFile {

	private String rootDir;
	private String relativelyPath;

	public String getRootDir() {
		return rootDir;
	}

	public ItemImageFile setRootDir(String rootDir) {
		this.rootDir = rootDir;
		return this;
	}

	public String getRelativelyPath() {
		return relativelyPath;
	}

	public ItemImageFile setRelativelyPath(String relativelyPath) {
		this.relativelyPath = relativelyPath;
		return this;
	}

}
