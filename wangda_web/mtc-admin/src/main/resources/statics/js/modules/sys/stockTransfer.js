$(function () {

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
            elem: '#startDate'
            ,done: function(value, date){
                vm.search.StartDate=value;
            }
        });
        laydate.render({
            elem: '#endDate'
            ,done: function(value, date){
                vm.search.EndDate=value;
            }
        });
    });

    $("#jqGrid").jqGrid({
        url: baseURL + 'sap/stocktransfer/list',
        datatype: "json",
        colModel: [
            { label: 'DocEntry', name: 'docEntry',index: "docEntry",hidden:true, key:true,width: 75,sortable:false },
            { label: '分支', name: 'bplname', width: 90 ,sortable:false},
            { label: '单据编号', name: 'docNum',index: "DocNum", width: 75,sortable:false },
            { label: '单据日期', name: 'taxDateStr', width: 80 ,sortable:false},
            { label: '从仓库', name: 'fromWhsName', width: 90 ,sortable:false},
            { label: '到仓库', name: 'toWhsName', width: 90 ,sortable:false},
            { label: '单据类型', name: 'transferTypeName', width: 75,sortable:false },
            { label: '单据状态', name: 'docStatusName', width: 70 ,sortable:false},
            { label: '备注', name: 'comments', width: 270 ,sortable:false},
            { label: '操作', name: 'action', width: 60,sortable:false,formatter: function(value, options, row){
                var html = '<div class="btn-group btn-group-xs" role="group" aria-label="Extra-small button group">';
                    if($('#btnUpdate').length > 0){
                    html+='<button type="button" transferType="'+row.transferType+'" statusName="'+row.docStatusName+'" value="'+row.docEntry+'" status="'+row.docStatus+'" class="sales btn btn-default">编辑</button>';
                }
                html+='</div>';
                return html;
            }},
        ],
        viewrecords: true,
        height: $(window).height()-200,
        rowNum: 20,
        rowList : [20,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $(".sales").click(function(){
                var id = $(this).val();
                var transferType = $(this).attr('transferType');
                var statusName = $(this).attr('statusName');
                vm.update(id, transferType, statusName);
            });
        },

    });

});

