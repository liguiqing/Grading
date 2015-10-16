/**
 * 
 */
package com.easytnt.io.scanfiledir;

import java.util.ArrayList;

/**
 * @author liuyu
 *
 */
public class FileInfoContainer {
	private ArrayList<FileInfo> container = new ArrayList<>();
	private int fileNumber = 0;

	public void add(FileInfo itemImageFile) {
		container.add(itemImageFile);
		count();
	}

	public void count() {
		fileNumber++;
	}

	public int getFileNumber() {
		return fileNumber;
	}
}
