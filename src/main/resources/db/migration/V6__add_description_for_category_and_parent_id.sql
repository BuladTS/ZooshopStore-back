ALTER TABLE categories
    ADD description TEXT;

ALTER TABLE categories
    ADD parent_id BIGINT;

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_PARENT FOREIGN KEY (parent_id) REFERENCES categories (id);