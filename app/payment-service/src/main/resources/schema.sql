CREATE TABLE Payments
(
    id             VARCHAR(36) PRIMARY KEY,
    order_id       VARCHAR(36)                                                              NOT NULL,
    amount         DECIMAL(15, 2)                                                           NOT NULL,
    provider       ENUM ('momo', 'zalopay', 'vnpay', 'bank_transfer', 'credit_card', 'cod') NOT NULL,
    status         ENUM ('pending', 'completed', 'failed')                                  NOT NULL DEFAULT 'pending',
    transaction_id VARCHAR(100),
    payment_date   TIMESTAMP                                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at     TIMESTAMP                                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     VARCHAR(255)                                                             NOT NULL,
    updated_at     TIMESTAMP                                                                         DEFAULT NULL,
    updated_by     VARCHAR(255)                                                                      DEFAULT NULL,
    UNIQUE KEY (order_id, provider)
);