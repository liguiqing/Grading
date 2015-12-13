/**
 * 
 */
package com.easytnt.grading.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easytnt.grading.domain.cuttings.CuttingSolution;

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
	@Autowired(required = false)
	private CuttingsSolutionService cuttingsSolutionService;

	@Test
	public void testStartCutting() throws Exception {
		CuttingSolution cuttingSolution = cuttingsSolutionService.getCuttingDefines(3L);
		service.cutting(cuttingSolution);
		System.out.println();
	}
}
