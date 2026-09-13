--liquibase formatted sql
--changeset ayakovlev:023
ALTER TABLE student ADD COLUMN preferred_language VARCHAR(5);