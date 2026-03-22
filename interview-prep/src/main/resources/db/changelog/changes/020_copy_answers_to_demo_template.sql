--liquibase formatted sql
--changeset ayakovlev:020

INSERT INTO answer (dcre, dmod, user_cre, user_mod, text, grade, answer_date, student_id, question_id, evaluator_id)
SELECT now(), now(), 'admin', 'admin', a.text, a.grade, a.answer_date,
       (SELECT id FROM student WHERE login = 'demo_template'),
       a.question_id, a.evaluator_id
FROM answer a
JOIN student s ON s.id = a.student_id
WHERE s.login = 'Andrew';