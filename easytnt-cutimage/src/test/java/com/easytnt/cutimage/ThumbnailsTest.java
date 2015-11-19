/**
 * 
 */
package com.easytnt.cutimage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

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

	@Test
	public void test02() throws Exception {
		int[][] postions = new int[7][];
		postions[0] = new int[] { 132, 1178, 1049, 230 };
		postions[1] = new int[] { 122, 1350, 1069, 722 };
		postions[2] = new int[] { 1164, 191, 1025, 272 };
		postions[3] = new int[] { 1173, 431, 1010, 735 };
		postions[4] = new int[] { 1165, 1113, 1021, 1005 };
		postions[5] = new int[] { 2194, 1035, 1023, 528 };
		postions[6] = new int[] { 2190, 1592, 1018, 516 };

		// Thumbnails.of("D:/test/image/3.png").rotate(-90).size(800,
		// 800).outputFormat("png").toFile("");
		// Builder<File> builder = Thumbnails.of("D:/test/image/3.png");
		BufferedImage bufImage = ImageIO.read(new File("D:/test/image/3.png"));
		long b = System.currentTimeMillis();
		int idx = 0;
		for (int[] pos : postions) {
			Thumbnails.of(bufImage).sourceRegion(pos[0], pos[1], pos[2], pos[3]).size(pos[2], pos[3])
					.toFile("D:/test/image/1/" + idx++ + ".png");
		}
		long e = System.currentTimeMillis();
		System.out.println((e - b) + "ms");
	}

	@Test
	public void test03() throws Exception {
		long b = System.currentTimeMillis();
		// RenderedOp src2 = JAI.create("fileload", "D:/test/image/1.Tif");
		// OutputStream os2 = new FileOutputStream("D:/test/image/2.png");
		// JPEGEncodeParam param2 = new JPEGEncodeParam();
		// // 指定格式类型，jpg 属于 JPEG 类型
		// ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2,
		// param2);
		// enc2.encode(src2);
		// os2.close();
		long e = System.currentTimeMillis();

		BufferedImage bufImage = ImageIO.read(new File("D:/test/image/2.png"));
		Thumbnails.of(bufImage).rotate(-90).size(bufImage.getWidth(), bufImage.getHeight())
				.toFile("D:/test/image/2.png");
		System.out.println((e - b) + "ms");

	}
}
