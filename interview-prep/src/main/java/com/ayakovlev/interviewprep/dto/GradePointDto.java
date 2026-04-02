package com.ayakovlev.interviewprep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GradePointDto {
    private Long        id;
    private LocalDate   answerDate;
    private BigDecimal  grade;
    private String      textPreview;
}
