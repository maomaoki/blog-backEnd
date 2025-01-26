-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userEmail    varchar(128)                           null comment '用户邮箱',
    userPhone     varchar(128)                          null comment '手机号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userTags     varchar(256)                           null comment '用户标签 json 字符串',
    userRole     varchar(128) default 'user'            not null comment '用户角色：boss/admin/user/blackUser',
    userStatus   tinyint     default 0                  not null comment '是否禁用 0 正常 1 禁用',
    userRegistrationSource varchar(128)  null comment '注册来源: 账号/qq邮箱/管理员添加',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除 0 未删除 1 已删除',
    INDEX idx_userName (userName), -- 用户昵称查找
    INDEX idx_userTags (userTags)  -- 用户标签查找
    ) comment '用户' collate = utf8mb4_unicode_ci;
