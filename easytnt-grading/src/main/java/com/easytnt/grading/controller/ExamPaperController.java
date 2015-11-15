package com.easytnt.grading.controller;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.cqrs.QueryBuilder;
import com.easytnt.commons.io.FileUtil;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.service.ExamPaperService;

@Controller
@RequestMapping(value = "/examPaper")
public class ExamPaperController {
	private static Logger logger = LoggerFactory.getLogger(ExamPaperController.class);

	@Autowired(required = false)
	private ExamPaperService examPaperService;
	
	@Value("img\\sample")
	private String imgDir;
	
	@RequestMapping(value = "/onCreateExamPaper",method = RequestMethod.POST)
	public ModelAndView onCreateExamPaper(@RequestBody ExamPaper examPaper)
					throws Exception {
		logger.debug("URL /examPaper Method POST ", examPaper);
		examPaperService.create(examPaper);
		return ModelAndViewFactory.newModelAndViewFor("/examPaper/editExamPaper").build();
	}
	
	@RequestMapping(value = "/{examPaperId}", method = RequestMethod.GET)
	public ModelAndView onViewExamPaper(@PathVariable Long examPaperId)
					throws Exception {
		logger.debug("URL /examPaperId/{} Method Get ", examPaperId);
		ExamPaper examPaper = examPaperService.load(examPaperId);
		return ModelAndViewFactory.newModelAndViewFor("/examPaper/editExamPaper").with("examPaper",examPaper).build();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView onUpdateExamPaper(@RequestBody ExamPaper examPaper)
					throws Exception {
		logger.debug("URL /examPaper Method PUT ", examPaper);
		examPaperService.update(examPaper);
		return ModelAndViewFactory.newModelAndViewFor("/examPaper/editExamPaper").build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView onDeleteExamPaper(@RequestBody ExamPaper examPaper)
					throws Exception {
		logger.debug("URL /examPaper Method DELETE ", examPaper);
		examPaperService.delete(examPaper);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/{examPaperId}/section",method = RequestMethod.POST)
	public ModelAndView onAddSection(@PathVariable Long examPaperId,@RequestBody Section section)
					throws Exception {
		logger.debug("URL /examPaper Method DELETE ", section);
		examPaperService.addSectionFor(examPaperId,section);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	
	@RequestMapping(value="/{examPaperId}",method = RequestMethod.POST)
	public ModelAndView onAddPaperCard(@PathVariable Long examPaperId,MultipartHttpServletRequest request)
					throws Exception {
		logger.debug("URL /examPaper Method onAddPaperCard "+imgDir);
		PaperCard paperCard = new PaperCard();
		Iterator<String> it = request.getFileNames();
		if(it.hasNext()) {
			String fileName = it.next();
			MultipartFile mfile = request.getFile(fileName);
			File file = FileUtil.inputStreamToFile(request,imgDir,mfile.getInputStream(),mfile.getOriginalFilename());
			logger.debug(file.getAbsolutePath());
			paperCard.setPath(imgDir+File.separator+file.getName());
		}else {
			throw new IllegalArgumentException("无效的文件名");
		}
		examPaperService.addPaperCardFor(examPaperId, paperCard);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/{examPaperId}/{cardId}",method = RequestMethod.GET)
	public ModelAndView onShowCard(@PathVariable Long examPaperId,@PathVariable Long cardId)
					throws Exception {
		logger.debug("URL /examPaper/{}/{} Method U ", examPaperId);
		//examPaperService.updateSectionFor(examPaperId,section,position);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/{examPaperId}/section/{position}",method = RequestMethod.PUT)
	public ModelAndView onUpdateSection(@PathVariable Long examPaperId,@RequestBody Section section,@PathVariable Integer position)
					throws Exception {
		logger.debug("URL /examPaper Method U ", section);
		examPaperService.updateSectionFor(examPaperId,section,position);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/onRemoveSection/{paperId}/section",method = RequestMethod.DELETE)
	public ModelAndView onRemoveSection(@PathVariable Long paperId,@RequestBody Section section)
					throws Exception {
		logger.debug("URL /examPaper Method U ", section);
		examPaperService.deleteSectionFor(paperId,section);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	@RequestMapping(value="/removePaperCard/{paperId}",method = RequestMethod.DELETE)
	public ModelAndView onRemovePaperCard(@PathVariable Long paperId,@RequestBody PaperCard paperCard,HttpServletRequest request)
					throws Exception {
		logger.debug("URL /examPaper Method U ", paperCard);
		examPaperService.deletePaperCardFor(paperId, paperCard);
		File file  = new File(request.getServletContext().getRealPath("/")+File.separator+paperCard.getPath());
		file.delete();
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/query/{page}/{size}",method = RequestMethod.GET)
	public ModelAndView onQueryExamPaper(@PathVariable int page,@PathVariable int size,HttpServletRequest request)
					throws Exception {
		logger.debug("URL /examPaper/query/{}/{} Method GET ", page,size);
        Query<ExamPaper> query = new QueryBuilder().newQuery(page,size,request.getParameterMap());
        examPaperService.query(query);
		return ModelAndViewFactory.newModelAndViewFor("/examPaper/listExamPaper").with("result",query.getResults())
				.with("totalPage",query.getTotalPage()).build();
	}
}
