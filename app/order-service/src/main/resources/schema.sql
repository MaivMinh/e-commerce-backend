-- 4. Đơn Hàng
CREATE TABLE orders
(
    id                  VARCHAR(36) PRIMARY KEY,
    account_id          VARCHAR(36)                                                                      NOT NULL,
    order_number        VARCHAR(50)                                                                      NOT NULL UNIQUE,
    shipping_address_id VARCHAR(255)                                                                     NOT NULL,
    status              ENUM ('pending', 'completed', 'processing', 'shipped', 'delivered', 'cancelled') NOT NULL DEFAULT 'pending',
    subtotal            DECIMAL(15, 2)                                                                   NOT NULL,
    discount            DECIMAL(15, 2)                                                                   NOT NULL DEFAULT 0,
    total               DECIMAL(15, 2)                                                                   NOT NULL,
    payment_method      ENUM ('credit_card', 'bank_transfer', 'e_wallet', 'cod')                         NOT NULL,
    payment_status      ENUM ('pending', 'paid', 'failed')                                               NOT NULL DEFAULT 'pending',
    promotion_id        VARCHAR(255),
    note                TEXT,
    created_at          TIMESTAMP                                                                        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by          VARCHAR(255)                                                                     NOT NULL,
    updated_at          TIMESTAMP                                                                                 DEFAULT NULL,
    updated_by          VARCHAR(255)                                                                              DEFAULT NULL
);

CREATE TABLE order_items
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    order_id           VARCHAR(36)    NOT NULL,
    product_variant_id VARCHAR(36)    NOT NULL,
    quantity           INT            NOT NULL,
    price              DECIMAL(15, 2) NOT NULL,
    total              DECIMAL(15, 2) NOT NULL,
    created_at         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by         VARCHAR(255)   NOT NULL,
    updated_at         TIMESTAMP               DEFAULT NULL,
    updated_by         VARCHAR(255)            DEFAULT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders (id) ON DELETE CASCADE
);