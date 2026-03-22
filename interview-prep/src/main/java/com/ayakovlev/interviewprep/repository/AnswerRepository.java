package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.GradePointDto;
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

    @Query("SELECT new com.ayakovlev.interviewprep.dto.TopicQuestionRow(" +
            "tt.name, a.question.id, qt.text, COUNT(a), AVG(a.grade)) " +
            "FROM Answer a " +
            "JOIN a.question.translations qt " +
            "JOIN a.question.topic.translations tt " +
            "WHERE a.student = :student " +
            "AND qt.locale = :locale " +
            "AND tt.locale = :locale " +
            "GROUP BY tt.name, a.question.id, qt.text " +
            "ORDER BY tt.name, qt.text")
    List<TopicQuestionRow> findTopicsWithQuestions(
            @Param("student") Student student,
            @Param("locale") String locale);

    @Query("SELECT new com.ayakovlev.interviewprep.dto.GradePointDto" +
            "(a.answerDate, a.grade) " +
            "FROM Answer a " +
            "WHERE a.student = :student AND a.question.id = :questionId " +
            "ORDER BY a.answerDate, a.dcre")
    List<GradePointDto> findGradesByQuestion(
            @Param("questionId") Long questionId,
            @Param("student") Student student);

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
