--liquibase formatted sql
--changeset ayakovlev:013

INSERT INTO topic_translation(topic_id, locale, name, dcre, dmod, user_cre, user_mod)
SELECT id, 'ru', name, dcre, dmod, user_cre, user_mod
FROM topic;