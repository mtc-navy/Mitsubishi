$(function () {
    $("#jqGrid").setjgGrid({api:'sys/common/fixcont', hiddenFields:['DOCENTRY'], buttons: [],height:$(window).height() - 165});
    $("#jqGridList").setjgGridList({api:'sys/verHis/fixcont', hiddenFields:['docEntry'], ischeck:true});
    $("#jqGriddiff").setjgGridList({api:'sys/verDiff/fixcont', hiddenFields:['docEntry','isAdd'], ischeck:false,isred:true});
    Document.initDeptTree(null,vm.orgLevel,"T");
    vm.initqdrop();
    vm.showTreeData();
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startDate'
            ,done: function(value, date){
                vm.q.param7=value;
                if(vm.q.param8)
                {
                    if(value>vm.q.param8)
                    {
                        alert('开始时间不能大于结束时间');
                        vm.q.param7='';
                    }
                }

            }
        });
        laydate.render({
            elem: '#endDate'
            ,done: function(value, date){
                vm.q.param8=value;
                if(vm.q.param7)
                {
                    if(value<vm.q.param7)
                    {
                        alert('结束时间不能小于开始时间')
                        vm.q.param8='';
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
            param6: '',
            param7: '',
            param8: '',
            param9: '',
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
            docentry:null,
            postname:null,
            postcode:null,

        },
        drop: {
            verList: [],
            compList: [],
            stateList: [],
            deptList: [],
            buziUnitList: [],
            salesUnitList: [],
            postCodeList:[]
        },
        qdrop: {
            verList: [],
            stateList: [],
            postCodeList: [],


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
            postcode:false,
        },
        treeNode:{
            deptCode:null,
            companyCode:null,
            buziCode:null,
            saleCode:null
        },
        msg:{
            showver:null,
            showverdiff:null,

        },
        grid:{
            width:0,
        },
        orgLevel:'CTRFIX',
        permission:{
            update:false,
        },
    },
    components: {
        'bud-table': jobstanComponent,
    },
    methods: {
        query: function () {
            vm.q.param2=vm.treeNode.companyCode;
            vm.q.param3=vm.treeNode.deptCode;
            vm.q.param4=vm.treeNode.buziCode;
            vm.q.param5=vm.treeNode.saleCode;
            vm.qdrop.postCodeList = Document.initPostCodeList(vm.treeNode.companyCode, vm.treeNode.deptCode)


            vm.reload();
        },
        cleanDrop: function () {

            vm.drop.verList = [];
            vm.drop.compList = [];
            vm.drop.stateList = [];
            vm.drop.deptList = [];
            vm.drop.buziUnitList = [];
            vm.drop.salesUnitList = [];
            vm.drop.expAcctList = [];
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
            vm.info.postcode=null;
            vm.info.postname=null;
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
                    vm.selectedcompcode(vm.info.bplid,function(){
                        vm.info.depcode = vm.treeNode.deptCode;
                        vm.selecteddeptcode(vm.info.depcode,function(){
                            vm.info.buziunitcode = vm.treeNode.buziCode;
                            vm.selectedbuzicode(vm.info.buziunitcode,function(){
                                vm.info.salesunitcode = vm.treeNode.saleCode;
                                vm.selectedsalescode(vm.info.salesunitcode);
                            });
                        });
                    });
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
            vm.showInput.postcode=false;

            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            vm.getInfo(key)
        },
        saveOrUpdate: function (event) {

            // var main=vm.info;
            // main.docdate=GetYMD(main.docdate);
            // var data={main:vm.info,detail:vm.Buds};
            // console.log(data)

            var url="budgbase/fixcont/add";
            if(vm.info.docentry)
            {
                //修改
                url="budgbase/fixcont/edit";
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

            httpGetInfo("budgbase/fixcont/view", {
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
                    Document.initDeptList(vm.orgLevel,vm.info.bplid,function(result){
                        vm.drop.deptList = result;
                    });
                    Document.initBuziUnitList(vm.orgLevel,vm.info.depcode,function (result) {
                        vm.drop.buziUnitList = result;
                    })
                    Document.initSalesUnitList(vm.orgLevel,vm.info.buziunitcode,function(result){
                        vm.drop.salesUnitList = result;
                    })
                    vm.drop.postCodeList = Document.initPostCodeList(vm.info.bplid, vm.info.depcode);
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
          //  vm.initBuds();
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
            $.each(vm.drop.expAcctList, function (key, value) {
                var buds = {
                    code: value.code
                    , name: value.name
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
        },
        selectedcompcode: function (r,callback) {
            //   console.log(r);
            $.each(vm.drop.compList, function (key, value) {
                if (value.code == r) {
                    vm.info.bplname = value.name;
                }
            });
            vm.info.depcode=null;
            vm.info.buziunitcode=null;
            vm.info.salesunitcode=null;
            vm.drop.postCodeList=[];
            vm.drop.buziUnitList=[];
            vm.drop.salesUnitList=[];
            Document.initDeptList(vm.orgLevel,r,function(result){
                vm.drop.deptList = result;
                if(typeof callback != 'undefined'){
                    callback();
                }
            });
            vm.initBuds();


        },
        selecteddeptcode: function (r,callback) {

            vm.showInput.salesUnit=false;
            vm.showInput.buziUnit=false;
            $.each(vm.drop.deptList, function (key, value) {
                if (value.code == r ) {
                    vm.info.depname = value.name;
                    if (value.type == 'B' ||  value.type == 'D' ) {
                        vm.showInput.salesUnit=true;
                        vm.showInput.buziUnit=true;
                    }
                }
            });
            // console.log(r);
            vm.info.buziunitcode=null;
            vm.info.salesunitcode=null;
            vm.info.postcode=null;
            vm.drop.buziUnitList=[];
            vm.drop.salesUnitList=[];
            vm.drop.postCodeList=[];

            Document.initBuziUnitList(vm.orgLevel,r,function (result) {
                vm.drop.buziUnitList = result;
                if(typeof callback != 'undefined'){
                    callback();
                }
            });
            vm.drop.postCodeList = Document.initPostCodeList(vm.info.bplid, r)
            vm.showInput.postcode=true;
            Document.initExpAcctList(vm.info.bplid, r,'','CTRFIX',function(result){
                vm.drop.expAcctList = result;
                vm.initBuds();
            });
            // console.log(vm.drop);

        },
        selectedbuzicode: function (r,callback) {
            $.each(vm.drop.buziUnitList, function (key, value) {
                if (value.code == r) {
                    vm.info.buziunitname = value.name;
                }
            });
            // console.log(vm.info);
            vm.info.salesunitcode=null;
            Document.initSalesUnitList(vm.orgLevel,r,function(result){
                vm.drop.salesUnitList = result;
                if(typeof callback != 'undefined'){
                    callback();
                }
            });

        },
        selectedsalescode: function (r) {
            $.each(vm.drop.salesUnitList, function (key, value) {
                if (value.code == r) {
                    vm.info.salesunitname = value.name;
                }
            });


        },
        selectedpostcode: function (r) {
            $.each(vm.drop.postCodeList, function (key, value) {
                if (value.code == r) {
                    vm.info.postname = value.name;
                }
            });
            Document.initExpAcctList(vm.info.bplid,vm.info.depcode, r,'CTRFIX',function(result){
                vm.drop.expAcctList = result;
                vm.initBuds();
            });
        },
        initqdrop: function () {
            vm.qdrop.stateList = Document.initStatusList();
            vm.qdrop.verList = Document.initVerList();
        },

        postapprove: function (event) {
            var url="budgbase/fixcont/postApprove";
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
            var url="budgbase/fixcont/approve";
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
            var url="budgbase/fixcont/reject";
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
            var url="budgbase/fixcont/close";
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
            var url="budgbase/fixcont/cancelClose";
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
                url: baseURL + "budgbase/fixcont/copyFrom",
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
        showver:function () {
            vm.msg.showver=layer.open({
                type: 1,
                title: ['变更历史', 'text-align: center;font-weight: bold;'],
                content: $('#verTable')
            });

            $("#jqGridList").jqGrid('setGridParam', {
                postData: {docEntry:vm.info.docentry},

            }).trigger("reloadGrid");
            layer.full(vm.msg.showver);
        },
        closever:function () {
            var id = getListSelectedRow();
            if (id == null) {
                return;
            }
            var key = $("#jqGridList").jqGrid('getCell', id, '版本号');
            layer.close(vm.msg.showver);
            vm.showverlist(key);

        },
        showverlist:function (version) {
            vm.msg.showverdiff=layer.open({
                type: 1,
                title: ['版本'+version+'的变更差异', 'text-align: center;font-weight: bold;'],
                content: $('#diffTable')
            });

            $("#jqGriddiff").jqGrid('setGridParam', {
                postData: {docEntry:vm.info.docentry,version:version},

            }).trigger("reloadGrid");
            layer.full(vm.msg.showverdiff);
        },
        closeverlist:function () {
            layer.close(vm.msg.showverdiff);
        },
        exportFile:function () {
            vm.BudTable.exportFile(vm.listTable.config.id,vm.Buds);
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
