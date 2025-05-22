CREATE TABLE `users`
(
    `id`            integer PRIMARY KEY,
    `avatar`        varchar(255),
    `first_name`    varchar(255),
    `last_name`     varchar(255),
    `username`      varchar(255) UNIQUE NOT NULL,
    `email`         varchar(255) UNIQUE NOT NULL,
    `password`      varchar(255),
    `birth_of_date` date,
    `phone_number`  varchar(255),
    `created_at`    timestamp,
    `deleted_at`    timestamp
);

CREATE TABLE `addresses`
(
    `id`             integer PRIMARY KEY,
    `user_id`        integer,
    `title`          varchar(255),
    `address_line_1` varchar(255),
    `address_line_2` varchar(255),
    `country`        varchar(255),
    `city`           varchar(255),
    `postal_code`    varchar(255),
    `landmark`       varchar(255),
    `phone_number`   varchar(255),
    `created_at`     timestamp,
    `deleted_at`     timestamp
);

CREATE TABLE `cart`
(
    `id`         integer PRIMARY KEY,
    `user_id`    integer,
    `total`      integer,
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `cart_item`
(
    `id`              integer PRIMARY KEY,
    `cart_id`         integer,
    `product_id`      integer,
    `products_sku_id` integer,
    `quantity`        integer,
    `created_at`      timestamp,
    `updated_at`      timestamp
);

CREATE TABLE `order_details`
(
    `id`         integer PRIMARY KEY,
    `user_id`    integer,
    `payment_id` integer,
    `total`      integer,
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `order_item`
(
    `id`              integer PRIMARY KEY,
    `order_id`        integer,
    `products_sku_id` integer,
    `quantity`        integer,
    `created_at`      timestamp,
    `updated_at`      timestamp,
    `status`          varchar(255)
);

CREATE TABLE `payment_details`
(
    `id`         integer PRIMARY KEY,
    `order_id`   integer,
    `amount`     integer,
    `provider`   varchar(255),
    `status`     varchar(255),
    `created_at` timestamp,
    `updated_at` timestamp
);

CREATE TABLE `promotions`
(
    `id`             integer PRIMARY KEY,
    `name`           varchar(255)   NOT NULL,
    `description`    varchar(255),
    `discount_type`  varchar(255)   NOT NULL,
    `discount_value` numeric(10, 2) NOT NULL,
    `quantity`       integer        NOT NULL,
    `is_active`      boolean        NOT NULL
);

CREATE TABLE `condition_types`
(
    `id`   integer PRIMARY KEY,
    `type` varchar(255)
);

CREATE TABLE `promotion_conditions`
(
    `id`                integer PRIMARY KEY,
    `promotion_id`      integer      NOT NULL,
    `condition_type_id` integer     NOT NULL,
    `condition_value`   varchar(255) NOT NULL,
    `operator`          varchar(255) NOT NULL
);

CREATE TABLE `promotion_product_mappings`
(
    `id`             integer PRIMARY KEY,
    `promotion_id`   integer NOT NULL,
    `product_sku_id` integer NOT NULL
);

ALTER TABLE `addresses`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

CREATE TABLE `products_sub_categories`
(
    `products_category_id` varchar,
    `sub_categories_id`    integer,
    PRIMARY KEY (`products_category_id`, `sub_categories_id`)
);

CREATE TABLE `product_attributes_products_skus`
(
    `product_attributes_id`           integer,
    `products_skus_size_attribute_id` integer,
    PRIMARY KEY (`product_attributes_id`, `products_skus_size_attribute_id`)
);

CREATE TABLE `product_attributes_products_skus(1)`
(
    `product_attributes_id`            integer,
    `products_skus_color_attribute_id` integer,
    PRIMARY KEY (`product_attributes_id`, `products_skus_color_attribute_id`)
);

CREATE TABLE `wishlist_users`
(
    `wishlist_user_id` integer,
    `users_id`         integer,
    PRIMARY KEY (`wishlist_user_id`, `users_id`)
);

ALTER TABLE `wishlist_users`
    ADD FOREIGN KEY (`users_id`) REFERENCES `users` (`id`);


CREATE TABLE `wishlist_products`
(
    `wishlist_product_id` integer,
    `products_id`         integer,
    PRIMARY KEY (`wishlist_product_id`, `products_id`)
);

ALTER TABLE `users`
    ADD FOREIGN KEY (`id`) REFERENCES `cart` (`user_id`);

ALTER TABLE `cart_item`
    ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

CREATE TABLE `cart_item_products`
(
    `cart_item_product_id` integer,
    `products_id`          integer,
    PRIMARY KEY (`cart_item_product_id`, `products_id`)
);

ALTER TABLE `cart_item_products`
    ADD FOREIGN KEY (`cart_item_product_id`) REFERENCES `cart_item` (`product_id`);


CREATE TABLE `cart_item_products_skus`
(
    `cart_item_products_sku_id` integer,
    `products_skus_id`          integer,
    PRIMARY KEY (`cart_item_products_sku_id`, `products_skus_id`)
);

ALTER TABLE `cart_item_products_skus`
    ADD FOREIGN KEY (`cart_item_products_sku_id`) REFERENCES `cart_item` (`products_sku_id`);


ALTER TABLE `users`
    ADD FOREIGN KEY (`id`) REFERENCES `order_details` (`user_id`);

ALTER TABLE `order_item`
    ADD FOREIGN KEY (`order_id`) REFERENCES `order_details` (`id`);

CREATE TABLE `order_item_products_skus`
(
    `order_item_products_sku_id` integer,
    `products_skus_id`           integer,
    PRIMARY KEY (`order_item_products_sku_id`, `products_skus_id`)
);

ALTER TABLE `order_item_products_skus`
    ADD FOREIGN KEY (`order_item_products_sku_id`) REFERENCES `order_item` (`products_sku_id`);


ALTER TABLE `order_details`
    ADD FOREIGN KEY (`id`) REFERENCES `payment_details` (`order_id`);

ALTER TABLE `promotion_conditions`
    ADD FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`);

ALTER TABLE `promotion_conditions`
    ADD FOREIGN KEY (`condition_type_id`) REFERENCES `condition_types` (`id`);

ALTER TABLE `promotion_product_mappings`
    ADD FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`);