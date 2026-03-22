--liquibase formatted sql
--changeset ayakovlev:021

ALTER TABLE answer
    DROP CONSTRAINT answer_student_id_fkey;

ALTER TABLE answer
    ADD CONSTRAINT answer_student_id_fkey
    FOREIGN KEY (student_id)
    REFERENCES student(id)
    ON DELETE CASCADE;