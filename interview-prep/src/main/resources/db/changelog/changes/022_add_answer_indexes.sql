--liquibase formatted sql
--changeset ayakovlev:022
CREATE INDEX idx_answer_student_id ON answer(student_id);
CREATE INDEX idx_answer_question_id ON answer(question_id);