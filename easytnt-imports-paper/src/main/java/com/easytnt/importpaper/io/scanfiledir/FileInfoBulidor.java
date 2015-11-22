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
public class FileInfoBulidor {
	private Path rootDir;
	private int dirDepth;

	public FileInfoBulidor(String rootDir) {
		this.rootDir = Paths.get(rootDir);
		this.dirDepth = this.rootDir.getNameCount();
	}

	public FileInfo building(String path) {
		return building(Paths.get(path));
	}

	public FileInfo building(Path path) {
		int pathDepath = path.getNameCount();
		String relativelyPath = path.subpath(dirDepth - 1, pathDepath).toString();
		String rootDir = path.subpath(0, dirDepth - 1).toString();

		FileInfo itemImageFile = new FileInfo();
		itemImageFile.setRootDir(rootDir).setRelativelyPath(relativelyPath).setFilePath(path);
		return itemImageFile;
	}

}
