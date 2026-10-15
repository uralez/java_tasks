--liquibase formatted sql
--changeset ayakovlev:028

UPDATE question q
SET order_number = r.new_number
FROM (
    SELECT id, ROW_NUMBER() OVER (PARTITION BY topic_id ORDER BY order_number, id) AS new_number
    FROM question
) r
WHERE q.id = r.id;