/**
 * 
 */
package com.easytnt.io.scanfiledir;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.bean.ScannerDirectoryConfig;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SacnDiretoryTest {

	private Logger log = LoggerFactory.getLogger(SacnDiretoryTest.class);

	@Test
	public void scanDir() throws Exception {
		ScannerDirectoryConfig config = new ScannerDirectoryConfig();
		config.setRootUrl("http://127.0.0.1:8080").setFileDir("D:/sj");

		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(config.getFileDir());
		CountContainer<FileInfo> fileInfos = directoryScanner.scan();

		log.debug(">>>>>>>>>>>>>>>>>>end");
	}

	@Test
	public void scanDir2() throws Exception {

		CountContainer<FileInfo> container = new CountContainer<>(20);

		ScannerDirectoryConfig config = new ScannerDirectoryConfig();
		config.setRootUrl("http://127.0.0.1:8080").setFileDir("D:/sj");
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(config.getFileDir());

		directoryScanner.scan(new MyVisitorFile(container));

		Assert.assertTrue(container.getFileNumber() == 22148);

		log.debug("end>>>>" + this.getClass().getName() + ".scanDir2()");
	}

	class MyVisitorFile implements VisitorFile {
		private CountContainer<FileInfo> container;

		public MyVisitorFile(CountContainer<FileInfo> container) {
			this.container = container;
		}

		@Override
		public void visit(FileInfo fileInfo) {
			// TODO Auto-generated method stub
			container.add(fileInfo);
		}

	}
}
