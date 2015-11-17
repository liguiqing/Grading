/**
 * 
 */
package com.easytnt.grading.service;

import org.junit.Assert;
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
public class CuttingsSolutionServiceTest {

	@Autowired(required = false)
	private CuttingsSolutionService service;

	@Test
	public void saveCuttingsSolution() throws Exception {
		Assert.assertNotNull(service);
		// service.saveCuttingsSolution(null);
		System.out.println();
	}
}
