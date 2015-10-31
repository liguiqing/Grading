/**
 * 
 */
package com.easytnt.cutimage;

import org.junit.Test;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ThumbnailsTest {

	@Test
	public void test01() throws Exception {
		Thumbnails.of("D:/test/dtk/1.jpg").sourceRegion(21, 225, 320, 106).size(320, 106).keepAspectRatio(false)
				.toFile("D:/test/dtk/myTest.jpg");
	}
}
