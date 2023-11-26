CREATE SEQUENCE IF NOT EXISTS rating_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE ratings
(
    id         BIGINT   NOT NULL,
    product_id BIGINT   NOT NULL,
    rating     SMALLINT NOT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_name UNIQUE (name);

ALTER TABLE ratings
    ADD CONSTRAINT FK_RATINGS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;