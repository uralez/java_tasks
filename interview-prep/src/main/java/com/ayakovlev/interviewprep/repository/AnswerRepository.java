package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByStudent(Student student);

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

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.question.id = :questionId")
    long countByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT COUNT(DISTINCT a.student.id) FROM Answer a WHERE a.question.id = :questionId")
    long countDistinctStudentsByQuestionId(@Param("questionId") Long questionId);

    List<Answer> findByQuestionId(Long questionId);
}
