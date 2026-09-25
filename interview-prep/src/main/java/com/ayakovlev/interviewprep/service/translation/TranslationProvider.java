package com.ayakovlev.interviewprep.service.translation;

public interface TranslationProvider {
    String translate(String text, String sourceLang, String targetLang);
    String getProviderName();
}
