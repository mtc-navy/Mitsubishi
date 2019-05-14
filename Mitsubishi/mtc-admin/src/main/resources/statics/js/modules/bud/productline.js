$(function () {
    $("#jqGrid").setjgGrid({api: 'sys/common/apprprodline', hiddenFields: ['DOCENTRY'], buttons: [],height:$(window).height() - 165});
    $("#jqGridList").setjgGridList({api:'sys/verHis/proLineApprove', hiddenFields:['docEntry'], ischeck:true});
    $("#jqGriddiff").setjgGridList({api:'sys/verDiff/proLineApprove', hiddenFields:['docEntry','isAdd'], ischeck:false,isred:true});
   // $("#djqGrid").setjgGrid({api:'sys/common/clickbudget', hiddenFields:['DOCENTRY'], buttons: [],dpager:'#djqGridPager'});
    Document.initDeptTree(null,vm.orgLevel,"T");
    vm.initqdrop();
    vm.showTreeData();
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startDate'
            ,done: function(value, date){
                vm.q.param4=value;
                if(vm.q.param5)
                {
                    if(value>vm.q.param5)
                    {
                        alert('开始时间不能大于结束时间');
                        vm.q.param4='';
                    }
                }

            }
        });
        laydate.render({
            elem: '#endDate'
            ,done: function(value, date){
                vm.q.param5=value;
                if(vm.q.param4)
                {
                    if(value<vm.q.param4)
                    {
                        alert('结束时间不能小于开始时间')
                        vm.q.param5='';
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
            docdate: null,
            docentry:null,
            prolcode:null,
            prolname:null

        },
        drop: {
            verList: [],
            compList: [],
            stateList: [],
            prodList: [],
            itemList:[]
        },
        qdrop: {
            verList: [],
            stateList: [],
            prodList: []
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
        orgLevel:'PROD',
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
            if(vm.treeNode.code != null && vm.treeNode.code != 0){
                vm.q.param2=vm.treeNode.code;
            }
            vm.reload();
            //vm.initqdrop();
        },
        cleanDrop: function () {

            vm.drop.verList = [];
            vm.drop.stateList = [];
            vm.drop.prodList = [];
            vm.drop.itemList = [];
        },
        cleanInput: function () {

            vm.info.vercode = null;
            vm.info.vername = null;
            vm.info.bplid = null;
            vm.info.bplname=null;
            vm.info.docstatus = 'D';
            vm.info.docdate = new Date();
            vm.info.prolcode=null;
            vm.info.prolname=null;
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
            Document.initProdList(vm.orgLevel,function(result){
                vm.drop.prodList = result;
                if(vm.treeNode.code != '0'){
                    vm.info.prolcode = vm.treeNode.code;
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



            var key = $("#jqGrid").jqGrid('getCell', id, 'DOCENTRY');
            vm.getInfo(key)
        },
        saveOrUpdate: function (event) {

           // var data={main:vm.info,detail:vm.Buds};
           // console.log(data)
            var mtcBudProdLineEntity=vm.info;
            mtcBudProdLineEntity.docdate=GetYMD(vm.info.docdate);
            var url="budgbase/apprprodline/add";
            if(vm.info.docentry)
            {
               //修改
                url="budgbase/apprprodline/edit";
            }
            httpPost(url,{
                mtcBudProdLineEntity:mtcBudProdLineEntity,mtcBudProdLine1EntityList:vm.Buds
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

            httpGetInfo("budgbase/apprprodline/view", {
                docEntry: id
            }, function (r) {
                if (r.code === 0) {


                    vm.info=r.apprprodline.mtcBudProdLineEntity;
                    vm.Buds=r.apprprodline.mtcBudProdLine1EntityList;
                    vm.drop.stateList = Document.initStatusList();
                    vm.drop.verList = Document.initVerList();
                    Document.initProdList(vm.orgLevel,function(result){
                        vm.drop.prodList = result;
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
        selectedprolcode: function (r) {
            //   console.log(r);
            $.each(vm.drop.prodList, function (key, value) {
                if (value.code == r) {
                    vm.info.prolname = value.name;
                }
            });
            vm.cleanInitBuds();
        },
        initqdrop: function () {
            vm.qdrop.stateList = Document.initStatusList();
            vm.qdrop.verList = Document.initVerList();
            Document.initProdList(vm.orgLevel,function(result){
                vm.drop.prodList = result;
            });
          //  console.log(vm.qdrop);
        },

        postapprove: function (event) {
            var url="budgbase/apprprodline/postapprove";
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
            var url="budgbase/apprprodline/approve";
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
            var url="budgbase/apprprodline/reject";
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
           var url="budgbase/apprprodline/close";
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
            var url="budgbase/apprprodline/cancelclose";
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
            if(!vm.info.prolcode)
            {
                strMsg+="请选择产品线，";
                isgen=false;
            }

            if(isgen)
            {
                var url="budgbase/apprprodline/genfrom";
                httpPostFormData(url,{
                    VERCODE:vm.info.vercode
                    ,PROLCODE:vm.info.prolcode

                },function(r){
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.drop.itemList=r.apprprodline;
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
                ,param5: ''
                ,param6: ''
                ,param7: ''
                ,param8: vm.info.prolcode
                ,param9: procode
                ,param10: month

            });
        },
        initDetilTable:function (procode,month) {


            var page = 1;//查询时默认为第一页
            var param=vm.getDetailParam(procode,month);
            $.jgrid.gridUnload("djqGrid");
            $("#djqGrid").setjgGrid({
                api: 'sys/common/clickProdLine',
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
            Document.exportDetail('sys/common/clickProdLine',param);
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
    }
});
