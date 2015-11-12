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

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-ds.xml" })
public class SpringTest {
	private Logger log = LoggerFactory.getLogger(SpringTest.class);

	@Test
	public void testLog() throws Exception {
		log.debug("test ...");
		DataSource ds = SpringContextUtil.getBean("ds");
		System.out.println();
	}
}
