-- 1. Quản lý Người Dùng
create table if not exists accounts
(
    id         varchar(36) primary key,
    username   varchar(255)                not null,
    password   varchar(255)                not null,
    email      varchar(255)                not null,
    role       ENUM ('admin', 'customer')  NOT NULL DEFAULT 'customer',
    status     ENUM ('active', 'inactive') NOT NULL DEFAULT 'active',
    phone      varchar(20)                          default null,
    name       varchar(255)                         default null,
    avatar     varchar(255)                         default null,
    created_at TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255)                         default null,
    updated_at TIMESTAMP                            default null,
    updated_by varchar(255)                         default null
);
-- Create indexes
CREATE INDEX idx_accounts_username ON accounts (username);
CREATE INDEX idx_accounts_email ON accounts (email);
CREATE INDEX idx_accounts_phone ON accounts (phone);


CREATE TABLE account_addresses
(
    id         varchar(36) primary key,
    account_id VARCHAR(36)  NOT NULL,
    full_name  VARCHAR(100) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    address    VARCHAR(255) NOT NULL,
    ward       VARCHAR(100) NOT NULL,
    district   VARCHAR(100) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    is_default BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255)          default null,
    updated_at TIMESTAMP             default null,
    updated_by varchar(255)          default null,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);
-- Create indexes
CREATE INDEX idx_account_addresses_account_id ON account_addresses (account_id);

