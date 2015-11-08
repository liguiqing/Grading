/**
 * <p><b>© 1997-2013 深圳市海云天教育测评有限公司 TEL: (86)755 - 86024188</b></p>
 * 
 **/

package com.easytnt.security;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/

@ContextHierarchy({
	@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath*:applicationContext-security.xml"})
})
public class ConfigurationTest extends AbstractJUnit4SpringContextTests {

	
	@Test
	public void test()throws Exception{
		
	}
}

