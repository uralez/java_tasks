package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluatorRepository extends JpaRepository<Evaluator, Long> {
}
