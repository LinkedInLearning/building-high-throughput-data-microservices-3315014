CREATE SCHEMA IF NOT EXISTS payment;

DROP TABLE IF EXISTS payment.payments ;

CREATE TABLE payment.payments  (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    details VARCHAR(20) NULL,
    contains VARCHAR(20) NOT NULL
);