/*
 Navicat Premium Data Transfer

 Source Server         : 47.100.197.51_3306
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 47.100.197.51:3306
 Source Schema         : cloudpan

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 28/05/2020 15:56:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for link_secret
-- ----------------------------
DROP TABLE IF EXISTS `link_secret`;
CREATE TABLE `link_secret`  (
  `fd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fd_downloadNum` int(11) NOT NULL COMMENT '下载数',
  `fd_expireDate` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `fd_fileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `fd_localLink` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '链接地址',
  `fd_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '加密',
  `fd_secretLink` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '加密链接',
  `fd_shareDate` datetime(0) NULL DEFAULT NULL COMMENT '分享时间',
  `fd_userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `fd_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `fd_createDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_updateDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `fd_reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段1',
  `fd_reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段2',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of link_secret
-- ----------------------------

-- ----------------------------
-- Table structure for pan_save
-- ----------------------------
DROP TABLE IF EXISTS `pan_save`;
CREATE TABLE `pan_save`  (
  `fd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fd_fileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件名',
  `fd_localLink` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件链接',
  `fd_userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `fd_panPath` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件路径',
  `fd_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `fd_createDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_updateDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `fd_reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段2',
  `fd_reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段2',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pan_save
-- ----------------------------

-- ----------------------------
-- Table structure for pan_user
-- ----------------------------
DROP TABLE IF EXISTS `pan_user`;
CREATE TABLE `pan_user`  (
  `fd_id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fd_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `fd_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `fd_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '权限',
  `fd_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `fd_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `fd_alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `fd_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `fd_createDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_updateDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `fd_reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保留字段2',
  `fd_reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保留字段1',
  PRIMARY KEY (`fd_id`) USING BTREE,
  UNIQUE INDEX `username`(`fd_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pan_user
-- ----------------------------
INSERT INTO `pan_user` VALUES (1, 'admin', '0d0de0ea0b76ee160fdfe7def3ca6673', '1', 'sandeepin@qq.com', '15578352978', 'f3949b408cb219bf064ddbd17026384a', 0, '2020-05-13 14:39:35', '2020-05-13 14:39:35', NULL, NULL);
INSERT INTO `pan_user` VALUES (12, 'ckuire@foxmail.com', '7dd326bf6e12467bb5ab4ffde7d07006', '0', 'ckuire@foxmail.com', '18980126552', '1563da2a236270e4e94bd0b2eda2584a', 0, '2020-05-12 00:52:02', '2020-05-12 00:52:02', NULL, NULL);
INSERT INTO `pan_user` VALUES (16, 'cjpan', 'fb2b1572b0491dd3a27578c46f76c260', '0', '965178162@qq.com', '17380654871', '7a7f2baafb2896251f544082c1f61e64', 0, '2020-05-14 10:26:54', '2020-05-14 10:26:54', NULL, NULL);

-- ----------------------------
-- Table structure for verify_code
-- ----------------------------
DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code`  (
  `fd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fd_customName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '使用用户',
  `fd_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `fd_operatePerson` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人',
  `fd_registerCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '注册码',
  `fd_state` bit(1) NOT NULL COMMENT '状态',
  `fd_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `fd_createDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_updateDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `fd_reserved1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段1',
  `fd_reserved2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '保留字段2',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of verify_code
-- ----------------------------
INSERT INTO `verify_code` VALUES (21, 'cjpan', '2020-05-13 03:18:28', 'admin', 'woHQxV', b'0', 0, NULL, NULL, NULL, NULL);
INSERT INTO `verify_code` VALUES (22, 'baoqi', '2020-05-13 21:32:03', 'admin', 'V7KFeR', b'0', 0, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
