$(function () {
    $("#jqGrid").setjgGrid({api: 'sys/common/diffcomp', hiddenFields: ['DOCENTRY'], buttons: []});
    Document.initDeptTree(null,vm.orgLevel,"T");
    vm.initqdrop();
    vm.showTreeData();
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startDate'
            ,done: function(value, date){
                vm.q.param3=value;
                if(vm.q.param4)
                {
                    if(value>vm.q.param4)
                    {
                        alert('开始时间不能大于结束时间');
                        vm.q.param3='';
                    }
                }

            }
        });
        laydate.render({
            elem: '#endDate'
            ,done: function(value, date){
                vm.q.param4=value;
                if(vm.q.param3)
                {
                    if(value<vm.q.param3)
                    {
                        alert('结束时间不能小于开始时间')
                        vm.q.param4='';
                    }

                }

            }
        });
    });

});


var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            param1: '',
            param2: '',
            param3: '',
            param4: '',
            param5: '',


        },

        showList: true,
        showTree:false,
        title: null,
        info: {
            vercode: null,
            vername: null,
            bplid: null,
            bplname:null,
            state: null,
            depcode: null,
            depname:null,
            buziunitcode: null,
            buziunitname:null,
            salesunitcode: null,
            salesunitname:null,
            docdate: null,
            docentry:null

        },
        drop: {
            verList: [],
            compList: [],
            stateList: [],
            deptList: [],
            buziUnitList: [],
            salesUnitList: [],
            itemList:[]
        },
        qdrop: {
            verList: [],
            compList: [],
            stateList: [],
            deptList: [],
            buziUnitList: [],
            salesUnitList: [],
            prodList:[]
        },
        showButton: {
            postapprove: false,
            approve: false,
            reject: false,
            close: false,
            cancelclose: false,
            save: false,
            copyfrom: false,
        },
        showInput: {
            add:true,
            buziUnit: false,
            salesUnit: false,
        },
        treeNode:{
            deptCode:null,
            companyCode:null,
            buziCode:null,
            saleCode:null
        },
        grid:{
            width:0,
        },
        orgLevel:'CIOMC',
        permission:{
            update:false,
        },

    },
    components: {
        'bud-table': prodsaleComponent,
    },
    methods: {
        query: function () {

            vm.q.param2=vm.treeNode.companyCode;
            vm.reload();
            //vm.initqdrop();
        },
        cleanDrop: function () {

            vm.drop.verList = [];
            vm.drop.compList = [];
            vm.drop.stateList = [];
            vm.drop.deptList = [];
            vm.drop.buziUnitList = [];
            vm.drop.salesUnitList = [];
            vm.drop.itemList = [];
        },
        cleanInput: function () {

            vm.info.vercode = null;
            vm.info.vername = null;
            vm.info.bplid = null;
            vm.info.bplname=null;
            vm.info.docstatus = 'D';
            vm.info.depcode = null;
            vm.info.depname= null;
            vm.info.buziunitcode = null;
            vm.info.buziunitname=null;
            vm.info.salesunitcode = null;
            vm.info.salesunitname=null;
            vm.info.docdate = new Date();
        },
        cleanButton: function () {

            vm.showButton.postapprove= false;
            vm.showButton.approve= false;
            vm.showButton.reject= false;
            vm.showButton.close= false;
            vm.showButton.cancelclose= false;
            vm.showButton.save= false;
            vm.showButton.copyfrom= false;
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            Document.getGridWidth();
            vm.cleanButton();
            vm.cleanInput();
            vm.cleanDrop();
            vm.initBuds();
            vm.info.docentry=null;
            vm.showButton.save=true;
            vm.showButton.copyfrom=true;
            vm.showInput.add=true;

            vm.drop.stateList = Document.initStatusList();
            vm.drop.verList = Document.initVerList();
            Document.initCompList(vm.orgLevel,function(result){
                vm.drop.compList = result;
                if(vm.treeNode.companyCode) {
                    vm.info.bplid = vm.treeNode.companyCode;
                    vm.selectedcompcode(vm.info.bplid);
                    vm.info.buziunitcode = vm.treeNode.buziCode;
                    vm.info.salesunitcode = vm.treeNode.saleCode;
                }
            });

        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            Document.getGridWidth();
            vm.cleanInput();
            vm.cleanDrop();
            vm.initBuds();
            vm.cleanButton();
            vm.showInput.add=false;
            vm.showInput.salesUnit=false;
            vm.showInput.buziUnit=false;


            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            vm.getInfo(key)
        },
        saveOrUpdate: function (event) {

            // var main=vm.info;
            // main.docdate=GetYMD(main.docdate);
            // var data={main:vm.info,detail:vm.Buds};
            // console.log(data)

            var url="budgbase/diffcomp/add";
            if(vm.info.docentry)
            {
               //修改
                url="budgbase/diffcomp/edit";
            }
            httpPost(url,{
                main:vm.info,detail:vm.Buds
            },function(r){
                if (r.code === 0) {
                    if(event=="postapprove")
                    {
                        vm.postapprove(event);
                    }
                    else{
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    }
                } else {
                    alert(r.msg);
                }
            });
        },
        del: function (event) {


        },
        getInfo: function (id) {

            httpGetInfo("budgbase/diffcomp/view", {
                docEntry: id
            }, function (r) {
                if (r.code === 0) {


                    vm.info=r.data.main;
                    vm.Buds=r.data.detail;
                    vm.drop.stateList = Document.initStatusList();
                    vm.drop.verList = Document.initVerList();
                    Document.initCompList(vm.orgLevel,function(result){
                        vm.drop.compList = result;
                    });

                    vm.BudTable.reload('Buds', {data: vm.Buds});

                    //控制按钮显示
                    Document.showBtnByStatus(vm.info.docstatus);


                } else {
                    alert(r.msg);
                }
            });
        },
        reload: function (event) {
            vm.cleanInput();
            vm.cleanDrop();
           // vm.initBuds();
          //  console.log(vm.q);
            vm.showList = true;
            Document.resetGridWidth();
            var page = 1;//查询时默认为第一页//$("#jqGrid").jqGrid('getGridParam','page');
            var param = JSON.stringify(vm.q);
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'param': param},
                page: page
            }).trigger("reloadGrid");
        },
        initBuds: function (event) {
            vm.Buds = [];
            vm.remoteBuds = [];
            $.each(vm.drop.itemList, function (key, value) {
                var buds = {
                    code: value.code
                    , name: value.name
                    , salesType:value.salesType
                    , month01: ''
                    , month02: ''
                    , month03: ''
                    , month04: ''
                    , month05: ''
                    , month06: ''
                    , month07: ''
                    , month08: ''
                    , month09: ''
                    , month10: ''
                    , month11: ''
                    , month12: ''
                };
                vm.Buds.push(buds);
            });


            vm.BudTable.reload('Buds', {data: vm.Buds});
        },
        addbud: function () {
            vm.Buds.push({});
            vm.BudTable.reload('Buds', {data: vm.Buds});
        },
        selectedVerCode: function (r) {
            $.each(vm.drop.verList, function (key, value) {
                if (value.code == r) {
                    vm.info.vername = value.name;
                }
            });
            if(vm.info.bplid != null){
                Document.initItemList(vm.info.bplid, 'COMP', '',vm.info.vercode,function (list) {
                    vm.drop.itemList = list;
                    vm.initBuds();
                })
            }
        },
        selectedcompcode: function (r) {
            //   console.log(r);
            $.each(vm.drop.compList, function (key, value) {
                if (value.code == r) {
                    vm.info.bplname = value.name;
                }
            });
            vm.info.depcode=null;
            vm.info.buziunitcode=null;
            vm.info.salesunitcode=null;
            vm.drop.prodList=[];
            vm.drop.buziUnitList=[];
            vm.drop.salesUnitList=[];

            if(vm.info.vercode != null){
                Document.initItemList(vm.info.bplid, 'COMP', '',vm.info.vercode,function (list) {
                    vm.drop.itemList = list;
                    vm.initBuds();
                })
            }
        },
        initqdrop: function () {
            vm.qdrop.stateList = Document.initStatusList();
            vm.qdrop.verList = Document.initVerList();

          //  console.log(vm.qdrop);
        },

        postapprove: function (event) {
            var url="budgbase/diffcomp/postApprove";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.getInfo(vm.info.docentry);
                    });
                } else {
                    alert(r.msg);
                }
            });
        },
        approve: function (event) {
            var url="budgbase/diffcomp/approve";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.getInfo(vm.info.docentry);
                    });
                } else {
                    alert(r.msg);
                }
            });
        },
        reject: function (event) {
            var url="budgbase/diffcomp/reject";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.getInfo(vm.info.docentry);
                    });
                } else {
                    alert(r.msg);
                }
            });
        },
        close: function (event) {
           var url="budgbase/diffcomp/close";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.getInfo(vm.info.docentry);
                    });
                } else {
                    alert(r.msg);
                }
            });
        },
        cancelclose: function (event) {
            var url="budgbase/diffcomp/cancelClose";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert('操作成功', function () {
                        vm.getInfo(vm.info.docentry);
                    });
                } else {
                    alert(r.msg);
                }
            });
        },
        copyfrom: function (event) {

            $.ajax({
                type: "POST",
                url: baseURL + "budgbase/diffcomp/copyFrom",
                contentType: "application/json",
                data: JSON.stringify({main:vm.info}),
                success: function (r) {
                    if (r.code === 0) {
                        vm.Buds=r.data.detail;
                        vm.BudTable.reload('Buds', {data: vm.Buds});
                        alert('操作成功', function () {


                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });

        },
        showTreeData:function () {
            Document.showTreeData();
        },
        exportFile:function () {
            vm.BudTable.exportFile(vm.listTable.config.id,vm.Buds);
        },
        genDiffsales: function (event) {
            var url="budgbase/diffcomp/genDiffsales";
            httpPostFormData(url,{
                docEntry: vm.info.docentry
            },function(r){
                if (r.code === 0) {
                    alert(r.msg, function () {

                    });
                } else {
                    alert(r.msg);
                }
            });
        },

    }
});
