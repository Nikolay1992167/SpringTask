--liquibase formatted sql
--changeset Minich:1
CREATE TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    uuid UUID NOT NULL UNIQUE,
    name VARCHAR(15) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    login VARCHAR(10) NOT NULL,
    password VARCHAR(10) NOT NULL,
    phone BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    user_type VARCHAR(10) NOT NULL,
    UNIQUE (name, surname, login, password, phone)
);