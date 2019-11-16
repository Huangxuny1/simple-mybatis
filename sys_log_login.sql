/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/11/2019 22:02:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login` (
  `id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `success` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
BEGIN;
INSERT INTO `sys_log_login` VALUES ('402880e4646e54dc01646e555e950001', '2018-07-06 14:44:06', '192.168.0.100', 'Message', '成功', '1', '402880e4641c692001641c6be3910002');
INSERT INTO `sys_log_login` VALUES ('402880e4646e54dc01646e555e950002', '2018-07-06 14:44:06', '192.168.0.100', '权限认证成功', '成功', '1', '402880e4641c692001641c6be3910002');
INSERT INTO `sys_log_login` VALUES ('402880e4643a734301643a7403d4111', '2018-07-06 14:44:06', '192.168.0.100', 'asdasd', '成功', '1', '402880e4641c692001641c6be3910002');
INSERT INTO `sys_log_login` VALUES ('402880e4646e54dc01646e555e950009', '2018-07-06 14:44:06', '192.168.0.100', '权限认证成功', '成功', '1', '402880e4641c692001641c6be3910002');
INSERT INTO `sys_log_login` VALUES ('402880e4646e54dc01646e555e950009', '2018-07-06 14:44:06', '192.168.0.100', '权限认证成功', '成功', '1', '402880e4641c692001641c6be3910002');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
