package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.QuestionTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTranslationRepository extends JpaRepository<QuestionTranslation, Long> {
}
