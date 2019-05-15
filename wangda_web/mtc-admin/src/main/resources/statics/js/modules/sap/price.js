$(function () {
    $("#jqGrid").setjgGrid({
        api:  'sys/list/priceOrderList', //API地址
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
        readonly:{
            cardCode:false,
            salesBranch:false,
            taxDate:false
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
        },
        remoteCustomers:[],
        remoteCustomersCopy:[],
        sales:{
        },
        salesBranches:[], //销售分支
        docStatusList:[],
        receivesTable:null,
        receives:[],
    },
    components:{
        'material-table':materialsComponent,
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
                    peramt:0,
                    usedamt:0,
                    lastamt:0,
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
            vm.title = "新增保价单";
            vm.showList = false;
            vm.initParams();
            vm.showButton.save=true;

            Document.initCustomer();
        },
        edit:function(docEntry){
            vm.title = "编辑订料单";
            vm.showList = false;

            vm.initParams();
            httpGetInfo('sap/priceorder/info',{docentry:docEntry},function(r){
                var result = r.data;

                if(result.mtcSdOBJD.docstatus == 'O'){
                    vm.showButton.update = true;
                    vm.showButton.cancel = true;
                    vm.showButton.close = true;
                    vm.showButton.receive = true;
                    vm.showButton.cancelReceive = true;
                }

                vm.readonly.cardCode = true;
                vm.readonly.salesBranch =true;
                vm.readonly.taxDate = true;
                vm.sales.docEntry = result.mtcSdOBJD.docentry;
                vm.sales.docNum = result.mtcSdOBJD.docnum;
                vm.sales.docStatusName = result.mtcSdOBJD.docStatusName;
                vm.sales.docStatus = result.mtcSdOBJD.docstatus;
                vm.sales.taxDate = result.mtcSdOBJD.docdate;
                vm.sales.remark = result.mtcSdOBJD.remark;
                vm.sales.usedamt = result.mtcSdOBJD.usedamt;
                vm.sales.lastamt = result.mtcSdOBJD.lastamt;
                vm.customer.code = result.mtcSdOBJD.cardcode;
                vm.customer.name = result.mtcSdOBJD.cardname;
                //销售分支
                Document.initSalesBranch(result.mtcSdOBJD.bplid,function(){
                    vm.sales.salesBranch = parseInt(result.mtcSdOBJD.bplid);
                    //处理物料
                    Document.initMaterials(function(){
                        //物料信息
                        result.mtcSdBJD1List.forEach(function(item){
                            var factor1 = item.facotr1;
                            if(factor1 == null){
                                factor1 =  vm.remoteMaterialsMap[item.itemcode].salFactor1;
                            }
                            vm.materials.push({
                                docEntry : item.docentry,
                                itemCode : item.itemcode,
                                itemName : item.itemname,
                                num : item.factor2,
                                salFactor1 : factor1,
                                kg:item.quantity,
                                factPrice:item.price,
                                factAmount:item.linetotal,
                                uesdqty:item.uesdqty,
                                lastqty:item.lastqty,
                                packagePrice:Multiply(factor1,item.price),
                            });
                        });
                        vm.materialTable.reload('materials',{data:vm.materials});
                    });
                });



                //收款方式
                result.mtcSdBJD2List.forEach(function(item){
                    vm.receives.push({
                        docEntry:item.docentry,
                        payName:item.payment,
                        payAmt:item.payamt,
                        payDocNum:item.paynum,
                        payCode:item.paycode,
                    })
                });
                vm.receivesTable.reload('receives',{data:vm.receives});
                vm.calcPerAmount();
            });

        },
        saveOrUpdate:function(){
            var params = vm.buildSaveParams();
            if(vm.sales.docEntry == null) {
                //新增
                httpPost('sap/priceorder/add',params,function(data){
                    console.log(data);
                    alert("添加单据成功！"+data.msg);
                    vm.reload(false);
                    var docEntry = data.data.mtcSdOBJD.docentry;
                    vm.edit(docEntry);
                },function(data){
                    alert(data.msg);
                    //vm.showButton.save = true;
                });
            }else{
                //修改
                //调用更新接口
                httpPost('sap/priceorder/update',params,function(data){
                    alert("更新单据成功！"+data.msg);
                    vm.reload(false);
                });
            }
        },
        cancel:function(){
            httpPostFormData('sap/priceorder/cancel',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        close:function(){
            httpPostFormData('sap/priceorder/close',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        receive:function(){
            httpPostFormData('sap/priceorder/receipt',{docentry:vm.sales.docEntry},function(){
                alert("操作成功");
                vm.edit(vm.sales.docEntry);
            });
        },
        cancelReceive:function(){
            httpPostFormData('sap/priceorder/cancelrpt',{docentry:vm.sales.docEntry},function(){
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
            vm.initReceive();
        },
        initReceive:function(){
            httpGet('sap/paydisc/list/',{
                CardCode:vm.customer.code,
                BPLId:vm.sales.salesBranch,
                FilterValue:'MTC_SD_OBJD',
            },function(data){
                vm.receives = data.pay; //收款信息
                vm.receivesTable.reload('receives',{data:vm.receives});
            });
        },
        addMaterial:function(){
            Document.addMaterial();
        },
        buildSaveParams:function(){
            var params = {};
            //主表
            var mtcSdOBJD = {
                docentry : vm.sales.docEntry,
                bplid:vm.sales.salesBranch,
                cardcode:vm.customer.code,
                cardname:vm.customer.name,
                docdate:vm.sales.taxDate,
                usedamt:vm.sales.usedamt, //已收款金额
                lastamt:vm.sales.lastamt, //还剩余额
                docstatus:vm.sales.docStatus, //单据状态
                docnum : vm.sales.docNum,
                remark : vm.sales.remark,
            };
            //明细表（物料）
            var mtcSdBJD1List = [];
            var index = 1;
            vm.materials.forEach(function(item){
                if(item.itemCode != null && item.itemCode != ""){
                    mtcSdBJD1List.push({
                        docentry:item.docEntry,
                        linenum:index,
                        itemcode:item.itemCode,
                        itemname:item.itemName,
                        price:item.factPrice, //单价
                        quantity:item.kg, //数量
                        linetotal:item.factAmount, //金额
                        facotr1:item.salFactor1, // 包重
                        factor2:item.num, //包数
                        uesdqty:null, // 已使用数量
                        lastqty:null, //剩余数量
                    });
                }
                index++;
            });

            //收款明细表
            var mtcSdBJD2List = [];

            vm.receives.forEach(function(item){
                mtcSdBJD2List.push({
                    docentry:item.docEntry,
                    payment:item.payName,
                    payamt:item.payAmt,
                    paynum:item.payDocNum,
                    paycode:item.payCode,
                });
            })

            params.mtcSdOBJD = mtcSdOBJD;
            params.mtcSdBJD1List = mtcSdBJD1List;
            params.mtcSdBJD2List = mtcSdBJD2List;
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
        //计算预收金额
        calcPerAmount(){
            var total = 0;
            vm.receives.forEach(function(item){
                total+=parseFloat(item.payAmt);
            });
            vm.sales.peramt = total;
        },
    },
    mounted:function() {
        Document.vm = this;

        Document.initCustomer();
        Document.initDocStatus('desOrderDocStatus');

        layui.use('table', function(){
            vm.receivesTable = layui.table;
            vm.receivesTable.render({
                elem: '#receives'
                ,cellMinWidth: 140
                ,cols:receivesCols
                ,data:null
                ,even: false
                ,totalRow: true
                ,id:'receives'
            });
            vm.receivesTable.on('edit(receives)', function(obj){
                var index = obj.tr.attr('data-index');
                vm.receives[index] = obj.data;

                if(obj.field=='payAmt'){
                    var oldValue = $(this).prev().text();
                    if(obj.data.payAmt < 0){
                        alert('金额不能小于0!');
                        $(this).val(oldValue);
                        vm.receives[index].payAmt = oldValue;
                    }
                }
                vm.receivesTable.reload('receives',{data:vm.receives});
                vm.calcPerAmount();
                Document.calcArrear();
            });
        });
    },
});

//收款信息
var receivesCols= [[
    {field: 'payCode', title: '收款方式代码',totalRowText:'合计',style:'background-color:#eee'}
    ,{field: 'payName', title: '收款方式名称',style:'background-color:#eee'}
    ,{field: 'payAmt', title: '金额',edit: 'text',totalRow:true}
    ,{field: 'payDocNum', title: '收款单号',style:'background-color:#eee'}
]];