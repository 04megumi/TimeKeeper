-- V1__create_user_table.sql

-- 创建用户表，用于存储系统用户的基础信息
CREATE TABLE IF NOT EXISTS user (
    uid VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '用户唯一标识（UID）',
    name VARCHAR(255) COMMENT '用户姓名',
    name_en VARCHAR(255) UNIQUE COMMENT '英文名或唯一用户名',
    phone_num VARCHAR(20) UNIQUE COMMENT '手机号',
    password VARCHAR(255) COMMENT '用户密码（加密存储）',
    openid VARCHAR(255) UNIQUE COMMENT '第三方登录唯一标识（OpenID）',

    is_deleted INT DEFAULT 0 NOT NULL COMMENT '逻辑删除标记：0=正常，1=已删除',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '最后更新时间',
    create_by VARCHAR(64) COMMENT '创建人UID',
    update_by VARCHAR(64) COMMENT '最后更新人UID',
    version INT DEFAULT 0 NOT NULL COMMENT '数据版本号（用于乐观锁）'
) COMMENT='用户表：存储系统中的用户信息';

CREATE UNIQUE INDEX idx_user_name_en ON user(name_en);
CREATE UNIQUE INDEX idx_user_phone_num ON user(phone_num);
CREATE UNIQUE INDEX idx_user_openid ON user(openid);