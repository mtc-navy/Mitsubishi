$(function () {
    vm.getTree(null);
    //填充下拉框
    vm.getTypeList();
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showCompany:false,
        showBusUnit:false,
        title: null,
        dept: {
            parentName: null,
            parentId: 0,
            deptId: 0,
            name: null,
            hrId: null,
            type: null,
            deptType: null,
            prolineyn: null,
            compyn: null,
            salesyn: null,
            open: null
        },
        checklist: {
            chk1: false,
            chk2: false,
            chk3: false,
            chk4: false
        },
        select: {
            typeoptions: [],
            typevalue: '',
            depttypeoptions: [],
            depttypevalue: ''

        }
    },
    methods: {
        add: function () {

        },
        update: function () {

        },
        del: function () {

        },
        saveOrUpdate: function (event) {
            var deptid = vm.dept.deptId;
            //重新赋值
            vm.dept.prolineyn = vm.checklist.chk1 ? "1" : "";
            vm.dept.compyn = vm.checklist.chk2 ? "1" : "";
            vm.dept.salesyn = vm.checklist.chk3 ? "1" : "";
            vm.dept.delFlag = vm.checklist.chk4 ? 0 : -1;
            var url = "sys/dept/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                            vm.getTree(deptid);

                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getTree: function (id) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                //展开所有节点
                ztree.expandAll(true);
                var node = ztree.getNodeByParam("deptId", id);
                ztree.selectNode(node);
            });
        },
        getTypeList: function () {
            httpGet('sys/dict/type', {
                type: 'dept_type01', sidx: "order_num", order: "asc"
            }, function (data) {
                vm.select.typeoptions = data.page.list;
            });
            httpGet('sys/dict/type', {
                type: 'dept_type02', sidx: "order_num", order: "asc"
            }, function (data) {
                vm.select.depttypeoptions = data.page.list;
            });
        },
        reload: function () {

        },
        initDeptTree:function () {
            layui.use('layer', function() {
                var layer = layui.layer;
                var index = layer.load(1);
                var url = "sys/dept/init";
                var deptid = vm.dept.deptId;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.dept),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('提取成功', function () {
                                vm.reload();
                                vm.getTree(null);
                              //  vm.getTree(deptid);

                            });
                        } else {
                            alert(r.msg);
                        }
                        layer.close(index);
                    }
                });
            });

        },
        collapse:function(){
            var companies = ztree.getNodes()[0].children;
            companies.forEach(function(company){
                ztree.expandNode(company, false, false, false);
            })
        },
        expand:function(){
            ztree.expandAll(true);
        }
    }
});
var ztree;

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
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {
    var url = 'sys/dept/info/' + treeNode.deptId;
    httpGet(url, {}, function (d) {
        var data = d.dept;
        vm.dept.deptCode = data.deptCode;
        vm.dept.deptId = data.deptId;
        vm.dept.name = data.name;
        vm.dept.parentId = data.parentCode;
        vm.dept.parentName = data.parentName;
        vm.dept.type = data.type;
        vm.dept.hrId = data.hrId;
        vm.dept.comareId = data.comareId;
        vm.dept.comareName = data.comareName;
        vm.dept.busDeptId = data.busDeptId;
        vm.dept.busDeptName = data.busDeptName;
        vm.dept.proLineId = data.proLineId;
        vm.dept.proLineName = data.proLineName;

        vm.dept.deptType = data.deptType;
        vm.checklist.chk1 = data.prolineyn == "1" ? true : false;
        vm.checklist.chk2 = data.compyn == "1" ? true : false;
        vm.checklist.chk3 = data.salesyn == "1" ? true : false;
        vm.checklist.chk4 = data.delFlag == 0 ? true : false;

        vm.showCompany = false;
        vm.showBusUnit = false;

        //如果是公司则展示所属大区
        if(vm.dept.type == 'B'){
            showCompany();
        }
        //如果是销售单元则展示经营单元和产品线
        if(vm.dept.type == 'D'){
            showBusUnit();
        }
    });


    // console.log(treeNode);
};

function showCompany(){
    vm.showCompany = true;
}

function showBusUnit(){
    vm.showBusUnit =true;
}








