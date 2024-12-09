-- liquibase formatted sql

-- changeset romantimosenko:1733589723983-1
ALTER TABLE public.user_entity
    ADD created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW());

-- changeset romantimosenko:1733589723983-2
ALTER TABLE public.user_entity
    ALTER COLUMN created_at SET NOT NULL;

