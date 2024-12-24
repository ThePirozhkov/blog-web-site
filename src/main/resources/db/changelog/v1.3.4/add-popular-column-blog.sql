-- liquibase formatted sql

-- changeset romantimosenko:1733925278189-1
ALTER TABLE public.blog_entity
    ADD popular BOOLEAN DEFAULT (false);

-- changeset romantimosenko:1733925278189-2
ALTER TABLE public.blog_entity
    ALTER COLUMN popular SET NOT NULL;

-- changeset romantimosenko:1733925278189-3
ALTER TABLE public.blog_entity
    ADD CONSTRAINT FK_BLOG_ENTITY_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES public.user_entity (id);

