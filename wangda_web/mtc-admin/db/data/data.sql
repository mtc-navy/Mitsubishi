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
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (41, 0, '销售管理', null, null, 0, 'fa fa-sellsy', 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (42, 41, '销售开票', 'modules/sys/salesInvoice.html', null, 1, 'fa fa-newspaper-o', 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (43, 41, '销售退货', 'modules/sys/return.html', null, 1, 'fa  fa-reply', 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (44, 0, '报表管理', null, null, 0, 'fa fa-pie-chart', 2);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (45, 42, '查看', null, 'sys:invoice:list,sys:invoice:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (46, 42, '新增', null, 'sys:invoice:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (47, 42, '修改', null, 'sys:invoice:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (49, 42, '打印', null, 'sys:invoice:print', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (50, 42, '收款', null, 'sys:invoice:receive', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (51, 42, '取消', null, 'sys:invoice:cancel', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (52, 43, '查看', null, 'sys:return:list,sys:return:info', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (53, 43, '新增', null, 'sys:return:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (54, 43, '修改', null, 'sys:return:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (55, 43, '取消', null, 'sys:return:cancel', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1000, 44, '销售开票', 'ReportServer?reportlet=sales-order.cpt', null, 1, null, 7);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1001, 44, '客户往来报表', 'ReportServer?reportlet=customer-inout.cpt', null, 1, null, 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1002, 44, '开票未提货', 'ReportServer?reportlet=invoiced-untake.cpt', null, 1, null, 2);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1003, 44, '收款信息统计', 'ReportServer?reportlet=receipt-amount-collection.cpt', null, 1, null, 3);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1004, 44, '未收款开票单', 'ReportServer?reportlet=unreceipt-invoice.cpt', null, 1, null, 4);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1005, 44, '根据客户-仓库查询库存', 'ReportServer?reportlet=querystock-by-customer-warehouse.cpt', '5', 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1006, 44, '销售交退货', 'ReportServer?reportlet=sales-delivery-return.cpt', '7', 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1007, 44, '销售开票物料明细', 'ReportServer?reportlet=sales-order-materials-detail.cpt', null, 1, null, 8);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1008, 42, '退货', null, 'sys:invoice:return', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1009, 42, '取消收款', null, 'sys:invoice:cancelReceive', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1010, 42, '收款', null, 'sys:invoice:receive', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1011, 42, '生成内联单', null, 'sys:invoice:inline', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1012, 0, '基础定义', null, null, 0, 'fa fa-pie-chart', 91);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1013, 1012, '定义部门及岗位', 'modules/sys/deptinfo.html', ' ', 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1014, 1013, '查看', null, ' ', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1015, 1012, '预算组织架构', 'modules/sys/budgorga.html', null, 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1016, 1012, '预算版本', 'modules/sys/budgver.html', null, 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1017, 1016, '新增', null, 'sys:budgver:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1018, 1016, '修改', null, 'sys:budgver:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1019, 1016, '删除', null, 'sys:budgver:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1021, 0, '基础预算录入', null, null, 0, 'fa fa-sellsy', 92);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1023, 0, '预算审核', null, null, 0, 'fa fa-university', 93);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1030, 1021, '岗位人员预算', 'modules/bud/userdept.html', null, 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1031, 1030, '新增', null, 'bud:userdept:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1032, 1030, '修改', null, 'bud:userdept:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1033, 1030, '删除', null, 'bud:userdept:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1034, 1021, '不可控费用预算', 'modules/bud/unjobstan.html', null, 1, null, 1);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1035, 1034, '新增', null, 'bud:unjobstan:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1036, 1034, '修改', null, 'bud:unjobstan:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1037, 1034, '删除', null, 'bud:unjobstan:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1038, 1021, '可控固定费用预算', 'modules/bud/fixedjobstan.html', null, 1, null, 2);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1039, 1038, '新增', null, 'bud:fixedjobstan:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1040, 1038, '修改', null, 'bud:fixedjobstan:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1041, 1038, '删除', null, 'bud:fixedjobstan:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1042, 1021, '可控变动费用预算', 'modules/bud/changejobstan.html', null, 1, null, 4);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1043, 1042, '新增', null, 'bud:changejobstan:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1044, 1042, '修改', null, 'bud:changejobstan:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1045, 1042, '删除', null, 'bud:changejobstan:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1046, 1021, '产品销量预算', 'modules/bud/prodsale.html', null, 1, null, 5);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1047, 1046, '新增', null, 'bud:prodsale:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1048, 1046, '修改', null, 'bud:prodsale:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1049, 1046, '删除', null, 'bud:prodsale:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1050, 1021, '产品配销差预算（按销售单元）', 'modules/bud/proddiff.html', null, 1, null, 6);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1051, 1050, '新增', null, 'bud:proddiff:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1052, 1050, '修改', null, 'bud:proddiff:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1053, 1050, '删除', null, 'bud:proddiff:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1054, 1021, '产品配销差预算（按公司）', 'modules/bud/proddiffbydept.html', null, 1, null, 7);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1055, 1054, '新建', null, 'bud:proddiffbydept:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1056, 1054, '修改', null, 'bud:proddiffbydept:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1057, 1054, '删除', null, 'bud:proddiffbydept:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1058, 1023, '销售单元预算审核', 'modules/bud/saleunit.html', null, 1, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1059, 1058, '新增', null, 'bud:saleunit:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1060, 1058, '修改', null, 'bud:saleunit:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1061, 1058, '删除', null, 'bud:saleunit:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1062, 1023, '经营单元预算审核', 'modules/bud/distunit.html', null, 1, null, 2);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1063, 1062, '新增', null, 'bud:distunit:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1064, 1062, '修改', null, 'bud:distunit:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1065, 1062, '删除', null, 'bud:distunit:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1066, 1023, '产品线预算审核', 'modules/bud/productline.html', null, 1, null, 3);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1067, 1066, '新增', null, 'bud:productline:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1068, 1066, '修改', null, 'bud:productline:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1069, 1066, '删除', null, 'bud:productline:delete', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1070, 1023, '注册公司预算审核', 'modules/bud/regcompany.html', null, 1, null, 4);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1071, 1070, '新增', null, 'bud:regcompany:save', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1072, 1070, '修改', null, 'bud:regcompany:update', 2, null, 0);
INSERT INTO SYS_MENU (MENU_ID, PARENT_ID, NAME, URL, PERMS, TYPE, ICON, ORDER_NUM) VALUES (1073, 1070, '删除', null, 'bud:regcompany:delete', 2, null, 0);

