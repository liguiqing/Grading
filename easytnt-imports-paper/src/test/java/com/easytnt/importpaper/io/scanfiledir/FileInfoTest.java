/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class FileInfoTest {

	@Test
	public void testFileName() throws Exception {
		String root = "D:/sj";
		String relativelyPath = "lishu/23/1060258300/1/1060258300011.jpg";

		FileInfo fileInfo = new FileInfo().setRootDir(root).setRelativelyPath(relativelyPath);

		Assert.assertTrue(fileInfo.getFileName().equals("1060258300011"));

	}
}
