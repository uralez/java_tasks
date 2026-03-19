package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.entity.Question;
import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<QuestionDto> findByTopicWithTranslation(Topic topic, String locale){
        return questionRepository.findByTopicWithTranslation(topic, locale);
    }
}