var vm = new Vue({
    el:'#rrapp',
    data:{
        search:{
            BPLId:null,
            StartDate:null,
            EndDate:null,
            DocStatus:null,
            fromWhsCode:null,
            toWhsCode:null,
        },
        transferType:null,
        transferTypeName: null,
        showList:true,
        title:null,
        tabShow:true,
        docStatusList:[],
        branches:[], //分支
        fromWhsList:[], //从仓库
        toWhsList:[], //到仓库
        qryfromWhsList:null, //从仓库
        qrytoWhsList:null, //到仓库
        drivers: [],//司机
        readonly:{
        },
        transfer:{
        },
        showButton:{
            save:false,
            cancelButton:false,
            add:false,
            update:false,
            toDocument: false,
            print:false,
            addMaterial: false
        },
        materials:[],
        carNos:[],
        boundNos:[],
    },
    components:{
        'material-table':materialsComponent,
    },
    methods: {
        query:function(){
            vm.reload();
        },
        add:function(){
            vm.showList = false;
            vm.title="新增转储申请";
            vm.initEditParams();

            vm.showButton.save = true;
            vm.showButton.addMaterial = true;
            vm.showButton.toDocument = true;

            vm.initBranches('');
            vm.drivers = Document.initDriverList();
        },
        update:function(docEntry, transferType, docStatusName){
            vm.title="编辑转储";
            vm.initEditParams();
            vm.drivers = Document.initDriverList();
            vm.transferType = transferType;
            vm.transfer.docStatusName = docStatusName;
            //loading层
            var index = layer.load(1, {
                shade: [0.5,'#fff']
            });
            httpGet('sap/stocktransfer/info',{
                docEntry:docEntry,
                status: transferType
            },function(data) {
                var result = data.result;

                switch (result.transferType) {
                    case 'R': //申请单
                        vm.title = "编辑转储申请";
                        vm.showButton.cancel = true;
                        vm.showButton.update = true;
                        vm.showButton.add = true;
                        vm.showButton.toDocument = true;
                        vm.showButton.print = true;
                        vm.showButton.addMaterial = true;
                        break;
                    default: //转储
                        vm.showButton.addMaterial = true;
                        vm.showButton.cancel = true;
                        vm.showButton.update = true;
                        vm.showButton.print = true;
                        vm.showButton.toDocument = false;
                        break;
                }

                //根据单据状态判断
                switch (result.docStatus){
                    case 'C':
                        vm.showButton.add = false;
                        vm.showButton.cancel = false;
                        vm.showButton.update = false;
                        vm.showButton.print = false;
                        vm.showButton.toDocument = false;
                        vm.showButton.addMaterial = false;
                        vm.transfer.docStatusName = '已结算';
                        break;
                    case 'O':
                        vm.transfer.docStatusName = '未清';
                        break;
                }

                vm.initBranches('');
                vm.drivers = Document.initDriverList();
                vm.transfer.bplId = result.bplId;
                var driverRst = parseInt(result.driver);
                vm.transfer.driver = result.driver != null && driverRst !='' && driverRst != 'undefined' ? driverRst : 0;
                vm.transfer.transType = result.tranType;
                vm.transfer.busiType = result.busiType;
                vm.changeBranch().then(()=>{
                    vm.transfer.bplName = result.bplName;
                    vm.transfer.docEntry = result.docEntry;
                    vm.transfer.docNum = result.docNum;
                    vm.transfer.docStatus = result.docStatus;
                    vm.transfer.taxDate = result.taxDate;
                    vm.transfer.fromWhsCode = result.filler;
                    vm.transfer.toWhsCode = result.toWhsCode;
                    vm.transfer.carNo = result.carNo;
                    vm.loadMaterials();

                    vm.materials = [];
                    var items = result.lines;
                    if(items != null){
                        items.forEach(function(item){
                            vm.materials.push({
                                itemCode:item.itemCode,
                                itemName:item.itemName,
                                quantity:item.quantity,
                                factor1:item.factor1,
                                factor2:item.factor2,
                                isPackage: item.isPackage,
                                stock: item.stock,
                                warehouseCode: item.warehouseCode,
                                warehouseName: vm.getWareHouseNameById(vm.toWhsList,item.warehouseCode),
                                fromWarehouseCode: item.fromWarehouseCode,
                                fromWarehouseName: vm.getWareHouseNameById(vm.fromWhsList,item.fromWarehouseCode),
                            });
                        });
                    }
                    return vm.initBoundNos();
                }).then(data=>{
                    vm.boundNos = data.bound;
                    vm.transfer.boundNo = result.boundNo;
                });

                //vm.materialTable.reload('materials',{data:vm.materials});

                layer.close(index);
            },function(data){
                alert(data.msg);
                layer.close(index);
            });

        },
        back:function(){
            vm.showList=true;
        },
        //转储申请保存
        saveOrUpdateRequest:function(){
            var params = vm.buildTransferInfo();
            if (!vm.verifySaveForm()) {
                return;
            }
            if (params.docEntry == null) {
                //调用新增接口
                httpPost('sap/stocktransfer/addRequest', params, function (data) {
                    alert("生成转储申请成功！");
                    vm.reload(false);
                    var docEntry = data.docEntry;
                    vm.update(docEntry,'R');
                });
            } else {
                //调用更新接口，请求单更新
                if (vm.transferType == 'R') {
                    httpPost('sap/stocktransfer/editRequest', params, function (data) {
                        alert("更新转储申请成功！");
                        vm.reload(false);
                    });
                } else { //转储单更新
                    httpPost('sap/stocktransfer/edit', params, function (data) {
                        alert("更新转储申请成功！");
                        vm.reload(false);
                    });
                }
            }
        },

        //转储保存
        saveOrUpdate:function(){
            var params = vm.buildTransferInfo();
            if (!vm.verifySaveForm()) {
                return;
            }
            //调用新增接口
            httpPost('sap/stocktransfer/add', params, function (data) {
                alert("生成转储成功！");
                vm.reload(false);
                var docEntry = data.docEntry;
                vm.update(docEntry,"O");
            });
        },
        cancel:function(){
            httpPostFormData('sap/stocktransfer/cancel',{
                docEntry:vm.transfer.docEntry,
                status:vm.transferType
            },function(data){
                alert('取消成功!',function(){
                    vm.add();
                });
                vm.reload(false);
            });
        },
        /**
         * 分支
         */
        initBranches: function (val) {
            httpGet('sap/bpl/listByUser', {filterValue: val}, function (data) {
                vm.branches = data.bpl;
            });
        },
        /**
         * 分支更改事件
         */
        changeBranch: function(){
            vm.transfer.fromWhsCode = '';
            vm.transfer.toWhsCode = '';
            vm.materials = [];
            return new Promise(resolve => {
                this.initFromWhsList('');
                this.initToWhsList('');
                this.initCarNos()
                    .then(data=>{
                        vm.carNos = data.car;
                    })
                this.initFromWhsList('')
                    .then(data=>{
                        this.initToWhsList('');
                    })
                    .then(data=>{
                        resolve();
                    });
            })
        },
        /**
         * 从仓库
         */
        initFromWhsList: function(val){
            return new Promise(resolve => {
                httpGet('sap/whs/searchFromWhs', {bplid: vm.transfer.bplId,filterValue: val}, function(data){
                    vm.fromWhsList = data.whs;
                    vm.fromWhsList.forEach(function(item){
                        if (item.whsCode == val){
                            vm.transfer.fromWhsCode = val;
                        }
                    });
                    resolve(data);
                });
            })

        },
        /**
         * 到仓库
         */
        initToWhsList: function(val){
           return new Promise(resolve => {
               httpGet('sap/whs/searchToWhs', {bplid: vm.transfer.bplId,filterValue: val}, function(data){
                   vm.toWhsList = data.whs;
                   vm.toWhsList.forEach(function(item){
                       if (item.whsCode == val){
                           vm.transfer.toWhsCode = val;
                       }
                   });
                   resolve(data);
               });
           })
        },
        /**
         * 获取车牌号
         */
        initCarNos:function(){
            vm.carNos = [];
            vm.transfer.carNo = null;
            vm.transfer.boundNo = null;
            return new Promise(resolve => {
                httpGet('sap/customer/car', {
                    CardCode: null,
                    TaxDate: vm.transfer.taxDate,
                    SalesBranch:vm.transfer.bplId,
                    TakeBranch:null,
                }, function (data) {
                    resolve(data);
                });
            })
        },
        /**
         * 过磅单号
         */
        initBoundNos:function(){
            vm.boundNos = [];
            vm.transfer.boundNo = null;
            return new Promise(resolve => {
                httpGet('sap/customer/bound', {
                    CardCode: null,
                    CarNo: vm.transfer.carNo,
                    TaxDate: vm.transfer.taxDate,
                }, function (data) {
                   resolve(data);
                });
            })
        },
        /**
         * 数据检查
         */
        verifySaveForm: function(){
            if (vm.transfer.bplId == null){
                alert('请选择分支！');
                return false;
            }
            /*if (vm.transfer.fromWhsCode == null){
                alert('请选择从仓库!');
                return false;
            }
            if (vm.transfer.toWhsCode == null){
                alert('请选择到仓库!');
                return false;
            }*/
            return true;
        },
        /**
         * 构造转储信息
         */
        buildTransferInfo: function() {
            var lines = [];
            var index = 0;
            vm.materials.forEach(function (item) {
                if (item.itemCode != null && item.itemCode != "") {
                    lines.push({
                        lineNum: index,
                        itemCode: item.itemCode,
                        itemName: item.itemName,
                        quantity: item.quantity,
                        factor1: item.factor1,
                        factor2: item.factor2,
                        isPackage: item.isPackage,
                        fromWarehouseCode : item.fromWarehouseCode,
                        warehouseCode:item.warehouseCode,
                    });
                    index++;
                }
            });
            var params = {
                docEntry: vm.transfer.docEntry,
                docNum: vm.transfer.docNum,
                taxDate: vm.transfer.taxDate,
                bplId: vm.transfer.bplId,
                filler: vm.transfer.fromWhsCode,
                toWhsCode: vm.transfer.toWhsCode,
                driver: vm.transfer.driver,
                tranType: vm.transfer.transType,
                busiType: vm.transfer.busiType,
                //物料信息
                lines: lines,
                carNo:vm.transfer.carNo,
                boundNo:vm.transfer.boundNo,
            };
            return params;
        },
        reload: function (showlist) {
            if(showlist == null){
                vm.showList = true;
            }else{
                vm.showList = showlist;
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.search,
                page: page
            }).trigger("reloadGrid");
        },
        addMaterial:function(){
            if (vm.transfer.bplId == null) {
                alert('请选择分支!');
                return;
            }
            var material = {
                fromWarehouseCode:vm.transfer.fromWhsCode,
                fromWarehouseName:vm.getWareHouseNameById(vm.fromWhsList,vm.transfer.fromWhsCode),
                warehouseCode:vm.transfer.toWhsCode,
                warehouseName:vm.getWareHouseNameById(vm.toWhsList,vm.transfer.toWhsCode),
            };
            vm.materials.push(material);
            //vm.materialTable.reload('materials', {data: vm.materials});
        },
        checkMaterials:function(){
            var result = true;
            var itemCodes = "";
            vm.materials.forEach(function (item) {
                if(item.itemCode != null && (item.packNum == null || item.packNum == '')){
                    itemCodes += item.itemCode +" "
                    result = false;
                }
            });
            if(!result){
                alert("请输入物料："+itemCodes+" 的件数！");
            }
            return result;
        },
        loadMaterials:function(){
            if (vm.transfer.bplId == null) return;

            //处理物料
            vm.initMaterials();

        },
        /**
         * 处理物料
         */
        initMaterials: function (callback) {
            vm.remoteMaterials = [];
            vm.remoteMaterialsMap = {};
            httpGet('sap/item/listByWhs', {
                whsCode: vm.transfer.fromWhsCode
            }, function (data) {
                vm.remoteMaterials = data.item;
                vm.remoteMaterials.forEach(function (item) {
                    item.value = item.itemCode + " " + item.itemName;
                    vm.remoteMaterialsMap[item.itemCode] = item;
                });

                if(typeof callback != 'undefined'){
                    callback();
                }
            });
        },
        initEditParams:function(){
            vm.showList = false;
            vm.showAddMaterial = true;
            vm.materials = [];
            //vm.materialTable.reload('materials',{data:vm.materials});
            vm.readonly = {};
            vm.transferType = null;
            vm.transferTypeName = null;
            vm.transfer={
                bplId: null,
                bplName:  null,
                docEntry: null,
                docNum: null,
                taxDate : getNowFormatDate(),
                fromWhsCode: null,
                toWhsCode: null,
                docStatus: null,
                docStatusName:  null,
                driver: null,
                transType: null,
                busiType: null,
                carNo:null,
                boundNo:null,
                carNos:[],
                boundNos:[],
            };

            vm.showButton={
                save:false,
                cancelButton:false,
                add:false,
                update:false,
                toDocument: false,
                print: false,
                addMaterial: false
            }
        },
        initDocStatus: function(){
            httpGet('sys/dict/type', {
                type: 'stocktransfer_status', sidx: "order_num", order: "asc"
            }, function (data) {
                vm.docStatusList = data.page.list;
            });
        },
        /**
         * 从仓库
         */
        initQryFromWhsList: function(){
            httpGet('sap/whs/searchFromWhs', {bplid: '',filterValue: ''}, function(data){
                vm.qryfromWhsList = data.whs;
            })
        },
        /**
         * 到仓库
         */
        initQrToWhsList: function(){
            httpGet('sap/whs/searchToWhs', {bplid: '',filterValue: ''}, function(data){
                vm.qrytoWhsList = data.whs;
            })
        },
        print:function(){
            var printUrl = baseURL+'statics/plugins/pdf/web/viewer.html?file=/sap/saleinv/print/'+vm.transfer.docEntry+"/";
            if(vm.transferType == 'R'){
                printUrl +="92"
            }else{
                printUrl +="91"
            }
            window.open(printUrl);
        },
        getWareHouseNameById(warehouses,code){
            if(code == null || code == ''){
                return null;
            }
            let warehouse = warehouses.find(item=>{
                return item.whsCode === code;
            });
            return warehouse==null?null:warehouse.whsName;
        },
        getItemNameByCode(items,code){
            let item = items.find(material=>{
                return material.itemCode === code;
            });
            return item.itemName;
        },
        changeFromWareHouse(scope){
            vm.$refs.editable.updateStatus(scope);
            scope.row.fromWarehouseName = vm.getWareHouseNameById(vm.fromWhsList,scope.row.fromWarehouseCode);
            scope.row.items = null;
            scope.row.factor1 = null;
            scope.row.factor2 = null;
            scope.row.itemCode = null;
            scope.row.itemName = null;
            scope.row.quantity = null;
            scope.row.stock = null;
            vm.$refs.editable.clearActive();
        },
        changeToWareHouse(scope){
            vm.$refs.editable.updateStatus(scope);
            scope.row.warehouseName = vm.getWareHouseNameById(vm.toWhsList,scope.row.warehouseCode);
            vm.$refs.editable.clearActive();
        },
        changeMaterial(scope){

            httpGet('sap/item/info',{
                whsCode:scope.row.fromWarehouseCode,
                itemCode:scope.row.itemCode,
                bplid:vm.transfer.bplId
            },function(data){
                scope.row.isPackage = data.item.isPackage;
                scope.row.factor1 = data.item.salFactor1;
                scope.row.factor2 = data.item.salFactor2;
                scope.row.stock = data.item.stock;
                if (scope.row.factor2 != null){
                    if(scope.row.isPackage=='Y'){
                        scope.row.quantity = Multiply(scope.row.factor1,scope.row.factor2);
                    }else{
                        scope.row.quantity = scope.row.factor2;
                    }
                }
                vm.$refs.editable.updateStatus(scope);
                scope.row.itemName = vm.getItemNameByCode(scope.row.items,scope.row.itemCode);
                vm.$refs.editable.clearActive();
            })

        },
        editActiveEvent (row, column) {
            if(column.property == 'itemCode' && row.fromWarehouseCode != null && row.fromWarehouseCode != ''
                && row.items == null){
                httpGetInfo('sap/item/listByWhs', {
                    whsCode: row.fromWarehouseCode
                }, function (data) {
                    row.items = data.item;
                });
            }
        },
        blurActiveEvent (row, column) {
            //如果输入的是件数
            if(column.property == 'factor2'){
                if(row.factor2 == null || row.factor2==''){
                    row.factor2 = 1;
                }
                //标包
                if(row.isPackage=='Y'){
                    row.quantity = Multiply(row.factor2,row.factor1);
                }else{
                    row.quantity = row.factor2;
                }
            }
            //如果输入的是数量
            if(column.property == 'quantity'){
                //标包
                if(row.isPackage=='Y'){
                    row.factor2 = Division(row.quantity,row.factor1);
                }else{
                    row.factor2 = row.quantity;
                }
            }
        },
        selectedCarNo(){
            vm.initBoundNos()
                .then(data=>{
                    vm.boundNos = data.bound;
                    if (data.bound != null && data.bound.length > 0) {
                        vm.transfer.boundNo = data.bound[0].boundNo;
                    }
                })
        },
        removeEvent(row){
            this.$refs.editable.remove(row)
        }
    },
    mounted:function(){
        this.initDocStatus();
        this.initQryFromWhsList();
        this.initQrToWhsList();
        this.initBranches('');
    }
});
