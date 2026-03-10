package com.ayakovlev.interviewprep.repository;

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
            "a.question.topic.name, a.question.text, COUNT(a), AVG(a.grade)) " +
            "FROM Answer a " +
            "WHERE a.student = :student " +
            "GROUP BY a.question.topic.name, a.question.text " +
            "ORDER BY a.question.topic.name, a.question.text")
    List<TopicQuestionRow> findTopicsWithQuestions(@Param("student") Student student);
}
