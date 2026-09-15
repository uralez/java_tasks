package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionRow;
import com.ayakovlev.interviewprep.dto.TopicQuestionProjection;
import com.ayakovlev.interviewprep.dto.TopicWithQuestionsDto;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public List<Answer> findByStudent(Student student){
        return answerRepository.findByStudent(student);
    }

    public void save(Answer answer){
        answerRepository.save(answer);
    }

    public List<TopicWithQuestionsDto> findTopicsWithQuestions(Student student, String locale){
        List<TopicQuestionProjection> rows = answerRepository.findTopicsWithQuestionsNative(student.getId(), locale);
        Map<Long, TopicWithQuestionsDto> map = new LinkedHashMap<>();

        for (TopicQuestionProjection row : rows){
            map.computeIfAbsent(
                    row.getTopicId(),
                    k -> new TopicWithQuestionsDto(
                            row.getTopicId(),
                            row.getTopicOrderNumber(),
                            row.getTopicName(),
                            row.getTopicAvgAnswerCount(),
                            row.getTopicAvgGrade(),
                            new ArrayList<>()
                    )
            ).getQuestions().add(new QuestionDto(row.getQuestionId(), row.getQuestionOrderNumber(), row.getQuestionText()));
        }

        return new ArrayList<>(map.values());
    }

    public List<GradePointDto> findGradesByQuestion(Long questionId, Student student) {
        return answerRepository.findGradesByQuestion(questionId, student);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found: " + id));
    }

    public List<Answer> findByStudentAndQuestion(
            Student student,
            Long questionId
            ){
        return answerRepository
                .findByStudentAndQuestionIdOrderByAnswerDateAscDcreAsc(student, questionId);
    }

    public void deleteById(Long id){
        log.info("Delete answer: " + id);
        answerRepository.deleteById(id);
    }

    public Double avgGradeByTopic(Student student, Long topicId){
        return answerRepository.avgGradeByTopic(student, topicId);
    }

    public Double avgGradeByQuestion(Student student, Long questionId){
        return answerRepository.avgGradeByQuestion(student, questionId);
    }
}
