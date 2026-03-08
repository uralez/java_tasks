--liquibase formatted sql

--changeset ayakovlev:005_create_evaluator
CREATE TABLE evaluator (
    id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id
    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,

    name    VARCHAR(255) NOT NULL UNIQUE
);