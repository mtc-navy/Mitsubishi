$(function () {

    $("#jqGrid").jqGrid({
        url: baseURL + 'sap/saleinv/list',
        datatype: "json",
        colModel: [
            {
                label: 'docentry',
                name: 'docentry',
                index: "docentry",
                hidden: true,
                key: true,
                width: 75,
                sortable: false
            },
            {
                label: '操作', name: 'action', width: 65, sortable: false, formatter: function (value, options, row) {
                    var html = '<div class="btn-group btn-group-xs" role="group" aria-label="Extra-small button group">';
                    if (vm.showButton.edit) {
                        html += '<button type="button" value="' + row.docentry + '" status="' + row.docstatus + '" class="sales btn btn-default">编辑</button>';
                    }
                    html += '</div>'
                    return html;
                }
            },
            {label: '状态', name: 'docstatsname', width: 60, sortable: false},
            {label: '销售单号', name: 'docnum', index: "docnum", width: 90, sortable: false},
            {label: '客户名称', name: 'cardname', width: 140, sortable: false},
            {label: '单据日期', name: 'taxdateStr', width: 85, sortable: false},
            {label: '发货单号', name: 'takegoodsno', width: 90, sortable: false},
            {label: '提货单号', name: 'takeno', width: 140, sortable: false},
            {label: '过磅单号', name: 'weighno', width: 90, sortable: false},
            {label: '车牌号', name: 'carno', width: 85, sortable: false},
            {label: '总计', name: 'totalamt', width: 85, sortable: false},
            {label: '欠款', name: 'arrearsamt', width: 85, sortable: false},
            {label: '司机', name: 'driver', width: 70, sortable: false},
            {label: '业务类型', name: 'busiType', width: 70, sortable: false},
            {label: '单据状态', name: 'docstatus', width: 70,hidden:true, sortable: false},
            {label: '订料单号', name: 'desOrderNum', width: 75, sortable: false},
            {label: '保价单号', name: 'priceOrderNum', width: 75, sortable: false},
            {label: '运输方式', name: 'tranType', width: 70, sortable: false},
        ],
        viewrecords: true,
        height: $(window).height() - 200,
        rowNum: 20,
        rowList: [20, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        shrinkToFit:false,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
            $(".sales").click(function () {
                var id = $(this).val();
                var status = $(this).attr("status");
                vm.update(id, status);
            });
        },
        beforeSelectRow:function(id){
        },
        onCellSelect(rowid,iCol,cellContent,e){
            if(cellContent.indexOf("checkbox")>=0){
                var curRowData = $("#jqGrid").jqGrid('getRowData', rowid);
                if(curRowData.docstatus != 'W'){
                    alert("只能选择未发货的订单！");
                }
            }
        },
        onSelectRow:function(id)//选择某行时触发事件
        {
            var curRowData = $("#jqGrid").jqGrid('getRowData', id);
            if(curRowData.docstatus != 'W'){
                //alert("只能选择未发货的订单！");
                $("#jqGrid").jqGrid("setSelection", id,false);
            }
        },
        onSelectAll:function(rowid,status){
            if($("#cb_jqGrid").is(':checked')){
                var rowIds =  $("#jqGrid").jqGrid('getDataIDs');//获取jqgrid中所有数据行的id
                for(var i=0; i<rowIds.length; i++) {
                    var curRowData = $("#jqGrid").jqGrid('getRowData', rowIds[i]);//获取指定id所在行的所有数据.
                    if(curRowData.docstatus != 'W'){
                        $("#jqGrid").jqGrid("setSelection", rowIds[i],false);
                    }
                }
                $("#cb_jqGrid").prop("checked",true);
            }
        },
    });

});


