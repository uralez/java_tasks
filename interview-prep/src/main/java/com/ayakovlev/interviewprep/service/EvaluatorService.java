package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Evaluator;
import com.ayakovlev.interviewprep.repository.EvaluatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluatorService {

    @Autowired
    private EvaluatorRepository evaluatorRepository;

    public List<Evaluator> findAll() {
        return evaluatorRepository.findAll();
    }
}
