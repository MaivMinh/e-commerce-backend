-- 6. Mã Giảm Giá
CREATE TABLE promotions
(
    id              VARCHAR(36) PRIMARY KEY,
    code            VARCHAR(255)                             NOT NULL UNIQUE,
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
    created_by      VARCHAR(255)                             NOT NULL,
    updated_at      TIMESTAMP                                         DEFAULT NULL,
    updated_by      VARCHAR(255)                                      DEFAULT NULL,
);

CREATE TABLE account_promotions
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    account_id    VARCHAR(36)  NOT NULL,
    promotion_id VARCHAR(36)  NOT NULL,
    is_used    BOOLEAN      NOT NULL DEFAULT FALSE,
    used_at    TIMESTAMP,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP             DEFAULT NULL,
    updated_by VARCHAR(255)          DEFAULT NULL,
    UNIQUE KEY (account_id, promotion_id)
);