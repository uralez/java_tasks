package com.ayakovlev.interviewprep.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Question extends BaseEntity{

    @Column (nullable = false)
    private Integer orderNumber;

    /**
     * mappedBy = "question" => эта сторона связи - не владеющая. Связью управляет класс QuestionTranslation через поле question .
     * fetch = FetchType.LAZY - добавлено только для ясности, по дефолту у аннотации @OneToMany и так такое поведение.
     */
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionTranslation> translations;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private Topic topic;
}
