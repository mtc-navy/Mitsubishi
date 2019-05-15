$(function () {
    $("#jqGrid").setjgGrid({api: 'sys/common/budgver', hiddenFields: ['DOCENTRY'], buttons: []});
});
var tempnow=new Date();
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: '',
            code: '',
            year: ''
        },
        showList: true,
        title: null,
        info: {},
        check: false,
        Baseveroptions: [],
        tempYear: tempnow,


    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.info = {};
            vm.getBaseverList(0);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(key);
            vm.getBaseverList(key);
        },
        saveOrUpdate: function (event) {
            vm.info.delFlag = vm.check ? 0 : -1;
            vm.info.year = vm.tempYear.getFullYear();
            var uri = "sys/budgver/edit";
            if (!vm.info.docentry) {
                uri = "sys/budgver/add";
               // vm.info.docentry = 0;
            }

            $.ajax({
                type: "POST",
                url: baseURL + uri,
                contentType: "application/json",
                data: JSON.stringify(vm.info),
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
        del: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var uri= "sys/budgver/del";
            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            $.ajax({
                type: "POST",
                url: baseURL + uri,
                contentType: "application/json",
                data: {DOCENTRY: key},
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
        getInfo: function (id) {
            $.ajax({
                type: "GET",
                url: baseURL + "sys/budgver/view",
                contentType: "application/json",
                data: {DOCENTRY: id},
                success: function (r) {
                    if (r.code === 0) {
                        vm.info = r.budgver;
                        vm.check = r.budgver.delFlag == 0 ? true : false;
                        vm.tempYear = new Date(vm.info.year + "-12-31 00:00:00");
                    } else {
                        alert(r.msg);
                    }
                }
            });


        },
        selyear: function (id) {

        },
        getBaseverList: function (id) {
            httpGet('sys/budgver/budgList', {
                DOCENTRY: id
            }, function (data) {
                vm.Baseveroptions = data.budgverList;
            });

        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            var param = JSON.stringify({param1: vm.q.code, param2: vm.q.name, param3: vm.q.year});
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'param': param},
                page: page
            }).trigger("reloadGrid");
        }
    }
});