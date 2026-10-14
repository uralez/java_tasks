package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.dto.QuestionFormDto;
import com.ayakovlev.interviewprep.entity.Question;
import com.ayakovlev.interviewprep.entity.QuestionTranslation;
import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import com.ayakovlev.interviewprep.repository.QuestionRepository;
import com.ayakovlev.interviewprep.repository.QuestionTranslationRepository;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import com.ayakovlev.interviewprep.service.exception.InvalidOrderNumberException;
import com.ayakovlev.interviewprep.service.exception.QuestionNotFoundException;
import com.ayakovlev.interviewprep.service.exception.TopicNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionTranslationRepository questionTranslationRepository;
    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<QuestionDto> findByTopicWithTranslation(Topic topic, String locale){
        return questionRepository.findByTopicWithTranslation(topic, locale);
    }

    public Question findById(Long id) throws QuestionNotFoundException {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public Question createQuestion(Long topicId, QuestionFormDto dto)
            throws TopicNotFoundException, InvalidOrderNumberException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        validateOrderNumber(dto.getOrderNumber(), questionRepository.count() + 1);

        Question question = new Question();
        question.setOrderNumber(dto.getOrderNumber());
        question.setTopic(topic);
        questionRepository.save(question);

        List<QuestionTranslation> translations = dto.getTranslations().entrySet().stream()
                .map(entry -> {
                    QuestionTranslation translation = new QuestionTranslation();
                    translation.setQuestion(question);
                    translation.setLocale(entry.getKey().toLowerCase());
                    translation.setText(entry.getValue());
                    return translation;
                })
                .toList();
        questionTranslationRepository.saveAll(translations);
        question.setTranslations(translations);

        return question;
    }

    @Transactional(rollbackFor = Exception.class)
    public Question updateQuestion(Long id, QuestionFormDto dto) throws QuestionNotFoundException, InvalidOrderNumberException {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        validateOrderNumber(dto.getOrderNumber(), questionRepository.count());

        question.setOrderNumber(dto.getOrderNumber());
        questionRepository.save(question);

        List<QuestionTranslation> existingTranslations = question.getTranslations();
        existingTranslations.forEach(t -> {
            String newText = dto.getTranslations().get(t.getLocale().toUpperCase());
            if (newText != null) {
                t.setText(newText);
            }
        });
        questionTranslationRepository.saveAll(existingTranslations);

        return question;
    }

    @Transactional(rollbackFor = Exception.class)
    public Question deleteQuestion(Long id) throws QuestionNotFoundException {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        answerRepository.deleteAll(answerRepository.findByQuestionId(id));
        questionTranslationRepository.deleteAll(question.getTranslations());
        questionRepository.delete(question);

        return question;
    }

    private void validateOrderNumber (Integer orderNumber, long maxAllowed) throws InvalidOrderNumberException {
        if (orderNumber == null || orderNumber > maxAllowed || orderNumber < 1) {
            throw new InvalidOrderNumberException(maxAllowed);
        }
    }

    public long count() {
        return questionRepository.count();
    }
}
