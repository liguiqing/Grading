/**
 * 
 */
package com.easytnt.cutimage;

import java.util.List;

import org.junit.Test;

import com.easytnt.cutimage.utils.CuttingBlockBuilder;
import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingBlockBuilderTest {
	@Test
	public void builderTest() throws Exception {
		CuttingSolution cuttingSolution = MockCuttingsSolution.cuttingsSolution();
		CuttingBlockBuilder builder = new CuttingBlockBuilder(cuttingSolution);
		List<CuttingBlock> cuttingBlocks = builder.toBuild();
		System.out.println();
	}
}
