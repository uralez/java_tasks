--liquibase formatted sql

--changeset ayakovlev:007_create_answer
CREATE TABLE answer (
    id       BIGSERIAL    PRIMARY KEY, -- это PostgreSQL-тип, автоинкремент для id
    dcre     TIMESTAMP    NOT NULL, -- даты создания и изменения, заполняет Spring аудит
    dmod     TIMESTAMP    NOT NULL,
    user_cre VARCHAR(255) NOT NULL, -- логины, заполняет Spring аудит
    user_mod VARCHAR(255) NOT NULL,

    text            TEXT            NOT NULL,
    grade           NUMERIC(2,1)    NOT NULL,
    answer_date     DATE            NOT NULL,
    student_id      BIGINT          NOT NULL REFERENCES student(id),
    question_id     BIGINT          NOT NULL REFERENCES question(id),
    evaluator_id    BIGINT          NOT NULL REFERENCES evaluator(id)
);