--liquibase formatted sql

--changeset ayakovlev:1
CREATE TABLE users (
    -- id is audit field
    id BIGSERIAL PRIMARY KEY,

    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255) NOT NULL,

    -- audit fields
    dcre TIMESTAMP,
    dmod TIMESTAMP,
    user_cre VARCHAR(255),
    user_mod VARCHAR(255)
);