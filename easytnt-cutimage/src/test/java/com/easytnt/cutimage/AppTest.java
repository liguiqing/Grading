package com.easytnt.cutimage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.easytnt.importpaper.io.scanfiledir.DirectoryScanner;
import com.easytnt.importpaper.io.scanfiledir.DirectoryScannerFactory;
import com.easytnt.importpaper.io.scanfiledir.FileInfo;
import com.easytnt.importpaper.io.scanfiledir.VisitorFile;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void test01() throws Exception {
		Path p = Paths.get("d:/test/1/1/1");
		if (!Files.exists(p)) {
			Files.createDirectories(p);
		}
	}

	@Test
	public void test02() throws Exception {
		DirectoryScanner directoryScanner = DirectoryScannerFactory.getDirectoryScanner("D:/test/tif/lizong");
		try {
			directoryScanner.scan(new VisitorFile() {

				@Override
				public void visit(FileInfo fileInfo) {

					System.out.println(fileInfo.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test03() throws Exception {
		Path p = Paths.get("e:/tif/lizong/01222000002/00000060.Tif");
		System.out.println(p.toString());
		System.out.println(p.subpath(0, 3).toString());
		System.out.println(p.getRoot());
		if (Files.exists(p.subpath(0, 3))) {
			System.out.println("yes");
		}

	}

	@Test
	public void test04() throws Exception {
		Path p = Paths.get("d:/test/tif/lizong/01222000002/00000060.Tif");
		System.out.println(p.subpath(2, 4));

	}

	@Test
	public void readFile() throws Exception {
		String filePath = "D:/test/tif/lizong/01/222000002/Index.dat";
		Path path = Paths.get(filePath);
		List<String> dataset = FileUtils.readLines(path.toFile());
		String info = dataset.get(0);
		String[] fileds = info.split(";");
		Path parentPath = Paths.get("D:/test/tif/lizong/01/222000002");
		int imageNum = Integer.parseInt(fileds[0]);
		int size = imageNum + 15;
		for (int i = 15; i < size; i++) {
			System.out.println(parentPath.resolve(Paths.get(fileds[i])).toString());
		}
		System.out.println(dataset.size());
	}

	@Test
	public void path() throws Exception {

		Path path1 = Paths.get("D:/test/tif/lizong/01/222000002");
		Path path2 = Paths.get("Index.dat");

		System.out.println(path1.resolve(path2));
		System.out.println(path1);
	}

}
