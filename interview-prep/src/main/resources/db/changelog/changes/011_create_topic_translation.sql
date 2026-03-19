--liquibase formatted sql
--changeset ayakovlev:011

CREATE TABLE topic_translation (
    id          BIGSERIAL       PRIMARY KEY,
    topic_id    BIGINT          NOT NULL REFERENCES topic(id),
    locale      VARCHAR(10)     NOT NULL,
    name        VARCHAR(255)    NOT NULL,

    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,

    UNIQUE (topic_id, locale)
);