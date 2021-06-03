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

 Date: 03/06/2021 17:36:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `key_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `code_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sn_` int(4) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '是否', '01', '是', 1);
INSERT INTO `sys_dict` VALUES (2, '是否', '02', '否', 2);
INSERT INTO `sys_dict` VALUES (3, '性别', '01', '男', 1);
INSERT INTO `sys_dict` VALUES (4, '性别', '02', '女', 2);
INSERT INTO `sys_dict` VALUES (5, '性别', '03', '未知', 3);
INSERT INTO `sys_dict` VALUES (6, 'string', 'string', 'string', 0);

SET FOREIGN_KEY_CHECKS = 1;
