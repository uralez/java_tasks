--liquibase formatted sql
--changeset ayakovlev:025
ALTER TABLE question ADD COLUMN order_number INTEGER;

UPDATE question
SET order_number = sub.rn
FROM (
    SELECT id, ROW_NUMBER() OVER (ORDER BY topic_id, id) AS rn
    FROM question
) sub
WHERE question.id = sub.id;

ALTER TABLE question ALTER COLUMN order_number SET NOT NULL;
