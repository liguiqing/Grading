/**
 * 
 */
package com.easytnt.importpaper.bean;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 增加注释
 * </pre>
 * 
 * @author liuyu
 *
 */
public class NioPathTest {

	private Logger log = LoggerFactory.getLogger(NioPathTest.class);

	@Test
	public void getPathName() throws Exception {
		Path path = Paths.get("D:/sj/lishu/23/1060258300/1/1060258300011.jpg");
		log.debug(path.getFileName().toString());

		String name = path.getFileName().toString();
		int index = name.indexOf(".");
		log.debug(name.substring(0, index));

	}

	@Test
	public void pareseInt() throws Exception {
		String id = "1060258300011";
		System.out.println(Long.parseLong(id));
	}
}
