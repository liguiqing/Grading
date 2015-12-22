/**
 * 
 */
package com.easytnt.grading.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easytnt.cutimage.utils.StartDistinguishOmr;
import com.easytnt.grading.domain.cuttings.OmrDefine;
import com.easytnt.grading.domain.cuttings.SelectItemArea;
import com.easytnt.grading.domain.cuttings.SelectItemDefine;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.filter.Transformer;
import ij.process.ImageProcessor;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-app.xml",
		"classpath:applicationContext-ds.xml", "classpath:applicationContext-tx.xml" })
public class DistinguishOmrServiceTest {

	@Autowired(required = false)
	private CuttingsSolutionService cuttingsSolutionService;

	// DistinguishOmr
	@Test
	public void testStartDistinguishOmr() throws Exception {
		OmrDefine omrDefine = cuttingsSolutionService.listSelectItemDefines(4L);
		StartDistinguishOmr starter = new StartDistinguishOmr(omrDefine, null);
		starter.setTest(true);
		starter.run();
		System.out.println();
	}

	@Test
	public void viewImage() throws Exception {
		OmrDefine omrDefine = cuttingsSolutionService.listSelectItemDefines(4L);

		String file = "D:/test/sx/04011300/00000009.Tif";
		ImagePlus imp = IJ.openImage(file);
		Transformer transformer = new Transformer();
		transformer.setup("left", imp);
		transformer.run(imp.getProcessor());

		ImageProcessor ip = imp.getProcessor();

		for (SelectItemDefine item : omrDefine.getSelectItemDefines()) {
			List<SelectItemArea> areas = item.getAreas();
			for (SelectItemArea area : areas) {
				Roi roi = new Roi(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight());
				ip.drawRoi(roi);
			}
		}

		imp.show();
		System.out.println();
	}
}
