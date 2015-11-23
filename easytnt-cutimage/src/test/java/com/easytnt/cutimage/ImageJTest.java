/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.Duplicator;
import ij.plugin.filter.Transformer;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ImageJTest {
	private String sourceImage = "d:/test/1.Tif";
	private String saveImage = "d:/test/2.png";

	@Test
	public void testTransformer() throws Exception {
		ImagePlus imp = IJ.openImage(sourceImage);
		transformer(imp);
		IJ.saveAs(imp, "png", saveImage);
	}

	private void transformer(ImagePlus imp) {
		Transformer transformer = new Transformer();
		transformer.setup("left", imp);
		transformer.run(imp.getProcessor());
	}

	@Test
	public void testCut() throws Exception {
		ImagePlus imp = IJ.openImage(sourceImage);
		transformer(imp);

		List<Roi> rois = getRois();
		int i = 1;
		for (Roi roi : rois) {
			imp.setRoi(roi);
			cut(imp, "d:/test/" + (i++) + ".png");
		}
	}

	private void cut(ImagePlus imp, String saveFilePath) {
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		IJ.saveAs(imp2, "png", saveFilePath);
	}

	private List<Roi> getRois() {
		ArrayList<Roi> rois = new ArrayList<>();
		Roi roi = new Roi(0, 0, 1000, 500);
		rois.add(roi);
		roi = new Roi(0, 500, 1000, 500);
		rois.add(roi);
		return rois;
	}
}
