--liquibase formatted sql

--changeset ayakovlev:006_create_question
CREATE TABLE question (
    id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id
    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,

    text        TEXT    NOT NULL,
    topic_id    BIGINT  NOT NULL REFERENCES topic(id)
);