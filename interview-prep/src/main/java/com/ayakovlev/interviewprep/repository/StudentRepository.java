package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Student entity.
 * Extends JpaRepository which provides standard CRUD operations out of teh box:
 * save(), findById(), findAll, deleteById() and others.
 */

public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * Finds a student by login.
     * Spring Data JPA automatically generates the SQL query based on the method name:
     * SELECT * FROM student WHERE login = ?
     *
     * There is no need to write the findByLogin method manually - Spring Data JPA generates the implementation
     * automatically based on the method name.
     */
    Optional<Student> findByLogin(String login);
}
