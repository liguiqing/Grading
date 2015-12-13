/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.easytnt.cutimage.disruptor.event.StudentTestPaperAnswerCardEvent;
import com.easytnt.cutimage.utils.CuttingBlockBuilder;
import com.easytnt.cutimage.utils.ImageJCuttingImage;
import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ImageJCuttingImageTest {

	@Test
	public void testCutting() throws Exception {

		CuttingSolution cuttingsSolution = MockCuttingsSolution.cuttingsSolution();
		CuttingBlockBuilder builder = new CuttingBlockBuilder(cuttingsSolution);
		List<CuttingBlock> cuttingBlocks = builder.toBuild();
		cuttingsSolution.setCuttingBlocks(cuttingBlocks);

		ArrayList<String> filePath = new ArrayList<>();
		filePath.add("D:/test/tif/lizong/01/222000002/00000001.Tif");
		filePath.add("D:/test/tif/lizong/01/222000002/00000002.Tif");

		String scanSourceImageRootDir = "D:/test/tif/lizong";

		StudentTestPaperAnswerCardEvent event = new StudentTestPaperAnswerCardEvent();
		event.setFilePaths(filePath).setScanSourceImageRootDir(scanSourceImageRootDir)
				.setCuttingsSolution(cuttingsSolution).setStudentId("12345");

		ImageJCuttingImage cut = new ImageJCuttingImage(event);
		cut.cutting();
	}
}
