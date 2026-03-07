--liquibase formatted sql

--changeset ayakovlev:003_add_check_constraints
ALTER TABLE student ADD CONSTRAINT chk_login_not_empty CHECK (length(trim(login)) > 0);
ALTER TABLE student ADD CONSTRAINT chk_password_not_empty CHECK (length(trim(password)) > 0);