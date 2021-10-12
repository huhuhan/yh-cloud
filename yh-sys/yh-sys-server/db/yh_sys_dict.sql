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

 Date: 12/10/2021 14:38:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yh_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `yh_sys_dict`;
CREATE TABLE `yh_sys_dict`  (
  `id_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `code_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `name_` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `key_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型',
  `delete_flag_` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除，0否1是',
  `tree_id_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组id',
  `parent_id_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级id',
  `is_root_` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否根节点，0否1是',
  `create_time_` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time_` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yh_sys_dict
-- ----------------------------
INSERT INTO `yh_sys_dict` VALUES ('10000000000001', 'zjlx', '证件类型', 'id_type', 0, NULL, NULL, 1, '2021-10-12 14:34:58', '2021-10-12 14:38:23');
INSERT INTO `yh_sys_dict` VALUES ('10000000000002', 'sfz', '身份证', 'id_type', 0, NULL, '10000000000001', 0, '2021-10-12 14:34:58', '2021-10-12 14:38:21');
INSERT INTO `yh_sys_dict` VALUES ('10000000000003', 'hz', '护照', 'id_type', 0, NULL, '10000000000001', 0, '2021-10-12 14:34:58', '2021-10-12 14:38:21');
INSERT INTO `yh_sys_dict` VALUES ('1447813019956240385', 'yhzt', '用户状态', 'user_status', 0, NULL, NULL, 1, '2021-10-12 14:34:58', '2021-10-12 14:34:58');
INSERT INTO `yh_sys_dict` VALUES ('1447813181256589314', '01', '正常', 'user_status', 0, NULL, '1447813019956240385', 0, '2021-10-12 14:35:36', '2021-10-12 14:35:36');
INSERT INTO `yh_sys_dict` VALUES ('1447813282842632193', '02', '禁用', 'user_status', 0, NULL, '1447813019956240385', 0, '2021-10-12 14:36:00', '2021-10-12 14:36:00');

SET FOREIGN_KEY_CHECKS = 1;
