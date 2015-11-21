/**
 * 
 */
package com.easytnt.importpaper.service;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.DirectoryMapping;
import com.easytnt.importpaper.bean.MappingName;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;
import com.easytnt.importpaper.disruptor.produce.ScanDirProduce;
import com.easytnt.importpaper.util.GetSubjectId;
import com.easytnt.importpaper.util.StartScanDirectoryUtil;
import com.easytnt.thread.EasytntExecutor;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StartScanDirServiceTest {

	@Test
	public void scanDirNoDataSource() throws Exception {
		ArrayList<DirectoryMapping> directoryMappings = new ArrayList<>();
		directoryMappings.add(new DirectoryMapping().setPlace(0).setMappingName(MappingName.KM));
		directoryMappings.add(new DirectoryMapping().setPlace(1).setMappingName(MappingName.DQ));
		directoryMappings.add(new DirectoryMapping().setPlace(2).setMappingName(MappingName.KC));
		directoryMappings.add(new DirectoryMapping().setPlace(3).setMappingName(MappingName.TH));

		final ScannerDirectoryConfig config = new ScannerDirectoryConfig();
		config.setRootUrl("http://127.0.0.1:8888").setFileDir(rootPath() +File.separator+"lishu").setTestId(10000010)
				.setDirectoryMappings(directoryMappings);

		
		StartScanDirectoryUtil.start(config, null);

		CountContainer<CutImageInfo> container = CountContainerMgr.getInstance().get(config.getUuId());

		System.out.println("");

	}
	private String rootPath() {
		File root = new File(this.getClass().getResource("/").getPath());
		return root.getAbsolutePath();
	}
	
	@Test
	public void testGetSubjectId() throws Exception {
		int subjectId = GetSubjectId.get("lishu");
		Assert.assertTrue(subjectId == 3);
	}

	@Test
	public void ScanDirProduceTest() throws Exception {
		//ScanDirProduce scanDirProduce = new ScanDirProduce(null, null);
		//CountDownLatch countDownLatch = new CountDownLatch(1);
		//EasytntExecutor.instance().getExecutorService().submit(scanDirProduce);

		//countDownLatch.await();

	}
}
