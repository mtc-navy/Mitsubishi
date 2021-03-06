$(function () {
    $("#jqGrid").setjgGrid({api: 'sys/common/apprsaleunit', hiddenFields: ['DOCENTRY'], buttons: []});
    $("#jqGridList").setjgGridList({api:'sys/verHis/saleUnitApprove', hiddenFields:['docEntry'], ischeck:true});
    $("#jqGriddiff").setjgGridList({api:'sys/verDiff/saleUnitApprove', hiddenFields:['docEntry','isAdd'], ischeck:false,isred:true});
    // $("#djqGrid").setjgGrid({
    //     api: 'sys/common/clickbudget',
    //     hiddenFields: ['DOCENTRY'],
    //     buttons: [],
    //     dpager: '#djqGridPager',
    //
    // });
    Document.initDeptTree(null,vm.orgLevel,"T");
    vm.initqdrop();
    vm.showTreeData();
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startDate'
            ,done: function(value, date){
                vm.q.param5=value;
                if(vm.q.param6)
                {
                    if(value>vm.q.param6)
                    {
                        alert('开始时间不能大于结束时间');
                        vm.q.param5='';
                    }
                }

            }
        });
        laydate.render({
            elem: '#endDate'
            ,done: function(value, date){
                vm.q.param6=value;
                if(vm.q.param5)
                {
                    if(value<vm.q.param5)
                    {
                        alert('结束时间不能小于开始时间')
                        vm.q.param6='';
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
            genfrom: false,
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
        msg:{
            showver:null,
            showverdiff:null,
            showbudget:null

        },
        grid:{
            width:0,
        },
        orgLevel:'SALES',
        permission:{
            update:false,
        },
        proCode:null,
        month:null,
    },
    components: {
        'bud-table': unitComponent,
    },
    methods: {
        query: function () {

            vm.q.param2=vm.treeNode.companyCode;
            vm.q.param3=vm.treeNode.buziCode;
            vm.q.param4=vm.treeNode.saleCode;
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
            vm.showButton.genfrom= false;
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.cleanButton();
            vm.cleanInput();
            vm.cleanDrop();
            vm.initBuds();
            vm.info.docentry=null;
            vm.showButton.save=true;
            vm.showButton.genfrom=true;
            vm.showInput.add=true;

            vm.drop.stateList = Document.initStatusList();
            vm.drop.verList = Document.initVerList();
            Document.initCompList(vm.orgLevel,function(result){
                vm.drop.compList = result;
                if(vm.treeNode.companyCode) {
                    vm.info.bplid = vm.treeNode.companyCode;
                    vm.selectedcompcode(vm.info.bplid,function(){
                        vm.selecteddeptcode('',function(){
                            vm.info.buziunitcode = vm.treeNode.buziCode;
                            vm.selectedbuzicode(vm.info.buziunitcode,function () {
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
            var mtcBudSalesUnitEntity=vm.info;
            mtcBudSalesUnitEntity.docdate=GetYMD(vm.info.docdate);
            // var data={mtcBudSalesUnitEntity:mtcBudSalesUnitEntity,mtcBudSalesUnit1EntityList:vm.Buds};
            // console.log(data)

            var url="budgbase/apprsaleunit/add";
            if(vm.info.docentry)
            {
               //修改
                url="budgbase/apprsaleunit/edit";
            }
            httpPost(url,{
                mtcBudSalesUnitEntity:mtcBudSalesUnitEntity,mtcBudSalesUnit1EntityList:vm.Buds
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

            httpGetInfo("budgbase/apprsaleunit/view", {
                docEntry: id
            }, function (r) {
                if (r.code === 0) {


                    vm.info=r.apprsaleunit.mtcBudSalesUnitEntity;
                    vm.Buds=r.apprsaleunit.mtcBudSalesUnit1EntityList;
                    vm.drop.stateList = Document.initStatusList();
                    vm.drop.verList = Document.initVerList();
                    Document.initCompList(vm.orgLevel,function(result){
                        vm.drop.compList = result;
                    });
                    vm.info.depcode='';
                    Document.initBuziUnitList(vm.orgLevel,vm.info.bplid,function (result) {
                        vm.drop.buziUnitList = result;
                    })
                    Document.initSalesUnitList(vm.orgLevel,vm.info.buziunitcode,function(result){
                        vm.drop.salesUnitList = result;
                    })
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
            var page = 1;//查询时默认为第一页//$("#jqGrid").jqGrid('getGridParam','page');
            var param = JSON.stringify(vm.q);
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'param': param},
                page: page
            }).trigger("reloadGrid");
        },
        initBuds: function (event) {
            vm.Buds = vm.drop.itemList;
            vm.BudTable.reload('Buds', {data: vm.Buds});
        },
        cleanInitBuds:function (event) {
                vm.Buds = [];
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
            vm.cleanInitBuds();
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
            vm.drop.prodList=[];
            vm.drop.buziUnitList=[];
            vm.drop.salesUnitList=[];
            vm.selecteddeptcode('');
            vm.cleanInitBuds();
            if(typeof callback != 'undefined'){
                callback();
            }

        },
        selecteddeptcode: function (r,callback) {

            vm.showInput.salesUnit=true;
            vm.showInput.buziUnit=true;

            vm.info.buziunitcode=null;
            vm.info.salesunitcode=null;
            vm.drop.buziUnitList=[];
            vm.drop.salesUnitList=[];
            Document.initBuziUnitList(vm.orgLevel,vm.info.bplid,function (result) {
                vm.drop.buziUnitList = result;
                if(typeof callback != 'undefined'){
                    callback();
                }
            })
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
            })
            vm.cleanInitBuds();
        },
        selectedsalescode: function (r) {
            $.each(vm.drop.salesUnitList, function (key, value) {
                if (value.code == r) {
                    vm.info.salesunitname = value.name;
                }
            });
            vm.cleanInitBuds();

        },

        initqdrop: function () {
            vm.qdrop.stateList = Document.initStatusList();
            vm.qdrop.verList = Document.initVerList();

          //  console.log(vm.qdrop);
        },

        postapprove: function (event) {
            var url="budgbase/apprsaleunit/postapprove";
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
            var url="budgbase/apprsaleunit/approve";
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
            var url="budgbase/apprsaleunit/reject";
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
           var url="budgbase/apprsaleunit/close";
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
            var url="budgbase/apprsaleunit/cancelclose";
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
        genfrom: function (event) {
            var isgen=true;
            var strMsg="";

            if(!vm.info.vercode)
            {
                strMsg+="请选择预算代码，";
                isgen=false;
            }
            if(!vm.info.bplid)
            {
                strMsg+="请选择公司，";
                isgen=false;
            }
            if(!vm.info.buziunitcode)
            {
                strMsg+="请选择经营单元，";
                isgen=false;
            }
            if(!vm.info.salesunitcode)
            {
                strMsg+="请选择销售单元";
                isgen=false;
            }
            if(isgen)
            {
                var url="budgbase/apprsaleunit/genfrom";
                httpPostFormData(url,{
                    VERCODE:vm.info.vercode
                    ,BPLID:vm.info.bplid
                    ,BUZICODE:vm.info.buziunitcode
                    ,SALECODE:vm.info.salesunitcode
                },function(r){
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.drop.itemList=r.apprsaleunit;
                            vm.initBuds();
                        });
                    } else {
                        alert(r.msg);
                    }
                });
            }
            else{
                alert(strMsg);
            }

        },
        showDetilTable:function (procode,month) {
            vm.msg.showbudget=layer.open({
                type: 1,
                title: '明细',
                content: $('#DetailTable')
            });
            vm.proCode = procode;
            vm.month = month;
            vm.initDetilTable(procode,month);
            layer.full(vm.msg.showbudget);
        },
        getDetailParam:function(procode,month){
            return JSON.stringify({param1:'B'
                ,param2: vm.info.vercode
                ,param3: ''
                ,param4: ''
                ,param5: vm.info.bplid
                ,param6: vm.info.buziunitcode
                ,param7: vm.info.salesunitcode
                ,param8: ''
                ,param9: procode
                ,param10: month

            });
        },
        initDetilTable:function (procode,month) {


            var page = 1;//查询时默认为第一页
            var param=vm.getDetailParam(procode,month);

            $.jgrid.gridUnload("djqGrid");
            $("#djqGrid").setjgGrid({
                api: 'sys/common/clickSaleUnit',
                hiddenFields: ['DOCENTRY'],
                buttons: [],
                dpager: '#djqGridPager',
                qParam: param
            });
            $("#djqGrid").jqGrid('setGridParam',{
                postData:{'param': param},
                page:page
            }).trigger("reloadGrid");
        },
        exportDetail:function(){
            var param = vm.getDetailParam(vm.proCode,vm.month);
            Document.exportDetail('sys/common/clickSaleUnit',param);
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
