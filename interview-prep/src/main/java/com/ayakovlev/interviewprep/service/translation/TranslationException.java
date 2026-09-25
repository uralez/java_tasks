package com.ayakovlev.interviewprep.service.translation;

public class TranslationException extends RuntimeException {
    public TranslationException(String message, Throwable cause){
        super(message, cause);
    }
}
