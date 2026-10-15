package com.ayakovlev.interviewprep.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class QuestionDto {
    private final Long id;
    private final Integer orderNumber;
    private final String text;
    @Setter
    private Integer globalNumber;
}
