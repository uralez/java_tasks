package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.dto.TopicDto;
import com.ayakovlev.interviewprep.dto.TopicQuestionProjection;
import com.ayakovlev.interviewprep.dto.TopicWithQuestionsDto;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public List<TopicDto> findAllWithTranslation (String locale) {
        List<TopicDto> res = topicRepository.findAllWithTranslation(locale);
        return res;
    }

    public List<TopicWithQuestionsDto> findTopicsWithQuestions(Student student, String locale) {
        List<TopicQuestionProjection> rows = topicRepository.findTopicsWithQuestions(student.getId(), locale);
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

}
