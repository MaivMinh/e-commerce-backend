create table if not exists `categories`
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255),
    created_at  timestamp    not null,
    created_by  int          not null,
    updated_at  timestamp    not null,
    updated_by  int          not null,
);

create table if not exists `sub_categories`
(
    id          int auto_increment primary key,
    parent_id   int,
    name        varchar(255) not null,
    description varchar(255),
    created_at  timestamp    not null,
    created_by  int          not null,
    updated_at  timestamp    not null,
    updated_by  int          not null,
    foreign key (parent_id) references "categories" (id)
);

create table if not exists `products`
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255),
    cover       varchar(255),
    created_at  timestamp    not null,
    created_by  int          not null,
    updated_at  timestamp    not null,
    updated_by  int          not null
);

CREATE TABLE IF NOT EXISTS `product_images`
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT          NOT NULL,
    url        VARCHAR(255) NOT NULL,
    created_at timestamp    not null,
    created_by int          not null,
    updated_at timestamp    not null,
    updated_by int          not null,
    FOREIGN KEY (product_id) REFERENCES "products" (id)
);


create table if not exists `products_sub_categories`
(
    id              int auto_increment primary key,
    product_id      int       not null,
    sub_category_id int       not null,
    created_at      timestamp not null,
    created_by      int       not null,
    updated_at      timestamp not null,
    updated_by      int       not null,
    foreign key (product_id) references "products" (id),
    foreign key (sub_category_id) references "sub_categories" (id)
);

create table if not exists `product_skus`
(
    id                 int auto_increment primary key,
    product_id         int            not null,
    size_attribute_id  int,
    color_attribute_id int,
    price              decimal(10, 2) not null,
    quantity           int            not null,
    created_at         timestamp      not null,
    created_by         int            not null,
    updated_at         timestamp      not null,
    updated_by         int            not null,
);

create table if not exists `product_attributes`
(
    id         int auto_increment primary key,
    type       varchar(255) not null, -- ENUM ('color', 'size')
    value      varchar(255) not null,
    created_at timestamp    not null,
    created_by int          not null,
    updated_at timestamp    not null,
    updated_by int          not null,
);

create table if not exists `product_attributes_product_skus`
(
    id                   int auto_increment primary key,
    product_sku_id       int       not null,
    product_attribute_id int       not null,
    created_at           timestamp not null,
    created_by           int       not null,
    updated_at           timestamp not null,
    updated_by           int       not null,
    foreign key (product_sku_id) references "product_skus" (id),
    foreign key (product_attribute_id) references "product_attributes" (id)
);

create table if not exists `promotion_product_mappings`
(
    id             int auto_increment primary key,
    promotion_id   int       not null,
    product_id int       not null,
    created_at     timestamp not null,
    created_by     int       not null,
    updated_at     timestamp not null,
    updated_by     int       not null,
    foreign key (product_id) references "products" (id),
);

create table if not exists `wishlists`
(
    id         int auto_increment primary key,
    user_id    int       not null, -- one user just has one wishlist
    created_at timestamp not null,
    created_by int       not null,
    updated_at timestamp not null,
    updated_by int       not null,
);

create table if not exists `wishlists_products`
(
    id          int auto_increment primary key,
    wishlist_id int       not null,
    product_id  int       not null, -- will be referred to products table, not product_skus table.
    created_at  timestamp not null,
    created_by  int       not null,
    updated_at  timestamp not null,
    updated_by  int       not null,
    foreign key (wishlist_id) references "wishlists" (id),
    foreign key (product_id) references "products" (id)
);