-- liquibase formatted sql

-- changeset romantimosenko:1776789026075-1
INSERT INTO user_entity
VALUES
    (default, 'testadmin', '{noop}testpass', 'testemail@test.com', 'ADMIN');