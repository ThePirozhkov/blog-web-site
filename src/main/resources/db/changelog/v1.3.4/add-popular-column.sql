-- liquibase formatted sql

-- changeset romantimosenko:1733925057908-1
ALTER TABLE public.user_entity
    ADD popular BOOLEAN DEFAULT (false);

-- changeset romantimosenko:1733925057908-2
ALTER TABLE public.user_entity
    ALTER COLUMN popular SET NOT NULL;

