package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.entity.Question;
import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.ayakovlev.interviewprep.dto.QuestionDto" +
            "(q.id, q.orderNumber, qt.text) " +
            "FROM Question q " +
            "JOIN q.translations qt " +
            "WHERE q.topic = :topic " +
            "AND qt.locale = :locale " +
            "ORDER BY qt.text")
    List<QuestionDto> findByTopicWithTranslation(
      @Param("topic") Topic topic,
      @Param("locale") String locale
    );

    long countByTopicId(Long topicId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE Question q 
            SET q.orderNumber = q.orderNumber + 1
            WHERE q.topic.id = :topicId AND q.orderNumber >= :from 
            """)
    void shiftUpFrom(@Param("topicId") Long topicId, @Param("from") int from);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE Question q
            SET q.orderNumber = q.orderNumber - 1
            WHERE q.topic.id = :topicId AND q.orderNumber > :from 
            """)
    void shiftDownAfter(@Param("topicId") Long topicId, @Param("from") int from);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE Question q 
            SET q.orderNumber = q.orderNumber - 1
            WHERE q.topic.id = :topicId AND q.orderNumber > :oldNum AND q.orderNumber <= :newNum
            """)
    void shiftDownBetween(@Param("topicId") Long topicId, @Param("oldNum") int oldNum, @Param("newNum") int newNum);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE Question q
            SET q.orderNumber = q.orderNumber + 1
            WHERE q.topic.id = :topicId AND q.orderNumber >= :newNum AND q.orderNumber < :oldNum
            """)
    void shiftUpBetween(@Param("topicId") Long topicId, @Param("oldNum") int oldNum, @Param("newNum") int newNum);
}
