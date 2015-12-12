/**
 * 
 */
package com.easytnt.cutimage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.Duplicator;
import ij.plugin.filter.Transformer;
import ij.process.ImageProcessor;

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

	@Test
	public void mergerImage() throws Exception {
		ImagePlus imp = IJ.openImage(sourceImage);
		Roi roi = new Roi(0, 0, 500, 500);
		imp.setRoi(roi);
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		roi = new Roi(0, 0, 500, 500);
		ImagePlus imp3 = duplicator.run(imp);

		BufferedImage bufferedImage = new BufferedImage(500, 1000, 1);

		ImagePlus imp5 = new ImagePlus("", bufferedImage);
		imp5.getProcessor().insert(imp2.getProcessor(), 0, 0);
		imp5.getProcessor().insert(imp5.getProcessor(), 0, 500);

		IJ.saveAs(imp5, "png", "d:/test/a.png");

	}

	@Test
	public void resizeImage() throws Exception {
		ImagePlus imp = IJ.openImage(sourceImage);
		Roi roi = new Roi(0, 0, 1000, 500);
		imp.setRoi(roi);
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		ImageProcessor tmp = imp2.getProcessor().resize(2000, 500, false);
		imp2.setProcessor(tmp);
		IJ.saveAs(imp2, "png", "d:/test/a.png");
	}

	public void scale() throws Exception {
		ImagePlus imp = IJ.openImage(sourceImage);
		Roi roi = new Roi(0, 0, 1000, 500);
		imp.setRoi(roi);
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		ImageProcessor ip = imp2.getProcessor();

	}
}
