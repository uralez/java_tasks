--liquibase formatted sql
--changeset ayakovlev:024
ALTER TABLE topic ADD COLUMN order_number INTEGER;

UPDATE topic
SET order_number = sub.rn
FROM (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM topic
) sub
WHERE topic.id = sub.id;

ALTER TABLE topic ALTER COLUMN order_number SET NOT NULL;