package com.ayakovlev.interviewprep.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicWithQuestionsDto {
    private Long topicId;
    private String topicName;
    private Long answerCount;
    private Double avgGrade;
    private List<QuestionDto> questions;
}
