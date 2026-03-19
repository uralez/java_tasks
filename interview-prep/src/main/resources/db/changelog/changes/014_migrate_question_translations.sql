--liquibase formatted sql
--changeset ayakovlev:014

INSERT INTO question_translation (question_id, locale, text, dcre, dmod, user_cre, user_mod)
SELECT id, 'ru', text, dcre, dmod, user_cre, user_mod
FROM question;