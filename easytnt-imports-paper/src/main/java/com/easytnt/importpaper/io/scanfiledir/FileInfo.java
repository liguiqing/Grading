/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liuyu
 *
 */
public class FileInfo {

	private Path rootDir;
	private Path relativelyPath;
	private Path filePath;

	public Path getFilePath() {
		return filePath;
	}

	public FileInfo setFilePath(Path filePath) {
		this.filePath = filePath;
		return this;
	}

	public String getRootDir() {
		return rootDir.toString();
	}

	public FileInfo setRootDir(String rootDir) {
		this.rootDir = Paths.get(rootDir);
		return this;
	}

	public String getRelativelyPath() {
		return relativelyPath.toString();
	}

	public String getRelativelyURL() {
		return relativelyPath.toString().replace("\\", "/");
	}

	public FileInfo setRelativelyPath(String relativelyPath) {
		this.relativelyPath = Paths.get(relativelyPath);
		return this;
	}

	public String getFileName() {
		String fileName = relativelyPath.getFileName().toString();
		int idx = fileName.indexOf(".");
		fileName = idx != -1 ? fileName.substring(0, idx) : fileName;
		return fileName;
	}

	public String getName(int index) {
		return relativelyPath.getName(index).toString();
	}

	@Override
	public String toString() {
		return filePath.toString();
	}

}
