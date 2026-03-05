package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a student who can log in and submit answers to interwiew questions.
 */
@Getter
@Setter
@Entity
public class Student extends BaseEntity{
    /**
     * Unique login name used for authentication.
     */
    @Column(nullable = false, unique = true)
    private String login;

    /**
     * Hashed password for authentication.
     * Plain text password is encoded by PasswordEncoder before saving.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Full name of teh student
     */
    @Column
    private String name;

    @Column
    private String email;

    /**
     * Role of the student in the system (USER or ADMIN).
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
