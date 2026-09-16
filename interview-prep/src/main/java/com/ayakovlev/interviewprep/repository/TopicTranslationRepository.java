package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.TopicTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicTranslationRepository extends JpaRepository<TopicTranslation, Long> {
}
