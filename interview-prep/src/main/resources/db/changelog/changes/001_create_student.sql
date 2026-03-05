--liquibase formatted sql
-- bla bla bla 

       
--changeset ayakovlev:001_create_student
-- Таблица студентов
-- Содержит данные для авторизации и роль пользователя
CREATE TABLE student
(
    id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id
    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255), -- без ограничений
    email    VARCHAR(255), -- без ограничений
    role     VARCHAR(50)  NOT NULL -- строка, хранит значение enum (USER, ADMIN, DEMO)
);

