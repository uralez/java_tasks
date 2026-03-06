package com.ayakovlev.interviewprep.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for student registration form.
 * DTO is used to transfer data between the view and the controller
 * without exposing the entity directly.
 */
@Getter
@Setter
public class StudentRegisterDto {
    private String login;
    private String password;
    private String name;
    private String email;
}
