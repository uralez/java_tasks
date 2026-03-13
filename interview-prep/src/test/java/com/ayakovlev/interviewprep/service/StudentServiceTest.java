package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Role;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;


    @Test
    void loadUserByUsername_whenStudentExists_returnsUserDetails() {
        // arrange
        Student student = new Student();
        student.setLogin("Andrew");
        when(studentRepository.findByLogin("Andrew")).thenReturn(Optional.of(student));

        // act
        UserDetails result = studentService.loadUserByUsername("Andrew");

        // asset
        assertNotNull(result);
        assertEquals("Andrew", result.getUsername());
    }

    @Test
    void loadUserByUsername_whenStudentNotFound_throwsUsernameNotFoundException(){
        // arrange
        when(studentRepository.findByLogin("unknown")).thenReturn(Optional.empty());

        // act & assrt
        // FI = org.junit.jupiter.api.function.Executable
        // SAM = void execute() throws Throwable;
        assertThrows(UsernameNotFoundException.class, () -> studentService.loadUserByUsername("unknown"));
    }

    @Test
    void register_saveStudentWithEncodedPasswordAndRoleUser(){
        // arrange
        when(passwordEncoder.encode("password123")).thenReturn("encoded123");
        Student saved = new Student();
        saved.setLogin("Andrew");
        saved.setPassword("encoded123");
        saved.setRole(Role.USER);
        when(studentRepository.save(any(Student.class))).thenReturn(saved);

        // act
        Student result = studentService.register("Andrew", "password123", "Andrew", "andrew@example.com");

        // assert
        assertEquals("encoded123", result.getPassword());
        assertEquals(Role.USER, result.getRole());
        verify(passwordEncoder).encode("password123");
        verify(studentRepository).save(any(Student.class));
    }
}
