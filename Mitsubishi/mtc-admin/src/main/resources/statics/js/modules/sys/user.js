$(function () {
    vm.getUserType(function() {
        $("#jqGrid").jqGrid({
            url: baseURL + 'sys/user/list',
            datatype: "json",
            colModel: [
                {label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true},
                {label: '用户代码', name: 'username', width: 75},
                {label: '用户名称', name: 'sapusername', width: 75},
                {label: '所属部门', name: 'deptName', sortable: false, width: 75},
                {label: '邮箱', name: 'email', width: 100},
                {label: '手机号', name: 'mobile', width: 80},
                {
                    label: '状态', name: 'status', width: 45, formatter: function (value, options, row) {
                        return value === 0 ?
                            '<span class="label label-danger">禁用</span>' :
                            '<span class="label label-success">正常</span>';
                    }
                },
                {label: '创建时间', name: 'createTime', index: "create_time", width: 90},
                {label: '备注', name: 'remark', width: 90}
            ],
            viewrecords: true,
            height: 385,
            rowNum: 10,
            rowList: [10, 30, 50],
            rownumbers: true,
            rownumWidth: 25,
            autowidth: true,
            multiselect: true,
            pager: "#jqGridPager",
            jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },
            gridComplete: function () {
                //隐藏grid底部滚动条
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                $("#jqGrid").setGridWidth($(window).width());
            },
            loadComplete: function (data) {
                if (data.code != 0) {
                    alert(data.msg);
                }
            }
        });
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            username: null
        },
        showList: true,
        title: null,
        roleList: {},
        areaList:{},
        userTypeList: {},
        userType: "",
        user: {
            status: 1,
            deptId: null,
            deptName: null,
            area: null,
            roleIdList: []
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getUserType: function () {
            $.get(baseURL + "sys/dict/type?type=user_type", function (r) {
                vm.userTypeList = r.page.list;
            });
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName: null, deptId: null, status: 1, roleIdList: []};

            //获取角色信息
            this.getRoleList();
            this.getAreaList();
            this.getUserType();

            vm.getDept();
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if (node != null) {
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
            this.getAreaList();
            this.getUserType();
        },
        del: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getUser: function (userId) {
            $.get(baseURL + "sys/user/info/" + userId, function (r) {
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        getUserType: function (callback) {
            $.get(baseURL + "sys/dict/type?type=user_type", function (r) {
                vm.userTypeList = r.page.list;
                if(typeof callback != 'undefined'){
                    callback();
                }
            });
        },
        getAreaList: function () {
            $.get(baseURL + "sys/dict/type?type=area", function (r) {
                vm.areaList = r.page.list;
            });
        },
        getCustomerList:function(orgLevel,callback){
            var url = "sys/droplist/customerList";
            httpGetInfo(url, function(r){
                if(typeof callback != 'undefined'){
                    callback(r.comareList);
                }
            });
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'username': vm.q.username},
                page: page
            }).trigger("reloadGrid");
        }
    }
});