--liquibase formatted sql
--changeset ayakovlev:017

ALTER TABLE topic DROP COLUMN name;