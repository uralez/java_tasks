package com.ayakovlev.interviewprep.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TopicFormDto {
    private Integer orderNumber;
    private Map<String, String> translations;
}
