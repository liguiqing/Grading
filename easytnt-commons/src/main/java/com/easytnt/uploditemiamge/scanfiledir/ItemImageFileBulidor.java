/**
 * 
 */
package com.easytnt.uploditemiamge.scanfiledir;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liuyu
 *
 */
public class ItemImageFileBulidor {
	private Path rootDir;
	private int dirDepth;

	public ItemImageFileBulidor(String rootDir) {
		this.rootDir = Paths.get(rootDir);
		this.dirDepth = this.rootDir.getNameCount();
	}

	public ItemImageFile building(String path) {
		return building(Paths.get(path));
	}

	public ItemImageFile building(Path path) {
		int pathDepath = path.getNameCount();
		String relativelyPath = path.subpath(dirDepth, pathDepath).toString();

		ItemImageFile itemImageFile = new ItemImageFile();
		itemImageFile.setRootDir(rootDir.toString()).setRelativelyPath(relativelyPath);
		return itemImageFile;
	}

}
