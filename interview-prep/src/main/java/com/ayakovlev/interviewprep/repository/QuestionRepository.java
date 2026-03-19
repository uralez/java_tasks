package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.entity.Question;
import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.ayakovlev.interviewprep.dto.QuestionDto" +
            "(q.id, qt.text) " +
            "FROM Question q " +
            "JOIN q.translations qt " +
            "WHERE q.topic = :topic " +
            "AND qt.locale = :locale " +
            "ORDER BY qt.text")
    List<QuestionDto> findByTopicWithTranslation(
      @Param("topic") Topic topic,
      @Param("locale") String locale
    );
}
