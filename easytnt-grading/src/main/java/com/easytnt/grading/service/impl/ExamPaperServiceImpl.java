package com.easytnt.grading.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.commons.io.FileUtil;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.repository.ExamPaperRepository;
import com.easytnt.grading.service.ExamPaperService;

@Service
public class ExamPaperServiceImpl extends AbstractEntityService<ExamPaper, Long>implements ExamPaperService {
	@Autowired(required = false)
	private ExamPaperRepository examPaperRepository;
	
	@Value("${easytnt.exampaper.card.sample.path}")
	private String cardImagePath;
	
	public ExamPaperServiceImpl() {
		
	}
	
	@Autowired(required=false)
	public void setRepository(ExamPaperRepository  repository) {
		this.examPaperRepository = repository;
		super.setRepository(repository);
	}
	
	@Transactional(readOnly=true)
	@Override
	public ExamPaper load(Long pk) {
		ExamPaper examPaper =  examPaperRepository.load(pk);
		return examPaper;
	}

	@Transactional(readOnly=true)
	@Override
	public void query(Query<ExamPaper> query) {
		// TODO Auto-generated method stub
	}

	@Transactional
	@Override
	public void deleteSectionFor(Long paperId, Section section) {
		ExamPaper examPaper = load(paperId);
		examPaper.removeSections(section);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void updateSectionFor(Long paperId, Section section,Integer position) {
		ExamPaper examPaper = load(paperId);
		examPaper.addSection(position, section);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void addSectionFor(Long paperId, Section section) {
		ExamPaper examPaper = load(paperId);
		examPaper.addSection(section);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void deletePaperCardFor(Long paperId, PaperCard paperCard) {
		ExamPaper examPaper = load(paperId);
		examPaper.removePaperCard(paperCard);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void addPaperCardTo(ExamPaper examPaper, File cardFile,int rotate) {
		File root = new File(this.cardImagePath);
		if(!root.exists())
			root.mkdir();
		PaperCard paperCard = new PaperCard();
		examPaper.addPaperCard(paperCard);
		examPaperRepository.save(examPaper);
		
		String path =  File.separator +examPaper.getPaperOid() + 
				File.separator + cardFile.getName();
		File file = new File(root + path);
		FileUtil.copyTo(cardFile, file);
		paperCard.setPath(path);
		paperCard.setRotate(rotate);
	}

	@Transactional(readOnly=true)
	@Override
	public File getPaperCardFile(ExamPaper examPaper,Long cardId) {
		Set<PaperCard> paperCardSet = examPaper.getPaperCards();		
		PaperCard paperCard=null;
		for(PaperCard pc:paperCardSet){
			if(pc.getCardId().equals(cardId)){
				paperCard = pc;
				break;
			}
		}
		
		
		File file=null;
		if(paperCard!=null){
			file= new File(this.cardImagePath + paperCard.getPath());
		}
		return file;
	}

	@Override
	public int countPapers(Long examPaperId) {
		return this.examPaperRepository.countPapersFor(examPaperId);
	}
	

}