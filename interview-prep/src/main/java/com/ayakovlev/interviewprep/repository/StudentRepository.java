package com.ayakovlev.interviewprep.repository;

import com.ayakovlev.interviewprep.entity.Role;
import com.ayakovlev.interviewprep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * Deletes expired DEMO students directly via a single DELETE query.
     * Associated answers are removed automatically via ON DELETE CASCADE on answer.student_id.
     * Without explicit @Query, Spring Data JPA would generate a SELECT first,
     * then delete each entity individually - resulting in 2n+1 queries.
     */
    @Modifying
    @Query("DELETE FROM Student s WHERE s.role = :role AND s.dcre < :cutoff")
    int deleteByRoleAndDcreBefore(@Param("role") Role role, @Param("cutoff") LocalDateTime cutoff);
}
