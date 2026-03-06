package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents a student who can log in and submit answers to interwiew questions.
 * Implements UserDetails to integrate with Spring Security.
 */
@Getter
@Setter
@Entity
public class Student extends BaseEntity implements UserDetails {
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

    // --- UserDetails ---

    /**
     * Returns the authorities (roles) granted to the student.
     * ROLE_ prefix is required by Spring Security.
     * @return list of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Returns login as username for Spring Security.
     * @return login
     */
    public String getUsername(){
        return login;
    }

    @Override
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isEnabled(){return true;}

}
