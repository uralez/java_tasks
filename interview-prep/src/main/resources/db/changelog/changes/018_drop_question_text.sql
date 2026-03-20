--liquibase formatted sql
--changeset ayakovlev:018

ALTER TABLE question DROP COLUMN text;