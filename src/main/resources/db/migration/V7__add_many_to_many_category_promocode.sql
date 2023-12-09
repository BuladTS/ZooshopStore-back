CREATE TABLE category_promocode_ref
(
    category_id  BIGINT NOT NULL,
    promocode_id BIGINT NOT NULL
);

ALTER TABLE category_promocode_ref
    ADD CONSTRAINT fk_catproref_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE category_promocode_ref
    ADD CONSTRAINT fk_catproref_on_promo_code FOREIGN KEY (promocode_id) REFERENCES promo_codes (id);