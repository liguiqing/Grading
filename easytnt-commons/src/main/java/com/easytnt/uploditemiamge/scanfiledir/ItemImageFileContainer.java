/**
 * 
 */
package com.easytnt.uploditemiamge.scanfiledir;

import java.util.ArrayList;

/**
 * @author liuyu
 *
 */
public class ItemImageFileContainer {
	private ArrayList<ItemImageFile> container = new ArrayList<>();
	private int fileNumber = 0;

	public void add(ItemImageFile itemImageFile) {
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
