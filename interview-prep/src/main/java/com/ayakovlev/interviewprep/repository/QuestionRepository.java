package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.Question;
import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopic(Topic topic);
}
