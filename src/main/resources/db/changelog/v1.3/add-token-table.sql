-- liquibase formatted sql

-- changeset romantimosenko:1733330363561-1
CREATE TABLE user_tokens (
                             series VARCHAR(64) NOT NULL PRIMARY KEY,
                             username VARCHAR(64) NOT NULL,
                             token VARCHAR(64) NOT NULL,
                             last_used TIMESTAMP
)