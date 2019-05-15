$(function () {
    $("#jqGrid").setjgGrid({
        api:  'sys/list/desOrderList', //API地址
        height:'full',
        hiddenFields: ['DOCENTRY'],               //隐藏字段
        qParam: vm.search,                  //查询参数
        chooseType: eChooseType.Multi,     //选项类型
        shrinkToFit:true,
        loadDefault: true,                 //初始不载入数据，由 tree 载入
        buttons:[                           //操作按钮
            {
                key:'DocEntry',
                command: 'edit', function: function(id){    //编辑
                    vm.edit(id);
                }
            },
        ],
        loadComplete: function(data){       //载入完成后载入树

        }
    });
    Document.initSalesBranch(null,function(){

    });
});



var vm = new Vue({
    el:'#rrapp',
    data:{
        showList:true,
        title:null,
        customer:{
            code:null,
            name:null,
            cntctPrsn:null,
            cntctName:null,
            canUseCost:null,
            inOutAmt:null,
            discAmt:null,
            canUseAmt:null,
        },
        remoteCustomers:[],
        remoteCustomersCopy:[],
        readonly:{
            cardCode:false,
            salesBranch:false,
            taxDate:false
        },
        sales:{
        },
        salesBranches:[], //销售分支
        search:{
            param1:null,
            param2:null,
            param3:null,
            param4:null,
            param5:null,
            param6:null,
        },
        showButton:{
            save:false,
            update:false,
            cancel:false,
            close:false,
            receive:false,
            cancelReceive:false,
        },
        docStatusList:[],
    },
    components:{
        'material-table':materialsComponent,
        'receive-discount-table':receiveAndDiscountComponent,
    },
    methods:{
        initParams:function(){
            vm.materials = [];
            vm.receives = [];
            vm.materialTable.reload('materials',{data:vm.materials});
            vm.receivesTable.reload('receives',{data:vm.receives});

            vm.customer={
                code:null,
                name:null,
                cntctPrsn:null,
                cntctName:null,
                canUseCost:null,
                inOutAmt:null,
                discAmt:null,
                canUseAmt:null,
            };
            vm.readonly={
                cardCode:false,
                salesBranch:false,
                taxDate:false
            },
            vm.sales={
                salesBranch:null,
                taxDate : getNowFormatDate()
            };
            vm.showButton={
                save:false,
                update:false,
                cancel:false,
                close:false,
                receive:false,
                cancelReceive:false,
            };
        },
        add:function(){
            vm.title="新增订料单";
            vm.showList=false;
            vm.initParams();
            vm.showButton.save=true;

            Document.initCustomer();
        },
        edit:function(docEntry){
            vm.title = "编辑订料单";
            vm.showList = false;

            vm.initParams();

            httpGetInfo('sap/desorder/info',{docentry:docEntry},function(r){
                var result = r.data;

                if(result.mtcSdODLD.docstatus == 'O'){
                    vm.showButton.update = true;
                    vm.showButton.cancel = true;
                    vm.showButton.close = true;
                    vm.showButton.receive = true;
                    vm.showButton.cancelReceive =true;
                }

                vm.readonly.cardCode = true;
                vm.readonly.salesBranch =true;
                vm.readonly.taxDate = true;
                vm.sales.docEntry = result.mtcSdODLD.docentry;
                vm.sales.docNum = result.mtcSdODLD.docnum;
                vm.sales.docStatusName = result.mtcSdODLD.docStatusName;
                vm.sales.docStatus = result.mtcSdODLD.docstatus;
                vm.sales.taxDate = result.mtcSdODLD.docdate;
                vm.sales.remark = result.mtcSdODLD.remark;
                vm.customer.code = result.mtcSdODLD.cardcode;
                vm.customer.name = result.mtcSdODLD.cardname;
                //销售分支
                Document.initSalesBranch(result.mtcSdODLD.bplid,function(){
                    vm.sales.salesBranch = parseInt(result.mtcSdODLD.bplid);
                    //处理物料
                    Document.initMaterials();
                });

                //物料信息
                result.mtcSdDLD1List.forEach(function(item){
                    vm.materials.push({
                        docEntry : item.docentry,
                        itemCode : item.itemcode,
                        itemName : item.itemname,
                        num : item.factor2,
                        salFactor1 : item.factor1,
                        kg:item.quantity,
                        factPrice:item.price,
                        factAmount:item.linetotal,
                        uesdqty:item.uesdqty,
                        lastqty:item.lastqty,
                        packagePrice:Multiply(item.factor1,item.price),
                    });
                });
                vm.materialTable.reload('materials',{data:vm.materials});

                //收款方式
                result.mtcSdDLD2List.forEach(function(item){
                   vm.receives.push({
                       docEntry:item.docentry,
                       payName:item.payment,
                       payAmt:item.payamt,
                       payDocNum:item.paynum,
                       payCode:item.paycode,
                   })
                });
                vm.receivesTable.reload('receives',{data:vm.receives});
            });
        },
        saveOrUpdate:function(){
            var params = vm.buildSaveParams();
            if(vm.sales.docEntry == null) {
                //新增
                httpPost('sap/desorder/add',params,function(data){
                    console.log(data);
                    alert("添加单据成功！"+data.msg);
                    vm.reload(false);
                    var docEntry = data.data.mtcSdODLD.docentry;
                    vm.edit(docEntry);
                },function(data){
                    alert(data.msg);
                    //vm.showButton.save = true;
                });
            }else{
                //修改
                //调用更新接口
                httpPost('sap/desorder/update',params,function(data){
                    alert("更新单据成功！"+data.msg);
                    vm.reload(false);
                });
            }
        },
        cancel:function(){
            httpPostFormData('sap/desorder/cancel',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        close:function(){
            httpPostFormData('sap/desorder/close',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        receive:function(){
            httpPostFormData('sap/desorder/receipt',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        cancelReceive:function(){
            httpPostFormData('sap/desorder/cancelrpt',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        filterCustomer:function(val){
            Document.filterCustomer(val);
        },
        selectedCustomer:function(val){
            var item = Document.filterCustomerById(val);
            vm.customer.name = item.show;
            vm.customer.code = item.id;

            //销售分支
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
        },
        changeDate:function(){
            Document.initMaterials();
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
            receiveAndDiscountComponent.methods.init('MTC_SD_ODLD');
        },
        addMaterial:function(){
            Document.addMaterial();
        },
        buildSaveParams:function(){
            var params = {};
            //主表
            var mtcSdODLD = {
                docentry : vm.sales.docEntry,
                bplid:vm.sales.salesBranch,
                cardcode:vm.customer.code,
                cardname:vm.customer.name,
                docdate:vm.sales.taxDate,
                usedamt:null, //已收款金额
                lastamt:null, //还剩余额
                docstatus:vm.sales.docStatus, //单据状态
                docnum : vm.sales.docNum,
                remark : vm.sales.remark,
            };
            //明细表（物料）
            var mtcSdDLD1List = [];
            var index = 1;
            vm.materials.forEach(function(item){
                if(item.itemCode != null && item.itemCode != ""){
                    mtcSdDLD1List.push({
                        docentry:item.docEntry,
                        linenum:index,
                        itemcode:item.itemCode,
                        itemname:item.itemName,
                        price:item.factPrice, //单价
                        quantity:item.kg, //数量
                        linetotal:item.factAmount, //金额
                        factor1:item.salFactor1, // 包重
                        factor2:item.num, //包数
                        uesdqty:null, // 已使用数量
                        lastqty:null, //剩余数量
                    });
                }
                index++;
            });

            //收款明细表
            var mtcSdDLD2List = [];

            vm.receives.forEach(function(item){
                mtcSdDLD2List.push({
                    docentry:item.docEntry,
                    payment:item.payName,
                    payamt:item.payAmt,
                    paynum:item.payDocNum,
                    paycode:item.payCode,
                });
            })

            params.mtcSdODLD = mtcSdODLD;
            params.mtcSdDLD1List = mtcSdDLD1List;
            params.mtcSdDLD2List = mtcSdDLD2List;
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
                postData:{param:JSON.stringify(vm.search)},
                page: page
            }).trigger("reloadGrid");
        },
    },
    mounted:function() {
        Document.vm = this;

        Document.initCustomer();
        Document.initDocStatus('desOrderDocStatus');
    },
});
