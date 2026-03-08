package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll(){
        return topicRepository.findAll();
    }
}
