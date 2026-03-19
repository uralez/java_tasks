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

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<QuestionTranslation> translations;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private Topic topic;
}
