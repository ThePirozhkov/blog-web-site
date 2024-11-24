-- liquibase formatted sql

-- changeset romantimosenko:1776789963775-1
ALTER TABLE user_entity
ADD COLUMN restoreKey VARCHAR UNIQUE NOT NULL DEFAULT (RANDOM());