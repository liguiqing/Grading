/**
 * 
 */
package com.easytnt.bean;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class ScannerDirectoryConfigTest {

	@Test
	public void testScannerDiretoryConfig() throws Exception {
		String rootUrl = "http://127.0.0.1:8080";
		String fileDir = "D:/sj";
		ScannerDirectoryConfig config = new ScannerDirectoryConfig();
		config.setRootUrl(rootUrl).setFileDir(fileDir);

		Assert.assertTrue(!rootUrl.equals(config.getRootUrl()));
		Assert.assertTrue((rootUrl + "/").equals(config.getRootUrl()) == true);

	}
}
