/*
Navicat MySQL Data Transfer

Source Server         : 10.255.30.142
Source Server Version : 80015
Source Host           : 10.255.30.142:3306
Source Database       : cloud_user2

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-07-15 11:02:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `label` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '组名称',
  `parentid` int(11) DEFAULT '0' COMMENT ' 组的父组id(0为没有父级，默认为0)',
  `groupShowOrder` int(3) DEFAULT NULL COMMENT '定义展示顺序标识',
  `level` smallint(1) DEFAULT '0' COMMENT '组的级别(0：集团级，1：公司级，2：部门级，3：子部门 4：科室级 5：组  6：班 默认插入为集团级别)',
  `groupChildCount` int(3) DEFAULT '0' COMMENT '子节点数(默认0)',
  `groupRemark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注',
  `groupAddress` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '组所在区域',
  `groupTel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组的联系电话',
  `enableTime` datetime DEFAULT NULL COMMENT '启用时间',
  `isUpdate` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否更新',
  `isDel` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识（0代表存在，1代表已删除）',
  `deleteBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除人',
  `deleteTime` datetime DEFAULT NULL COMMENT '删除时间',
  `createBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '修改人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='组织表';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('260', '最新的测试', '0', '1', '1', '4', '', '南二环', '122345678', null, '0', '0', null, '2019-06-28 14:42:34', '', null, 'liu111', '2019-06-28 14:20:12');
INSERT INTO `sys_group` VALUES ('337', 'wmy', '260', '2', '1', '2', '', '南二环', null, null, '0', '0', 'mymy', '2019-06-28 14:38:42', '', null, '', null);
INSERT INTO `sys_group` VALUES ('338', 'liuek', '260', '3', '1', '0', '', '南二环', '122345678', null, '0', '0', 'wmy', '2019-06-28 15:18:46', '', null, '', null);
INSERT INTO `sys_group` VALUES ('339', 'dp', '260', null, '1', '0', '', '南二环', '122345678', null, '0', '0', 'wmy', '2019-06-28 15:18:46', '', null, '', null);
INSERT INTO `sys_group` VALUES ('342', 'ceshi', '337', '100', '1', '0', '', '南二环', null, null, '0', '0', null, null, '', null, '', null);
INSERT INTO `sys_group` VALUES ('347', 'string', '337', '300', '4', '0', 'string', '徐水', 'string', null, '0', '0', null, null, 'string', null, '', null);
INSERT INTO `sys_group` VALUES ('348', 'wmytest', '338', '14', '1', '0', '', '徐水', null, null, '0', '0', null, null, 'wmy', '2019-06-28 14:23:38', '', null);
INSERT INTO `sys_group` VALUES ('349', '最新最新的测试', '342', null, '1', '0', '', '徐水', '13463626896', null, '0', '0', null, null, 'liuenke', '2019-06-29 17:07:13', 'liuenke', '2019-06-29 17:21:20');
INSERT INTO `sys_group` VALUES ('350', '测试', '342', '110', '1', '0', '', '徐水', '13213123', null, '0', '0', null, null, '', null, '', null);
INSERT INTO `sys_group` VALUES ('351', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', 'wo', '2019-07-12 12:36:02', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('352', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', 'wo', '2019-07-12 12:36:02', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('353', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('354', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('355', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '1', '0', null, null, 'liu', '2019-07-11 15:52:27', '', '2019-07-13 17:18:57');
INSERT INTO `sys_group` VALUES ('356', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '1', '0', null, null, 'liu', '2019-07-11 15:52:27', '', '2019-07-13 17:18:35');
INSERT INTO `sys_group` VALUES ('357', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:52:33', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('358', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('359', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:52:38', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('360', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼1楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('361', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼2楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('362', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼2楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:52:45', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('363', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼2楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('364', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼2楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:52:28', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('365', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼2楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:58:09', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('366', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:58:31', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('367', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '1', 'superadmin', '2019-07-12 15:52:42', 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('368', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('369', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '1', '0', null, null, 'liu', '2019-07-11 15:52:27', '', '2019-07-13 17:19:30');
INSERT INTO `sys_group` VALUES ('370', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('371', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('372', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('373', '测试组织', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼3楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('374', '班', '375', '0', '4', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('375', '组', '380', '0', '5', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('376', '集团', '0', '0', '0', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '1', '0', null, null, 'liu', '2019-07-11 15:52:27', '', '2019-07-13 17:20:34');
INSERT INTO `sys_group` VALUES ('377', '公司', '376', '0', '1', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('378', '部门', '377', '0', '2', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('379', '子部门', '378', '0', '3', '3', '这是一个测试用的组织', '工程楼4楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('380', '科室', '379', '0', '4', '3', '这是一个测试用的组织', '工程楼', '1333333333', null, '0', '0', null, null, 'liu', '2019-07-11 15:52:27', '', null);
INSERT INTO `sys_group` VALUES ('381', '阿斯蒂芬', '0', null, '1', '0', '123123123', '天津', '123123111', null, '1', '0', 'superadmin', '2019-07-12 15:43:01', '', '2019-07-12 11:22:39', '', '2019-07-13 14:04:01');

-- ----------------------------
-- Table structure for sys_group_expand
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_expand`;
CREATE TABLE `sys_group_expand` (
  `gExpandId` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织表拓展表id',
  `unitId` int(11) DEFAULT NULL COMMENT '单位id',
  `unitName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位名称（2）',
  `deptId` int(11) DEFAULT NULL COMMENT '部门id',
  `deptName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称（3）',
  `teamId` int(11) DEFAULT NULL COMMENT '科室id',
  `teamName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名字（4）',
  `gModule` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模块',
  `subModule` varchar(30) DEFAULT NULL COMMENT '子模块',
  `gFullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '该组织全称（路径）',
  `gGrade` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '组织级别（0集团级别 10公司级 20部门级 30科室级）',
  `gDirectLeader` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当前组织直接领导工号',
  `gDeptopLeader` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门最高领导工号',
  `gUnittopLeader` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位最高领导工号',
  `groupId` int(11) NOT NULL COMMENT '关联group主表的标识',
  PRIMARY KEY (`gExpandId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='组织拓展表';

-- ----------------------------
-- Records of sys_group_expand
-- ----------------------------
INSERT INTO `sys_group_expand` VALUES ('1', null, null, null, null, null, null, null, null, null, '0', 'wwwww', '45544', null, '260');
INSERT INTO `sys_group_expand` VALUES ('2', null, null, null, null, null, null, null, null, null, '0', null, null, null, '340');
INSERT INTO `sys_group_expand` VALUES ('7', '1', '一科', '4', '信息部', '1', '人事部', null, null, null, '0', null, null, null, '347');
INSERT INTO `sys_group_expand` VALUES ('8', null, null, null, null, null, null, null, null, null, '0', 'wwwww', '45544', null, '348');
INSERT INTO `sys_group_expand` VALUES ('9', null, '长城股份有限公司', null, '大数据环保部', null, '人事科', '环保模块', null, '长城股份有限公司大数据环保部人事科', '0', null, null, null, '349');
INSERT INTO `sys_group_expand` VALUES ('10', null, null, null, null, null, null, null, null, 'nullnullnull', '10', null, null, null, '381');

-- ----------------------------
-- Table structure for sys_group_grouping
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_grouping`;
CREATE TABLE `sys_group_grouping` (
  `groupId` int(11) NOT NULL COMMENT '组织id',
  `groupingId` int(11) NOT NULL COMMENT '分组id',
  PRIMARY KEY (`groupId`,`groupingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分组组织中间表';

-- ----------------------------
-- Records of sys_group_grouping
-- ----------------------------
INSERT INTO `sys_group_grouping` VALUES ('260', '10');
INSERT INTO `sys_group_grouping` VALUES ('260', '11');
INSERT INTO `sys_group_grouping` VALUES ('337', '9');
INSERT INTO `sys_group_grouping` VALUES ('337', '10');
INSERT INTO `sys_group_grouping` VALUES ('337', '11');
INSERT INTO `sys_group_grouping` VALUES ('337', '15');
INSERT INTO `sys_group_grouping` VALUES ('337', '17');
INSERT INTO `sys_group_grouping` VALUES ('338', '10');
INSERT INTO `sys_group_grouping` VALUES ('338', '11');
INSERT INTO `sys_group_grouping` VALUES ('338', '13');
INSERT INTO `sys_group_grouping` VALUES ('338', '17');
INSERT INTO `sys_group_grouping` VALUES ('339', '9');
INSERT INTO `sys_group_grouping` VALUES ('339', '11');
INSERT INTO `sys_group_grouping` VALUES ('339', '12');
INSERT INTO `sys_group_grouping` VALUES ('339', '14');
INSERT INTO `sys_group_grouping` VALUES ('339', '16');
INSERT INTO `sys_group_grouping` VALUES ('339', '18');
INSERT INTO `sys_group_grouping` VALUES ('342', '9');
INSERT INTO `sys_group_grouping` VALUES ('342', '11');
INSERT INTO `sys_group_grouping` VALUES ('342', '14');
INSERT INTO `sys_group_grouping` VALUES ('342', '15');
INSERT INTO `sys_group_grouping` VALUES ('342', '17');
INSERT INTO `sys_group_grouping` VALUES ('347', '9');
INSERT INTO `sys_group_grouping` VALUES ('347', '11');
INSERT INTO `sys_group_grouping` VALUES ('347', '15');
INSERT INTO `sys_group_grouping` VALUES ('347', '17');
INSERT INTO `sys_group_grouping` VALUES ('348', '11');
INSERT INTO `sys_group_grouping` VALUES ('348', '13');
INSERT INTO `sys_group_grouping` VALUES ('348', '17');
INSERT INTO `sys_group_grouping` VALUES ('349', '9');
INSERT INTO `sys_group_grouping` VALUES ('349', '11');
INSERT INTO `sys_group_grouping` VALUES ('349', '14');
INSERT INTO `sys_group_grouping` VALUES ('349', '15');
INSERT INTO `sys_group_grouping` VALUES ('349', '17');
INSERT INTO `sys_group_grouping` VALUES ('350', '9');
INSERT INTO `sys_group_grouping` VALUES ('363', '21');
INSERT INTO `sys_group_grouping` VALUES ('364', '23');
INSERT INTO `sys_group_grouping` VALUES ('365', '20');
INSERT INTO `sys_group_grouping` VALUES ('366', '20');
INSERT INTO `sys_group_grouping` VALUES ('366', '23');
INSERT INTO `sys_group_grouping` VALUES ('367', '21');
INSERT INTO `sys_group_grouping` VALUES ('369', '21');
INSERT INTO `sys_group_grouping` VALUES ('370', '22');
INSERT INTO `sys_group_grouping` VALUES ('372', '25');
INSERT INTO `sys_group_grouping` VALUES ('373', '19');
INSERT INTO `sys_group_grouping` VALUES ('373', '23');
INSERT INTO `sys_group_grouping` VALUES ('381', '9');
INSERT INTO `sys_group_grouping` VALUES ('381', '24');

-- ----------------------------
-- Table structure for sys_grouping
-- ----------------------------
DROP TABLE IF EXISTS `sys_grouping`;
CREATE TABLE `sys_grouping` (
  `groupingId` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组表id主键',
  `groupingName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组名称',
  `groupingRemark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组备注',
  `isDel` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识(0存在，1删除)',
  `groupNumber` int(1) DEFAULT NULL COMMENT '该分组共组织数',
  `createTime` datetime DEFAULT NULL COMMENT '分组创建时间',
  `createBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '分组修改时间',
  `updateBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组修改人',
  `deleteTime` datetime DEFAULT NULL COMMENT '分组删除时间',
  `deleteBy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组删除人',
  `groupingShowOrder` int(3) DEFAULT NULL COMMENT '分组展示顺序',
  PRIMARY KEY (`groupingId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='组织分组表';

-- ----------------------------
-- Records of sys_grouping
-- ----------------------------
INSERT INTO `sys_grouping` VALUES ('2', '测试的分组', '这个是测', '0', '0', '2019-06-25 11:40:52', 'liuek', null, null, '2019-07-11 08:38:10', 'superadmin', '1');
INSERT INTO `sys_grouping` VALUES ('3', '122', '这个是测试', '0', '0', '2019-06-25 14:44:51', 'liuek', null, null, '2019-07-11 08:36:34', 'superadmin', '2');
INSERT INTO `sys_grouping` VALUES ('4', 'updateTest', 'wmyupdate', '0', '5', '2019-06-25 14:46:27', 'liuek', '2019-06-28 15:52:59', 'wmy', '2019-07-11 08:34:16', 'superadmin', '1');
INSERT INTO `sys_grouping` VALUES ('5', '122', '这个是测试1', '0', '0', '2019-06-25 15:33:13', 'liuek', null, null, '2019-06-28 15:32:28', 'wmy', '4');
INSERT INTO `sys_grouping` VALUES ('6', '122', '这个是测试2', '0', '0', '2019-06-25 16:57:48', '刘恩科', null, null, '2019-06-26 15:16:39', 'admin', '7');
INSERT INTO `sys_grouping` VALUES ('7', '122', '这个是测试3', '0', '0', '2019-06-25 17:49:30', null, null, null, '2019-07-10 18:19:09', 'superadmin', '6');
INSERT INTO `sys_grouping` VALUES ('8', '测试编辑的分组', '这测试真难4', '0', '0', '2019-06-26 09:33:05', 'liuek', '2019-06-26 14:00:05', 'liuenke', '2019-07-10 19:09:02', 'superadmin', '7');
INSERT INTO `sys_grouping` VALUES ('9', 'wmy1222', 'wmyceshi1112', '0', null, '2019-06-27 14:23:49', null, '2019-07-13 10:33:40', 'superadmin', '2019-07-10 19:09:10', 'superadmin', '8');
INSERT INTO `sys_grouping` VALUES ('10', 'wmytest', '测试用', '0', '4', '2019-06-28 15:47:40', 'wmy', null, null, '2019-07-10 19:09:23', 'superadmin', '1');
INSERT INTO `sys_grouping` VALUES ('11', '测试1', '测试1', '0', null, '2019-07-10 10:49:31', 'superadmin', null, null, '2019-07-10 19:09:23', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('12', '分组测试21', '分组测试2', '0', null, '2019-07-10 15:39:28', 'superadmin', '2019-07-13 10:24:23', 'superadmin', '2019-07-10 19:09:10', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('13', '分组测试3', '分组测试3', '0', null, '2019-07-10 15:40:31', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('14', '分组测试4', '分组测试4', '0', null, '2019-07-10 15:41:18', 'superadmin', null, null, '2019-07-10 19:09:10', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('15', '分组5', '分组5', '0', null, '2019-07-10 15:49:00', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('16', '测试6', '测试6', '0', null, '2019-07-10 15:50:57', 'superadmin', null, null, '2019-07-11 08:41:08', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('17', '测试7', '测试7', '0', null, '2019-07-10 18:20:04', 'superadmin', null, null, '2019-07-11 08:36:41', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('18', '测试无数次', '测试无数次', '0', null, '2019-07-10 18:58:14', 'superadmin', null, null, '2019-07-11 08:36:41', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('19', 'aaaaa', 'aaaaa', '0', null, '2019-07-11 16:08:02', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('20', 'bbbbb', 'bbbbbb', '0', null, '2019-07-11 16:08:21', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('21', 'cccc', 'ccccc', '0', null, '2019-07-11 16:08:30', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('22', 'dddd', 'ddddd', '0', null, '2019-07-11 16:08:37', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('23', 'eeee', 'eeeee', '1', null, '2019-07-11 16:08:43', 'superadmin', null, null, '2019-07-12 19:02:54', 'superadmin', null);
INSERT INTO `sys_grouping` VALUES ('24', '哎哎哎哎哎', '啊啊啊啊啊啊', '0', null, '2019-07-13 15:43:40', 'superadmin', null, null, null, null, null);
INSERT INTO `sys_grouping` VALUES ('25', '测试1', '测试1', '0', null, '2019-07-15 09:38:29', '', null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `permission` varchar(32) NOT NULL COMMENT '权限标识',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限标识表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'back:permission:save', '保存权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('2', 'back:permission:update', '修改权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('3', 'back:permission:delete', '删除权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('4', 'back:permission:query', '查询权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('5', 'back:role:save', '添加角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('6', 'back:role:update', '修改角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('7', 'back:role:delete', '删除角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('8', 'back:role:permission:set', '给角色分配权限', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('9', 'back:user:query', '用户查询', '2018-01-18 17:12:00', '2018-01-18 17:12:05');
INSERT INTO `sys_permission` VALUES ('10', 'back:user:update', '修改用户', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('11', 'back:user:role:set', '给用户分配角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('12', 'back:user:password', '用户重置密码', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('13', 'back:menu:save', '添加菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('14', 'back:menu:update', '修改菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('15', 'back:menu:delete', '删除菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('16', 'back:menu:query', '查询菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('17', 'back:menu:set2role', '给角色分配菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('18', 'back:role:query', '查询角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('19', 'user:role:byuid', '获取用户的角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('20', 'role:permission:byroleid', '获取角色的权限', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('21', 'menu:byroleid', '获取角色的菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('22', 'ip:black:query', '查询黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('23', 'ip:black:save', '添加黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('24', 'ip:black:delete', '删除黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('25', 'log:query', '日志查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('26', 'file:query', '文件查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('27', 'file:del', '文件删除', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('28', 'mail:save', '保存邮件', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('29', 'mail:update', '修改邮件', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('30', 'mail:query', '邮件查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('31', 'sms:query', '短信发送记录查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('32', 'client:save', '保存client', '2018-06-28 17:06:39', '2018-06-28 17:06:39');
INSERT INTO `sys_permission` VALUES ('33', 'client:update', '修改client', '2018-06-28 17:06:39', '2018-06-28 17:06:39');
INSERT INTO `sys_permission` VALUES ('34', 'client:query', '查询client', '2018-06-28 17:06:39', '2018-06-28 17:06:39');
INSERT INTO `sys_permission` VALUES ('35', 'client:del', '删除client', '2018-06-28 17:06:39', '2018-06-28 17:06:39');
INSERT INTO `sys_permission` VALUES ('37', 'back:add:all', '添加按钮', '2019-06-26 17:15:30', '2019-06-26 17:15:33');
INSERT INTO `sys_permission` VALUES ('38', 'sys:menu:view', '查看', '2019-07-01 15:38:30', '2019-07-01 15:38:32');
INSERT INTO `sys_permission` VALUES ('39', 'sys:menu:add', '新增', '2019-07-01 15:39:01', '2019-07-01 15:39:03');
INSERT INTO `sys_permission` VALUES ('40', 'sys:menu:edit', '修改', '2019-07-01 15:39:21', '2019-07-01 15:39:23');
INSERT INTO `sys_permission` VALUES ('41', 'ip:black:deleteall', '删除所有黑名单', '2019-07-05 13:17:43', '2019-07-05 13:17:49');
INSERT INTO `sys_permission` VALUES ('42', 'back:sms:delete', '删除短息记录', '2019-07-05 14:47:30', '2019-07-05 14:47:33');
INSERT INTO `sys_permission` VALUES ('43', 'back:user:save', '添加用户', '2019-07-08 17:02:43', '2019-07-08 17:02:45');
INSERT INTO `sys_permission` VALUES ('44', 'back:group:save', '添加组织,分组', '2019-07-09 09:43:26', '2019-07-09 09:43:29');
INSERT INTO `sys_permission` VALUES ('45', 'back:group:update', '修改组织,分组', '2019-07-09 09:46:27', '2019-07-09 09:46:29');
INSERT INTO `sys_permission` VALUES ('46', 'back:group:delete', '删除组织,分组', '2019-07-09 09:47:25', '2019-07-09 09:47:27');
INSERT INTO `sys_permission` VALUES ('47', 'back:group:query', '查询组织,分组', '2019-07-09 09:52:01', '2019-07-09 09:52:03');
INSERT INTO `sys_permission` VALUES ('56', 'back:mail:delete', '删除邮件', '2019-07-09 14:11:54', '2019-07-09 14:11:59');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(32) NOT NULL COMMENT '角色code',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `createBy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'SUPER_ADMIN', '超级管理员', 'admin', '2019-07-04 17:35:09', '2019-07-04 17:35:12');
INSERT INTO `sys_role` VALUES ('2', 'ROLE_USER', 'code授权用户', 'admin', '2019-02-12 11:26:06', '2019-02-12 11:26:06');
INSERT INTO `sys_role` VALUES ('3', 'system', '测试数据', 'admin', null, null);
INSERT INTO `sys_role` VALUES ('10', 'f', 'f', null, null, null);
INSERT INTO `sys_role` VALUES ('11', 'e', 'e', null, null, null);
INSERT INTO `sys_role` VALUES ('13', 'h', 'h', null, null, null);
INSERT INTO `sys_role` VALUES ('14', 'asd', 'asd', null, null, null);
INSERT INTO `sys_role` VALUES ('15', 'asdad', 'asd', null, null, null);
INSERT INTO `sys_role` VALUES ('19', 'ceshi2', 'ceshi2', null, null, null);
INSERT INTO `sys_role` VALUES ('20', 'ceshi3', 'ceshi3', null, null, null);
INSERT INTO `sys_role` VALUES ('21', 'ceshi4', 'ceshi4', null, null, null);
INSERT INTO `sys_role` VALUES ('22', 'adad1', 'asdsa', null, null, null);
INSERT INTO `sys_role` VALUES ('23', '2019.7.9ceshi', '2019.7.9ceshi', null, '2019-07-09 09:02:08', null);
INSERT INTO `sys_role` VALUES ('24', 'hahaha', '测试测试', null, '2019-07-11 15:35:12', null);
INSERT INTO `sys_role` VALUES ('25', 'ceshi', '7.12ceshi', null, '2019-07-12 11:16:49', null);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `permissionId` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '3');
INSERT INTO `sys_role_permission` VALUES ('1', '4');
INSERT INTO `sys_role_permission` VALUES ('1', '5');
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('1', '7');
INSERT INTO `sys_role_permission` VALUES ('1', '8');
INSERT INTO `sys_role_permission` VALUES ('1', '9');
INSERT INTO `sys_role_permission` VALUES ('1', '10');
INSERT INTO `sys_role_permission` VALUES ('1', '11');
INSERT INTO `sys_role_permission` VALUES ('1', '12');
INSERT INTO `sys_role_permission` VALUES ('1', '13');
INSERT INTO `sys_role_permission` VALUES ('1', '14');
INSERT INTO `sys_role_permission` VALUES ('1', '15');
INSERT INTO `sys_role_permission` VALUES ('1', '16');
INSERT INTO `sys_role_permission` VALUES ('1', '17');
INSERT INTO `sys_role_permission` VALUES ('1', '18');
INSERT INTO `sys_role_permission` VALUES ('1', '19');
INSERT INTO `sys_role_permission` VALUES ('1', '20');
INSERT INTO `sys_role_permission` VALUES ('1', '21');
INSERT INTO `sys_role_permission` VALUES ('1', '22');
INSERT INTO `sys_role_permission` VALUES ('1', '23');
INSERT INTO `sys_role_permission` VALUES ('1', '24');
INSERT INTO `sys_role_permission` VALUES ('1', '25');
INSERT INTO `sys_role_permission` VALUES ('1', '26');
INSERT INTO `sys_role_permission` VALUES ('1', '27');
INSERT INTO `sys_role_permission` VALUES ('1', '28');
INSERT INTO `sys_role_permission` VALUES ('1', '29');
INSERT INTO `sys_role_permission` VALUES ('1', '30');
INSERT INTO `sys_role_permission` VALUES ('1', '31');
INSERT INTO `sys_role_permission` VALUES ('1', '32');
INSERT INTO `sys_role_permission` VALUES ('1', '33');
INSERT INTO `sys_role_permission` VALUES ('1', '34');
INSERT INTO `sys_role_permission` VALUES ('1', '35');
INSERT INTO `sys_role_permission` VALUES ('1', '37');
INSERT INTO `sys_role_permission` VALUES ('1', '41');
INSERT INTO `sys_role_permission` VALUES ('1', '42');
INSERT INTO `sys_role_permission` VALUES ('1', '43');
INSERT INTO `sys_role_permission` VALUES ('1', '44');
INSERT INTO `sys_role_permission` VALUES ('1', '45');
INSERT INTO `sys_role_permission` VALUES ('1', '46');
INSERT INTO `sys_role_permission` VALUES ('1', '47');
INSERT INTO `sys_role_permission` VALUES ('1', '56');
INSERT INTO `sys_role_permission` VALUES ('3', '9');
INSERT INTO `sys_role_permission` VALUES ('10', '1');
INSERT INTO `sys_role_permission` VALUES ('10', '3');
INSERT INTO `sys_role_permission` VALUES ('10', '4');
INSERT INTO `sys_role_permission` VALUES ('10', '7');
INSERT INTO `sys_role_permission` VALUES ('11', '56');
INSERT INTO `sys_role_permission` VALUES ('15', '1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `userId` int(11) NOT NULL COMMENT '用户id',
  `roleId` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`userId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色用户关系表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1');
INSERT INTO `sys_role_user` VALUES ('2', '1');
INSERT INTO `sys_role_user` VALUES ('3', '3');
INSERT INTO `sys_role_user` VALUES ('10', '21');
INSERT INTO `sys_role_user` VALUES ('10', '22');
INSERT INTO `sys_role_user` VALUES ('18', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `headImgUrl` varchar(1024) DEFAULT NULL COMMENT '头像url',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别0女,1男',
  `groupId` int(11) DEFAULT NULL COMMENT '组织',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1有效,0无效）',
  `type` varchar(16) NOT NULL COMMENT '类型（暂未用）',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$3uOoX1ps14CxuotogUoDreW8zXJOZB9XeGdrC/xDV36hhaE8Rn9HO', '测试1', '', '', '1', '338', '0', 'APP', '2018-01-17 16:57:01', '2018-01-17 16:57:01');
INSERT INTO `sys_user` VALUES ('2', 'superadmin', '$2a$10$97vXtA9DQZ7nbDocLmGaL.sttpwmO70KPjTSonuKKvwgFCgGKRWY2', '超级管理员', 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100', '19931652745', '0', '342', '1', 'BACKEND', '2018-01-19 20:30:11', '2019-06-29 16:36:01');
INSERT INTO `sys_user` VALUES ('3', '老帽', '123.com', '测试用来做什么', null, '12345678910', '1', '372', '0', 'BACKEND', '2019-06-25 08:50:15', '2019-06-25 08:50:15');
INSERT INTO `sys_user` VALUES ('4', 'tttt', '$2a$10$9reQCfFKIOsOKG5qjiJEBuF9oIhZY5TN.wb1IYCEsWNiQamJDN7KC', 'tttt', null, null, '0', '377', '0', 'BACKEND', '2019-06-26 16:25:10', '2019-06-26 16:25:10');
INSERT INTO `sys_user` VALUES ('5', 'adad', 'sad', 'dsad', null, null, null, '378', '1', 'ad', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('6', 'aaa', 'sad', 'dsad', null, null, null, '379', '1', 'ad', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('7', 'addd', 'sad', 'dsad', null, null, null, '380', '1', 'ad', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('8', 'dsadad', 'sad', 'dsad', null, null, null, null, '1', 'ad', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('9', 'dsadads', 'sad', 'dsad', null, '13462', '0', null, '1', '后台用户', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('10', '侧室', '123.com', 'dsad', null, null, '1', '347', '0', 'ad', '2019-07-08 16:46:46', '2019-07-08 16:46:49');
INSERT INTO `sys_user` VALUES ('11', '测试100', '$2a$10$6YfeA/tt0.YuSN6LiAb4R.vuKrlvA.qDegn.1WbxkdFpMDWxj0TkO', '测试100', null, '', '1', '352', '1', 'APP', '2019-07-08 17:56:31', '2019-07-08 17:56:31');
INSERT INTO `sys_user` VALUES ('12', '测试123', '$2a$10$m0d4ExkbkOzmG.Imr9PWY.f5Oi2hmEcq4r.6wVFYkrhGGz69quLdm', '测试111', null, '123456789', '1', '381', '0', 'APP', '2019-07-10 08:36:38', '2019-07-10 08:36:38');
INSERT INTO `sys_user` VALUES ('17', '策士', '$2a$10$JuJ/cyOTNPzVS3n9PooR7uhGnxnVv4AcJed7YU9PPttaP6xadHq.m', '123456', null, '', '1', '260', '1', 'APP', '2019-07-13 14:12:40', '2019-07-13 14:12:40');
INSERT INTO `sys_user` VALUES ('18', 'superadmin1', '$2a$10$DC2n2hYi1S341X4UBctxYOOZd75/CqpBv7ChVBDZwcVzAfeBXKT9q', '超管1', null, '', '1', '376', '1', 'BACKEND', '2019-07-13 16:18:18', '2019-07-13 16:18:18');

-- ----------------------------
-- Table structure for sys_user_grouping
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_grouping`;
CREATE TABLE `sys_user_grouping` (
  `userId` int(11) NOT NULL COMMENT '用户id',
  `groupingId` int(11) NOT NULL COMMENT '分组id',
  PRIMARY KEY (`userId`,`groupingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户，分组中间表';

-- ----------------------------
-- Records of sys_user_grouping
-- ----------------------------
INSERT INTO `sys_user_grouping` VALUES ('1', '9');

-- ----------------------------
-- Table structure for t_wechat
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat`;
CREATE TABLE `t_wechat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `openid` varchar(128) NOT NULL COMMENT '微信openid',
  `unionid` varchar(128) DEFAULT NULL COMMENT '微信unionid',
  `userId` int(11) DEFAULT NULL COMMENT '绑定用户的id',
  `app` varchar(32) NOT NULL COMMENT '公众号标识',
  `nickname` varchar(64) NOT NULL COMMENT '微信昵称',
  `sex` varchar(16) DEFAULT NULL COMMENT '微信返回的性别',
  `province` varchar(64) DEFAULT NULL COMMENT '微信返回的省',
  `city` varchar(64) DEFAULT NULL COMMENT '微信返回的城市',
  `country` varchar(64) DEFAULT NULL COMMENT '微信返回的国家',
  `headimgurl` varchar(1024) DEFAULT NULL COMMENT '微信头像',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`),
  KEY `userId` (`userId`),
  KEY `unionid` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='微信信息表';

-- ----------------------------
-- Records of t_wechat
-- ----------------------------

-- ----------------------------
-- Table structure for user_credentials
-- ----------------------------
DROP TABLE IF EXISTS `user_credentials`;
CREATE TABLE `user_credentials` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名或手机号等',
  `type` varchar(16) NOT NULL COMMENT '账号类型（用户名、手机号）',
  `userId` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`username`),
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户凭证表';

-- ----------------------------
-- Records of user_credentials
-- ----------------------------
INSERT INTO `user_credentials` VALUES ('', 'PHONE', '1');
INSERT INTO `user_credentials` VALUES ('12345678910', 'PHONE', '3');
INSERT INTO `user_credentials` VALUES ('19931652745', 'PHONE', '2');
INSERT INTO `user_credentials` VALUES ('admin', 'USERNAME', '1');
INSERT INTO `user_credentials` VALUES ('superadmin', 'USERNAME', '2');
INSERT INTO `user_credentials` VALUES ('superadmin1', 'USERNAME', '18');
INSERT INTO `user_credentials` VALUES ('tttt', 'USERNAME', '4');
INSERT INTO `user_credentials` VALUES ('wmy', 'USERNAME', '3');
INSERT INTO `user_credentials` VALUES ('侧室', 'USERNAME', '10');
INSERT INTO `user_credentials` VALUES ('测试', 'USERNAME', '16');
INSERT INTO `user_credentials` VALUES ('测试100', 'USERNAME', '11');
INSERT INTO `user_credentials` VALUES ('测试1000', 'USERNAME', '13');
INSERT INTO `user_credentials` VALUES ('测试123', 'USERNAME', '12');
INSERT INTO `user_credentials` VALUES ('策士', 'USERNAME', '17');
