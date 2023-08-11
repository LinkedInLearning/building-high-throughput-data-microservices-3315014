---- liquibase formatted sql
---- changeset liquibaseuser:1

CREATE SCHEMA IF NOT EXISTS payment;

---- rollback DROP TABLE payment.payments ;

CREATE TABLE payment.payments  (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    details VARCHAR(20) NULL,
    contact VARCHAR(20) NULL,
    location VARCHAR(20) NULL,
    amount NUMERIC(20,3) NOT NULL,
    "timestamp" timestamp
);

---- rollback drop table products;
