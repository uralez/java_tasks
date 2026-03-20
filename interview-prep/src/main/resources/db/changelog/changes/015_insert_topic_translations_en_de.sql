--liquibase formatted sql
--changeset ayakovlev:015

INSERT INTO topic_translation (topic_id, locale, name, dcre, dmod, user_cre, user_mod)
SELECT id, 'en',
    CASE name
        WHEN 'Core Java'              THEN 'Core Java'
        WHEN 'Collections Framework'  THEN 'Collections Framework'
        WHEN 'Многопоточность'        THEN 'Multithreading'
        WHEN 'JVM и память'           THEN 'JVM and Memory'
        WHEN 'Исключения'             THEN 'Exceptions'
        WHEN 'ООП и принципы'         THEN 'OOP and Principles'
        WHEN 'Java 8+'                THEN 'Java 8+'
        WHEN 'Spring'                 THEN 'Spring'
        WHEN 'Базы данных и SQL'      THEN 'Databases and SQL'
        WHEN 'Практические вопросы'   THEN 'Practical Questions'
    END,
    dcre, dmod, user_cre, user_mod
FROM topic;

INSERT INTO topic_translation (topic_id, locale, name, dcre, dmod, user_cre, user_mod)
SELECT id, 'de',
    CASE name
        WHEN 'Core Java'              THEN 'Core Java'
        WHEN 'Collections Framework'  THEN 'Collections Framework'
        WHEN 'Многопоточность'        THEN 'Multithreading'
        WHEN 'JVM и память'           THEN 'JVM und Speicher'
        WHEN 'Исключения'             THEN 'Ausnahmen'
        WHEN 'ООП и принципы'         THEN 'OOP und Prinzipien'
        WHEN 'Java 8+'                THEN 'Java 8+'
        WHEN 'Spring'                 THEN 'Spring'
        WHEN 'Базы данных и SQL'      THEN 'Datenbanken und SQL'
        WHEN 'Практические вопросы'   THEN 'Praktische Fragen'
    END,
    dcre, dmod, user_cre, user_mod
FROM topic;