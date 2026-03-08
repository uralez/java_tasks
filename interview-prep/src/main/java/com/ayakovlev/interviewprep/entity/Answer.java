package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String text;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal grade;

    @Column(nullable = false)
    private LocalDate answerDate;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Evaluator evaluator;
}
