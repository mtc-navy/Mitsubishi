var Document = {
    vm: null,

    /**
     * 单据状态
     */
    initDocStatus: function (doctype) {
        httpGet('sys/dict/type', {
            type: doctype, sidx: "order_num", order: "asc"
        }, function (data) {
            vm.docStatusList = data.page.list;
        });
    },

    /**
     * 获取客户信息
     */
    initCustomer: function (value) {
        httpGet('sap/customer/list', {
            DataType: 'A',
            FilterValue:value,
        }, function (result) {
            if (result.code != 0) {
                return;
            }
            var data = [];
            result.customerList.forEach(function (item) {
                data.push({
                    id: item.cardCode,
                    name: item.cardCode + " " + item.cardName + " " + item.shortName,
                    show: item.cardName,
                    canUseCost: item.canUseCost, //可用额度
                    inOutAmt: item.inOutAmt, //往来款
                    discAmt: item.discAmt,//折扣余额
                    canUseAmt: item.canUseAmt,//可用金额
                    cntctPrsn: item.cntctPrsn,
                    cntctName: item.cntctName,
                });
            });
            vm.remoteCustomers = data;
        });
    },

    /**
     * 收货人
     */
    initContacts: function (callback) {
        vm.contacts = [];
        vm.customer.cntctPrsn = null;
        vm.customer.cntctName = null;
        vm.customer.cntctPhone = null;
        //处理收货人
        httpGet('sap/cntctPrsn/list?CardCode=' + vm.customer.code, null, function (data) {
            vm.contacts = data.cntctPrsn;
            if (typeof callback != 'undefined') {
                callback(data.cntctPrsn);
            }
        });
    },

    /**
     * 过磅单号
     */
    initBoundNos: function () {
        vm.boundNos = [];
        vm.sales.boundNo = null;
        httpGet('sap/customer/bound', {
            CardCode: vm.customer.code,
            CarNo: vm.sales.carNo,
            TaxDate: vm.sales.taxDate,
        }, function (data) {
            vm.boundNos = data.bound;
            if (data.bound != null && data.bound.length > 0) {
                vm.sales.boundNo = data.bound[0].boundNo;
            }
        });
    },

    /**
     * 分支
     */
    initBranches: function (cardcode, type, filterValue, callback) {
        httpGet('sap/bpl/list', {
            CardCode: cardcode, DataType: type, FilterValue: filterValue
        }, function (data) {
            callback(data.bpl);
        });
    },

    /**
     * 销售分支
     */
    initSalesBranch: function (branchId, callback) {
        vm.salesBranches = [];
        vm.sales.salesBranch = null;
        vm.sales.salesBranchTaxId = null;
        this.initBranches(vm.customer.code, 'S', branchId, function (result) {
            vm.salesBranches = result;
            vm.salesBranches.forEach(function(item){
                if(item.dflFlg=='Y'){
                    vm.sales.salesBranch=item.bplid;
                }
            });
            if (typeof callback != 'undefined') {
                callback(result);
            }
        });
    },
    /**
     * 提货分支
     */
    initTakeBranch: function (branchId, callback) {
        vm.takeBranches = [];
        vm.sales.takeBranch = null;
        vm.sales.takeBranchTaxId = null;
        this.initBranches(vm.customer.code, 'T', branchId, function (result) {
            vm.takeBranches = result;
            if (typeof callback != 'undefined') {
                callback(result);
            }
        });
    },

    /**
     * 处理物料
     */
    initMaterials: function (callback) {
        vm.remoteMaterials = [];
        vm.remoteMaterialsMap = {};
        httpGet('sap/item/list', {
            CardCode: vm.customer.code,
            BPLId: vm.sales.salesBranch,
            TakeBPLId:vm.sales.takeBranch,
            DocDate:vm.sales.taxDate,
            DesOrder:vm.sales.desOrder,
            PriceOrder:vm.sales.priceOrder,
        }, function (data) {
            vm.remoteMaterials = data.item;
            vm.remoteMaterials.forEach(function (item) {
                item.value = item.itemCode + " " + item.itemName + " " + item.shortName;
                vm.remoteMaterialsMap[item.itemCode] = item;
            });

            if (vm.sales.srcEntry == null && vm.sales.docEntry == null) {
                vm.materials = [];
                for (var i = 0; i < 5; i++) {
                    var material = {cardCode: "", cardName: "", temp1: "", temp2: ""};
                    vm.materials.push(material);
                    vm.materialTable.reload('materials', {data: vm.materials});
                }
            }
            if(typeof callback != 'undefined'){
                callback();
            }
        });
    },
    initMaterialsReturn: function (items) {
        vm.remoteMaterials = [];
        vm.remoteMaterialsMap = {};

        items.forEach(function(item){
            vm.remoteMaterials.push({
                value : item.itemCode + " " + item.itemName,
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
                isGiveGD:item.realdisc==null?'N':item.realdisc,
                whsCode:item.warehouseCode,
                order:item.discOrder,
            });
            vm.remoteMaterialsMap[item.itemCode] = item;
            if(vm.sales.docEntry == null) {
                vm.materials = [];
                for (var i = 0; i < 5; i++) {
                    var material = {cardCode: "", cardName: "", temp1: "", temp2: ""};
                    vm.materials.push(material);
                    vm.materialTable.reload('materials', {data: vm.materials});
                }
            }
        });

    },
    addMaterial: function () {
        if (vm.customer.code == null) {
            alert('请输入客户代码!');
            return;
        }
        if (vm.sales.salesBranch == null) {
            alert('请选择销售分支!');
            return;
        }
        if (vm.sales.taxDate == null) {
            alert('请输入单据日期!');
            return;
        }

        var material = {cardCode: "", cardName: "", temp1: "", temp2: ""};
        vm.materials.push(material);
        vm.materialTable.reload('materials', {data: vm.materials});
        vm.sales.prsDisc = false;
    },

    /**
     * 车牌号
     */
    initCarNos: function (callback) {
        vm.carNos = [];
        vm.sales.carNo = null;
        vm.sales.boundNo = null;
        httpGet('sap/customer/car', {
            CardCode: vm.customer.code,
            TaxDate: vm.sales.taxDate,
            SalesBranch:vm.sales.salesBranch,
            TakeBranch:vm.sales.takeBranch,
        }, function (data) {
            vm.carNos = data.car;
            if(typeof callback != 'undefined'){
                callback();
            }
        });
    },

    calcAllMaterials: function () {
        /*var itemCodes = [];
        vm.materials.forEach(function (item) {
            itemCodes.push(item.itemCode);
        });
        if (itemCodes.length > 0) {
            httpGet("sap/item/priceAll", {
                CardCode: vm.customer.code,
                itemCodes: itemCodes.toString(),
                DocDate: vm.sales.taxDate,
                BPLId: vm.sales.salesBranch,
            }, function (data) {
                vm.materials.forEach(function (item, index) {
                    if (item.itemCode != null) {
                        item.factPrice = data.price[item.itemCode].price == null ? 0 : data.price[item.itemCode].price;
                        if(item.isGiveGD == 'Y'){
                            item.currDisc = 0
                        }else{
                            item.currDisc = data.price[item.itemCode].currDisc;
                        }
                        item.isPackage = data.price[item.itemCode].isPackage;
                        item.salFactor1 = data.price[item.itemCode].salFactor1;
                        item.whsCode = data.price[item.itemCode].whsCode;
                        item.whsName = data.price[item.itemCode].whsName;
                        item.regSupName = data.price[item.itemCode].regSupName;
                        Document.calcMaterial(index, item);
                    }
                });
                vm.materialTable.reload('materials', {data: vm.materials});
            })
        }*/
    },

    /**
     * 计算物料价格
     */
    calcMaterial: function (index, material) {

        if (material.num != null && material.kg == null) {
            //标包
            if (material.isPackage == 'Y') {
                material.kg = Multiply(material.num, material.salFactor1);
            } else {
                material.kg = material.num;
            }
        }

        if (material.factPrice != null && material.kg != null) {
            //包价(单价*单包重)
            material.packagePrice = Multiply(material.factPrice,material.salFactor1);
            //出厂价（单价*数量）
            material.factAmount = Multiply(material.factPrice, material.kg);
            //折扣总金额（折扣金额*包数）
            material.totalDisc = Multiply(material.num,material.currDisc);
            //最终价(出厂价-折扣总金额)
            material.amount = material.factAmount;
        }
        vm.materials[index] = material;
        Document.calcArrear();
    },

    /**
     * 计算本次欠款
     * @param val
     */
    calcArrear: function () {
        var amount = 0;
        var factAmount = 0; //出厂金额
        vm.materials.forEach(function (item) {
            if (item.amount != null) {
                amount = Add(amount, item.amount);
                factAmount = Add(factAmount,item.factAmount);
            }
        });

        if (vm.receives != null) {
            vm.receives.forEach(function (item) {
                amount = Minus(amount, item.payAmt);
            });
        }

        if (vm.discounts != null && vm.sales.srcEntry == null) {
            vm.discounts.forEach(function (item) {
                amount = Minus(amount, item.thisUserAmt);
            });
        }

        vm.sales.baseDiscountDisable = false;
        vm.sales.baseDiscount = Minus(factAmount,amount);
        vm.sales.baseDiscountDisable = true;

        if(vm.sales.discount != null){
            amount = Minus(amount, vm.sales.discount);
        }

        if(vm.sales.cash != null){
            amount = Minus(amount, vm.sales.cash);
        }

        if(vm.sales.fanjiAmount != null){
            amount = Minus(amount, vm.sales.fanjiAmount);
        }

        vm.sales.thisArrears = parseFloat(amount).toFixed(2);
        $("#thisArrears").val(vm.sales.thisArrears);
    },

    filterCustomer: function (val) {
        /*vm.remoteCustomersCopy = vm.remoteCustomers;
        var filter = [];
        vm.remoteCustomersCopy.forEach(function (customer) {
            if (customer.name.toUpperCase().indexOf(val.toUpperCase()) != -1) {
                filter.push(customer);
            }
        })
        vm.remoteCustomersCopy = filter;*/
        this.initCustomer(val.toUpperCase());
    },
    filterCustomerById: function (id) {
        var item = null;
        vm.remoteCustomers.forEach(function (customer) {
            if (customer.id == id) {
                item = customer;
            }
        })
        return item;
    },
    /**
     *  构造退货参数
     */
    buildReturnParams: function () {
        var itemInfos = [];
        var index = 1;
        vm.materials.forEach(function (item) {
            if (item.itemCode != null && item.itemCode != "") {
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
            takeBPLId: vm.sales.takeBranch,
            //物料信息
            itemInfos: itemInfos,
            srcNum: vm.sales.srcNum,
            takeNum: vm.sales.takeNum,
            discount:vm.sales.discount,
            baseDiscount:vm.sales.baseDiscount,
            fanjiAmount:vm.sales.fanjiAmount,
            cash:vm.sales.cash,
            thisArrears:vm.sales.thisArrears,
            srcEntry : vm.sales.srcEntry,
            prsDisc : vm.sales.prsDisc,
            remark : vm.sales.remark,
            tranType: vm.sales.transType,
            busiType: vm.sales.busiType,
            driver: vm.customer.driver,
    };
        return params;
    },
    /**
     * 退货
     */
    salesReturn: function (params, callback) {
        if (!this.verifySaveForm()) {
            return;
        }
        if (params.docEntry == null) {
            //调用新增接口
            httpPost('sap/salertn/save', params, function (data) {
                alert("生成退货单成功！", function () {
                    if (typeof callback != 'undefined') {
                        callback(data);
                    }
                });
            });
        } else {
            //调用新增接口
            httpPost('sap/salertn/update', params, function (data) {
                alert("更新退货单成功！", function () {
                    if (typeof callback != 'undefined') {
                        callback(data);
                    }
                });
            });
        }
    },
    verifySaveForm: function () {
        if (vm.customer.code == null) {
            alert("请输入客户代码！");
            return false;
        }
        if (vm.sales.salesBranch == null) {
            alert("请选择销售分支！");
            return false;
        }
        if(!vm.checkMaterials()){
            return false;
        }
        return true;
    },
    /**
     * 部门tree
     */
    initDeptTree: function (id,OrgLevel,FitSale) {
        //加载菜单树
        $.get(baseURL + "sys/droplist/treeList",{OrgLevel:OrgLevel,FitSale:FitSale}, function (r) {
            ztree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, r.treeList);
            //展开所有节点
            ztree.expandAll(true);
            var node = ztree.getNodeByParam('code', 0);//获取id为1的点
            ztree.selectNode(node);//选择点
            ztree.setting.callback.onClick(null, ztree.setting.treeId, node);//调用事件


           /* var node = ztree.getNodeByParam("deptId", id);
            ztree.selectNode(node);*/
            //Document.fillterDeptTree(ztree);
        });
    },
    fillterDeptTree: function (treeObj) {
        var treeObj = $.fn.zTree.getZTreeObj("#deptTree");
        //获得树形图对象
        var nodeList = treeObj.getNodes();
        for (var i = 0; i < nodeList.length; i++) {
            treeObj.expandNode(nodeList[i], true, false, true);
            var nodespan = nodeList[i].children;
            for (var j = 0; j < nodespan.length; j++) {
                treeObj.removeChildNodes(nodespan[j]);
            }

        }
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
     * 预算
     */
    initVerList: function () {
        //预算版本
        var obj = [];
        var url = "sys/droplist/verList";
        $.ajax({
            type: "GET",
            url: baseURL + url,
            contentType: "application/json",
            data: {},
            async: false,
            success: function (r) {
                if (r.code === 0) {
                    obj = r.verList;
                } else {
                    alert(r.msg);
                }
            }
        });
        return obj;

    },
    /**
     * 事业部
     */
    initDivisionList:function(orgLevel,callback){
        var obj = [];
        var url = "sys/droplist/divisionList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'A'},function(r){
            if(typeof callback != 'undefined'){
                callback(r.divisionList);
            }
        });
    },
    /**
     * 大区
     */
    initComareList:function(orgLevel,callback){
        var url = "sys/droplist/comareList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'F'},function(r){
            if(typeof callback != 'undefined'){
                callback(r.comareList);
            }
        });
    },
    /**
     * 公司
     */
    initCompList: function (orgLevel,callback) {
        var url = "sys/droplist/compList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'B'},function(r){
            if(typeof callback != 'undefined'){
                callback(r.compList);
            }
        });

    },


    /**
     * 部门
     */
    initDeptList: function (orgLevel,BPLId,callback) {

        var url = "sys/droplist/deptList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'C',BPLId: BPLId},function(r){
            if(typeof callback != 'undefined'){
                callback(r.deptList);
            }
        });
    },
    /**
     * 岗位
     */
    initPostCodeList: function (BPLId, DeptCode) {

        var obj = [];
        var url = "sys/droplist/postCodeList";
        $.ajax({
            type: "GET",
            url: baseURL + url,
            contentType: "application/json",
            data: {BPLId: BPLId, DeptCode: DeptCode},
            async: false,
            success: function (r) {
                if (r.code === 0) {
                    obj = r.postCodeList;
                } else {
                    alert(r.msg);
                }
            }
        });
        return obj;
    },
    /**
     * 经营单元
     */
    initBuziUnitList: function (orgLevel,DeptCode,callback) {

        var url = "sys/droplist/buziUnitList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'D',DeptCode: DeptCode},function(r){
            if(typeof callback != 'undefined'){
                callback(r.buziUnitList);
            }
        });
    },
    /**
     * 销售单元
     */
    initSalesUnitList: function (orgLevel,BuziCode,callback) {
        var url = "sys/droplist/salesUnitList";
        httpGetInfo(url,{orgLevel:orgLevel,type:'E', BuziCode: BuziCode},function(r){
            if(typeof callback != 'undefined'){
                callback(r.salesUnitList);
            }
        });
    },
    /**
     * 状态
     */
    initStatusList: function () {
        var obj = [];
        obj.push({code: 'D', name: '草稿'});
        obj.push({code: 'S', name: '提交审核'});
        obj.push({code: 'P', name: '审核通过'});
        obj.push({code: 'R', name: '拒绝'});
        obj.push({code: 'C', name: '关闭'});
        return obj;
    },
    /**
     * 费用科目
     */
    initExpAcctList: function (sBPLId, sDeptCode,sPostCode, sType,callback) {
        var url = "sys/droplist/expAcctList";
        httpGetInfo(url,{ BPLId: sBPLId, DeptCode: sDeptCode,PostCode:sPostCode,Type: sType},function(r){
            if(typeof callback != 'undefined'){
                callback(r.expAcctList)
            }
        });
    },
    /**
     * 产品线
     */
    initProdList: function (orgLevel,callback) {
        var url = "sys/droplist/prodList";
        httpGetInfo(url,{ orgLevel:orgLevel,type:'P'},function(r){
            if(typeof callback != 'undefined'){
                callback(r.prodList);
            }
        });
    },
    /**
     * 物料列表
     */
    initItemList: function (sBPLId, sDataType,sSalesUnit,verCode,callback) {
        var url = "sys/droplist/itemList";
        httpGetInfo(url,{ BPLId: sBPLId, DataType: sDataType,SalesUnit:sSalesUnit,verCode:verCode},function(r){
            if(typeof callback != 'undefined'){
                callback(r.itemList)
            }
        });
    },
    /**
     * 控制页面按钮状态
     */
    showBtnByStatus: function (docstatus){
        vm.showButton.postapprove= false;
        vm.showButton.approve= false;
        vm.showButton.reject= false;
        vm.showButton.close= false;
        vm.showButton.cancelclose= false;
        //草稿:显示提交审核
        if(docstatus=="D")
        {
            vm.showButton.postapprove=true;
        }
        else  if(docstatus=="S")
        { //提交审核：显示审核，拒绝
            vm.showButton.approve= true;
            vm.showButton.reject= true;
        }
        else  if(docstatus=="P")
        { //审核：显示拒绝
            vm.showButton.reject= true;
        }
        else  if(docstatus=="R")
        {//拒绝：显示关闭
            vm.showButton.close= true;
            vm.showButton.postapprove= true;
        }
        else  if(docstatus=="C")
        {  //关闭：显示取消关闭
            vm.showButton.cancelclose= true;
        }
        //只有草稿和拒绝的才能进行修改
        vm.showButton.save=false;
        vm.showButton.copyfrom=false;
        vm.showButton.genfrom=false;
        if(docstatus=="D"
            || docstatus=="R"){
            if(vm.permission.update){
                vm.showButton.save =true;
                vm.showButton.genfrom=true;
            }
            vm.showButton.copyfrom=true;
        }
    },
    /**
     * 设置tree和列表的宽度
     */
    showTreeData: function () {
        vm.showTree = !vm.showTree;
        var rightDiv = document.getElementById("right");
        if (vm.showTree) {
            rightDiv.className = "col-md-9";
        }
        else {
            rightDiv.className = "col-md-12";
        }
        var w = rightDiv.clientWidth || rightDiv.offsetWidth;
        if(w>100) $("#jqGrid").setGridWidth(w - 30);
        $("#left").animate({width: 'toggle'});
    },
    /**
     * 获取列表页表格宽度
     */
    getGridWidth:function () {
        var divgrid = document.getElementById("divgrid");
        var w = divgrid.clientWidth || divgrid.offsetWidth;
        if(w)vm.grid.width = w;
    },
    /**
     * 重置列表页表格宽度
     */
    resetGridWidth:function () {
        var divgrid = document.getElementById("divgrid");
        var w = divgrid.clientWidth || divgrid.offsetWidth;
        if(!w && vm.grid.width) {
            document.getElementById("divgrid").style.width = vm.grid.width + 'px';
            $("#jqGrid").setGridWidth(vm.grid.width );
        }

    },
    /**
     * 计算行合计
     */
    calMonthTotal:function () {
        $.each(vm.Buds, function (index, bud) {
            var monthTotal=0;
            $.each(bud, function (key, value) {
                if (key.lastIndexOf("month") >= 0) {
                    if (value) {
                        if (isNumber(value)) {
                            monthTotal += parseFloat(value);
                        }
                    }
                }
            });
            bud.total=monthTotal;
            vm.Buds[index] = bud;
        });
    },
    exportDetail:function(url,param){
        httpGetInfo(url,{
            pageSize:100000000,
            pageIndex:1,
            param:param
        },function(r){
            var fields = r.page.fields;
            var list = r.page.list;
            var datas = [];
            list.forEach(function (item) {
                var data = {};
                fields.forEach(function(field){
                    data[field] = item[field];
                })
                datas.push(data);
            });
            console.log(datas);
            layui.use('table', function(){
                var table = layui.table;
                table.exportFile(fields,datas,'csv');
            });
        })
    },
    reloadMaterialTable:function(){
        vm.materialTable.reload('materials',{data:vm.materials});
        for(var i=0;i<vm.materials.length;i++){
            if(vm.materials[i].itemCode != null && vm.materials[i].itemCode != ''){
                this.createWarehouseSelect(i,vm.sales.salesBranch,vm.materials[i].itemCode);
            }
        }
    },
    createWarehouseSelect:function(index,salesBranch,itemCode){
        var warehouse = $("#warehouse_"+index);
        if(salesBranch!=null && itemCode !=null && warehouse != null && typeof warehouse != 'undefined' && warehouse.length > 0){
            if(vm.warehouseMap[itemCode] != null){
                vm.warehouseMap[itemCode].forEach(function(item){
                    warehouse.append("<option value='"+item.id+"'>"+item.code+"</option>");
                });
            }else{
                httpGet('sys/droplist/returnWhsDropList',{
                    param:JSON.stringify({param1:salesBranch,param2:itemCode})
                },function(data){
                    var result = data.data;
                    vm.warehouseMap[itemCode] = result;
                    result.forEach(function(item){
                        warehouse.append("<option value='"+item.id+"'>"+item.code+"</option>");
                    });
                    if(result.length>0){
                        vm.materials[index].warehouseCode = result[0].id;
                    }
                })
            }
            if(vm.materials[index].warehouseCode != null){
                warehouse.val(vm.materials[index].warehouseCode);
            }else{
                if(vm.warehouseMap[itemCode] != null){
                    vm.materials[index].warehouseCode = vm.warehouseMap[itemCode][0].id;
                }
            }
        }
    },
}
/**
 * 部门tree设置
 */
var deptTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "code",
            pIdKey: "parentCode",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {

    vm.treeNode=treeNode;
    vm.query();
};