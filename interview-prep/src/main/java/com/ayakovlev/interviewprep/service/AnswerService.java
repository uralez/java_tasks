package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionRow;
import com.ayakovlev.interviewprep.dto.TopicWithQuestionsDto;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public List<TopicWithQuestionsDto> findTopicsWithQuestions(Student student){
        List<TopicQuestionRow> rows = answerRepository.findTopicsWithQuestions(student);
        Map<String, TopicWithQuestionsDto> map = new LinkedHashMap<>();
        for (TopicQuestionRow row : rows){
            map.computeIfAbsent(
                    row.getTopicName(),
                    k -> new TopicWithQuestionsDto(row.getTopicName(), row.getAnswerCount(), row.getAvgGrade(), new ArrayList<>())
            ).getQuestions().add(new QuestionDto(row.getQuestionId(), row.getQuestionText()));
        }
        return new ArrayList<>(map.values());
    }

    public List<GradePointDto> findGradesByQuestion(Long questionId, Student student) {
        return answerRepository.findGradesByQuestion(questionId, student);
    }
}
