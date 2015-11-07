/**
 * 
 */
package com.easytnt.grading.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.service.ScannerDirectoryService;
import com.easytnt.importpaper.bean.CountContainer;
import com.easytnt.importpaper.bean.CountContainerMgr;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.bean.ScannerDirectoryConfig;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Controller
@RequestMapping("/importPaper")
public class ImportPaperController {
	private static Logger log = LoggerFactory.getLogger(ImportPaperController.class);

	@Autowired(required = false)
	private ScannerDirectoryService scannerDirectoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping(value = "/{saveToDb}/scan", method = RequestMethod.POST)
	public ModelAndView scan(@PathVariable int saveToDb, @RequestBody ScannerDirectoryConfig config,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// saveToDb 0 不保存数据库 1保存数据
		log.debug("begin /{target}/scan...");

		CountContainerMgr containerMgr = CountContainerMgr.getInstance();

		boolean isSaveToDb = saveToDb == 1 ? true : false;
		scannerDirectoryService.scanDirectory(config, isSaveToDb);
		log.debug("end /{target}/scan...");
		return ModelAndViewFactory.newModelAndViewFor().with("config", config).build();
	}

	@RequestMapping(value = "/{type}/stat/{uuId}", method = RequestMethod.POST)
	public ModelAndView getScanStatisticInfo(@PathVariable int type, @PathVariable String uuId,
			@RequestBody ScannerDirectoryConfig config, HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		log.debug("begin /{type}/stat...");
		CountContainer<CutImageInfo> container = CountContainerMgr.getInstance().get(uuId);
		CountContainerMgr.getInstance().remove(uuId);
		List<CutImageInfo> cutImageInfos = container.getContainer();
		int totalFielSize = container.getFileNumber();
		String url = config.getRootUrl();

		String template = "";
		if (type == 1) {
			template = "importpaper/configTable";
		} else {
			template = "importpaper/resultStat";
		}
		log.debug("end /{type}/stat...");
		return ModelAndViewFactory.newModelAndViewFor(template).with("totalFielSize", totalFielSize)
				.with("cutImageInfos", cutImageInfos).with("url", url).build();
	}
}
