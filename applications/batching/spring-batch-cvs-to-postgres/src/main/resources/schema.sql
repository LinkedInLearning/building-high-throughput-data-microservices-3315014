DROP TABLE ms_transactions IF EXISTS;


CREATE TABLE ms_transactions  (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    details VARCHAR(20) NOT NULL
);

--CREATE TABLE people  (
--    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
--    first_name VARCHAR(20),
--    last_name VARCHAR(20)
--);

--ms_transactions (id, details)