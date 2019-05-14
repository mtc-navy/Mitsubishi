-- 菜单
CREATE TABLE sys_menu (
  menu_id bigint NOT NULL primary key,
  parent_id bigint COMMENT '父菜单ID，一级菜单为0',
  name NVARCHAR(50) COMMENT '菜单名称',
  url NVARCHAR(200) COMMENT '菜单URL',
  perms NVARCHAR(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  type int COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon varchar(50) COMMENT '菜单图标',
  order_num int COMMENT '排序'
) COMMENT '菜单管理';
CREATE SEQUENCE sys_menu_seq start with 100 ;

-- 部门
CREATE COLUMN TABLE MTC_SYS_DEPT (
 DEPT_ID  INT PRIMARY KEY  COMMENT  'DocEntry',
 DEPTCODE  NVARCHAR(30)   COMMENT  'DEPT_ID',
 PARENT_ID  INT   COMMENT  '上级部门ID，一级部门为0',
 NAME  NVARCHAR(200)   COMMENT  '部门名称',
 ORDER_NUM  INT   COMMENT  '排序',
 DEL_FLAG  INT   COMMENT  '是否删除  -1：已删除  0：正常',
 TYPE  NVARCHAR(10)   COMMENT  '类型',
 DEPT_TYPE  NVARCHAR(10)   COMMENT  '部门类型',
 PROLINEYN  NVARCHAR(10)   COMMENT  '影响产品线',
 COMPYN  NVARCHAR(10)   COMMENT  '影响注册公司',
 SALESYN  NVARCHAR(10)   COMMENT  '影响销售组织',
 HRID  NVARCHAR(30)   COMMENT  'HR ID号',
 CLASSCODE  NVARCHAR(30)   COMMENT  '分类编号',
 CREATED_DATE  DateTime   COMMENT  '创建日期',
 CREATED_TIME  NVARCHAR(10)   COMMENT  '创建时间',
 UPDATED_DATE  DateTime   COMMENT  '更新日期',
 UPDATED_TIME  NVARCHAR(10)   COMMENT  '更新时间',
 CREATOR  NVARCHAR(10)   COMMENT  '创建者',
 UPDATER  NVARCHAR(10)   COMMENT  '更新者')
;
CREATE SEQUENCE MTC_SYS_DEPT_SEQ start with 100 ;
CREATE UNIQUE  INDEX IDX_MTC_SYS_DEPT ON MTC_SYS_DEPT(DEPTCODE);

-- 系统用户
CREATE TABLE sys_user (
  user_id bigint NOT NULL COMMENT '用户ID',
  username varchar(50) NOT NULL COMMENT '用户代码',
  sapusername varchar(100) NOT NULL COMMENT '用户名称',
  password varchar(100) COMMENT '密码',
  salt varchar(20) COMMENT '密钥',
  email varchar(100) COMMENT '邮箱',
  mobile varchar(100) COMMENT '手机号',
  status tinyint COMMENT '状态  0：禁用   1：正常',
  dept_id bigint COMMENT '部门ID',
  create_time datetime COMMENT '创建时间',
  user_type varchar(10) COMMENT '用户类型',
  area varchar(10) COMMENT '区域',
  card_code varchar(10) COMMENT '客户',
  province varchar(20) COMMENT '省',
  city varchar(20) COMMENT '市',
  address varchar(100) COMMENT '地址',
  del_flag tinyint DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
  remark varchar(500) COMMENT '备注',
  PRIMARY KEY (user_id)
) COMMENT '系统用户';
CREATE SEQUENCE sys_user_seq start with 100 ;
CREATE unique INDEX sys_user_id_idx ON sys_user(username);

-- 角色
CREATE TABLE sys_role (
  role_id bigint not null,
  role_name varchar(100) COMMENT '角色名称',
  remark varchar(100) COMMENT '备注',
  dept_id bigint COMMENT '部门ID',
  create_time datetime COMMENT '创建时间',
  PRIMARY KEY (role_id)
) COMMENT '角色';
CREATE SEQUENCE sys_role_seq start with 100 ;

-- 用户与角色对应关系
CREATE TABLE sys_user_role (
  id bigint NOT NULL,
  user_id bigint COMMENT '用户ID',
  role_id bigint COMMENT '角色ID',
  PRIMARY KEY (id)
) COMMENT '用户与角色对应关系';
CREATE SEQUENCE sys_user_role_seq start with 100 ;

-- 角色与菜单对应关系
CREATE TABLE sys_role_menu (
  id bigint NOT NULL,
  role_id bigint COMMENT '角色ID',
  menu_id bigint COMMENT '菜单ID',
  PRIMARY KEY (id)
) COMMENT '角色与菜单对应关系';
CREATE SEQUENCE sys_role_menu_seq start with 100 ;

-- 角色与部门对应关系
CREATE TABLE sys_role_dept (
  id bigint NOT NULL,
  role_id bigint COMMENT '角色ID',
  dept_id bigint COMMENT '部门ID',
  PRIMARY KEY (id)
) COMMENT '角色与部门对应关系';
CREATE SEQUENCE sys_role_dept_seq start with 100 ;

-- 系统配置信息
CREATE TABLE sys_config (
  id bigint NOT NULL,
  param_key varchar(50) COMMENT 'key',
  param_value varchar(2000) COMMENT 'value',
  status tinyint DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  remark varchar(500) COMMENT '备注',
  PRIMARY KEY (id)
) COMMENT '系统配置信息表';
CREATE SEQUENCE sys_config_seq start with 100 ;
CREATE unique INDEX sys_cinfig_param_key_idx ON sys_config(param_key);

-- 数据字典
CREATE TABLE sys_dict (
  id bigint NOT NULL,
  name varchar(100) NOT NULL COMMENT '字典名称',
  type varchar(100) NOT NULL COMMENT '字典类型',
  code varchar(100) NOT NULL COMMENT '字典码',
  value varchar(1000) NOT NULL COMMENT '字典值',
  order_num int DEFAULT 0 COMMENT '排序',
  remark varchar(255) COMMENT '备注',
  del_flag tinyint DEFAULT 0 COMMENT '删除标记  1：已删除  0：正常',
  PRIMARY KEY (id)
) COMMENT '数据字典表';
CREATE SEQUENCE sys_dict_seq start with 100 ;
CREATE unique INDEX sys_dict_type_code_idx ON sys_dict(type,code);

-- 系统日志
CREATE TABLE sys_log (
  id bigint NOT NULL,
  username varchar(50) COMMENT '用户名',
  operation varchar(50) COMMENT '用户操作',
  method varchar(200) COMMENT '请求方法',
  params NCLOB COMMENT '请求参数',
  time bigint NOT NULL COMMENT '执行时长(毫秒)',
  ip varchar(64) COMMENT 'IP地址',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id)
) COMMENT '系统日志';
CREATE SEQUENCE sys_log_seq start with 100 ;

CREATE TABLE schedule_job (
  job_id bigint NOT NULL primary key COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status tinyint DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  create_time datetime DEFAULT NULL COMMENT '创建时间'
) COMMENT '定时任务';
CREATE SEQUENCE schedule_job_seq start with 100 ;

-- 定时任务日志
CREATE TABLE schedule_job_log (
  log_id bigint NOT NULL PRIMARY KEY COMMENT '任务日志id',
  job_id bigint NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status tinyint NOT NULL COMMENT '任务状态    0：成功    1：失败',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int NOT NULL COMMENT '耗时(单位：毫秒)',
  create_time datetime DEFAULT NULL COMMENT '创建时间'
) COMMENT '定时任务日志';
CREATE SEQUENCE schedule_job_log_seq start with 100 ;


CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME));
