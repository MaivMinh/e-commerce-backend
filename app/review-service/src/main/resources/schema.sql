-- 5. Đánh Giá và Nhận Xét
CREATE TABLE reviews
(
    id         VARCHAR(36) PRIMARY KEY,
    account_id    VARCHAR(36)  NOT NULL,
    product_id VARCHAR(36)  NOT NULL,
    rating     TINYINT      NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content    TEXT,
    likes      INT          NOT NULL DEFAULT 0,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP             DEFAULT NULL,
    updated_by VARCHAR(255)          DEFAULT NULL
);
create index idx_reviews_user_id ON Reviews (account_id);
create index idx_reviews_product_id ON Reviews (product_id);

CREATE TABLE review_images
(
    id         VARCHAR(36) PRIMARY KEY,
    review_id  VARCHAR(36)  NOT NULL,
    url        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP             DEFAULT NULL,
    updated_by VARCHAR(255)          DEFAULT NULL,
    FOREIGN KEY (review_id) REFERENCES Reviews (id) ON DELETE CASCADE
);
create index idx_review_images_review_id on review_images (review_id);

CREATE TABLE IF NOT EXISTS account_purchases
(
    id         VARCHAR(36) PRIMARY KEY,
    account_id VARCHAR(36)  NOT NULL,
    product_id VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP             DEFAULT NULL,
    updated_by VARCHAR(255)          DEFAULT NULL,
);
create index idx_account_purchases_account_id ON account_purchases (account_id);
create index idx_account_purchases_product_id ON account_purchases (product_id);