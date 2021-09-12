/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : ll-user

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 11/09/2021 17:51:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `gid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '群ID',
  `uid` bigint(20) NOT NULL COMMENT '创建者用户ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '群头像',
  `member_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '成员数量',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for group_member
-- ----------------------------
DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `gid` bigint(20) UNSIGNED NOT NULL COMMENT '群ID',
  `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `remark` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群里的备注',
  `rank` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '等级（0：普通成员，1：管理员，2：群主）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_gid_uid`(`gid`, `uid`) USING BTREE,
  INDEX `idx_uid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群组成员表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of group_member
-- ----------------------------

-- ----------------------------
-- Table structure for group_msg
-- ----------------------------
DROP TABLE IF EXISTS `group_msg`;
CREATE TABLE `group_msg`  (
  `msg_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `gid` bigint(20) UNSIGNED NOT NULL COMMENT '群ID',
  `sender_uid` bigint(20) UNSIGNED NOT NULL COMMENT '发送消息的用户ID',
  `msg_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '消息类型（0：普通文字消息，1：图片消息，2：文件消息，3：语音消息，4：视频消息）',
  `msg_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `need_ack_num` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '需要确认的数量',
  `ack_num` int(10) NULL DEFAULT NULL COMMENT '已确认数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `idx_gid_create_time`(`gid`, `create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群的消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_msg
-- ----------------------------

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `uid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uk_tel`(`tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, '15213230873', 'c3284d0f94606de1fd2af172aba15bf3', '555', '11', '22', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (2, '15213230874', 'c3284d0f94606de1fd2af172aba15bf3', '999', '22', '33', '2021-09-10 23:10:08', '2021-09-10 23:10:08');
INSERT INTO `member` VALUES (3, '15213230875', 'c3284d0f94606de1fd2af172aba15bf3', '666', '44', '55', '2021-09-10 23:10:08', '2021-09-10 23:10:08');

-- ----------------------------
-- Table structure for member_friend
-- ----------------------------
DROP TABLE IF EXISTS `member_friend`;
CREATE TABLE `member_friend`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `friend_uid` bigint(20) NOT NULL COMMENT '朋友的用户id',
  `remark` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime NOT NULL COMMENT '更新的时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uid_friend_uid`(`uid`, `friend_uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 170 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户的朋友表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_friend
-- ----------------------------

-- ----------------------------
-- Table structure for member_friend_msg
-- ----------------------------
DROP TABLE IF EXISTS `member_friend_msg`;
CREATE TABLE `member_friend_msg`  (
  `msg_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `to_uid` bigint(20) UNSIGNED NOT NULL COMMENT '和上面的uid做查询用（查询时始终保持uid小的在前面，这样解决两个好友只需要保存一份数据）',
  `sender_uid` bigint(20) UNSIGNED NOT NULL COMMENT '发送方用户ID',
  `msg_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '消息类型（0：普通文字消息，1：图片消息，2：文件消息，3：语音消息，4：视频消息）',
  `msg_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `create_time` datetime NOT NULL COMMENT '消息创建时间',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `idx_uid_to_uid`(`uid`, `to_uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户的好友消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_friend_msg
-- ----------------------------

-- ----------------------------
-- Table structure for member_recent_msg
-- ----------------------------
DROP TABLE IF EXISTS `member_recent_msg`;
CREATE TABLE `member_recent_msg`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `type` tinyint(3) UNSIGNED NOT NULL COMMENT '类型',
  `gid` bigint(20) UNSIGNED NOT NULL COMMENT '如果是群组消息，则返回群组ID',
  `receive_id` bigint(20) NOT NULL COMMENT '接收者ID',
  `sender_id` bigint(20) NOT NULL COMMENT '发送者ID',
  `msg_type` int(10) UNSIGNED NOT NULL COMMENT '消息类型',
  `msg_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `un_msg_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '未读消息数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户最近的消息列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_recent_msg
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
