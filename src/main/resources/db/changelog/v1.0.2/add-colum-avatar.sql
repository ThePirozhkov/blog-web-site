-- liquibase formatted sql

-- changeset romantimosenko:1776789963666-1
ALTER TABLE user_entity
ADD COLUMN avatar VARCHAR UNIQUE