-- Tạo database
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
    updated_at  TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    updated_at     TIMESTAMP                                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES Categories (id) ON DELETE SET NULL
);

CREATE TABLE ProductImages
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    product_id    VARCHAR(36)  NOT NULL,
    image_url     VARCHAR(255) NOT NULL,
    display_order INT          NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    updated_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE
);

-- 3. Giỏ Hàng
CREATE TABLE Carts
(
    id         VARCHAR(36) PRIMARY KEY,
    user_id    VARCHAR(36) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE CartItems
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    cart_id            VARCHAR(36) NOT NULL,
    product_variant_id VARCHAR(36) NOT NULL,
    quantity           INT         NOT NULL DEFAULT 1,
    created_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES Carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_variant_id) REFERENCES product_variants (id) ON DELETE CASCADE,
    UNIQUE KEY (cart_id, product_variant_id)
);

-- 4. Đơn Hàng và Thanh Toán
CREATE TABLE Orders
(
    id                  VARCHAR(36) PRIMARY KEY,
    user_id             VARCHAR(36)                                                         NOT NULL,
    order_number        VARCHAR(50)                                                         NOT NULL UNIQUE,
    status              ENUM ('pending', 'processing', 'shipped', 'delivered', 'cancelled') NOT NULL DEFAULT 'pending',
    shipping_address_id INT                                                                 NOT NULL,
    shipping_fee        DECIMAL(15, 2)                                                      NOT NULL DEFAULT 0,
    subtotal            DECIMAL(15, 2)                                                      NOT NULL,
    discount            DECIMAL(15, 2)                                                      NOT NULL DEFAULT 0,
    total               DECIMAL(15, 2)                                                      NOT NULL,
    payment_method      ENUM ('credit_card', 'bank_transfer', 'e_wallet', 'cod')            NOT NULL,
    payment_status      ENUM ('pending', 'paid', 'failed')                                  NOT NULL DEFAULT 'pending',
    voucher_code        VARCHAR(50),
    note                TEXT,
    created_at          TIMESTAMP                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (shipping_address_id) REFERENCES UserAddresses (id) ON DELETE RESTRICT
);

CREATE TABLE OrderItems
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    order_id           VARCHAR(36)    NOT NULL,
    product_variant_id VARCHAR(36)    NOT NULL,
    quantity           INT            NOT NULL,
    price              DECIMAL(15, 2) NOT NULL,
    total              DECIMAL(15, 2) NOT NULL,
    created_at         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_variant_id) REFERENCES product_variants (id) ON DELETE RESTRICT
);

CREATE TABLE Payments
(
    id             VARCHAR(36) PRIMARY KEY,
    order_id       VARCHAR(36)                                                              NOT NULL,
    amount         DECIMAL(15, 2)                                                           NOT NULL,
    provider       ENUM ('momo', 'zalopay', 'vnpay', 'bank_transfer', 'credit_card', 'cod') NOT NULL,
    status         ENUM ('pending', 'completed', 'failed')                                  NOT NULL DEFAULT 'pending',
    transaction_id VARCHAR(100),
    payment_date   TIMESTAMP,
    created_at     TIMESTAMP                                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP                                                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE CASCADE,
    UNIQUE KEY (order_id, provider)
);

-- 5. Đánh Giá và Nhận Xét
CREATE TABLE Reviews
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    VARCHAR(36)                              NOT NULL,
    product_id VARCHAR(36)                              NOT NULL,
    order_id   VARCHAR(36),
    rating     TINYINT                                  NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content    TEXT,
    likes      INT                                      NOT NULL DEFAULT 0,
    status     ENUM ('pending', 'approved', 'rejected') NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE SET NULL
);

CREATE TABLE ReviewImages
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    review_id  INT          NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES Reviews (id) ON DELETE CASCADE
);

-- 6. Mã Giảm Giá
CREATE TABLE Vouchers
(
    id              VARCHAR(36) PRIMARY KEY,
    code            VARCHAR(50)                              NOT NULL UNIQUE,
    type            ENUM ('percentage', 'fixed', 'shipping') NOT NULL,
    discount_value  DECIMAL(15, 2)                           NOT NULL,
    min_order_value DECIMAL(15, 2)                           NOT NULL DEFAULT 0,
    max_discount    DECIMAL(15, 2),
    start_date      TIMESTAMP                                NOT NULL,
    end_date        TIMESTAMP                                NOT NULL,
    usage_limit     INT,
    usage_count     INT                                      NOT NULL DEFAULT 0,
    status          ENUM ('active', 'inactive')              NOT NULL DEFAULT 'active',
    created_at      TIMESTAMP                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE UserVouchers
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    VARCHAR(36) NOT NULL,
    voucher_id VARCHAR(36) NOT NULL,
    is_used    BOOLEAN     NOT NULL DEFAULT FALSE,
    used_at    TIMESTAMP,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES Vouchers (id) ON DELETE CASCADE,
    UNIQUE KEY (user_id, voucher_id)
);

-- 7. Danh Sách Yêu Thích
CREATE TABLE Wishlists
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    VARCHAR(36) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    UNIQUE KEY (user_id)    -- mỗi người dùng chỉ có một danh sách yêu thích
);

CREATE TABLE IF NOT EXISTS WishlistProducts
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id INT          NOT NULL,
    product_id VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (wishlist_id) REFERENCES Wishlists (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE,
    UNIQUE KEY (wishlist_id, product_id) -- mỗi sản phẩm chỉ có thể có một lần trong danh sách yêu thích
);



-- Tạo các chỉ mục bổ sung để tối ưu truy vấn
CREATE INDEX idx_products_category ON Products (category_id);
CREATE INDEX idx_products_featured ON Products (is_featured);
CREATE INDEX idx_products_bestseller ON Products (is_bestseller);
CREATE INDEX idx_products_new ON Products (is_new);
CREATE INDEX idx_products_status ON Products (status);
CREATE INDEX idx_orders_user ON Orders (user_id);
CREATE INDEX idx_orders_status ON Orders (status);
CREATE INDEX idx_orders_created_at ON Orders (created_at);
CREATE INDEX idx_reviews_product ON Reviews (product_id);
CREATE INDEX idx_reviews_rating ON Reviews (rating);
CREATE INDEX idx_vouchers_status ON Vouchers (status);
CREATE INDEX idx_vouchers_date ON Vouchers (start_date, end_date);