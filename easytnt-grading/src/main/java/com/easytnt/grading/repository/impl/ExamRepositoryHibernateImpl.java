package com.easytnt.grading.repository.impl;

import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.repository.ExamRepository;

@Repository
public class ExamRepositoryHibernateImpl extends HibernateRepository<Exam,Long> implements ExamRepository {

}
