/**
 * 
 */
package com.easytnt.cutimage;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easytnt.commons.util.SpringContextUtil;
import com.easytnt.cutimage.utils.StartCuttingTestpaper;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-ds.xml" })
public class StartCutTest {
	private Logger log = LoggerFactory.getLogger(StartCutTest.class);

	@Test
	public void testLog() throws Exception {
		log.debug("test ...");
		DataSource ds = SpringContextUtil.getBean("ds");
		System.out.println();
	}

	@Test
	public void testCutting() throws Exception {
		long b = System.currentTimeMillis();
		// DataSource ds = SpringContextUtil.getBean("ds");

		CuttingsSolution cuttingsSolution = MockCuttingsSolution.cuttingsSolution();

		StartCuttingTestpaper cutting = new StartCuttingTestpaper(cuttingsSolution, null);
		cutting.run();
		long e = System.currentTimeMillis();
		System.out.println((e - b) * 1.0 / 1000 + "s");
		System.out.println("测试提交l");
	}
}
