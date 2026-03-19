--liquibase formatted sql
--changeset ayakovlev:012

CREATE TABLE question_translation (
    id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id

    question_id BIGINT      NOT NULL REFERENCES question(id),
    locale      VARCHAR(10) NOT NULL,
    text        TEXT        NOT NULL,

    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,

    UNIQUE (question_id, locale)
);