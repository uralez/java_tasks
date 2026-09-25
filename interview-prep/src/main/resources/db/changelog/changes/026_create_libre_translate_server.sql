    --liquibase formatted sql
    --changeset ayakovlev:026

    CREATE TABLE libre_translate_server (
        id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id
        dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
        dmod     TIMESTAMP    NOT NULL,
        user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
        user_mod VARCHAR(255) NOT NULL,

        url                 VARCHAR(500) NOT NULL UNIQUE,
        available           BOOLEAN,
        last_checked_at     TIMESTAMP,
        last_available_at   TIMESTAMP
    )