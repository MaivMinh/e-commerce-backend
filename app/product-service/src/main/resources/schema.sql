-- 2. Quản lý Sản Phẩm
CREATE TABLE Categories
(
    id          VARCHAR(36) PRIMARY KEY,
    parent_id   VARCHAR(36),
    name        VARCHAR(100)                NOT NULL,
    slug        VARCHAR(120)                NOT NULL UNIQUE,
    description TEXT,
    image       VARCHAR(255),
    status      ENUM ('active', 'inactive') NOT NULL DEFAULT 'active',
    created_at  TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  varchar(255)                NOT NULL,
    updated_at  TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by  varchar(255)                         DEFAULT NULL,
    FOREIGN KEY (parent_id) REFERENCES Categories (id) ON DELETE SET NULL
);

CREATE TABLE Products
(
    id             VARCHAR(36) PRIMARY KEY,
    name           VARCHAR(255)                                NOT NULL,
    slug           VARCHAR(300)                                NOT NULL UNIQUE,
    description    TEXT,
    cover_image    VARCHAR(255),
    price          DECIMAL(15, 2)                              NOT NULL,
    original_price DECIMAL(15, 2),
    status         ENUM ('active', 'inactive', 'out_of_stock') NOT NULL DEFAULT 'active',
    is_featured    BOOLEAN                                     NOT NULL DEFAULT FALSE,
    is_new         BOOLEAN                                     NOT NULL DEFAULT FALSE,
    is_bestseller  BOOLEAN                                     NOT NULL DEFAULT FALSE,
    category_id    VARCHAR(36),
    created_at     TIMESTAMP                                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     varchar(255)                                NOT NULL,
    updated_at     TIMESTAMP                                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     varchar(255)                                         DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories (id) ON DELETE SET NULL
);

CREATE TABLE ProductImages
(
    id            VARCHAR(36) PRIMARY KEY,
    product_id    VARCHAR(36)  NOT NULL,
    image_url     VARCHAR(255) NOT NULL,
    display_order INT          NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    varchar(255) NOT NULL,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by    varchar(255)          DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE
);

CREATE TABLE ProductVariants
(
    id             VARCHAR(36) PRIMARY KEY,
    product_id     VARCHAR(36)    NOT NULL,
    -- Trong các biến thể của Product, sẽ không có 2 variant nào có cùng size và color_hex.
    -- Điều này đảm bảo rằng mỗi biến thể là duy nhất theo size và color.
    -- Khi client nhấn chọn size, thì hệ thống sẽ lọc ra các biến thể có size đó. Tiếp đó, người dùng chọn color, và sẽ chỉ hiển thị ra 1 biến thể duy nhất đi kèm số lượng.
    size           VARCHAR(20)    NOT NULL,
    color_name     VARCHAR(50)    NOT NULL,
    color_hex      VARCHAR(10),
    price          DECIMAL(15, 2) NOT NULL,
    stock_quantity INT            NOT NULL DEFAULT 0,
    sku            VARCHAR(100)   NOT NULL UNIQUE,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     varchar(255)   NOT NULL,
    updated_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     varchar(255)            DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE
);

-- 7. Danh Sách Yêu Thích
CREATE TABLE Wishlists
(
    id         VARCHAR(36)  PRIMARY KEY,
    account_id    VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255) NOT NULL,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by varchar(255)          DEFAULT NULL,
    UNIQUE KEY (account_id) -- mỗi người dùng chỉ có một danh sách yêu thích
);

CREATE TABLE IF NOT EXISTS WishlistProducts
(
    id          VARCHAR(36)  PRIMARY KEY,
    wishlist_id VARCHAR(36)  NOT NULL,
    product_id  VARCHAR(36)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  varchar(255) NOT NULL,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by  varchar(255)          DEFAULT NULL,
    FOREIGN KEY (wishlist_id) REFERENCES Wishlists (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE,
    UNIQUE KEY (wishlist_id, product_id) -- mỗi sản phẩm chỉ có thể có một lần trong danh sách yêu thích
);

CREATE INDEX idx_categories_parent ON Categories (parent_id);
create index idx_categories_slug ON Categories (slug);
create index idx_products_slug ON Products (slug);
CREATE INDEX idx_products_category ON Products (category_id);
CREATE INDEX idx_products_featured ON Products (is_featured);
CREATE INDEX idx_products_bestseller ON Products (is_bestseller);
create index idx_product_variants_product ON ProductVariants (product_id);
create index ix_product_images_product ON ProductImages (product_id);
create index idx_wishlist on Wishlists (user_id);
CREATE INDEX idx_wishlist_products ON WishlistProducts (wishlist_id, product_id);