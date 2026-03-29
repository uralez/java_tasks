package com.ayakovlev.interviewprep.dto;

import com.ayakovlev.interviewprep.entity.Answer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AnswerPageDto {
    private Long id;
    private String text;
    private BigDecimal grade;
    private LocalDate answerDate;
    private Long evaluatorId;
    private String evaluatorName;
    private int index;

    public AnswerPageDto(Answer a, int index){
        this.id             = a.getId();
        this.text           = a.getText();
        this.grade          = a.getGrade();
        this.answerDate     = a.getAnswerDate();
        this.evaluatorId    = a.getEvaluator().getId();
        this.evaluatorName  = a.getEvaluator().getName();
        this.index          = index;
    }
}
