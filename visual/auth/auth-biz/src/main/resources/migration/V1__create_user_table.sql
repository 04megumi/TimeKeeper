-- V1__create_user_table.sql

CREATE TABLE user (
    uid VARCHAR(64) NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    name_en VARCHAR(255) UNIQUE,
    phone_num VARCHAR(20) UNIQUE,
    password VARCHAR(255),
    openid VARCHAR(255) UNIQUE,

    is_deleted INT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    create_by INT,
    update_by INT,
    version INT DEFAULT 0 NOT NULL,

    CONSTRAINT chk_is_deleted CHECK (is_deleted IN (0,1))
);

-- 额外索引，如果需要
CREATE UNIQUE INDEX idx_user_name_en ON user(name_en);
CREATE UNIQUE INDEX idx_user_phone_num ON user(phone_num);
CREATE UNIQUE INDEX idx_user_openid ON user(openid);