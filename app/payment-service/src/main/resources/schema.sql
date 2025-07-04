CREATE TABLE payment_methods
(
    id          VARCHAR(36) PRIMARY KEY,
    code        VARCHAR(50)                                              NOT NULL UNIQUE, -- e.g., 'credit_card', 'e_wallet_momo'
    name        VARCHAR(100)                                             NOT NULL,        -- e.g., 'Credit Card', 'MoMo Wallet'
    description TEXT,
    type        ENUM ( 'bank_transfer','credit_card', 'cod', 'e_wallet') NOT NULL,
    provider    VARCHAR(255),                                                              -- For e-wallets: 'momo', 'zalopay', 'vnpay'
    icon_url    VARCHAR(255),
    currency    ENUM ('vnd', 'usd', 'eur')                                        DEFAULT 'vnd',
    is_active   BOOLEAN                                                           DEFAULT TRUE,
    created_at  TIMESTAMP                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(255)                                             NOT NULL,
    updated_at  TIMESTAMP                                                         DEFAULT NULL,
    updated_by  VARCHAR(255)                                                      DEFAULT NULL
);

CREATE TABLE payments
(
    id                VARCHAR(36) PRIMARY KEY,
    order_id          VARCHAR(36)    NOT NULL,
    payment_method_id VARCHAR(36)    NOT NULL,
    amount            DECIMAL(15, 2) NOT NULL,
    currency          VARCHAR(3)              DEFAULT 'VND',
    status            VARCHAR(20)    NOT NULL, -- 'pending', 'completed', 'failed', 'refunded'
    transaction_id    VARCHAR(100),
    payment_date      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by        VARCHAR(255)   NOT NULL,
    updated_at        TIMESTAMP               DEFAULT NULL,
    updated_by        VARCHAR(255)            DEFAULT NULL,
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods (id)
);
create index idx_payments_order_id on payments (order_id);
create index idx_payments_transaction_id on payments (transaction_id);