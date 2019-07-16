/*
Navicat MySQL Data Transfer

Source Server         : 10.255.30.142
Source Server Version : 80015
Source Host           : 10.255.30.142:3306
Source Database       : cloud_file2

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-07-15 11:06:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '文件md5',
  `name` varchar(128) NOT NULL COMMENT '文件名',
  `isImg` tinyint(1) NOT NULL COMMENT '是否是图片',
  `contentType` varchar(128) NOT NULL COMMENT '文件类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `path` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) NOT NULL COMMENT '文件网络url',
  `source` varchar(32) NOT NULL COMMENT '文件存储地方',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `createTime` (`createTime`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件信息表';

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO `file_info` VALUES ('5', '1122', '2', '2434', '33', '44', '55', '66', '2019-07-15 09:56:22');
INSERT INTO `file_info` VALUES ('8', '1122', '2', '2434', '33', '44', '55', '66', '2019-07-15 09:56:22');
INSERT INTO `file_info` VALUES ('10', '1122', '2', '2434', '33', '44', '55', '66', '2019-07-15 09:56:22');
INSERT INTO `file_info` VALUES ('11', '1122', '2', '2434', '33', '44', '55', '66', '2019-07-15 09:56:22');
INSERT INTO `file_info` VALUES ('12', '1122', '2', '2434', '33', '44', '55', '66', '2019-07-15 09:56:22');
