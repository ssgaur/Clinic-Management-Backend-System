

--liquibase formatted sql
--changeset shailendra.singh:1.2

SET search_path TO public;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tbl_user(
    id              UUID  PRIMARY KEY DEFAULT uuid_generate_v4(),
    phone_number    VARCHAR(255),
    first_name      VARCHAR(255)  NOT NULL,
    last_name       VARCHAR(255)  NOT NULL,
    gender          VARCHAR(255)  NOT NULL,
    email           VARCHAR(255)  NOT NULL,
    password        TEXT  NOT NULL,
    user_meta        jsonb,
    is_verified     BOOLEAN DEFAULT FALSE,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT NOW()
);

CREATE INDEX email_index_users        ON tbl_user (email);
CREATE INDEX phone_number_index_users ON tbl_user (phone_number);

--rollback DROP INDEX email_index_users;
--rollback DROP INDEX phone_number_index_users;
--rollback DROP TABLE tbl_user;