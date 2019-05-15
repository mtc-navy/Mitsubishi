$(function () {
    vm.getTree(null);
    //填充下拉框
    vm.getTypeList();
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        info: {
            delFlag: null,
            docentry: null,
            name: null,
            parentCode: null,
            parentId: null,
            parentName: null,
            prolcode: null,
            prolname: null,
            type: null,
        },
        check: false,
        select: {
            typeoptions: [],
            typevalue: null
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
            var id = vm.info.docentry;
            //重新赋值
            vm.info.delFlag = vm.check ? 0 : -1;
            var url = "sys/budgorga/edit";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.info),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                            //vm.getTree(id);

                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getTree: function (id) {
            //加载菜单树
            $.get(baseURL + "/sys/budgorga/treeList", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.orgaList);
                //展开所有节点

                var tree = $.fn.zTree.getZTreeObj('deptTree');
                //获取 zTree 的全部节点数据将节点数据转换为简单 Array 格式
                var nodes = tree.transformToArray(tree.getNodes());
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].level == 0) {
                        //  console.log(nodes[i].name);
                        //根节点展开
                        tree.expandNode(nodes[i], true, true, false)
                    } else {
                        tree.expandNode(nodes[i], false, true, false)
                    }
                }


                var node = ztree.getNodeByParam("docentry", id);
                ztree.selectNode(node);

            });
        },
        getTypeList: function () {
            httpGet('sys/dict/type', {
                type: 'budgorga_type', sidx: "order_num", order: "asc"
            }, function (data) {
                vm.select.typeoptions = data.page.list;
            });

        },
        reload: function () {

        },
        initDeptTree:function () {
            layui.use('layer', function() {
                var layer = layui.layer;
                var index = layer.load(1);
                var url = "sys/budgorga/init";

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

                            });
                        } else {
                            alert(r.msg);
                        }
                        layer.close(index);
                    }
                });
            });

        }
    }
});
var ztree;

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "docentry",
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
    var url = '/sys/budgorga/view/' + treeNode.docentry;

    httpGet(url, {}, function (d) {
        var data = d.budgorga;
        vm.info.delFlag = data.delFlag;
        vm.info.docentry = data.docentry;
        vm.info.name = data.name;
        vm.info.orderNum = data.orderNum;
        vm.info.parentCode = data.parentCode;
        vm.info.parentId = data.parentId;
        vm.info.parentName = data.parentName;
        vm.info.prolcode = data.prolcode;
        vm.info.prolname = data.prolname;
        vm.info.type = data.type;
        vm.check = data.delFlag == 0 ? true : false;

    });
};










