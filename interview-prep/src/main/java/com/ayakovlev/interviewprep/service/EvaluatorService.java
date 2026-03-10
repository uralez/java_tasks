package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Evaluator;
import com.ayakovlev.interviewprep.repository.EvaluatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluatorService {

    private final EvaluatorRepository evaluatorRepository;

    public List<Evaluator> findAll() {
        return evaluatorRepository.findAll();
    }
}
