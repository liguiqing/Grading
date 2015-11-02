package com.easytnt.cutimage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		Path p = Paths.get("d:/test/tif/lizong/01222000002/00000060.Tif");
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

}
