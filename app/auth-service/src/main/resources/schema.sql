-- 1. Quản lý Người Dùng
create table if not exists accounts
(
    id         varchar(36) primary key,
    username   varchar(255)                not null,
    password   varchar(255)                not null,
    email      varchar(255)                not null,
    role       ENUM ('admin', 'customer')  NOT NULL DEFAULT 'customer',
    status     ENUM ('active', 'inactive') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255)                         default null,
    updated_at TIMESTAMP                            default null,
    updated_by varchar(255)                         default null
);
-- Create indexes
CREATE INDEX idx_accounts_username ON accounts (username);
CREATE INDEX idx_accounts_email ON accounts (email);
