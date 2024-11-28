-- liquibase formatted sql

-- changeset romantimosenko:1776789963667-1
ALTER TABLE user_entity
ALTER COLUMN avatar SET DEFAULT ('/images/avatar.png');

ALTER TABLE user_entity
ALTER COLUMN avatar SET NOT NULL;