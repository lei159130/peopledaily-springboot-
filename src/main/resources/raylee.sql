/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MariaDB
 Source Server Version : 100328

 Target Server Type    : MariaDB
 Target Server Version : 100328
 File Encoding         : 65001

 Date: 28/07/2021 21:50:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for people_daily
-- ----------------------------
DROP TABLE IF EXISTS `people_daily`;
CREATE TABLE `people_daily`  (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `seminar_id` int(255) NULL DEFAULT NULL COMMENT '分类ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `rongyun_id` int(11) NULL DEFAULT NULL COMMENT '融云ID',
  `unique_id` int(11) NULL DEFAULT NULL COMMENT '微信uniqueID',
  `contents` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `audio_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频链接',
  `audio_play_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频时长',
  `share_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享链接',
  `share_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享图片',
  `show_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展示图片',
  `share_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `news_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `business` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务模块',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务功能代码',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务功能对应值',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务功能代码描述',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `modify_data` datetime NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`business`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('peopledaily', 'getInfoUp', 'https://app.peopleapp.com/WapApi/610/ArtInfoApi/getInfoUp?id=ID&securitykey=SECURITYKEY&interface_code=INTERFACE_CODE', '人民日报文章详情请求接口', '2021-07-27 21:15:03', '2021-07-27 21:15:05');
INSERT INTO `sys_config` VALUES ('peopledaily', 'getSeminarList', 'https://app.peopleapp.com/WapApi/610/homeApi/getSeminarList?seminar_id=SEMINAR_ID&refresh_time=REFRESH_TIME&page=PAGE&securitykey=SECURITYKEY&interface_code=INTERFACE_CODE', '人民日报文章列表请求接口', '2019-12-13 10:23:30', '2019-12-13 10:23:30');
INSERT INTO `sys_config` VALUES ('peopledaily', 'paramvalue', '[{\"id\":\"52\",\"name\":\"健康侦探\"},{\"id\":\"53\",\"name\":\"美食美地\"},{\"id\":\"54\",\"name\":\"星星晚上好\"},{\"id\":\"55\",\"name\":\"新读新听\"},{\"id\":\"56\",\"name\":\"全天热点梳理\"},{\"id\":\"57\",\"name\":\"夜读\"}]', '人民日报文章请求接口参数集合', '2019-12-13 10:23:31', '2019-12-13 10:23:31');
INSERT INTO `sys_config` VALUES ('peopledaily', 'securitykey', 'rmj$cd&2e09elp468b7b87dlsye#jipl', '人民日报securitykey生成规则', '2021-07-22 22:43:18', '2021-07-22 22:43:21');
INSERT INTO `sys_config` VALUES ('proxy', 'url', 'xxxx', '代理查询接口', '2021-07-27 21:15:08', '2021-07-27 21:15:10');

SET FOREIGN_KEY_CHECKS = 1;
