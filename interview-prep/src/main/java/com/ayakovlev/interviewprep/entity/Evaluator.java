package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Evaluator extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
}
