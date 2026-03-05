package com.ayakovlev.interviewprep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base class for all entities.
 * Contains common audit fields automatically filled by Spring Data JPA auditing.
 *
 * **@Getter and @Setter** — Lombok annotations that auto-generate bytecode for getters and setters in class files,
 * eliminating boilerplate code.
 *
 * **@MappedSuperclass** — a Jakarta Persistence annotation that instructs Hibernate to treat this class as a base class
 * whose fields should be included in the tables of all child Entity classes. Note: only those child Entity classes that
 * explicitly declare `extends BaseEntity` will inherit these fields. Classes that don't need this behavior
 * can either not inherit from this base class or inherit from a different one.
 * To enable automatic filling of audit fields, `@EnableJpaAuditing` must also be placed on the main application class.
 *
 * **@EntityListeners(AuditingEntityListener.class)** — instructs Spring to invoke the specified listener(s) whenever
 * an entity is inserted or updated. The built-in `AuditingEntityListener` automatically fills in dates and the current
 * user. A custom listener class can be provided instead to write any desired values. Multiple listeners can be
 * specified using curly braces: `@EntityListeners({AuditingEntityListener.class, MyCustomListener.class})`.
 * */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * **@Column(updatable = false)** — this field will not be included in the SQL `UPDATE` statement.
     * The field can still be modified on the Java object, but the change will not be persisted to the database.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dcre;

    @LastModifiedDate
    private LocalDateTime dmod;

    @CreatedBy
    @Column(updatable = false)
    private String userCre;

    @LastModifiedBy
    private String userMod;
}
