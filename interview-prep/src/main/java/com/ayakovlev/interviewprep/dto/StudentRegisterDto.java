package com.ayakovlev.interviewprep.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "{validation.login.notBlank}")
    private String login;

    @NotBlank(message = "validation.password.notBlank")
    private String password;

    private String name;
    private String email;
}
