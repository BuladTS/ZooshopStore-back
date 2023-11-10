ALTER TABLE product_images
    ADD name VARCHAR(255);

ALTER TABLE product_images
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE product_images
    ADD CONSTRAINT uc_product_images_name UNIQUE (name);

ALTER TABLE product_images
    ADD CONSTRAINT uc_product_images_url UNIQUE (url);