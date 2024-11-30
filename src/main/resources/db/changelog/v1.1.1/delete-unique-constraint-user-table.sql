-- liquibase formatted sql

-- changeset romantimosenko:1733002785682-1
ALTER TABLE public.user_entity
    ALTER COLUMN avatar TYPE VARCHAR(255) USING (avatar::VARCHAR(255));

-- changeset romantimosenko:1733002785682-2
ALTER TABLE public.user_entity
    ALTER COLUMN avatar DROP NOT NULL;

-- changeset romantimosenko:1733002785682-3
ALTER TABLE public.user_entity
    ALTER COLUMN restorekey TYPE VARCHAR(255) USING (restorekey::VARCHAR(255));

