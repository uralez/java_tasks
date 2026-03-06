package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Role;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for Student Entity.
 * Implements UserDetailsService to integrate with Spring Security.
 * Spring Security calls loadUserByUserName() during authentication.
 */

@Service
@RequiredArgsConstructor
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * called by Spring Security during login.
     * Loads student by login and returns it as UserDetails.
     * @param login
     * @return userDetails<login>
     * @throws UsernameNotFoundException when user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return studentRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found: " + login));
    }

    /**
     * Register a new student with encoded password and default role USER.
     * @param login
     * @param password
     * @param name
     * @param email
     * @return
     */
    public Student register(String login, String password, String name, String email){
        Student student = new Student();
        student.setLogin(login);
        student.setPassword(passwordEncoder.encode(password));
        student.setName(name);
        student.setEmail(email);
        student.setRole(Role.USER);
        return studentRepository.save(student);
    }
}
