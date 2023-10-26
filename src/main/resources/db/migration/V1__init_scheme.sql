CREATE SEQUENCE IF NOT EXISTS category_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS product_image_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS product_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS promo_code_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE categories
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE product_images
(
    id         BIGINT       NOT NULL,
    product_id BIGINT       NOT NULL,
    url        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_product_images PRIMARY KEY (id)
);

CREATE TABLE products
(
    id          BIGINT       NOT NULL,
    category_id BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    slug        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    price       DECIMAL      NOT NULL,
    quantity    INTEGER      NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE promo_codes
(
    id          BIGINT   NOT NULL,
    value       VARCHAR(255),
    description TEXT,
    category_id BIGINT   NOT NULL,
    discount    SMALLINT NOT NULL,
    active      BOOLEAN  NOT NULL,
    CONSTRAINT pk_promo_codes PRIMARY KEY (id)
);

ALTER TABLE promo_codes
    ADD CONSTRAINT uc_promo_codes_value UNIQUE (value);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;

ALTER TABLE product_images
    ADD CONSTRAINT FK_PRODUCT_IMAGES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;

ALTER TABLE promo_codes
    ADD CONSTRAINT FK_PROMO_CODES_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;