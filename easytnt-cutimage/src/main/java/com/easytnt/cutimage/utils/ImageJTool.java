/**
 * 
 */
package com.easytnt.cutimage.utils;

import com.easytnt.grading.domain.share.Area;

import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.filter.Transformer;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ImageJTool {
	/**
	 * <pre>
	 * 转换图片
	 * </pre>
	 *
	 * @return void
	 * @autor liuyu
	 * @datatime 2015年12月20日 下午6:26:05
	 * @param imp
	 * @param command
	 */
	public static void transformer(ImagePlus imp, String command) {
		Transformer transformer = new Transformer();
		transformer.setup(command, imp);
		transformer.run(imp.getProcessor());
	}

	public static Roi createRoi(Area area) {
		Roi roi = new Roi(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight());
		return roi;
	}
}
