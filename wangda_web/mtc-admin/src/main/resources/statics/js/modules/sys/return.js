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
        url: baseURL + 'sap/salertn/list',
        datatype: "json",
        colModel: [
            { label: 'docentry', name: 'docentry',index: "docentry",hidden:true, key:true,width: 75,sortable:false },
            { label: '退货单号', name: 'docnum',index: "docnum", width: 75,sortable:false },
            { label: '客户名称', name: 'cardname', width: 75 ,sortable:false},
            { label: '单据日期', name: 'taxdateStr', width: 90 ,sortable:false},
            { label: '收货人', name: 'cntctname', width: 90 ,sortable:false},
            { label: '交货单号', name: 'takegoodsno', width: 90 ,sortable:false},
            { label: '过磅单号', name: 'weighno', width: 90 ,sortable:false},
            { label: '车牌号', name: 'carno', width: 90 ,sortable:false},
            { label: '总计', name: 'totalamt', width: 90 ,sortable:false},
            { label: '单据状态', name: 'docstatsname', width: 90 ,sortable:false},
            { label: '操作', name: 'action', width: 60,sortable:false,formatter: function(value, options, row){
                var html = '<div class="btn-group btn-group-xs" role="group" aria-label="Extra-small button group">';
                if(vm.showButton.edit){
                    html+='<button type="button" value="'+row.docentry+'" status="'+row.docstatus+'" class="sales btn btn-default">查看</button>';
                }
                html+='</div>'
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
            //$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            $(".sales").click(function(){
                var id = $(this).val();
                vm.update(id);
            });
        },

    });

});