--初始化数据字典
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1, '性别', 'sex', '0', '女', 0, '2296', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (2, '性别', 'sex', '1', '男', 1, '1', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (3, '性别', 'sex', '2', '未知', 2, '2', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (4, '用户类型', 'user_type', '1', '基地开票员', 1, '222', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (5, '用户类型', 'user_type', '2', '办事处开票员', 2, '42', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (6, '用户类型', 'user_type', '3', '客户', 3, '522', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1000, '付款方式', 'pay_type', '预收款', '预收款', 1, '6', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1001, '付款方式', 'pay_type', '现金', '现金', 2, '7', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1002, '付款方式', 'pay_type', 'POS机', 'POS机', 3, '8', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1003, '付款方式', 'pay_type', '信用社', '信用社', 4, '811', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1005, '付款方式', 'pay_type', '邮局', '邮局', 5, '44', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1006, '付款方式', 'pay_type', '手续费', '手续费', 6, '55', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1008, '付款方式', 'pay_type', '电话银行', '电话银行', 7, '1', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1009, '付款方式', 'pay_type', '银行存款', '银行存款', 8, '2', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1011, '付款方式', 'pay_type', '电汇', '电汇', 9, '3', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1013, '付款方式', 'pay_type', '-', '-', 10, '111', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1014, '单据状态', 'doc_status', 'O', '打开', 1, null, 1);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1015, '单据状态', 'doc_status', 'C', '已取消', 4, '5', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1016, '提货基地', 'OFFICE_BPLID', '4', '四川旺达饲料有限公司A（优聚众成）', 1, '6', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1017, '单据状态', 'doc_status', 'D', '草稿', 1, '7', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1018, '单据状态', 'doc_status', 'W', '未发货', 2, '1111222', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1019, '单据状态', 'doc_status', 'Y', '已发货', 3, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1020, '部门类型', 'dept_type01', 'A', '公司', 1, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1021, '部门类型', 'dept_type01', 'B', '部门', 2, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1022, '部门类型', 'dept_type01', 'C', '岗位', 3, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1023, '部门类型', 'dept_type02', 'C', '管理', 1, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1024, '部门类型', 'dept_type02', 'A', '生产', 2, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1025, '部门类型', 'dept_type02', 'B', '销售', 3, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1026, '预算组织架构类型', 'budgorga_type', 'A', '公司', 1, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1027, '预算组织架构类型', 'budgorga_type', 'B', '经营单元', 2, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1028, '预算组织架构类型', 'budgorga_type', 'C', '销售单元', 3, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1029, '列表类型', 'list_type', 'prodList', 'MTC_PD_GetList', 1, '产品线列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1030, '部门类型', 'dept_type02', 'D', '技术', 4, null, 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1031, '列表类型	', 'list_type', 'budgver', 'MTC_PD_GetBudgVerList', 1, '预算版本列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1033, '列表类型', 'list_type', 'userdept', 'MTC_PD_GetUserDeptList', 1, '岗位人员预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1035, '下拉项类型', 'drop_list', 'compList', 'MTC_BUD_GetBPLId', 1, '公司下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1036, '下拉项类型', 'drop_list', 'deptList', 'MTC_BUD_GetDeptCode', 1, '部门下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1037, '下拉项类型', 'drop_list', 'buziUnitList', 'MTC_BUD_GetBuziUnit', 1, '经营单元下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1038, '下拉项类型', 'drop_list', 'salesUnitList', 'MTC_BUD_GetSalesUnit', 1, '销售单元下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1039, '下拉项类型', 'drop_list', 'expAcctList', 'MTC_BUD_GetExpAcct', 1, '费用科目下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1040, '下拉项类型', 'drop_list', 'postCodeList', 'MTC_BUD_GetPostCode', 1, '岗位下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1041, '下拉项类型', 'drop_list', 'itemList', 'MTC_BUD_GetItemCode', 1, '物料下拉项', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1042, '下拉项类型', 'drop_list', 'verList', 'MTC_BUD_GetBudVerList', 1, '预算版本列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1043, '列表类型', 'list_type', 'uncont', 'MTC_PD_GetNctrExpList', 1, '不可控费用预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1044, '列表类型', 'list_type', 'fixcont', 'MTC_PD_GetCtrFixExpList', 1, '可控固定费用列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1045, '列表类型', 'list_type', 'chancont', 'MTC_PD_GetCtrVarExpList', 1, '可控变动费用预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1046, '列表类型', 'list_type', 'prodsale', 'MTC_PD_GetSalesQtyList', 1, '产品销量预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1047, '列表类型', 'list_type', 'diffcomp', 'MTC_PD_GetIomcForCmpList', 1, '产品配销差预算列表（按公司）', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1048, '列表类型', 'list_type', 'diffsales', 'MTC_PD_GetIomcList', 1, '产品配销差预算列表（按销售单元）', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1049, '列表类型', 'list_type', 'apprsaleunit', 'MTC_PD_GetSalesUnitList', 1, '销售单元预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1050, '列表类型', 'list_type', 'apprbuziunit', 'MTC_PD_GetBuziUnitList', 1, '经营单元预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1051, '列表类型', 'list_type', 'apprprodline', 'MTC_PD_GetProdLineList', 1, '产品线预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1052, '列表类型', 'list_type', 'apprcompany', 'MTC_PD_GetCompanyList', 1, '注册公司预算列表', 0);
INSERT INTO SYS_DICT (ID, NAME, TYPE, CODE, "VALUE", ORDER_NUM, REMARK, DEL_FLAG) VALUES (1053, '下拉项类型', 'drop_list', 'prodList', 'MTC_BUD_GetProdList', 1, '产品线下拉项', 0);

INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (1, 'USER_DEFAULT_PASSWORD_COMPANY', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 1, 'SAP创建用户默认密码(基地账户)');
INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (2, 'USER_DEFAULT_SALT_COMPANY', 'YzcmCZNvbXocrsz9dm8e', 1, 'SAP创建用户默认密钥(基地账户)');
INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (3, 'USER_DEFAULT_PASSWORD_OFFICE', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 1, 'SAP创建用户默认密码(办事处账户)');
INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (4, 'USER_DEFAULT_SALT_OFFICE', 'YzcmCZNvbXocrsz9dm8e', 1, 'SAP创建用户默认密钥(办事处账户)');
INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (5, 'USER_DEFAULT_PASSWORD_CUSTOMER', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 1, 'SAP创建用户默认密码(客户)');
INSERT INTO SYS_CONFIG (ID, PARAM_KEY, PARAM_VALUE, STATUS, REMARK) VALUES (6, 'USER_DEFAULT_SALT_CUSTOMER', 'YzcmCZNvbXocrsz9dm8e', 1, 'SAP创建用户默认密钥(客户)');

--初始化角色
INSERT INTO SBO_TQ_LIVE.SYS_ROLE (ROLE_ID, ROLE_NAME, REMARK, DEPT_ID, CREATE_TIME, SAP_ID) VALUES (1, '基地开票员', '基地开票员', null, '2018-10-15 16:24:24', '1');
INSERT INTO SBO_TQ_LIVE.SYS_ROLE (ROLE_ID, ROLE_NAME, REMARK, DEPT_ID, CREATE_TIME, SAP_ID) VALUES (2, '办事处开票员', '办事处开票员', null, '2018-10-15 16:24:46', '2');
INSERT INTO SBO_TQ_LIVE.SYS_ROLE (ROLE_ID, ROLE_NAME, REMARK, DEPT_ID, CREATE_TIME, SAP_ID) VALUES (3, '客户', '客户', null, '2018-10-15 16:25:02', '3');

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