var vm = new Vue({
    el: '#rrapp',
    data: {
        search: {
            CustomerNo: null,
            StartDate: null,
            EndDate: null,
            DocStatus: null,
            DocNum: null,
            TakeNo:null,
        },
        showList: true,
        title: null,
        tabShow: true,

        showAddMaterial: true,
        docStatusList: [],
        customer: {
            code: null,
            name: null,
            cntctPrsn: null,
            cntctName: null,
            canUseCost: null,
            inOutAmt: null,
            discAmt: null,
            canUseAmt: null,
            driver: null,
        },
        sales: {},
        contacts: [],
        salesBranches: [], //销售分支
        takeBranches: [],  //提货分支
        readonly: {
            cardCode: false,
            salesBranch: false,
            priceOrder: false,
        },
        remoteCustomers: [],
        remoteCustomersCopy: [],
        drivers: [],
        carNos: [],
        boundNos: [],
        remotePresentMaterials: [],
        giftInfoEntities: [],//随单折（增）信息表
        showButton: {
            print: false,
            return: false,
            receive: false,
            inline: false,
            cancelReceive: false,
            cancelOrder: false,
            save: false,
            draft: false,
            add: false,
            update: false,
            toDocument: false,
            printDiscount: false,
        },
        disabled: {
            inlineNo: true,
        },
        desOrders: [],
        priceOrders: [],
    },
    components: {
        'material-table': materialsComponent,
        'receive-discount-table': receiveAndDiscountComponent,
    },
    methods: {
        initEditParams: function () {
            vm.showList = false;
            vm.showAddMaterial = true;
            vm.materials = [];
            vm.receives = [];
            vm.discounts = [];
            vm.customer = {
                code: null,
                name: null,
                cntctPrsn: null,
                cntctName: null,
                canUseCost: null,
                inOutAmt: null,
                discAmt: null,
                canUseAmt: null,
                driver: null,
            };
            vm.materialTable.reload('materials', {data: vm.materials});
            vm.receivesTable.reload('receives', {data: vm.receives});
            vm.readonly = {};
            vm.sales = {
                salesBranch: null,
                takeBranch: null,
                taxDate: getNowFormatDate(),
                busiType: null,
                transType: null,
                carNo: null,
                boundNo: null,
            };
            vm.showButton = {
                print: false,
                return: false,
                receive: false,
                inline: false,
                cancelReceive: false,
                cancelOrder: false,
                save: false,
                draft: false,
                add: false,
                update: false,
                toDocument: false,
                printDiscount: false,
            };
            vm.disabled = {
                inlineNo: true,
            };
            vm.giftInfoEntities = [];
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.title = "新增开票";
            vm.initEditParams();

            vm.readonly.priceOrder = false;

            vm.showButton.save = true;
            vm.showButton.draft = true;

            Document.initCustomer();
        },
        update: function (docEntry, status) {
            vm.title = "编辑开票";

            vm.initEditParams();

            vm.readonly = {
                cardCode: true,
                salesBranch: true,
                takeBranch: true,
                taxDate: true,
            }

            //loading层
            var index = layer.load(1, {
                shade: [0.5, '#fff']
            });
            httpGet('sap/saleinv/info', {
                docEntry: docEntry, status: status
            }, function (data) {
                var result = data.result;
                vm.showButton.add = true;
                vm.showButton.save = false;

                //按钮显示
                if (result.inlineNo == "失败") {
                    vm.showButton.inline = true;
                }
                //草稿
                if (result.docStatus == "D") {
                    vm.showButton.cancelOrder = true;
                    vm.showButton.draft = true;
                    vm.showButton.toDocument = true;
                }
                //未发货
                if (result.docStatus == "W") {
                    vm.showButton.update = true;
                    vm.showButton.print = true;
                    vm.showButton.printDiscount = true;
                    vm.showButton.receive = true;
                    vm.showButton.cancelReceive = true;
                    vm.showButton.cancelOrder = true;
                    vm.showButton.inline = true;
                }
                //已发货
                if (result.docStatus == "Y") {
                    vm.showButton.print = true;
                    vm.showButton.printDiscount = true;
                    vm.showButton.return = true;
                    vm.showButton.receive = true;
                    vm.showButton.cancelReceive = true;
                    vm.showButton.inline = true;
                }

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

                //处理收货人
                Document.initContacts();

                //销售分支
                Document.initSalesBranch(result.bplid, function () {
                    vm.sales.salesBranch = parseInt(result.bplid);
                    vm.sales.salesBranchTaxId = parseInt(result.taxIdNum);

                    //提货分支
                    Document.initTakeBranch(result.takeBPLId, function () {
                        vm.sales.takeBranch = parseInt(result.takeBPLId);
                        //处理物料
                        Document.initMaterials(function () {
                            vm.buildUpdateResult(result);
                        });
                        if (vm.sales.takeBranch == vm.sales.salesBranch) {
                            vm.showButton.inline = false;
                        }
                        //车牌号
                        Document.initCarNos(function () {
                            vm.sales.carNo = result.carNo;
                            vm.sales.boundNo = result.boundNo;
                            Document.initBoundNos();
                        });
                    });

                });

                vm.initDesOrder(function () {
                    vm.sales.desOrder = result.desOrderNum == null ? null : parseInt(result.desOrderNum);
                    vm.readonly.desOrder = true;
                });

                vm.initPriceOrder(function () {
                    vm.sales.priceOrder = result.priceOrderNum == null ? null : parseInt(result.priceOrderNum);
                    vm.readonly.priceOrder = true;
                });

                vm.initDesOrder();

                vm.reload(false);

                layer.close(index);
            }, function (data) {
                alert(data.msg);
                layer.close(index);
            });
        },
        buildSaveParams: function () {
            var itemInfos = [];
            var index = 1;
            vm.materials.forEach(function (item) {
                if (item.itemCode != null) {
                    itemInfos.push({
                        lineNum: index,
                        itemCode: item.itemCode,
                        itemName: item.itemName,
                        quantity: item.kg,
                        packNum: item.num,
                        price: item.factPrice,
                        currDisc: item.currDisc,
                        isPackage: item.isPackage,
                        salFactor1: item.salFactor1,
                        payAmt: item.amount,
                        realdisc: item.isGiveGD,
                        warehouseCode: item.warehouseCode,
                        discOrder: item.order,
                        stock: item.stock,
                        regSupName: item.regSupName,
                    });
                    index++;
                }
            });
            var params = {
                docEntry: vm.sales.docEntry,
                docNum: vm.sales.docNum,
                cardCode: vm.customer.code,
                cardName: vm.customer.name,
                cntctPrsn: vm.customer.cntctPrsn,
                cntctName: vm.customer.cntctName,
                taxDate: vm.sales.taxDate,
                carNo: vm.sales.carNo,
                boundNo: vm.sales.boundNo,
                bplid: vm.sales.salesBranch,
                taxIdNum: vm.sales.salesBranchTaxId,
                canUseCost: vm.customer.canUseCost,
                inOutAmt: vm.customer.inOutAmt,
                discAmt: vm.customer.discAmt,
                canUseAmt: vm.customer.canUseAmt,
                remark: vm.sales.remark,
                takeBPLId: vm.sales.takeBranch,
                takeNum: vm.sales.takeNum,
                prsDisc: vm.sales.prsDisc,
                maxInOutAmt: vm.sales.maxInOutAmt,
                tranType: vm.sales.transType,
                desOrderNum: vm.sales.desOrder,
                priceOrderNum: vm.sales.priceOrder,
                busiType: vm.sales.busiType,
                driver: vm.customer.driver,

                //物料信息
                itemInfos: itemInfos,

                //折扣信息
                discountEntities: vm.discounts,

                //收款信息
                paymentEntities: vm.receives,

                //giftInfoEntities
                giftInfoEntities: vm.giftInfoEntities,

            };
            return params;
        },
        saveOrUpdate: function () {
            vm.showButton.save = false;
            vm.showButton.toDocument = false;
            var params = vm.buildSaveParams();
            if (!vm.checkMaterials()) {
                return;
            }
            //如果订单状态是草稿
            if (vm.sales.docStatus == 'D') {
                //先调用更新草稿接口,成功后再调用草稿到正式单据接口
                httpPost('sap/saleinv/draftTodoc', params, function (data) {
                    alert("生成销售单成功! " + data.msg);
                    vm.reload(false);
                    var docEntry = data.docEntry;
                    vm.update(docEntry, "W");
                }, function (data) {
                    alert(data.msg);
                    vm.showButton.toDocument = true;
                });

            } else {
                if (vm.sales.docEntry == null) {
                    //调用新增接口
                    httpPost('sap/saleinv/add', params, function (data) {
                        alert("添加单据成功！" + data.msg);
                        vm.reload(false);
                        var docEntry = data.docEntry;
                        vm.update(docEntry, "W");
                    }, function (data) {
                        alert(data.msg);
                        vm.showButton.save = true;
                    });
                } else {
                    //调用更新接口
                    httpPost('sap/saleinv/edit', params, function (data) {
                        alert("更新单据成功！" + data.msg);
                        vm.reload(false);
                    });
                }
            }
        },
        draft: function () {
            var params = vm.buildSaveParams();
            if (vm.sales.docEntry == null) {
                //新增草稿
                httpPost('sap/saleinv/draft', params, function (data) {
                    alert("添加草稿成功！");
                    vm.reload(false);
                    var docEntry = data.docEntry;
                    vm.update(docEntry, "D");
                });
            } else {
                //更新草稿
                httpPost('sap/saleinv/draftEdit', params, function (data) {
                    alert("更新草稿成功！");
                    vm.reload(false);
                });
            }
        },
        print: function () {
            window.open(baseURL + 'modules/sys/pdf.html?docEntry=' + vm.sales.docEntry + "&docType=ORDR");

        },
        printDiscount: function () {
            window.open(baseURL + 'modules/sys/pdf.html?docEntry=' + vm.sales.docEntry + "&type=8&docType=ORDR");
        },
        back: function () {
            vm.showList = true;
        },
        inline: function () {
            httpPostFormData('sap/saleinv/inline', {
                docEntry: vm.sales.docEntry,
            }, function (data) {
                alert('生成内联单[' + data.inlineNo + ']成功！');
                vm.disabled.inlineNo = false;
                vm.sales.inlineNo = data.inlineNo;
                vm.disabled.inlineNo = true;
                vm.reload(false);
            })
        },
        addMaterial: function () {
            Document.addMaterial();
        },
        /**
         * 随单折（增）
         */
        discount: function () {
            var params = vm.buildSaveParams();
            params.isOrder = 'Y';
            if (!vm.checkMaterials()) {
                return;
            }
            httpPost('sap/senditem/give', params, function (data) {
                alert('更新随单折信息成功！');
                var result = data.give;
                vm.buildUpdateResult(result);
                Document.calcArrear();
            })
        },
        checkMaterials: function () {
            var result = true;
            var itemCodes = "";
            vm.materials.forEach(function (item) {
                if (item.itemCode != null && (item.num == null || item.num == '')) {
                    itemCodes += item.itemCode + " "
                    result = false;
                }
            });
            if (!result) {
                alert("请输入物料：" + itemCodes + "的包数！");
            }
            return result;
        },
        showTab1: function () {
            vm.tabShow = true;
        },
        showTab2: function () {
            vm.tabShow = false;
        },
        getMaterialById: function () {
        },
        reload: function (showlist) {
            if (showlist == null) {
                vm.showList = true;
            } else {
                vm.showList = showlist;
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.search,
                page: page
            }).trigger("reloadGrid");
        },
        calcAllMaterials: function () {
            Document.calcAllMaterials();
        },
        filterCustomer: function (val) {
            Document.filterCustomer(val);
        },
        selectedCustomerSearch: function (val) {
            var item = Document.filterCustomerById(val);
            vm.search.CustomerNo = item.id;
        },
        selectedCntctPrsn: function (val) {
            vm.calcAllMaterials();
            vm.contacts.forEach(function (contact) {
                if (contact.cntctPrsn == vm.customer.cntctPrsn) {
                    vm.customer.cntctPhone = contact.cntctPhone;
                }
            });
        },
        selectedCustomer: function (val) {
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
            Document.initContacts(function (r) {
                vm.customer.cntctPrsn = item.cntctPrsn;
                vm.customer.cntctName = item.cntctName;
                r.forEach(function (contact) {
                    if (contact.cntctPrsn == vm.customer.cntctPrsn) {
                        vm.customer.cntctPhone = contact.cntctPhone;
                    }
                });
            });

            //销售分支
            Document.initSalesBranch(null, function (data) {
                if (vm.sales.salesBranch != null) {
                    vm.selectedSalesBranch();
                } else {
                    if (data != null && data.length == 1) {
                        vm.sales.salesBranch = data[0].bplid;
                        vm.selectedSalesBranch();
                    }
                }
            });


        },
        selectedCarNo: function () {
            Document.initBoundNos();
        },
        selectedSalesBranch: function () {
            httpGet('sap/customer/amt', {
                CardCode: vm.customer.code,
                BPLId: vm.sales.salesBranch,
            }, function (data) {
                if (data.amt != null && data.amt.length > 0) {
                    vm.customer.inOutAmt = data.amt[0].actBalance;
                    vm.customer.discAmt = data.amt[0].discAmt;
                    vm.customer.canUseAmt = data.amt[0].custAmt;
                }
            });

            vm.salesBranches.forEach(function (item) {
                if (item.bplid == vm.sales.salesBranch) {
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
            //车牌号
            Document.initCarNos();
        },
        selectedTakeBranch: function () {
            //车牌号
            Document.initCarNos();
            //处理物料
            Document.initMaterials();
        },
        changeDate: function () {
            Document.initMaterials();
            Document.initCarNos();
        },

        cancelOrder: function () {
            httpPostFormData('sap/saleinv/cancel', {
                docEntry: vm.sales.docEntry,
                status: vm.sales.docStatus,
            }, function (data) {
                alert('取消成功！');
                vm.reload(false);
                vm.update(vm.sales.docEntry, vm.sales.docStatus);
            });
        },
        receive: function () {
            httpPostFormData('sap/saleinv/inpay', {docEntry: vm.sales.docEntry}, function (data) {
                alert("收款成功！");
                vm.reload(false);
                vm.update(vm.sales.docEntry, vm.sales.docStatus);
            });
        },
        receiveCancel: function () {
            httpPostFormData('sap/saleinv/celpay ', {docEntry: vm.sales.docEntry}, function (data) {
                alert("取消收款成功！");
                vm.reload(false);
                vm.update(vm.sales.docEntry, vm.sales.docStatus);
            });
        },
        returnGoods: function () {
            //构造退货参数
            var params = Document.buildReturnParams();
            //这里构造的参数是根据销售订单构造的，所以
            //1.将源单编号设置为销售单号
            params.srcNum = params.srcNum;
            //2.将销售单号和销售entry清空，为了新增退货单
            params.docEntry = params.docNum = null;
            Document.salesReturn(params, function () {
                parent.location.href = baseURL + "index.html#modules/sys/return.html"
            })
        },
        /**
         * 初始化订料单
         */
        initDesOrder: function (callback) {
            httpGetInfo('sys/droplist/desOrderDropList', {
                param: JSON.stringify({param1: vm.customer.code, param2: vm.sales.salesBranch})
            }, function (r) {
                vm.desOrders = r.data;
                if (typeof callback != 'undefined') {
                    callback();
                }
            });
        },
        /**
         * 初始化保价单
         */
        initPriceOrder: function (callback) {
            httpGetInfo('sys/droplist/priceOrderDropList', {
                param: JSON.stringify({param1: vm.customer.code, param2: vm.sales.salesBranch})
            }, function (r) {
                vm.priceOrders = r.data;
                if (typeof callback != 'undefined') {
                    callback();
                }
            });
        },
        /**
         * 选择订料单
         */
        selectedDesOrder: function () {
            if (vm.sales.desOrder != null && vm.sales.desOrder != '') {
                vm.readonly.priceOrder = true;
                Document.initMaterials();
            }
        },
        /**
         * 清空订料单
         */
        clearDesOrder: function () {
            vm.readonly.priceOrder = false;
            Document.initMaterials();
        },
        /**
         * 选择保价单
         */
        selectedPriceOrder: function () {
            if (vm.sales.priceOrder != null && vm.sales.priceOrder != '') {
                vm.readonly.desOrder = true;
                Document.initMaterials();
            }
        },
        /**
         * 清空保价单
         */
        clearPriceOrder: function () {
            vm.readonly.desOrder = false;
            Document.initMaterials();
        },
        buildUpdateResult: function (result) {
            vm.customer.cntctPrsn = result.cntctPrsn;
            vm.customer.cntctName = result.cntctName;
            var driverRst = parseInt(result.driver);
            vm.customer.driver = result.driver != 'undefined' && result.driver !='' && result.driver != null ? driverRst : 0;
            vm.contacts.forEach(function (contact) {
                if (vm.customer.cntctPrsn == contact.cntctPrsn) {
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
            if (items != null) {
                items.forEach(function (item) {
                    vm.materials.push({
                        itemCode: item.itemCode,
                        itemName: item.itemName,
                        kg: item.quantity,
                        num: item.packNum,
                        factPrice: item.price,
                        currDisc: item.currDisc,
                        factAmount: Multiply(item.quantity, item.price),
                        isPackage: item.isPackage,
                        salFactor1: item.salFactor1,
                        amount: Multiply(item.quantity, item.price),
                        isGiveGD: item.realdisc,
                        whsCode: item.warehouseCode,
                        order: item.discOrder,
                        originalQuantity: item.quantity,
                        regSupName: item.regSupName,
                        totalDisc: Multiply(item.packNum, item.currDisc),
                        useQty: item.useQty,
                        ableQty: item.ableQty,
                        packagePrice:Multiply(item.salFactor1, item.price),
                    });
                });
            }

            vm.materialTable.reload('materials', {data: vm.materials});
            vm.receivesTable.reload('receives', {data: vm.receives});
        },
        takeGoods(){
            var ids = getSelectedRows("docentry");
            if(ids == null || ids.length < 1){
                return;
            }
            httpPost('sap/saleinv/takegoods',ids,function(data){
                alert(data.msg);
                vm.reload(true);
            })
        },
        cancelTakeGoods(){
            var ids = getSelectedRows("docentry");
            if(ids == null || ids.length < 1){
                return;
            }
            httpPost('sap/saleinv/canceltake',ids,function(data){
                alert(data.msg);
                vm.reload(true);
            })
        },
    },
    mounted: function () {

        Document.vm = this;
        Document.initCustomer();
        Document.vm.drivers = Document.initDriverList();
        /**
         * 单据状态
         */
        Document.initDocStatus('doc_status');

    },
});
