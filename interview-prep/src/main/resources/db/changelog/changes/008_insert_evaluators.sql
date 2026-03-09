--liquibase formatted sql

--changeset ayakovlev:008_insert_evaluators
INSERT INTO evaluator (dcre, dmod, user_cre, user_mod, name) VALUES
(now(), now(), 'admin', 'admin', 'ChatGPT'),
(now(), now(), 'admin', 'admin', 'Claude');