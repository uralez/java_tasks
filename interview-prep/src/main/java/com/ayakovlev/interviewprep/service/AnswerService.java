package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> findByStudent(Student student){
        return answerRepository.findByStudent(student);
    }

    public void save(Answer answer){
        answerRepository.save(answer);
    }
}
