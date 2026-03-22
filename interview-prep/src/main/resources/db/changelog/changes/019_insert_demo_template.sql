--liquibase formatted sql
--changeset ayakovlev:019

INSERT INTO student (dcre, dmod, user_cre, user_mod, login, password, name, email, role)
VALUES (now(), now(), 'admin', 'admin', 'demo_template', 'not_a_real_password', 'Demo Template', null, 'DEMO_TEMPLATE');