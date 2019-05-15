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
        copy: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            httpPostFormData("sys/budgver/copy",{
                docEntry:key
            },function(data){
                if(data.msg==null || data.msg==""){
                    alert("生成版本成功");
                    vm.reload();
                }else{
                    alertV2(data.msg,{
                        area: ['620px'], //宽高
                    });
                }

            })
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