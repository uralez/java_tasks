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
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    private String name;
    private String email;
}
