/**
 * 
 */
package com.easytnt.cutimage.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.disruptor.event.DistinguishOMREvent;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.OmrResult;
import com.easytnt.grading.domain.cuttings.SelectItemArea;
import com.easytnt.grading.domain.cuttings.SelectItemDefine;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class DistinguishOmr {
	private Logger log = LoggerFactory.getLogger(DistinguishOmr.class);
	private DistinguishOMREvent event;
	private ArrayList<ImagePlus> sourceImageImps = new ArrayList<>();

	public DistinguishOmr(DistinguishOMREvent event) {
		this.event = event;
	}

	private void loadImagePlus() {
		List<String> filePaths = event.getFilePaths();
		for (String filePath : filePaths) {
			ImagePlus imp = IJ.openImage(filePath);
			sourceImageImps.add(imp);
		}
	}

	private void rotateImages() {
		List<AnswerCardCuttingTemplate> templateInfos = event.getOmrDefine().getPaper().getAnswerCardCuttingTemplates();
		for (AnswerCardCuttingTemplate templateInfo : templateInfos) {
			ImagePlus imp = sourceImageImps.get(templateInfo.getIndex());
			String command = "left";
			if (templateInfo.getRotate() > 0) {
				command = "right";
			}
			ImageJTool.transformer(imp, command);
		}
	}

	public void distinguish() {
		loadImagePlus();
		rotateImages();
		List<SelectItemDefine> items = event.getOmrDefine().getSelectItemDefines();

		StringBuffer selectedOptionBuffer = new StringBuffer();
		StringBuffer scoreBuffer = new StringBuffer();
		Float kgScore = 0f;

		for (SelectItemDefine item : items) {
			String selectedOption = distinguishItem(item);
			Float score = calcualteScore(item, selectedOption);
			kgScore += score;
			selectedOptionBuffer.append(selectedOption).append(",");
			scoreBuffer.append(NumberFormat.clearZero(score)).append(",");
		}
		if (selectedOptionBuffer.length() > 0) {
			selectedOptionBuffer.deleteCharAt(selectedOptionBuffer.length() - 1);
		}
		if (scoreBuffer.length() > 0) {
			scoreBuffer.deleteCharAt(scoreBuffer.length() - 1);
		}

		OmrResult omrResult = new OmrResult();
		omrResult.setPaperId(event.getOmrDefine().getPaper().getPaperId()).setKgScore(kgScore)
				.setStudentId(Long.parseLong(event.getStudentId())).setOmrStr(selectedOptionBuffer.toString())
				.setOmrScore(scoreBuffer.toString());
		event.setOrmResult(omrResult);

		log.debug(omrResult.toString());
	}

	private Float calcualteScore(SelectItemDefine item, String selectedOption) {
		if (item.isSingleSelect()) {
			if (selectedOption.equals(item.getAnswer())) {
				return item.getFullScore();
			} else {
				return 0f;
			}
		} else {
			float score = 0f;
			int idx = item.getGiveScoreRule().indexOf(item);
			if (idx != -1) {
				score = item.getGiveScoreRuleScore().get(idx);
			}
			return score;
		}
	}

	private String distinguishItem(SelectItemDefine item) {
		ImagePlus imp = sourceImageImps.get(item.getTemplateIndex());
		ImageProcessor ip = imp.getProcessor();
		List<SelectItemArea> areas = item.getAreas();
		StringBuffer selectedOption = new StringBuffer();
		ArrayList<ImageStatistics> stats = new ArrayList<>();
		for (SelectItemArea area : areas) {
			Roi roi = ImageJTool.createRoi(area);
			ip.setRoi(roi);
			ImageStatistics stat = ip.getStatistics();
			stats.add(stat);
			if (isSelected(stat)) {
				selectedOption.append(area.getSelectOption());
			}
		}

		if (selectedOption.length() == 0) {
			int idx = findMax(stats);
			if (idx == -1) {
				selectedOption.append("#");
			} else {
				selectedOption.append(areas.get(idx).getSelectOption());
			}

		}
		return selectedOption.toString();
	}

	private int findMax(final List<ImageStatistics> stats) {
		int idx = -1;
		double max = 0;
		for (int i = 0; i < stats.size(); i++) {
			double mean = stats.get(i).mean;
			if (mean > max) {
				max = mean;
				idx = i;
			}
		}
		return idx;
	}

	private boolean isSelected(ImageStatistics stat) {
		int min = 100;

		if (stat.mean < min) {
			return false;
		}
		int[] h = stat.histogram;
		int sum = 0;
		for (int i = min; i < 256; i++) {
			sum += h[i];
		}

		if (1.0 * sum / stat.longPixelCount < 0.5) {
			return false;
		}
		return true;
	}
}
