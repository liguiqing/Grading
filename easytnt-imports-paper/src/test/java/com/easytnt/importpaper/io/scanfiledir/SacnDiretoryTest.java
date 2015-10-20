/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.DirectoryMapping;
import com.easytnt.importpaper.bean.MappingName;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.service.ConvertFileInfoToCutImageInfoService;
import com.easytnt.importpaper.service.impl.ConvertFileInfoToCutImageInfoServiceImpl;

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

	@Test
	public void createCutImageSQL() throws Exception {

		final ConvertFileInfoToCutImageInfoService convertFileInfoToCutImageInfoService = new ConvertFileInfoToCutImageInfoServiceImpl();
		ArrayList<DirectoryMapping> directoryMappings = new ArrayList<>();

		directoryMappings.add(new DirectoryMapping().setPlace(0).setMappingName(MappingName.KM));
		directoryMappings.add(new DirectoryMapping().setPlace(1).setMappingName(MappingName.DQ));
		directoryMappings.add(new DirectoryMapping().setPlace(2).setMappingName(MappingName.KC));
		directoryMappings.add(new DirectoryMapping().setPlace(3).setMappingName(MappingName.TH));

		final ScannerDirectoryConfig config = new ScannerDirectoryConfig();
		config.setRootUrl("http://127.0.0.1:8888").setFileDir("D:/sj").setTestId(10000010)
				.setDirectoryMappings(directoryMappings);

		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner(config.getFileDir());
		directoryScanner.scan(new VisitorFile() {

			@Override
			public void visit(FileInfo fileInfo) {
				CutImageInfo cutImageInfo = convertFileInfoToCutImageInfoService.convert(config, fileInfo);
				try {
					printSQL(null, cutImageInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		log.debug(">>>>>>>>>>>>>>>>>>>end");
	}

	private void printSQL(File file, CutImageInfo info) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into getpaper(paperid,kemuoid,virtualroomid,studentoid,itemid,scored,pingci,imagepath)");
		sql.append("values(");
		sql.append(info.getPaperId());
		sql.append(",");
		sql.append(info.getKemuId());
		sql.append(",");
		sql.append(info.getVirtualroomId());
		sql.append(",");
		sql.append(info.getStudentId());
		sql.append(",");
		sql.append(info.getItemId());
		sql.append(",");
		sql.append("0,0,");
		sql.append("'" + info.getImagePath() + "'");
		sql.append(");\n");

		// System.out.println(sql.toString());
		File file2 = new File("d:/test/sql.sql");
		// FileUtils.writeStringToFile(file2, new ArrayList<>(), "UTF-8");
		// FileUtils.w
		// ArrayList<String> sqls = new ArrayList<>();
		// sqls.add(sql.toString());
		// FileUtils.writeLines(file2, "UTF-8", sqls);
		FileUtils.write(file2, sql.toString(), "UTF-8", true);
	}
}
