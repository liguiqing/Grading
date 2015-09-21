package com.joy.grading.repository.impl;

import org.springframework.stereotype.Repository;

import com.joy.commons.entity.repository.HibernateRepository;
import com.joy.grading.domain.exam.Exam;
import com.joy.grading.repository.ExamRepository;

@Repository
public class ExamRepositoryHibernateImpl extends HibernateRepository<Exam,Long> implements ExamRepository {

}
