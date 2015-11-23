/**
 * 
 */
package com.easytnt.cutimage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.cutimage.utils.StartCuttingTestpaper;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StartCutTest2 {
	private Logger log = LoggerFactory.getLogger(StartCutTest2.class);

	@Test
	public void testCutting() throws Exception {
		long b = System.currentTimeMillis();
		CuttingsSolution cuttingsSolution = MockCuttingsSolution.cuttingsSolution();

		StartCuttingTestpaper cutting = new StartCuttingTestpaper(cuttingsSolution, null);
		cutting.run();
		long e = System.currentTimeMillis();
		System.out.println((e - b) * 1.0 / 1000 + "s");
		System.out.println("测试提交l");
	}
}
