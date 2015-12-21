/**
 * 
 */
package com.easytnt.cutimage;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.cutimage.utils.DistinguishOmr;
import com.easytnt.cutimage.utils.ImageJTool;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.OmrDefine;
import com.easytnt.grading.domain.cuttings.SelectItemArea;
import com.easytnt.grading.domain.cuttings.SelectItemDefine;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.share.Area;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.Duplicator;
import ij.plugin.filter.Transformer;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class DiscernImageTest {

	@Test
	public void test() throws Exception {

		ArrayList<Area> areas = new ArrayList<>();

		List<String> lines = FileUtils.readLines(new File("D:/test/Overlay Elements.xls"));
		int size = lines.size();
		int idx = 0;
		for (String line : lines) {
			idx++;
			if (idx == 1 || idx == size) {
				continue;
			}
			String[] fileds = line.split("\t");
			Area area = new Area(Integer.parseInt(fileds[3]), Integer.parseInt(fileds[4]), Integer.parseInt(fileds[5]),
					Integer.parseInt(fileds[6]));
			areas.add(area);
			System.out.println(area);
		}

		ImagePlus imp = IJ.openImage("d:/test/2.tif");

		Transformer transformer = new Transformer();
		transformer.setup("left", imp);
		transformer.run(imp.getProcessor());

		// imp.show();
		ImageProcessor ip = imp.getProcessor();

		for (Area area : areas) {
			Roi roi = toRoi(area);
			// ip.setRoi(roi);
			imp.setRoi(roi);
			ImageStatistics stat = ip.getStatistics();
			int[] k = stat.histogram;

			int sum = 0;
			for (int i = 128; i < 256; i++) {
				sum += k[i];
			}

			System.out.println(1.0 * sum / stat.longPixelCount);
			ip.draw(roi);
			// imp.show();

			ImagePlus imp2 = cutting(imp);
			// imp2.show();
			System.out.println(stat);

			for (int i = area.getLeft(); i < area.getLeft() + area.getWidth(); i++) {
				for (int j = area.getTop(); j < area.getTop() + area.getHeight(); j++) {
					System.out.print(ip.get(i, j) + "\t");
					int[] m = imp.getPixel(i, j);
					System.out.print("");
				}
				System.out.println();
			}
		}

		System.out.println();
	}

	private Roi toRoi(Area area) {
		Roi roi = new Roi(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight());
		return roi;
	}

	private ImagePlus cutting(ImagePlus imp) {
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		return imp2;
	}

	@Test
	public void test02() throws Exception {
		System.out.println(255 & 255);
		System.out.println(256 >> 1);
	}

	@Test
	public void test03() throws Exception {
		Double value = 12.0500;
		String valueStr = value.toString();

		int j = valueStr.indexOf(".");

		int idx = 0;
		for (int i = valueStr.length() - 1; i >= 0; i--) {
			char c = valueStr.charAt(i);
			if ('0' != c || '.' == c) {
				idx = i + 1;
				break;
			}
		}
		String k = valueStr.substring(0, idx);
		System.out.println();
	}

	@Test
	public void test04() throws Exception {
		ImagePlus imp = IJ.openImage("d:/test/2.tif");

		ImageProcessor ip = imp.getProcessor();
		ip.setColor(Color.BLACK);
		ImageJTool.transformer(imp, "left");

		ip = imp.getProcessor();

		imp.show();
		List<SelectItemDefine> items = MockSelectItem.items();
		for (SelectItemDefine item : items) {
			List<SelectItemArea> areas = item.getAreas();
			for (SelectItemArea area : areas) {
				Roi roi = new Roi(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight());
				ip.drawRoi(roi);
			}
		}

		imp.show();

		System.out.println();
	}

	private List<Roi> createRoi(int x, int y, int wStep, int w, int h) {
		ArrayList<Roi> result = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int newX = x + (i * wStep);
			Roi roi = new Roi(newX, y, w, h);
			result.add(roi);
		}
		return result;
	}

	@Test
	public void testDistinguish() throws Exception {
		ExamPaper paper = new ExamPaper();
		paper.setPaperId(3L);
		List<AnswerCardCuttingTemplate> tmplates = new ArrayList<>();
		paper.setAnswerCardCuttingTemplates(tmplates);
		AnswerCardCuttingTemplate template = new AnswerCardCuttingTemplate();
		template.setRotate(-90);
		tmplates.add(template);

		List<SelectItemDefine> items = MockSelectItem.items();
		ArrayList<String> filepaths = new ArrayList<>();
		filepaths.add("d:/test/2.tif");

		OmrDefine omrDefine = new OmrDefine();
		omrDefine.setPaper(paper).setSelectItemDefines(items);

		DistinguishOMREvent event = new DistinguishOMREvent();
		event.setStudentId("11111111111").setOmrDefine(omrDefine).setFilePaths(filepaths);
		DistinguishOmr omr = new DistinguishOmr(event);
		omr.distinguish();
		System.out.println();
	}
}
