package com.ayakovlev.interviewprep.service.exception;

public class TopicHasQuestionsException extends Exception{
    private final long questionCount;

    public TopicHasQuestionsException(long questionCount){
        super("Topic has " + questionCount + " questions");
        this.questionCount = questionCount;
    }

    public long getQuestionCount() {
        return questionCount;
    }
}
