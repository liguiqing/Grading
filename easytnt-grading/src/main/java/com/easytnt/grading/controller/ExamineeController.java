package com.easytnt.grading.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.cqrs.QueryBuilder;
import com.easytnt.commons.io.DefaultListDataSourceMapper;
import com.easytnt.commons.io.FileListDataSourceFactory;
import com.easytnt.commons.io.FileUtil;
import com.easytnt.commons.io.ListDataSourceFactory;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.controller.formbean.DataSourceMapperFormBean;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.service.ExamineeService;


/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月10日
 * @version 1.0
 **/
@Controller
@RequestMapping(value="/examinee")
public class ExamineeController {
	private static Logger logger = LoggerFactory.getLogger(ExamineeController.class);
	
	@Autowired(required = false)
	private ExamineeService examineeService;
	
	@Value("${easytnt.img.sample.dir}")
	private String imgDir;
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onGet(HttpServletRequest request)throws Exception {
		logger.debug("URL /examinee Method GET ");
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		MenuGroup configMenuGroup = MenuGroupFactory.getInstance().getConfigMenuGroup();
		configMenuGroup.activedMenuByIndex(2);
		rightMenuGroup.activedMenuByIndex(3);
		Query<Examinee> query = new QueryBuilder().newQuery(1,10,new HashMap());
		//query.rows(202);
		String[] dataSourceFields = new String[0];
		//先放在Session里，以后在处理 TODO
		ListDataSourceReader reader = (ListDataSourceReader)request.getSession().getAttribute(ListDataSourceReader.class.getName());
		if(reader != null) {
			reader.open();
			dataSourceFields = reader.getFields();
		}
		
		examineeService.query(query);

		//TODO
		int i= 0 ;
		DataSourceMapperFormBean[] formBeans = new DataSourceMapperFormBean[15];
		formBeans[i++] = new DataSourceMapperFormBean("student_number", "学籍号");
		formBeans[i++] = new DataSourceMapperFormBean("student_name", "学生姓名");
		formBeans[i++] = new DataSourceMapperFormBean("gender", "性别");
		formBeans[i++] = new DataSourceMapperFormBean("nation", "民族");
		formBeans[i++] = new DataSourceMapperFormBean("birthday", "出生日期");
		formBeans[i++] = new DataSourceMapperFormBean("arts", "文理科标志");
		formBeans[i++] = new DataSourceMapperFormBean("examinne_uuid", "准考证号");
		formBeans[i++] = new DataSourceMapperFormBean("seating_number", "座位号");
		formBeans[i++] = new DataSourceMapperFormBean("room_number", "考场号");
		formBeans[i++] = new DataSourceMapperFormBean("clazz_name", "班级名称");
		formBeans[i++] = new DataSourceMapperFormBean("clazz_code", "班级代码");
		formBeans[i++] = new DataSourceMapperFormBean("school_name", "学校名称");
		formBeans[i++] = new DataSourceMapperFormBean("school_code", "学校代码");
		formBeans[i++] = new DataSourceMapperFormBean("district_number", "区县编码");
		formBeans[i++] = new DataSourceMapperFormBean("district_name", "区县名称");
		
		return ModelAndViewFactory.newModelAndViewFor("/config")
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", configMenuGroup.getMenus())
				.with("query",query)
				.with("dsFields",dataSourceFields)
				.with("mappeds",formBeans)
				.with("page","examinee").build();
	}
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public ModelAndView onUpload(MultipartHttpServletRequest request)throws Exception {
		logger.debug("URL /upload Method POST ");
		Iterator<String> it = request.getFileNames();
		if(it.hasNext()) {
			String fileName = it.next();
			MultipartFile mfile = request.getFile(fileName);
			File file = FileUtil.inputStreamToFile(mfile.getInputStream(),mfile.getOriginalFilename());
			ListDataSourceFactory dataSourceFactory = new FileListDataSourceFactory(file);
			//先放在Session里，以后在处理 TODO
			request.getSession().setAttribute(ListDataSourceReader.class.getName(), dataSourceFactory.getReader());
			logger.debug(file.getAbsolutePath());
		}else {
			throw new IllegalArgumentException("无效的文件名");
		}
		
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/importExaminee",method = RequestMethod.POST)
	public ModelAndView importExaminee(@RequestBody DataSourceMapperFormBean[] mappedBeans,HttpServletRequest request)throws Exception {
		logger.debug("URL /importExaminee Method POST ");
		ListDataSourceReader reader = (ListDataSourceReader)request.getSession().getAttribute(ListDataSourceReader.class.getName());
		String[] targetFields = reader.getFields();
		String[] mapper = new String[mappedBeans.length];
		int i = 0;
		for(DataSourceMapperFormBean dsBean:mappedBeans) {
			for(String field:targetFields) {
				if(dsBean.getTargetName().equalsIgnoreCase(field)) {
					mapper[i++] = dsBean.getFieldName();
				}
			}
		}
		
		examineeService.imports(new DefaultListDataSourceMapper(), reader);
		//先放在Session里，以后在处理 TODO
		request.getSession().removeAttribute(ListDataSourceReader.class.getName());
		
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
}
