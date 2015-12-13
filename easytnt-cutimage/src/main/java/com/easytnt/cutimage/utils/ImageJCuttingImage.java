/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.share.Area;
import com.easytnt.importpaper.bean.CutImageInfo;

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
public class ImageJCuttingImage extends BaseCuttingImage {

	private ArrayList<ImagePlus> sourceImageImps = new ArrayList<>();

	public ImageJCuttingImage(StudentTestPaperAnswerCardEvent event) {
		super(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.cutimage.utils.BaseCuttingImage#cutting()
	 */
	@Override
	public void cutting() throws Exception {
		loadImagePlus();
		rotateImages();
		executeCutting();
	}

	private void loadImagePlus() {
		List<String> filePaths = event.getFilePaths();
		for (String filePath : filePaths) {
			ImagePlus imp = IJ.openImage(filePath);
			sourceImageImps.add(imp);
		}
	}

	private void rotateImages() {
		List<AnswerCardCuttingTemplate> templateInfos = event.getCuttingsSolution().getPaper()
				.getAnswerCardCuttingTemplates();
		for (AnswerCardCuttingTemplate templateInfo : templateInfos) {
			ImagePlus imp = sourceImageImps.get(templateInfo.getIndex());
			String command = "left";
			if (templateInfo.getRotate() > 0) {
				command = "right";
			}
			transformer(imp, command);
		}
	}

	private void transformer(ImagePlus imp, String command) {
		Transformer transformer = new Transformer();
		transformer.setup(command, imp);
		transformer.run(imp.getProcessor());
	}

	private void executeCutting() throws Exception {
		ArrayList<CutImageInfo> cutImageInfos = new ArrayList<>();
		List<CuttingBlock> cuttingBlocks = event.getCuttingsSolution().getCuttingBlocks();
		for (CuttingBlock cutBlock : cuttingBlocks) {
			String saveFilePath = createSaveFilePath(cutBlock.getKey());
			cuttingImageAndSave(cutBlock, saveFilePath);
			cutImageInfos.add(createCutImageInfo(cutBlock.getKey()));
		}
		event.setCutImageInfos(cutImageInfos);
	}

	private void cuttingImageAndSave(CuttingBlock cutBlock, String saveFilePath) {
		List<ImagePlus> imps = cutting(cutBlock);
		ImagePlus imp = mergeImage(cutBlock, imps);
		saveFile(imp, saveFilePath);
	}

	private List<ImagePlus> cutting(CuttingBlock cutBlock) {
		List<CuttingDefine> cuttingDefines = cutBlock.getCuttingDefines();
		ArrayList<ImagePlus> imps = new ArrayList<>();
		for (CuttingDefine cutDef : cuttingDefines) {
			ImagePlus imp = sourceImageImps.get(cutDef.getAnswerCardImageIdx());
			Roi roi = createRoi(cutDef);
			ImagePlus beCutImp = cutImage(imp, roi);
			imps.add(beCutImp);
		}
		return imps;
	}

	private Roi createRoi(CuttingDefine cutDef) {
		Area area = cutDef.getArea();
		Roi roi = new Roi(area.getLeft(), area.getTop(), area.getWidth(), area.getHeight());
		return roi;
	}

	private ImagePlus cutImage(ImagePlus imp, Roi roi) {
		imp.setRoi(roi);
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		return imp2;
	}

	private ImagePlus mergeImage(CuttingBlock cutBlock, List<ImagePlus> imps) {
		if (imps.size() == 1) {
			return imps.get(0);
		}
		BufferedImage bufferedImage = new BufferedImage(cutBlock.getWidth(), cutBlock.getHeight(), 1);
		ImagePlus resultImp = new ImagePlus("", bufferedImage);
		int y = 0;
		for (ImagePlus imp : imps) {
			resizeImage(cutBlock, imp);
			resultImp.getProcessor().insert(imp.getProcessor(), 0, y);
			y += imp.getHeight();
		}
		return resultImp;
	}

	private void resizeImage(CuttingBlock cutBlock, ImagePlus imp) {
		if (imp.getWidth() < cutBlock.getWidth()) {
			ImageProcessor ip = imp.getProcessor().resize(cutBlock.getWidth(), imp.getHeight());
			imp.setProcessor(ip);
		}
	}

	private void saveFile(ImagePlus imp, String saveFilePath) {
		IJ.saveAs(imp, "png", saveFilePath);
	}

}
