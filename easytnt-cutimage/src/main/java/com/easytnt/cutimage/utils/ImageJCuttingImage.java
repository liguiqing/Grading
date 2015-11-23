/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.share.Area;
import com.easytnt.importpaper.bean.CutImageInfo;

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
public class ImageJCuttingImage extends BaseCuttingImage {

	private ArrayList<ImagePlus> imps = new ArrayList<>();

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
			imps.add(imp);
		}
	}

	private void rotateImages() {
		List<AnswerCardCuttingTemplate> templateInfos = event.getCuttingsSolution().getDesignFor()
				.getAnswerCardCuttingTemplates();
		for (AnswerCardCuttingTemplate templateInfo : templateInfos) {
			ImagePlus imp = imps.get(templateInfo.getIndex());
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
		List<CuttingsArea> cutTo = event.getCuttingsSolution().getCutTo();
		for (CuttingsArea cuttingsArea : cutTo) {
			String saveFilePath = createSaveFilePath(cuttingsArea.getId());
			cuttingImage(cuttingsArea, saveFilePath);
			cutImageInfos.add(createCutImageInfo(cuttingsArea.getId()));
		}
		event.setCutImageInfos(cutImageInfos);
	}

	private void cuttingImage(CuttingsArea cuttingsArea, String saveFilePath) {
		ImagePlus imp = imps.get(cuttingsArea.getAnswerCardImageIdx());
		Roi roi = createRoi(cuttingsArea);
		imp.setRoi(roi);
		cutAndSaveFile(imp, saveFilePath);
	}

	private void cutAndSaveFile(ImagePlus imp, String saveFilePath) {
		Duplicator duplicator = new Duplicator();
		ImagePlus imp2 = duplicator.run(imp);
		IJ.saveAs(imp2, "png", saveFilePath);
	}

	private Roi createRoi(CuttingsArea cuttingsArea) {
		Area areaInPaper = cuttingsArea.getAreaInPaper();
		Roi roi = new Roi(areaInPaper.getLeft(), areaInPaper.getTop(), areaInPaper.getWidth(), areaInPaper.getHeight());
		return roi;
	}

}
