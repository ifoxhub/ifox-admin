SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
    `password` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
    `icon` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
    `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
    `nick_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
    `note` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
    `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin` VALUES (3, 'admin', '$2a$10$D9dChdIV/4QdfPBn1mej5uF4WSag3XnaGRcPHmRXoTlmjd0piRYa6', 'https://portrait.gitee.com/uploads/avatars/user/2838/8514798_ifoxhub_1609308156.png!avatar200', 'support@ifoxhub.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2019-04-20 12:45:16', 1);
COMMIT;

-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_login_log`;
CREATE TABLE `ums_admin_login_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `admin_id` bigint(20) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `ip` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
    `address` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
    `user_agent` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '浏览器登录类型',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户登录日志表';

-- ----------------------------
-- Records of ums_admin_login_log
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin_login_log` VALUES (287, 3, '2021-01-07 16:15:49', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (288, 3, '2021-01-07 16:51:32', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (289, 3, '2021-01-08 12:44:59', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (290, 3, '2021-01-08 12:45:37', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (291, 3, '2021-01-08 13:29:11', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (292, 3, '2021-01-08 13:33:43', '0:0:0:0:0:0:0:1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (293, 3, '2021-03-04 12:43:49', '127.0.0.1', NULL, NULL);
INSERT INTO `ums_admin_login_log` VALUES (294, 3, '2021-03-04 12:45:17', '0:0:0:0:0:0:0:1', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `admin_id` bigint(20) DEFAULT NULL,
    `role_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_admin_role_relation` VALUES (40, 3, 5);
COMMIT;

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `title` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '菜单名称',
    `level` int(4) DEFAULT NULL COMMENT '菜单级数',
    `sort` int(4) DEFAULT NULL COMMENT '菜单排序',
    `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端名称',
    `icon` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端图标',
    `hidden` int(1) DEFAULT NULL COMMENT '前端隐藏',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
BEGIN;
INSERT INTO `ums_menu` VALUES (21, 0, '2020-02-07 16:29:13', '权限', 0, 0, 'ums', 'ums', 0);
INSERT INTO `ums_menu` VALUES (22, 21, '2020-02-07 16:29:51', '用户列表', 1, 0, 'admin', 'ums-admin', 0);
INSERT INTO `ums_menu` VALUES (23, 21, '2020-02-07 16:30:13', '角色列表', 1, 0, 'role', 'ums-role', 0);
INSERT INTO `ums_menu` VALUES (24, 21, '2020-02-07 16:30:53', '菜单列表', 1, 0, 'menu', 'ums-menu', 0);
INSERT INTO `ums_menu` VALUES (25, 21, '2020-02-07 16:31:13', '资源列表', 1, 0, 'resource', 'ums-resource', 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源名称',
    `url` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源URL',
    `description` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
    `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
BEGIN;
INSERT INTO `ums_resource` VALUES (25, '2020-02-07 16:47:34', '后台用户管理', '/admin/**', '', 4);
INSERT INTO `ums_resource` VALUES (26, '2020-02-07 16:48:24', '后台用户角色管理', '/role/**', '', 4);
INSERT INTO `ums_resource` VALUES (27, '2020-02-07 16:48:48', '后台菜单管理', '/menu/**', '', 4);
INSERT INTO `ums_resource` VALUES (28, '2020-02-07 16:49:18', '后台资源分类管理', '/resourceCategory/**', '', 4);
INSERT INTO `ums_resource` VALUES (29, '2020-02-07 16:49:45', '后台资源管理', '/resource/**', '', 4);
COMMIT;

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '分类名称',
    `sort` int(4) DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
BEGIN;
INSERT INTO `ums_resource_category` VALUES (4, '2020-02-05 10:23:04', '权限模块', 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '名称',
    `description` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
    `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
    `sort` int(11) DEFAULT '0',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
BEGIN;
INSERT INTO `ums_role` VALUES (5, '超级管理员', '拥有所有查看和操作功能', 0, '2020-02-02 15:11:05', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
    `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_role_menu_relation` VALUES (91, 5, 21);
INSERT INTO `ums_role_menu_relation` VALUES (92, 5, 22);
INSERT INTO `ums_role_menu_relation` VALUES (93, 5, 23);
INSERT INTO `ums_role_menu_relation` VALUES (94, 5, 24);
INSERT INTO `ums_role_menu_relation` VALUES (95, 5, 25);
COMMIT;

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
    `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
BEGIN;
INSERT INTO `ums_role_resource_relation` VALUES (165, 5, 25);
INSERT INTO `ums_role_resource_relation` VALUES (166, 5, 26);
INSERT INTO `ums_role_resource_relation` VALUES (167, 5, 27);
INSERT INTO `ums_role_resource_relation` VALUES (168, 5, 28);
INSERT INTO `ums_role_resource_relation` VALUES (169, 5, 29);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
