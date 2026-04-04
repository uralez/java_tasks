package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionRow;
import com.ayakovlev.interviewprep.dto.TopicWithQuestionsDto;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    void findByStudent_returnsAnswersFromRepository(){
        // arrange
        Student student = new Student();
        Answer answer = new Answer();
        when(answerRepository.findByStudent(student)).thenReturn(List.of(answer));

        // act
        List<Answer> result = answerService.findByStudent(student);

        // assert
        assertEquals(1, result.size());
        verify(answerRepository).findByStudent(student);
//        verify(answerRepository).save(answer);
    }

    @Test
    void save_callsRepositorySave(){
        // arrange
        Answer answer = new Answer();

        // act
        answerService.save(answer);

        // assert
        verify(answerRepository).save(answer);
    }

    @Test
    void findTopicsWithQuestions_groupRowsByTopic(){
        // arrange
        String locale = "en";
        Student student = new Student();
        List<TopicQuestionRow> rows = List.of(
                new TopicQuestionRow("Core Java", 1L, "Чем == отличается от equals()?", 2L, 4.0),
                new TopicQuestionRow("Core Java", 2L, "Как работает hashCode()?", 2L, 4.0),
                new TopicQuestionRow("Collections", 3L, "Чем ArrayList отличается от LinkedList?", 1L, 3.5)
        );
        when(answerRepository.findTopicsWithQuestions(student, locale)).thenReturn(rows);

        // act
        List<TopicWithQuestionsDto> result = answerService.findTopicsWithQuestions(student, locale);

        // assert
        assertEquals(2, result.size());
        assertEquals("Core Java", result.get(0).getTopicName());
        assertEquals(2, result.get(0).getQuestions().size());
        assertEquals("Collections", result.get(1).getTopicName());
        assertEquals(1, result.get(1).getQuestions().size());
    }

    @Test
    void findGradesByQuestion_returnGradePointsFromRepository(){
        // arrange
        Student student = new Student();
        Long questionId = 1L;
        List<GradePointDto> points = List.of(
                new GradePointDto(1L, LocalDate.of(2025, 12, 15), new BigDecimal("3.8"), "Def"),
                new GradePointDto(2L, LocalDate.of(2025, 12, 15), new BigDecimal("4.8"), "Ert")
        );
        when(answerRepository.findGradesByQuestion(questionId, student)).thenReturn(points);

        // act
        List<GradePointDto> result = answerService.findGradesByQuestion(questionId, student);

        // assert
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("3.8"), result.get(0).getGrade());
        verify(answerRepository).findGradesByQuestion(questionId, student);
    }
}
