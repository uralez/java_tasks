package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Topic extends BaseEntity{

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<TopicTranslation> translations;
}
