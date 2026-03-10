package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> findAll(){
        return topicRepository.findAll();
    }
}
