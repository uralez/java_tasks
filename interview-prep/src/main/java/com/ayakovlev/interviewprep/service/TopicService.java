package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.*;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.entity.TopicTranslation;
import com.ayakovlev.interviewprep.repository.QuestionRepository;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import com.ayakovlev.interviewprep.repository.TopicTranslationRepository;
import com.ayakovlev.interviewprep.service.exception.InvalidOrderNumberException;
import com.ayakovlev.interviewprep.service.exception.TopicHasQuestionsException;
import com.ayakovlev.interviewprep.service.exception.TopicNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicTranslationRepository topicTranslationRepository;
    private final QuestionRepository questionRepository;

    public List<TopicDto> findAllWithTranslation (String locale) {
        List<TopicDto> res = topicRepository.findAllWithTranslation(locale);
        return res;
    }

    public List<TopicWithQuestionsDto> findTopicsWithQuestions(Student student, String locale) {
        List<TopicQuestionProjection> rows = topicRepository.findTopicsWithQuestions(student.getId(), locale);
        Map<Long, TopicWithQuestionsDto> map = new LinkedHashMap<>();

        int globalNumber = 0;
        for (TopicQuestionProjection row : rows){
            TopicWithQuestionsDto topic = map.computeIfAbsent(
                    row.getTopicId(),
                    k -> new TopicWithQuestionsDto(
                            row.getTopicId(),
                            row.getTopicOrderNumber(),
                            row.getTopicName(),
                            row.getTopicAvgAnswerCount(),
                            row.getTopicAvgGrade(),
                            new ArrayList<>()
                    )
            );
            QuestionDto question = new QuestionDto(row.getQuestionId(), row.getQuestionOrderNumber(), row.getQuestionText());
            if (row.getQuestionId() != null) {
                question.setGlobalNumber(++globalNumber);
            }
            topic.getQuestions().add(question);
        }

        return new ArrayList<>(map.values());
    }

    @Transactional
    public Topic createTopic(TopicFormDto dto) throws InvalidOrderNumberException {
        long maxAllowed = topicRepository.count() + 1;
        validateOrderNumber(dto.getOrderNumber(), maxAllowed);

        topicRepository.shiftUpFrom(dto.getOrderNumber());

        Topic topic = new Topic();
        topic.setOrderNumber(dto.getOrderNumber());
        topicRepository.save(topic);

        List<TopicTranslation> translations = dto.getTranslations().entrySet().stream()
                .map(entry -> {
                    TopicTranslation translation = new TopicTranslation();
                    translation.setTopic(topic);
                    translation.setLocale(entry.getKey().toLowerCase());
                    translation.setName(entry.getValue());
                    return translation;
                })
                .toList();
        topicTranslationRepository.saveAll(translations);

        // excessive action
        topic.setTranslations(translations);

        return topic;
    }

    @Transactional
    public Topic updateTopic(Long id, TopicFormDto dto) throws InvalidOrderNumberException, TopicNotFoundException {
        long maxAllowed = topicRepository.count();
        validateOrderNumber(dto.getOrderNumber(), maxAllowed);

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        int oldNum = topic.getOrderNumber();
        int newNum = dto.getOrderNumber();
        if(newNum > oldNum) topicRepository.shiftDownBetween(oldNum, newNum);
        else if(newNum < oldNum) topicRepository.shiftUpBetween(oldNum, newNum);

        topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topic.setOrderNumber(newNum);

//        topic.setOrderNumber(dto.getOrderNumber());

        topicRepository.save(topic);

        List<TopicTranslation> existingTranslations = topic.getTranslations();
        existingTranslations.forEach(t -> {
            String newText = dto.getTranslations().get(t.getLocale().toUpperCase());
            if (newText != null) {
                t.setName(newText);
            }
        });
        topicTranslationRepository.saveAll(existingTranslations);

        return topic;
    }

    @Transactional
    public Topic deleteTopic(Long topicId) throws TopicNotFoundException, TopicHasQuestionsException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));

        long questionCount = questionRepository.countByTopicId(topicId);
        if (questionCount > 0){
            throw new TopicHasQuestionsException(questionCount);
        }

        List<TopicTranslation> translations = topic.getTranslations();

        topicTranslationRepository.deleteAll(topic.getTranslations());
        topicRepository.delete(topic);

        topicRepository.shiftDownAfter(topic.getOrderNumber());

        // excessive action
        topic.setTranslations(translations);
        return topic;
    }

    private void validateOrderNumber(Integer orderNumber, long maxAllowed) throws InvalidOrderNumberException {
        if (orderNumber == null || orderNumber > maxAllowed || orderNumber < 1) {
            throw new InvalidOrderNumberException(maxAllowed);
        }
    }

    public long getCount() {
        return topicRepository.count();
    }

    public Topic findById(Long id) throws TopicNotFoundException{
        return topicRepository.findById(id)
                .orElseThrow(() -> {
                    return new TopicNotFoundException(id);
                });
    }

}
