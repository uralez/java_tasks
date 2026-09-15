package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionProjection;
import com.ayakovlev.interviewprep.dto.TopicQuestionRow;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByStudent(Student student);

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
            FROM answer a
            JOIN question q ON q.id = a.question_id
            JOIN question_translation qt ON qt.question_id = q.id
            JOIN topic t ON t.id = q.topic_id
            JOIN topic_translation tt ON tt.topic_id = t.id
            WHERE a.student_id = :studentId
              AND qt.locale = :locale
              AND tt.locale = :locale
            GROUP BY t.id, t.order_number, tt.name, q.id, qt.text
            ORDER BY t.order_number, q.id 
            """, nativeQuery = true)
    List<TopicQuestionProjection> findTopicsWithQuestionsNative(
            @Param("studentId") Long studentId,
            @Param("locale") String locale
    );
/*
    @Query("SELECT new com.ayakovlev.interviewprep.dto.TopicQuestionRow(" +
            "a.question.topic.id, " +
            "a.question.topic.orderNumber, " +
            "tt.name, " +
            "a.question.id, " +
            "qt.text, " +
            "COUNT(a), " +
            "AVG(a.grade)" +
            ") " +
            "FROM Answer a " +
            "JOIN a.question.translations qt " +
            "JOIN a.question.topic.translations tt " +
            "WHERE a.student = :student " +
            "AND qt.locale = :locale " +
            "AND tt.locale = :locale " +
            "GROUP BY a.question.topic.id, tt.name, a.question.topic.orderNumber, a.question.id, qt.text " +
            "ORDER BY a.question.topic.orderNumber, a.question.id")
    List<TopicQuestionRow> findTopicsWithQuestions(
            @Param("student") Student student,
            @Param("locale") String locale);
*/
    @Query("SELECT new com.ayakovlev.interviewprep.dto.GradePointDto" +
            "(" +
            "   a.id, " +
            "   a.answerDate, " +
            "   a.grade," +
            "   SUBSTRING(a.text, 1, 120)" +
            ") " +
            "FROM Answer a " +
            "WHERE a.student = :student AND a.question.id = :questionId " +
            "ORDER BY a.answerDate, a.dcre")
    List<GradePointDto> findGradesByQuestion(
            @Param("questionId") Long questionId,
            @Param("student") Student student);

    List<Answer> findByStudentAndQuestionIdOrderByAnswerDateAscDcreAsc(
            Student student,
            Long questionId
    );

    @Query("SELECT AVG(a.grade) FROM Answer a " +
            "WHERE a.student = :student AND a.question.topic.id = :topicId")
    Double avgGradeByTopic(
            @Param("student") Student student,
            @Param("topicId") Long topicId
    );

    @Query("SELECT AVG(a.grade) FROM Answer a " +
            "WHERE a.student = :student AND a.question.id = :questionId")
    Double avgGradeByQuestion(
            @Param("student") Student student,
            @Param("questionId") Long questionId
    );

    /*
    * @Modifying — это аннотация Spring Data JPA которая говорит что запрос изменяет данные в базе
    * (INSERT, UPDATE, DELETE), а не читает их.
    * Без неё Spring Data ожидает что @Query возвращает данные и выбрасывает исключение если метод возвращает void или int.
    * Также @Modifying автоматически сбрасывает кеш первого уровня Hibernate после выполнения запроса — это важно
    * чтобы последующие запросы видели актуальные данные, а не устаревшие из кеша.
    * Обычно используется вместе с @Transactional.
    * */
    @Modifying
    @Query(value = """
            INSERT INTO answer (student_id, question_id, text, grade, answer_date, evaluator_id, dcre, dmod, user_cre, user_mod)
            SELECT :demoId, a.question_id, a.text, a.grade, a.answer_date, a.evaluator_id, now(), now(), 'system', 'system'
            FROM answer a WHERE a.student_id = :templateId 
            """, nativeQuery = true)
    void copyAnswersFromTemplate(@Param("templateId") Long templateId, @Param("demoId") Long demoId);

    void deleteByStudent(Student student);
}
