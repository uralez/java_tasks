package com.ayakovlev.interviewprep.service.exception;

public class TopicNotFoundException extends Exception{
    private final Long topicId;
    public TopicNotFoundException(Long topicId){
        super("Topic not found: " + topicId);
        this.topicId = topicId;
    }
    public Long getTopicId(){
        return topicId;
    }
}