var vm = new Vue({
    el:'#rrapp',
    data:{
        search:{
            CustomerNo:null,
            StartDate:null,
            EndDate:null,
            DocStatus:null,
            DeliveryNum:null,
        },
        showList:true,
        title:null,
        tabShow:true,
        docStatusList:[],
        remoteCustomers:[],
        remoteCustomersCopy:[],
        readonly:{
            cardCode:false,
            salesBranch:false,
            all:false,
        },
        sales:{
            busiType: null,
            transType: null,
            carNo: null,
            boundNo: null,
        },
        customer:{
            code:null,
            name:null,
            cntctPrsn:null,
            cntctName:null,
            canUseCost:null,
            inOutAmt:null,
            discAmt:null,
            canUseAmt:null,
            driver: null,
        },
        contacts:[],
        carNos:[],
        boundNos:[],
        drivers: [],
        salesBranches:[], //销售分支
        takeBranches:[],  //提货分支
        remoteSalesOrder:[],
        remoteSalesOrderCopy:[],
        showButton:{
            save:false,
            cancelButton:false,
            add:false,
            update:false,
            addMaterial:false,
        },
    },
    components:{
        'material-table':materialsComponent,
    },
    methods: {
        query:function(){
            vm.reload();
        },
        add:function(){
            materialsComponent.methods.init();
            vm.showList = false;
            vm.title="新增退货";
            vm.initEditParams();

            vm.showButton.save = true;
            vm.showButton.addMaterial = true;

            Document.initCustomer();

        },
        update:function(docEntry){
            vm.title="编辑开票";

            vm.initEditParams();
            vm.readonly.all=true;

            //loading层
            var index = layer.load(1, {
                shade: [0.5,'#fff']
            });

            layui.use('table', function() {
                vm.warehouseMap = {};
                vm.materialTable = layui.table;
                vm.materialTable.render({
                    elem: '#materials'
                    , cellMinWidth: 80
                    , cols: materialsEditCols
                    , data: null
                    , even: false
                    , totalRow: true
                    , id: 'materials'
                });
            });

            httpGet('sap/salertn/info',{
                docEntry:docEntry,
            },function(data){
                var result = data.result;

                if(result.docStatus != 'C'){
                    vm.showButton.cancelOrder = true;
                }
                vm.showButton.print = true;

                vm.customer.code = result.cardCode;
                vm.customer.name = result.cardName;
                vm.customer.canUseCost = result.canUseCost;
                vm.customer.inOutAmt = result.inOutAmt;
                vm.customer.discAmt = result.discAmt;
                vm.customer.canUseAmt = result.canUseAmt;
                var driverRst = parseInt(result.driver);
                vm.customer.driver = result.driver != null && driverRst !='' && driverRst != 'undefined' ? driverRst : 0;

                vm.sales.docEntry = result.docEntry;
                vm.sales.docNum = result.docNum;
                vm.sales.docStatus = result.docStatus;
                vm.sales.docStatusName = result.docStatsName;
                vm.sales.taxDate = result.taxDate;
                vm.sales.takeNum = result.takeNum;
                vm.sales.discount = result.discount;
                vm.sales.baseDiscount = result.baseDiscount;
                vm.sales.fanjiAmount = result.fanjiAmount;
                vm.sales.cash = result.cash;
                vm.sales.thisArrears = result.thisArrears;
                vm.sales.remark = result.remark;
                vm.sales.transType = result.tranType;
                vm.sales.busiType = result.busiType;

                //处理收货人
                Document.initContacts();

                //销售分支
                Document.initSalesBranch(result.bplid,function(){
                    vm.sales.salesBranch = parseInt(result.bplid);
                    vm.sales.salesBranchTaxId = parseInt(result.taxIdNum);

                    //处理物料
                    //Document.initMaterials();
                });

                vm.sales.salesBranch = parseInt(result.bplid);

                //车牌号
                Document.initCarNos();

                vm.customer.cntctPrsn = result.cntctPrsn;
                vm.customer.cntctName = result.cntctName;

                vm.sales.carNo = result.carNo;
                vm.sales.boundNo = result.boundNo;
                vm.sales.srcNum = result.srcNum;
                //销售订单
                vm.initDeliveryOrder(function(data){
                    vm.sales.srcEntry=result.srcEntry;
                });

                vm.materials = [];
                var items = result.itemInfos;
                if(items != null){
                    items.forEach(function(item){
                        vm.materials.push({
                            itemCode:item.itemCode,
                            itemName:item.itemName,
                            kg:item.quantity,
                            num:item.packNum,
                            factPrice:item.price,
                            currDisc:item.currDisc,
                            factAmount:Multiply(item.quantity,item.price),
                            isPackage:item.isPackage,
                            salFactor1:item.salFactor1,
                            amount:item.payAmt,
                            isGiveGD:item.realdisc,
                            warehouseCode:item.warehouseCode,
                            order:item.discOrder,
                            warehouseName:item.warehouseName,
                        });
                    });
                }

                Document.reloadMaterialTable();

                //处理物料
                Document.initMaterialsReturn(items);

                layer.close(index);
            },function(data){
                alert(data.msg);
                layer.close(index);
            });

        },
        saveOrUpdate:function(){
            var params = Document.buildReturnParams();
            params.driver = vm.customer.driver;
            Document.salesReturn(params,function(data){
                vm.reload(false);
                if(params.docEntry == null){
                    vm.update(data.docEntry);
                }
            })
            console.log(params);
        },
        cancelOrder:function(){
            httpPostFormData('sap/salertn/cancel',{
                docEntry:vm.sales.docEntry,
            },function(data){
                alert('取消成功!',function(){
                    vm.add();
                });
                vm.reload(false);
            });
        },
        back:function(){
            vm.showList=true;
        },
        showTab1:function(){
            vm.tabShow = true;
        },
        showTab2:function(){
            vm.tabShow = false;
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
        filterCustomer:function(val){
            Document.filterCustomer(val);
        },
        addMaterial:function(){
            Document.addMaterial();
        },
        /**
         * 随单折（增）
         */
        discount:function(){
            var params = vm.buildSaveParams();
            console.log(params);
            params.isOrder = 'N';
            params.srcEntry = vm.sales.srcEntry;
            if(!vm.checkMaterials()){
                return;
            }
            httpPost('sap/senditem/give',params,function(data){
                alert('更新随单折信息成功！');
                var result = data.give;
                vm.buildUpdateResult(result);
                Document.calcArrear();
            })
        },
        checkMaterials:function(){
            var result = true;
            var itemCodes = "";
            vm.materials.forEach(function (item) {
                if(item.itemCode != null && (item.num==null || item.num =='')){
                    itemCodes += item.itemCode +" "
                    result = false;
                }
            });
            if(!result){
                alert("请输入物料："+itemCodes+"的包数！");
            }
            return result;
        },
        selectedCustomerSearch:function(val){
            var item = Document.filterCustomerById(val);
            vm.search.CustomerNo = item.id;
        },
        selectedCustomer:function(val){
            vm.materials = [];
            Document.reloadMaterialTable();
            var item = Document.filterCustomerById(val);
            vm.customer.name = item.show;
            vm.customer.code = item.id;
            vm.initSalesParams();

            //处理收货人
            Document.initContacts(function(){
                vm.customer.cntctPrsn = item.cntctPrsn;
                vm.customer.cntctName = item.cntctName;
            })

            //销售分支
            Document.initSalesBranch(null,function(data){
                if(vm.sales.salesBranch != null){
                    vm.selectedSalesBranch();
                }else{
                    if(data != null && data.length == 1){
                        vm.sales.salesBranch = data[0].bplid;
                        vm.selectedSalesBranch();
                    }
                }
            });

            //车牌号
            Document.initCarNos();

            //销售交货单
            vm.initDeliveryOrder();

        },
        calcAllMaterials:function(){
            Document.calcAllMaterials();
        },
        selectedCarNo:function(){
            Document.initBoundNos();
        },
        selectedSalesBranch:function(){
            vm.salesBranches.forEach(function(item){
                if(item.bplid == vm.sales.salesBranch){
                    vm.sales.salesBranchTaxId = item.taxIdNum;
                }
            });

            //处理物料
            Document.initMaterials();
            Document.initCarNos();
            vm.initDeliveryOrder();
            vm.materials = [];
        },
        initEditParams:function(){
            vm.showList = false;
            vm.showAddMaterial = true;
            vm.materials = [];
            vm.customer={
                code:null,
                name:null,
                cntctPrsn:null,
                cntctName:null,
            };
            Document.reloadMaterialTable();
            vm.readonly = {};
            vm.initSalesParams();
            vm.showButton={
                save:false,
                cancelOrder:false,
                add:false,
                update:false,
                print:false,
                addMaterial:false,
            }
        },
        initSalesParams:function(){
            vm.sales={
                docNum : null,
                srcEntry : null,
                salesBranch:null,
                takeBranch : null,
                taxDate : getNowFormatDate(),
                baseDiscount:0,
                discount:0,
                cash:0,
                fanjiAmount:0,
                thisArrears:0,
                baseDiscountDisable:true,
                discountDisable:true,
            };
        },
        /**
         * 交货订单
         */
        initDeliveryOrder:function(callback){
            vm.remoteSalesOrder = vm.remoteSalesOrderCopy = [];
            vm.sales.srcEntry = null;
            httpGet('sys/droplist/deliveryOrderDropList',{
                param:JSON.stringify({param1:vm.customer.code,param2:vm.sales.salesBranch})
            },function(data){
                vm.remoteSalesOrder = vm.remoteSalesOrderCopy = data.data;
                if(typeof callback != 'undefined'){
                    callback(data);
                }
            })
        },
        clearOrder:function(){
            vm.sales.srcEntry = null;
            Document.initMaterials();
        },
        selectedSalesOrder:function(){
            if(vm.sales.srcEntry == null || vm.sales.srcEntry==''){
                return;
            }
            httpGet('sap/saleinv/back',{
                docEntry:vm.sales.srcEntry,
                status:'Y',
            },function(data){
                var result = data.result;
                vm.materials = [];
                //销售分支
                Document.initSalesBranch(result.bplid,function(){
                    vm.sales.salesBranch = parseInt(result.bplid);
                    vm.sales.salesBranchTaxId = parseInt(result.taxIdNum);
                });

                //车牌号
                Document.initCarNos();

                vm.sales.srcNum = result.docNum;

                var items = result.itemInfos;
                //处理物料
                Document.initMaterialsReturn(items);

                Document.calcArrear();
            });
        },
        buildSaveParams:function(){
            var itemInfos = [];
            var index = 1;
            vm.materials.forEach(function(item){
               if(item.itemCode != null && item.itemCode != ""){
                   itemInfos.push({
                       lineNum:index,
                       itemCode:item.itemCode,
                       itemName:item.itemName,
                       quantity:item.kg,
                       packNum:item.num,
                       price:item.factPrice,
                       currDisc:item.currDisc,
                       isPackage:item.isPackage,
                       salFactor1:item.salFactor1,
                       payAmt:item.amount,
                       realdisc:item.isGiveGD,
                       warehouseCode : item.warehouseCode,
                       discOrder:item.order,
                   });
                   index++;
               }
            });
            var params = {
                docEntry:vm.sales.docEntry,
                cardCode:vm.customer.code,
                cardName:vm.customer.name,
                cntctPrsn:vm.customer.cntctPrsn,
                cntctName:vm.customer.cntctName,
                taxDate:vm.sales.taxDate,
                carNo:vm.sales.carNo,
                boundNo:vm.sales.boundNo,
                bplid:vm.sales.salesBranch,
                taxIdNum:vm.sales.salesBranchTaxId,
                takeBPLId:vm.sales.takeBranch,
                //物料信息
                itemInfos:itemInfos,
                srcNum:vm.sales.srcNum,
                remark:vm.sales.remark,
                busiType: vm.sales.busiType,
                driver: vm.customer.driver,
                tranType: vm.sales.transType,
            };
            return params;
        },
        buildUpdateResult:function(result){
            vm.customer.cntctPrsn = result.cntctPrsn;
            vm.customer.cntctName = result.cntctName;
            var driverRst = parseInt(result.driver);
            vm.customer.driver = driverRst != 'undefined' ? driverRst : 0;
            vm.contacts.forEach(function(contact){
                if(vm.customer.cntctPrsn == contact.cntctPrsn){
                    vm.customer.cntctPhone = contact.cntctPhone;
                }
            });

            vm.sales.discountDisable = false;
            vm.sales.carNo = result.carNo;
            vm.sales.boundNo = result.boundNo;
            vm.sales.inlineNo = result.inlineNo;
            vm.sales.printor = result.printor;
            vm.sales.printDate = result.printDate;
            vm.sales.printTime = result.printTime;
            vm.sales.printNum = result.printNum;
            vm.sales.creator = result.creator;
            vm.sales.createDate = result.createDate;
            vm.sales.createTime = result.createTime;
            vm.sales.invDocNum = result.invDocNum;
            vm.sales.prsDisc = result.prsDisc;
            vm.sales.baseDiscount = result.baseDiscount;
            vm.sales.discount = result.discount;
            vm.sales.fanjiAmount = result.fanjiAmount;
            vm.sales.cash = result.cash;
            vm.sales.thisArrears = result.thisArrears;
            vm.sales.discountDisable = true;
            vm.sales.transType = result.tranType;
            vm.sales.busiType = result.busiType;

            vm.discounts = result.discountEntities;
            vm.receives = result.paymentEntities;
            vm.giftInfoEntities = result.giftInfoEntities;

            vm.materials = [];
            var items = result.itemInfos;
            if(items != null){
                items.forEach(function(item){
                    vm.materials.push({
                        itemCode:item.itemCode,
                        itemName:item.itemName,
                        kg:item.quantity,
                        num:item.packNum,
                        factPrice:item.price,
                        currDisc:item.currDisc,
                        factAmount:Multiply(item.quantity,item.price),
                        isPackage:item.isPackage,
                        salFactor1:item.salFactor1,
                        amount:item.payAmt,
                        isGiveGD:item.realdisc,
                        whsCode:item.warehouseCode,
                        order:item.discOrder,
                        stock:vm.remoteMaterialsMap[item.itemCode].stock,
                        originalQuantity:item.quantity,
                        regSupName:item.regSupName,
                    });
                });
            }

            Document.reloadMaterialTable();
        },
        changeCash:function(){
            Document.calcArrear();
        },
        changeFanjiAmount:function(){
            Document.calcArrear();
        },
        print:function(){
            var printUrl = baseURL+'statics/plugins/pdf/web/viewer.html?file=/sap/saleinv/print/'+vm.sales.docEntry+"/81";
            window.open(printUrl);
        },
    },
    mounted:function(){
        Document.vm = this;
        Document.initCustomer();
        Document.vm.drivers = Document.initDriverList();
        Document.initDocStatus('return_status');
    }

});
