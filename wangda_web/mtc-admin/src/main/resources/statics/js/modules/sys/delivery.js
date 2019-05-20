$(function () {

    $(function () {
        $("#jqGrid").setjgGrid({
            api:  'sys/list/orderDeliverysList', //API地址
            height:'full',
            hiddenFields: ['DOCENTRY','ODLNDocEntry'],               //隐藏字段
            qParam: vm.search,                  //查询参数
            chooseType: eChooseType.Multi,     //选项类型
            shrinkToFit:true,
            loadDefault: true,                 //初始不载入数据，由 tree 载入
            rowList : [10,50,100,500,1000],
            buttons:[                           //操作按钮
                {
                    key:'DOCENTRY',
                    command: 'view', function: function(id){    //编辑
                        vm.view(id);
                    }
                },
            ],
            loadComplete: function(data){       //载入完成后载入树
            }
        });
        Document.initSalesBranch(null,function(){
        });
    });
    //销售分支
    Document.initSalesBranch(null,function(data){
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        search:{
            param1:null,
            param2:getNowFormatDate(),
            param3:getNowFormatDate(),
            param4:'O',
            param5:null,
            param6:null,
        },
        showList:true,
        salesBranches:[],
        sales:{},
        customer:{
            code:'',
            driver:null,
        },
        salesOrderForm:false,
        tabShow:true,
        remoteCustomers:[],
        remoteCustomersCopy:[],
        contacts:[],
        desOrders:[],
        priceOrders:[],
        carNos:[],
        boundNos:[],
        drivers: [],
    },
    components:{
        'material-table':materialsComponent,
        'receive-discount-table':receiveAndDiscountComponent,
    },
    methods:{
        query:function(){
            vm.reload();
        },
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData:{param:JSON.stringify(vm.search)},
                page: page
            }).trigger("reloadGrid");
        },
        receive:function(){
            var ids = getSelectedRows("DOCENTRY");
            if(ids == null){
                return null;
            }
            httpPost('sap/saleinv/batchdelivery',ids, function(data){
                alert(data.msg);
                vm.reload();
            });
        },
        cancelReceive:function(){
            var ids = getSelectedRows("ODLNDocEntry");
            if(ids == null){
                return null;
            }
            httpPost('sap/saleinv/batchceldelivery',ids, function(data){
                alert(data.msg);
                vm.reload();
            });
        },
        add:function(){
        },
        view:function(docEntry){
            httpGetInfo('sap/saleinv/info',{
                docEntry:docEntry,status:'O'
            },function(data){
                var result = data.result;

                //初始化司机
                vm.drivers = vm.initDriverList();

                vm.customer.code = result.cardCode;
                vm.customer.name = result.cardName;
                vm.customer.canUseCost = result.canUseCost;
                vm.customer.inOutAmt = result.inOutAmt;
                vm.customer.discAmt = result.discAmt;
                vm.customer.canUseAmt = result.canUseAmt;

                vm.sales.docEntry = result.docEntry;
                vm.sales.docNum = result.docNum;
                vm.sales.docStatus = result.docStatus;
                vm.sales.docStatusName = result.docStatsName;
                vm.sales.taxDate = result.taxDate;
                vm.sales.remark = result.remark;
                vm.sales.thisArrears = result.thisArrears;
                vm.sales.takeNum = result.takeNum;
                vm.sales.carNo = result.carNo;
                vm.sales.boundNo = result.boundNo;
                vm.sales.maxInOutAmt = result.maxInOutAmt;
                vm.sales.transType = result.tranType;
                vm.sales.busiType = result.busiType;
                vm.customer.driver = result.driver != 'undefined' && result.driver !='' && result.driver != null ? parseInt(result.driver) : 0;

                //处理收货人
                Document.initContacts();

                //销售分支
                Document.initSalesBranch(result.bplid,function(){
                    vm.sales.salesBranch = parseInt(result.bplid);
                    vm.sales.salesBranchTaxId = parseInt(result.taxIdNum);

                    //处理物料
                    Document.initMaterials(function () {
                        vm.buildUpdateResult(result);
                    });

                    //车牌号
                    Document.initCarNos(function(){
                        vm.sales.carNo = result.carNo;
                        vm.sales.boundNo = result.boundNo;
                        Document.initBoundNos();
                    });

                });

                vm.initDesOrder(function(){
                    vm.sales.desOrder = result.desOrderNum==null?null:parseInt(result.desOrderNum);
                });

                vm.initPriceOrder(function(){
                    vm.sales.priceOrder  = result.priceOrderNum==null?null:parseInt(result.priceOrderNum);
                });

                vm.initDesOrder();

                vm.salesOrderForm=true;
            });
        },
        showTab1:function(){
            vm.tabShow = true;
        },
        showTab2:function(){
            vm.tabShow = false;
        },
        filterCustomer:function(val){
            Document.filterCustomer(val);
        },
        selectedCustomer:function(val){
            var item = Document.filterCustomerById(val);
            vm.customer.name = item.show;
            vm.customer.code = item.id;
            vm.customer.canUseCost = item.canUseCost;
            vm.customer.inOutAmt = item.inOutAmt;
            vm.customer.discAmt = item.discAmt;
            vm.customer.canUseAmt = item.canUseAmt;
            vm.salesBranches = [];
            vm.takeBranches = [];

            //处理收货人
            Document.initContacts(function(r){
                vm.customer.cntctPrsn = item.cntctPrsn;
                vm.customer.cntctName = item.cntctName;
                r.forEach(function(contact){
                    if(contact.cntctPrsn == vm.customer.cntctPrsn){
                        vm.customer.cntctPhone = contact.cntctPhone;
                    }
                });
            });

            //销售分支
            Document.initSalesBranch(null,function(data){
                if(data != null && data.length == 1){
                    vm.sales.salesBranch = data[0].bplid;
                    //提货分支
                    Document.initTakeBranch(null,function(data){
                        if(data != null && data.length == 1){
                            vm.sales.takeBranch = data[0].bplid;
                            vm.selectedSalesBranch();
                        }else{
                            vm.selectedSalesBranch();
                        }
                        vm.selectedTakeBranch();
                    });
                }else{
                    Document.initTakeBranch(null,function(data){
                        if(data != null && data.length == 1){
                            vm.sales.takeBranch = data[0].bplid;
                            vm.selectedTakeBranch();
                        }
                    });
                }
            });

        },
        selectedCntctPrsn:function(val){
            vm.calcAllMaterials();
            vm.contacts.forEach(function(contact){
                if(contact.cntctPrsn == vm.customer.cntctPrsn){
                    vm.customer.cntctPhone = contact.cntctPhone;
                }
            });
        },
        changeDate:function(){
            Document.initMaterials();
            Document.initCarNos();
        },
        selectedSalesBranch:function(){
            httpGet('sap/customer/amt',{
                CardCode:vm.customer.code,
                BPLId:vm.sales.salesBranch,
            },function(data){
                if(data.amt != null && data.amt.length>0){
                    vm.customer.inOutAmt=data.amt[0].actBalance;
                    vm.customer.discAmt=data.amt[0].discAmt;
                    vm.customer.canUseAmt=data.amt[0].custAmt;
                }
            });

            vm.salesBranches.forEach(function(item){
                if(item.bplid == vm.sales.salesBranch){
                    vm.sales.salesBranchTaxId = item.taxIdNum;
                }
            });

            Document.calcAllMaterials();
            //处理物料
            Document.initMaterials();
            //收款折扣信息
            receiveAndDiscountComponent.methods.init('');
            //初始化订料单
            vm.initDesOrder();
            //初始化保价单
            vm.initPriceOrder();
        },
        /**
         * 初始化订料单
         */
        initDesOrder:function(callback){
            httpGetInfo('sys/droplist/desOrderDropList',{
                param:JSON.stringify({param1:vm.customer.code,param2:vm.sales.salesBranch})
            },function(r){
                vm.desOrders = r.data;
                if(typeof  callback != 'undefined'){
                    callback();
                }
            });
        },
        /**
         * 司机
         */
        initDriverList: function (callback) {
            var obj = [];
            var url = "sys/droplist/driverList";
            $.ajax({
                type: "GET",
                url: baseURL + url,
                contentType: "application/json",
                data: {},
                async: false,
                success: function (r) {
                    obj  = r.data;
                }
            });
            return obj;

        },
        /**
         * 初始化保价单
         */
        initPriceOrder:function(callback){
            httpGetInfo('sys/droplist/priceOrderDropList',{
                param:JSON.stringify({param1:vm.customer.code,param2:vm.sales.salesBranch})
            },function(r){
                vm.priceOrders = r.data;
                if(typeof  callback != 'undefined'){
                    callback();
                }
            });
        },
        /**
         * 选择订料单
         */
        selectedDesOrder:function(){
            if(vm.sales.desOrder != null && vm.sales.desOrder != ''){
                vm.readonly.priceOrder = true;
                Document.initMaterials();
            }
        },
        /**
         * 清空订料单
         */
        clearDesOrder:function(){
            vm.readonly.priceOrder = false;
            Document.initMaterials();
        },
        /**
         * 选择保价单
         */
        selectedPriceOrder:function(){
            if(vm.sales.priceOrder != null && vm.sales.priceOrder != ''){
                vm.readonly.desOrder = true;
                Document.initMaterials();
            }
        },
        /**
         * 清空保价单
         */
        clearPriceOrder:function(){
            vm.readonly.desOrder = false;
            Document.initMaterials();
        },
        selectedCarNo:function(){
            Document.initBoundNos();
        },
        buildUpdateResult:function(result){
            vm.customer.cntctPrsn = result.cntctPrsn;
            vm.customer.cntctName = result.cntctName;
            vm.contacts.forEach(function(contact){
                if(vm.customer.cntctPrsn == contact.cntctPrsn){
                    vm.customer.cntctPhone = contact.cntctPhone;
                }
            });

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
                        amount:Multiply(item.quantity,item.price),
                        isGiveGD:item.realdisc,
                        whsCode:item.warehouseCode,
                        order:item.discOrder,
                        originalQuantity:item.quantity,
                        regSupName:item.regSupName,
                        totalDisc:Multiply(item.packNum,item.currDisc),
                        useQty:item.useQty,
                        ableQty:item.ableQty,
                    });
                });
            }
        },
        dialogOpened:function(){
            vm.materialTable.reload('materials',{data:vm.materials});
            vm.receivesTable.reload('receives',{data:vm.receives});
        },
    },
    mounted:function(){
        Document.vm = this;
    }
});
