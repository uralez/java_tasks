package com.ayakovlev.interviewprep.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicWithQuestionsDto {
    private Long topicId;
    private Integer topicOrderNumber;
    private String topicName;
    private Double answerCount;
    private Double avgGrade;
    private List<QuestionDto> questions;
}
