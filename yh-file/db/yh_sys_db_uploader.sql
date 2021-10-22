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

 Date: 21/10/2021 18:15:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yh_sys_db_uploader
-- ----------------------------
DROP TABLE IF EXISTS `yh_sys_db_uploader`;
CREATE TABLE `yh_sys_db_uploader`  (
  `id_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `bytes_` longblob NULL COMMENT '二进制文件',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-文件数据表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
