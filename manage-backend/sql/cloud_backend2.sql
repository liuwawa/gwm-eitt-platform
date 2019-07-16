/*
Navicat MySQL Data Transfer

Source Server         : 10.255.30.142
Source Server Version : 80015
Source Host           : 10.255.30.142:3306
Source Database       : cloud_backend2

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-07-15 11:06:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for black_ip
-- ----------------------------
DROP TABLE IF EXISTS `black_ip`;
CREATE TABLE `black_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ip` varchar(32) NOT NULL COMMENT '黑名单ip',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip地址` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ip黑名单表';

-- ----------------------------
-- Records of black_ip
-- ----------------------------
INSERT INTO `black_ip` VALUES ('71', '121.121.1.11', '2019-07-10 11:31:49');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parentId` int(11) NOT NULL COMMENT '父菜单id',
  `name` varchar(64) NOT NULL COMMENT '菜单名',
  `url` varchar(1024) DEFAULT NULL COMMENT '菜单url',
  `css` varchar(32) DEFAULT NULL COMMENT '菜单样式',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `delFlag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '0', '系统设置', '', 'liu-iconicon_shezhi', '1', '2018-01-23 10:20:30', '2018-01-23 10:20:31', '0', '0');
INSERT INTO `menu` VALUES ('2', '1', '菜单', '/menu/menu', 'liu-iconcaidan', '2', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '1', '0');
INSERT INTO `menu` VALUES ('3', '1', '角色', '/sys/role', 'liu-iconrole', '3', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '1', '0');
INSERT INTO `menu` VALUES ('4', '1', '权限', '/sys/permission', 'liu-iconsetting-permissions', '4', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '1', '0');
INSERT INTO `menu` VALUES ('5', '0', '用户管理', '', 'liu-iconrenyuanguanli', '4', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('6', '5', '用户查询', '/user/userCheck', 'liu-iconsousuo1', '4', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '1', '0');
INSERT INTO `menu` VALUES ('7', '0', '文件查询', '/file/file', 'liu-iconfilesearch', '5', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('8', '0', '邮件管理', '/mail/mail', 'liu-iconmail_fill', '6', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('9', '0', '注册中心', 'http://10.255.30.142:8761', 'liu-iconsupervise', '7', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('10', '0', '监控中心', 'http://10.255.30.142:9001', 'liu-iconicon-test1', '8', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('11', '0', 'swagger文档', '/sys/swagger', 'liu-icondaibanshixiang', '8', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('12', '0', '黑名单ip', '/sys/blackip', 'liu-iconicon-test2', '9', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('13', '0', '日志查询', '/sys/log', 'liu-iconicon-test3', '10', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('14', '0', '短信历史查询', '/sms/sms', 'liu-iconxiaoxi', '11', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');
INSERT INTO `menu` VALUES ('15', '1', 'client管理', '/user/client', 'liu-iconwxbgongju', '13', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '1', '0');
INSERT INTO `menu` VALUES ('37', '1', '添加分组', '/sys/grouping', 'fa fa-users', '15', '2019-07-11 10:46:25', '2019-07-11 10:53:48', '1', '0');
INSERT INTO `menu` VALUES ('38', '1', '组织管理', '/sys/organization', 'fa fa-tree', '14', '2019-07-11 10:53:23', '2019-07-11 10:54:00', '1', '0');
INSERT INTO `menu` VALUES ('42', '0', 'Zipkin中心', 'http://10.255.30.142:9411', 'liu-iconicon-test1', '8', '2018-01-23 14:04:40', '2018-01-23 14:04:43', '0', '0');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `menuId` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`roleId`,`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('1', '6');
INSERT INTO `role_menu` VALUES ('1', '7');
INSERT INTO `role_menu` VALUES ('1', '8');
INSERT INTO `role_menu` VALUES ('1', '9');
INSERT INTO `role_menu` VALUES ('1', '10');
INSERT INTO `role_menu` VALUES ('1', '11');
INSERT INTO `role_menu` VALUES ('1', '12');
INSERT INTO `role_menu` VALUES ('1', '13');
INSERT INTO `role_menu` VALUES ('1', '14');
INSERT INTO `role_menu` VALUES ('1', '15');
INSERT INTO `role_menu` VALUES ('3', '0');
INSERT INTO `role_menu` VALUES ('3', '5');
INSERT INTO `role_menu` VALUES ('3', '6');
INSERT INTO `role_menu` VALUES ('10', '9');
INSERT INTO `role_menu` VALUES ('10', '10');
INSERT INTO `role_menu` VALUES ('10', '11');
INSERT INTO `role_menu` VALUES ('15', '10');
INSERT INTO `role_menu` VALUES ('15', '11');

-- ----------------------------
-- Table structure for t_mail
-- ----------------------------
DROP TABLE IF EXISTS `t_mail`;
CREATE TABLE `t_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userId` int(11) NOT NULL COMMENT '发送人id',
  `username` varchar(50) NOT NULL COMMENT '发送人用户名',
  `toEmail` varchar(128) NOT NULL COMMENT '收件人邮件地址',
  `subject` varchar(255) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '正文',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0草稿，1成功，2失败',
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `isRead` tinyint(1) DEFAULT '0' COMMENT '0:未读,1:已读',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `updateTime` (`updateTime`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='邮件发送记录表';

-- ----------------------------
-- Records of t_mail
-- ----------------------------
INSERT INTO `t_mail` VALUES ('16', '1', 'superadmin', '112233', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '0', '2019-07-16 13:21:53', '2019-07-10 13:09:20', '2019-07-10 13:09:25', '0');
INSERT INTO `t_mail` VALUES ('17', '1', 'superadmin', '112233', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '0', '2019-07-16 13:21:53', '2019-07-10 13:09:20', '2019-07-10 13:09:25', '0');
INSERT INTO `t_mail` VALUES ('30', '2', 'superadmin', '112233', '就是老帽就是老帽', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '0', '2019-07-16 13:21:53', '2019-07-10 13:09:20', '2019-07-10 13:09:25', '1');
INSERT INTO `t_mail` VALUES ('33', '2', 'superadmin', '112233', '就是老帽就是老帽', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '0', '2019-07-16 13:21:53', '2019-07-10 13:09:20', '2019-07-10 13:09:25', '1');
INSERT INTO `t_mail` VALUES ('34', '2', 'superadmin', '112233', '就是老帽就是老帽', '就是就是，我就知道他是老帽gfsdgshfgsdffdatrefgdfsgtw', '0', '2019-07-16 13:21:53', '2019-07-10 13:09:20', '2019-07-10 13:09:25', '1');
