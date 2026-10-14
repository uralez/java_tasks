package com.ayakovlev.interviewprep.service.exception;

public class QuestionNotFoundException extends Exception {
    private final Long questionId;

    public QuestionNotFoundException (Long questionId){
        super("Question not found: " + questionId);
        this.questionId = questionId;
    }

    public Long getQuestionId(){
        return questionId;
    }
}
