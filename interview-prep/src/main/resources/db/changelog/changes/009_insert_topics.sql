--liquibase formatted sql

--changeset ayakovlev:009_insert_topics
INSERT INTO topic (dcre, dmod, user_cre, user_mod, name) VALUES
(now(), now(), 'admin', 'admin', 'Core Java'),
(now(), now(), 'admin', 'admin', 'Collections Framework'),
(now(), now(), 'admin', 'admin', 'Многопоточность'),
(now(), now(), 'admin', 'admin', 'JVM и память'),
(now(), now(), 'admin', 'admin', 'Исключения'),
(now(), now(), 'admin', 'admin', 'ООП и принципы'),
(now(), now(), 'admin', 'admin', 'Java 8+'),
(now(), now(), 'admin', 'admin', 'Spring'),
(now(), now(), 'admin', 'admin', 'Базы данных и SQL'),
(now(), now(), 'admin', 'admin', 'Практические вопросы');