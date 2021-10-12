/*
 Navicat Premium Data Transfer

 Source Server         : mysql-localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : test_base

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 23/09/2021 14:16:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yh_org_user
-- ----------------------------
DROP TABLE IF EXISTS `yh_org_user`;
CREATE TABLE `yh_org_user`  (
  `id_` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `username_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户',
  `nickname_` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `password_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile_` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `address_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `photo_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像图片附件ID',
  `sex_` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别：男，女，未知',
  `from_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
  `status_` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '01' COMMENT '状态，数据字典，系统用户状态',
  `create_time_` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time_` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag_` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除，0否1是',
  PRIMARY KEY (`id_`) USING BTREE,
  UNIQUE INDEX `email`(`email_`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile_`) USING BTREE,
  INDEX `username`(`username_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yh_org_user
-- ----------------------------
INSERT INTO `yh_org_user` VALUES ('1438789370565718017', 'string', 'string', 'fASDfrNWVl4ouxTlod7bJApawlYfjtMYxUonn7apZl4=', 'string', 'string', 'string', 'string', 'string', 'string', '01', '2021-09-17 16:56:05', 'string', 'string', '2021-09-23 13:59:11', 0);
INSERT INTO `yh_org_user` VALUES ('50831', 'admin', '系统管理员', '8x9Znf79o+OKoJHH/k0X3DHYfxF4exkUqYoxkAskPCE=', 'yhgogo816@gmail.com', NULL, 'test', NULL, '未知', 'system', '01', NULL, NULL, NULL, '2021-08-25 15:26:18', 0);

SET FOREIGN_KEY_CHECKS = 1;
