-- 初始数据
-- 初始用户
INSERT INTO sys_user (user_id, username, sapusername, password, salt, email, mobile, status, dept_id, create_time, user_type, del_flag, remark) VALUES ('1', 'admin', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'admin@admin.io', '13112345678', '1', '1', '2016-11-11 11:11:11', '1', 0, '系统管理员');


-- 初始化菜单
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1, 0, '系统管理', null, null, 0, 'fa fa-cog', 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (2, 1, '用户管理', 'modules/sys/user.html', null, 1, 'fa fa-user', 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (3, 1, '角色管理', 'modules/sys/role.html', null, 1, 'fa fa-user-secret', 2);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (4, 1, '菜单管理', 'modules/sys/menu.html', null, 1, 'fa fa-th-list', 3);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (5, 1, 'SQL监控', 'druid/sql.html', null, 1, 'fa fa-bug', 4);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (15, 2, '查看', null, 'sys:user:list,sys:user:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (16, 2, '新增', null, 'sys:user:save,sys:role:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (17, 2, '修改', null, 'sys:user:update,sys:role:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (18, 2, '删除', null, 'sys:user:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (19, 3, '查看', null, 'sys:role:list,sys:role:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (20, 3, '新增', null, 'sys:role:save,sys:menu:perms', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (21, 3, '修改', null, 'sys:role:update,sys:menu:perms', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (22, 3, '删除', null, 'sys:role:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (23, 4, '查看', null, 'sys:menu:list,sys:menu:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (24, 4, '新增', null, 'sys:menu:save,sys:menu:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (25, 4, '修改', null, 'sys:menu:update,sys:menu:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (26, 4, '删除', null, 'sys:menu:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (27, 1, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'fa fa-sun-o', 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (29, 1, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'fa fa-file-text-o', 7);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (31, 1, '部门管理', 'modules/sys/dept.html', null, 1, 'fa fa-file-code-o', 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (32, 31, '查看', null, 'sys:dept:list,sys:dept:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (33, 31, '新增', null, 'sys:dept:save,sys:dept:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (34, 31, '修改', null, 'sys:dept:update,sys:dept:select', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (35, 31, '删除', null, 'sys:dept:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (36, 1, '字典管理', 'modules/sys/dict.html', null, 1, 'fa fa-bookmark-o', 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (37, 36, '查看', null, 'sys:dict:list,sys:dict:info', 2, null, 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (38, 36, '新增', null, 'sys:dict:save', 2, null, 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (39, 36, '修改', null, 'sys:dict:update', 2, null, 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (40, 36, '删除', null, 'sys:dict:delete', 2, null, 6);

--初始化数据字典
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1, '性别', 'sex', '0', '女', 0, '0', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (2, '性别', 'sex', '1', '男', 1, '1', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (3, '性别', 'sex', '2', '未知', 2, '2', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (4, '用户类型', 'user_type', '0', '管理员', 0, '管理员', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (5, '用户类型', 'user_type', '1', '销售员', 1, '销售员', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (6, '用户类型', 'user_type', '2', '采购员', 2, '采购员', 0);


-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- 云存储服务相关SQL，如果不使用该功能，则不用执行下面SQL -------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
-- 文件上传
CREATE TABLE sys_oss (
  id bigint NOT NULL,
  url varchar(200) COMMENT 'URL地址',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id)
) COMMENT '文件上传';
CREATE SEQUENCE sys_oss_seq start with 1000 ;

INSERT INTO sys_config (id,param_key, param_value, status, remark) VALUES (1,'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');



-- 以下删除不用
-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- 定时任务相关表结构，如果不使用job模块，则不用执行下面SQL -------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------


-- 初始化菜单数据
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('6', '1', '定时任务', 'modules/job/schedule.html', NULL, '1', 'fa fa-tasks', '5');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('7', '6', '查看', NULL, 'sys:schedule:list,sys:schedule:info', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('8', '6', '新增', NULL, 'sys:schedule:save', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('9', '6', '修改', NULL, 'sys:schedule:update', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('10', '6', '删除', NULL, 'sys:schedule:delete', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('11', '6', '暂停', NULL, 'sys:schedule:pause', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('12', '6', '恢复', NULL, 'sys:schedule:resume', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('13', '6', '立即执行', NULL, 'sys:schedule:run', '2', NULL, '0');
INSERT INTO sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES ('14', '6', '日志列表', NULL, 'sys:schedule:log', '2', NULL, '0');

-- 定时任务
CREATE TABLE schedule_job (
  job_id bigint NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status tinyint DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (job_id)
) COMMENT '定时任务';
CREATE SEQUENCE schedule_job_seq start with 1000 ;

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id bigint NOT NULL COMMENT '任务日志id',
  job_id bigint NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status tinyint NOT NULL COMMENT '任务状态    0：成功    1：失败',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int NOT NULL COMMENT '耗时(单位：毫秒)',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (log_id)
) COMMENT '定时任务日志';
CREATE SEQUENCE schedule_job_log_seq start with 1000 ;

INSERT INTO schedule_job (job_id,bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES (1,'testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO schedule_job (job_id,bean_name, method_name, params, cron_expression, status, remark, create_time) VALUES (2,'testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');



--  quartz自带表结构
CREATE TABLE QRTZ_JOB_DETAILS(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME VARCHAR(250) NOT NULL,
  IS_DURABLE VARCHAR(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP));

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT NULL,
  PREV_FIRE_TIME BIGINT NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT NOT NULL,
  END_TIME BIGINT NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP));

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT NOT NULL,
  REPEAT_INTERVAL BIGINT NOT NULL,
  TIMES_TRIGGERED BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP));

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP));

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR(1) NULL,
  BOOL_PROP_2 VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP));

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP));

CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME));

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP));

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT NOT NULL,
  SCHED_TIME BIGINT NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT VARCHAR(1) NULL,
  REQUESTS_RECOVERY VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID));

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT NOT NULL,
  CHECKIN_INTERVAL BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME));

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME));

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
*/

