--liquibase formatted sql

--changeset ayakovlev:002_alter_student_audit_nullable
ALTER TABLE student ALTER COLUMN user_cre DROP NOT NULL;
ALTER TABLE student ALTER COLUMN user_mod DROP NOT NULL;