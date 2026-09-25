package com.ayakovlev.interviewprep.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TranslateRequestDto {
    private String text;
    private String sourceLang;
    private List<String> targetLangs;
}
