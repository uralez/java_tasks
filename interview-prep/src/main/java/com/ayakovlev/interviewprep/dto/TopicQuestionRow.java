package com.ayakovlev.interviewprep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicQuestionRow {
    private String topicName;
    private Long questionId;
    private String questionText;
    private Long answerCount;
    private Double avgGrade;
}
