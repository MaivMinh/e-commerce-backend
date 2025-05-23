create table if not exists `categories`
(
    id           varchar(255) primary key,
    name         varchar(255) not null,
    description  varchar(255),
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

create table if not exists `sub_categories`
(
    id           varchar(255) primary key,
    parent_id    varchar(255),
    name         varchar(255) not null,
    description  varchar(255),
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    foreign key (parent_id) references categories (id)
);
create index idx_sub_categories_parent_id on sub_categories (parent_id);

create table if not exists `products`
(
    id           varchar(255) primary key,
    name         varchar(255) not null,
    description  varchar(255),
    cover        varchar(255),
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `product_images`
(
    id           varchar(255) PRIMARY KEY,
    product_id   varchar(255) NOT NULL,
    url          VARCHAR(255) NOT NULL,
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id)
);
create index idx_product_images_product_id on product_images (product_id);


create table if not exists `products_sub_categories`
(
    id              varchar(255) primary key,
    product_id      varchar(255) not null,
    sub_category_id varchar(255) not null,
    `created_at`    date         NOT NULL,
    `created_by`    varchar(20)  NOT NULL,
    `updated_at`    date        DEFAULT NULL,
    `updated_by`    varchar(20) DEFAULT NULL,
    foreign key (product_id) references products (id),
    foreign key (sub_category_id) references sub_categories (id)
);
create index idx_products_sub_categories_product_id on products_sub_categories (product_id);

create table if not exists `product_skus`
(
    id                 varchar(255) primary key,
    product_id         varchar(255)   not null,
    size_attribute_id  varchar(255),
    color_attribute_id varchar(255),
    price              decimal(10, 2) not null,
    quantity           int            not null,
    `created_at`       date           NOT NULL,
    `created_by`       varchar(20)    NOT NULL,
    `updated_at`       date        DEFAULT NULL,
    `updated_by`       varchar(20) DEFAULT NULL
);
create index idx_product_skus_size_attribute_id on product_skus (size_attribute_id);
create index idx_product_skus_color_attribute_id on product_skus (color_attribute_id);


create table if not exists `product_attributes`
(
    id           varchar(255) primary key,
    type         varchar(255) not null, -- ENUM ('color', 'size')
    value        varchar(255) not null,
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

create table if not exists `product_attributes_product_skus`
(
    id                   varchar(255) primary key,
    product_sku_id       varchar(255) not null,
    product_attribute_id varchar(255) not null,
    `created_at`         date         NOT NULL,
    `created_by`         varchar(20)  NOT NULL,
    `updated_at`         date        DEFAULT NULL,
    `updated_by`         varchar(20) DEFAULT NULL,
    foreign key (product_sku_id) references product_skus (id),
    foreign key (product_attribute_id) references product_attributes (id)
);
create index idx_product_attributes_product_skus_product_sku_id on product_attributes_product_skus (product_sku_id);
create index idx_product_attributes_product_skus_product_attribute_id on product_attributes_product_skus (product_attribute_id);

create table if not exists `promotions`
(
    id           varchar(255) primary key,
    name         varchar(255) not null,
    description  varchar(255),
    start_date   timestamp    not null,
    end_date     timestamp    not null,
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

create table if not exists `promotion_product_mappings`
(
    id           varchar(255) primary key,
    promotion_id varchar(255) not null,
    product_id   varchar(255) not null,
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    foreign key (product_id) references products (id)
);
create index idx_promotion_product_mappings_product_id on promotion_product_mappings (product_id);

create table if not exists `wishlists`
(
    id           varchar(255) primary key,
    user_id      varchar(255) not null, -- one user just has one wishlist
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);
create index idx_wishlists_user_id on wishlists (user_id);

create table if not exists `wishlists_products`
(
    id           varchar(255) primary key,
    wishlist_id  varchar(255) not null,
    product_id   varchar(255) not null, -- will be referred to products table, not product_skus table.
    `created_at` date         NOT NULL,
    `created_by` varchar(20)  NOT NULL,
    `updated_at` date        DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    foreign key (wishlist_id) references wishlists (id),
    foreign key (product_id) references products (id)
);

create index idx_wishlists_products_product_id on wishlists_products (product_id);