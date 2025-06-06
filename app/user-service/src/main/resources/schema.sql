-- Quản lý người dùng và địa chỉ
CREATE TABLE users
(
    id         VARCHAR(36) PRIMARY KEY,
    username   VARCHAR(36) NOT NULL,
    full_name  VARCHAR(100),
    avatar     VARCHAR(255),
    gender     ENUM ('male', 'female', 'other') DEFAULT 'other',
    birthdate  DATE,
    created_at TIMESTAMP   NOT NULL             DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);
create index idx_user_profiles_username on users (username);


CREATE TABLE addresses
(
    id         varchar(36) primary key,
    user_id    VARCHAR(36)  NOT NULL,
    full_name  VARCHAR(100) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    address    VARCHAR(255) NOT NULL,
    ward       VARCHAR(100),
    district   VARCHAR(100),
    city       VARCHAR(100),
    is_default BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255)          default null,
    updated_at TIMESTAMP             default null,
    updated_by varchar(255)          default null
);
-- Create indexes
CREATE INDEX idx_account_addresses_account_id ON addresses (user_id);

