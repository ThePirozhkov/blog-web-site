-- liquibase formatted sql

-- changeset romantimosenko:1733330363560-1
CREATE TABLE public.like_entity
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id BIGINT,
    blog_id BIGINT,
    CONSTRAINT pk_like_entity PRIMARY KEY (id)
);

-- changeset romantimosenko:1733330363560-2
ALTER TABLE public.like_entity
    ADD CONSTRAINT FK_LIKE_ENTITY_ON_BLOG FOREIGN KEY (blog_id) REFERENCES public.blog_entity (id);

-- changeset romantimosenko:1733330363560-3
ALTER TABLE public.like_entity
    ADD CONSTRAINT FK_LIKE_ENTITY_ON_USER FOREIGN KEY (user_id) REFERENCES public.user_entity (id);

