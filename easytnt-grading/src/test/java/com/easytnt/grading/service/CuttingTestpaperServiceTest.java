/**
 * 
 */
package com.easytnt.grading.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath*:applicationContext-*.xml" })
public class CuttingTestpaperServiceTest {
	@Autowired(required = false)
	private CuttingTestpaperService service;

	@Test
	public void testStartCutting() throws Exception {
		service.cutting(3L);
		System.out.println();
	}
}
