/*
 Navicat Premium Data Transfer

 Source Server         : localhost-mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : activiti5

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 02/12/2019 17:33:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '身份信息-组信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------
INSERT INTO `act_id_group` VALUES ('adminDemo', 1, 'adminDemo', 'security-role');
INSERT INTO `act_id_group` VALUES ('deptLeaderDemo', 1, 'deptLeaderDemo', 'assignment');
INSERT INTO `act_id_group` VALUES ('hrDemo', 1, 'hrDemo', 'assignment');
INSERT INTO `act_id_group` VALUES ('userDemo', 1, 'userDemo', 'security-role');

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership`  (
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`, `GROUP_ID_`) USING BTREE,
  INDEX `ACT_FK_MEMB_GROUP`(`GROUP_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '	\r\n身份信息-用户和组关系的中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------
INSERT INTO `act_id_membership` VALUES ('88888888', 'adminDemo');
INSERT INTO `act_id_membership` VALUES ('adminDemo', 'adminDemo');
INSERT INTO `act_id_membership` VALUES ('88888888', 'deptLeaderDemo');
INSERT INTO `act_id_membership` VALUES ('adminDemo', 'deptLeaderDemo');
INSERT INTO `act_id_membership` VALUES ('88888888', 'hrDemo');
INSERT INTO `act_id_membership` VALUES ('adminDemo', 'hrDemo');
INSERT INTO `act_id_membership` VALUES ('88888888', 'userDemo');
INSERT INTO `act_id_membership` VALUES ('leaderuserDemo', 'userDemo');

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `FIRST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LAST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EMAIL_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PWD_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PICTURE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '身份信息-用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------
INSERT INTO `act_id_user` VALUES ('88888888', 1, 'admin', 'Demo', NULL, '000000', NULL);
INSERT INTO `act_id_user` VALUES ('adminDemo', 1, 'adminDemo', 'adminDemo', '', '000000', NULL);
INSERT INTO `act_id_user` VALUES ('hruserDemo', 1, 'hruserDemo', 'hruserDemo', '', '000000', NULL);
INSERT INTO `act_id_user` VALUES ('leaderuserDemo', 1, 'leaderuserDemo', 'leaderuserDemo', '', '000000', NULL);

SET FOREIGN_KEY_CHECKS = 1;
