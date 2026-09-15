package com.ayakovlev.interviewprep.dto;

public interface TopicQuestionProjection {
    Long getTopicId();
    Integer getTopicOrderNumber();
    String getTopicName();
    Long getQuestionId();
    Integer getQuestionOrderNumber();
    String getQuestionText();
    Long getAnswerCount();
    Double getQuestionAvgGrade();
    Double getTopicAvgAnswerCount();
    Double getTopicAvgGrade();
}
