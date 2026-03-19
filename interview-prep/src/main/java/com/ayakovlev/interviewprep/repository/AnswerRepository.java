package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionRow;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
