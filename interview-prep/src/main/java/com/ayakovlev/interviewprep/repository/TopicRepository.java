package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.TopicDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionProjection;
import com.ayakovlev.interviewprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = """
            SELECT 
                t.id AS topic_id,
                t.order_number AS topic_order_number,
                tt.name AS topic_name,
                q.id AS question_id,
                q.order_number AS question_order_number,
                qt.text AS question_text,
                COUNT(a.id) AS answer_count,
                AVG(a.grade) AS question_avg_grade,
                AVG(COUNT(a.id)) OVER (PARTITION BY t.id) AS topic_avg_answer_count,
                AVG(AVG(a.grade)) OVER (PARTITION BY t.id) AS topic_avg_grade
            FROM topic t
            LEFT JOIN topic_translation tt      ON t.id = tt.topic_id       AND tt.locale = :locale
            LEFT JOIN question q                ON t.id = q.topic_id
            LEFT JOIN question_translation qt   ON q.id = qt.question_id    AND qt.locale = :locale
            LEFT JOIN answer a                  ON q.id = a.question_id     AND a.student_id = :studentId
            GROUP BY t.id, t.order_number, tt.name, q.id, qt.text
            ORDER BY t.order_number, q.id
            """, nativeQuery = true)
    List<TopicQuestionProjection> findTopicsWithQuestions(
            @Param("studentId") Long studentId,
            @Param("locale") String locale
    );

    @Query(value = """
            SELECT new com.ayakovlev.interviewprep.dto.TopicDto
            (t.id, t.orderNumber, tt.name) 
            FROM Topic t 
            LEFT JOIN t.translations tt ON tt.locale = :locale 
            ORDER BY t.orderNumber
            """
    )
    List<TopicDto> findAllWithTranslation(@Param("locale") String locale);
}
