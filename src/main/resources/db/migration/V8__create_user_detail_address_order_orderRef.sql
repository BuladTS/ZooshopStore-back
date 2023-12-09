CREATE SEQUENCE IF NOT EXISTS order_product_ref_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS order_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_address_generator START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE "order"
(
    id           BIGINT NOT NULL,
    user_id      BIGINT,
    address_id   BIGINT NOT NULL,
    promocode_id BIGINT,
    status       VARCHAR(255),
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_product_ref
(
    id         BIGINT        NOT NULL,
    order_id   BIGINT        NOT NULL,
    product_id BIGINT        NOT NULL,
    price      numeric(9, 2) NOT NULL,
    quantity   INTEGER       NOT NULL,
    CONSTRAINT pk_orderproductref PRIMARY KEY (id)
);

CREATE TABLE t_user_details
(
    user_id   BIGINT NOT NULL,
    email     VARCHAR(255),
    telephone VARCHAR(255),
    CONSTRAINT pk_t_user_details PRIMARY KEY (user_id)
);

CREATE TABLE t_users
(
    id       BIGINT NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255),
    CONSTRAINT pk_t_users PRIMARY KEY (id)
);

CREATE TABLE user_address
(
    id      BIGINT NOT NULL,
    user_id BIGINT,
    city    VARCHAR(255),
    street  VARCHAR(255),
    house   VARCHAR(255),
    flat    VARCHAR(255),
    CONSTRAINT pk_user_address PRIMARY KEY (id)
);

ALTER TABLE t_user_details
    ADD CONSTRAINT uc_t_user_details_email UNIQUE (email);

ALTER TABLE t_user_details
    ADD CONSTRAINT uc_t_user_details_telephone UNIQUE (telephone);

ALTER TABLE t_users
    ADD CONSTRAINT uc_t_users_username UNIQUE (username);

ALTER TABLE order_product_ref
    ADD CONSTRAINT FK_ORDERPRODUCTREF_ON_ORDER FOREIGN KEY (order_id) REFERENCES "order" (id);

ALTER TABLE order_product_ref
    ADD CONSTRAINT FK_ORDERPRODUCTREF_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES user_address (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_PROMOCODE FOREIGN KEY (promocode_id) REFERENCES promo_codes (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES t_users (id);

ALTER TABLE t_user_details
    ADD CONSTRAINT FK_T_USER_DETAILS_ON_USER FOREIGN KEY (user_id) REFERENCES t_users (id);

ALTER TABLE user_address
    ADD CONSTRAINT FK_USER_ADDRESS_ON_USER FOREIGN KEY (user_id) REFERENCES t_users (id);