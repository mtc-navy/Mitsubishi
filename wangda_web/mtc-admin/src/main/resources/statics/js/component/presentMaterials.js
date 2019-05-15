/**
 * 赠料按钮
 */
var presentMaterialsComponent={
    template:`
        <span>
            <a class="btn btn-default" @click="presentMaterials">赠料选择</a>
             <!-- 赠料信息 -->
            <div id="presentMaterialsDiv" style="display: none">
                <table class="layui-hide" id="presentMaterials" lay-filter="presentMaterials"></table>
                <div class="form-group">
                    <div class="col-sm-3">
                        <input type="button" class="btn btn-primary" :disabled="choosePresentBtnDisabled" @click="choosePresentMaterials" value="确定"/> &nbsp;&nbsp;
                        <input type="button" class="btn btn-warning" @click="closePresentMaterialsWindow" value="取消"/> &nbsp;&nbsp;
                    </div>
                </div>
            </div>    
        </span>    
    `,

    data: function () {
        return {
            choosePresentBtnDisabled:false,
            choosePresentMaterialsWindow:null,
            presentMaterialsTable:null,
        }
    },
    methods:{
        presentMaterials:function(){

            if(vm.customer.code == null){
                alert('请输入客户代码!');
                return;
            }
            if(vm.sales.salesBranch == null){
                alert('请选择销售分支!');
                return;
            }
            if(vm.sales.taxDate == null){
                alert('请输入单据日期!');
                return;
            }
            vm.choosePresentBtnDisabled = false;
            choosePresentMaterialsWindow = layer.open({
                type: 1,
                title: false, //不显示标题
                skin: 'layui-layer-rim', //加上边框
                area: ['1100px','580px'],
                shadeClose: true,
                scrollbar: false,
                closeBtn:0,
                content: $('#presentMaterialsDiv'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            });
            var index = 0;
            vm.remotePresentMaterials.forEach(function(present){
                vm.materials.forEach(function(item){
                    if(present.order == item.order){
                        present.LAY_CHECKED = true;
                        present.useQty = item.num;
                        item.presentIndex = index;
                    }
                })
                index++;
            });
            vm.presentMaterialsTable.reload('presentMaterials',{data:vm.remotePresentMaterials});
        },
        choosePresentMaterials:function(){

            var checkStatus = vm.presentMaterialsTable.checkStatus('presentMaterials')
            var data = checkStatus.data;

            //删除原来的赠料数据
            var newData = [];
            vm.materials.forEach(function(item){
                if(item.itemCode != null){
                    if(item.isGiveGD != 'Y'){
                        newData.push(item);
                    }else{
                        var remote = vm.remotePresentMaterials[item.presentIndex];
                        remote.LAY_CHECKED = falsepriceAll
                        remote.useQty = 0;
                    }
                }
            })

            var oldLength = vm.materials.length;
            vm.materials = newData;

            data.forEach(function(item){
                vm.materials.push({
                    itemCode : item.itemCode,
                    itemName : item.itemName,
                    isGiveGD : 'Y',
                    num:item.useQty,
                    order:item.order,
                    discPack:item.discPack,
                    presentIndex:item.index,
                    stock:vm.remoteMaterialsMap[item.itemCode].stock,
                });
                var remote = vm.remotePresentMaterials[item.index];
                remote.LAY_CHECKED = true;
                remote.useQty = item.useQty;
            });

            var diff = oldLength - newData.length;
            for(var i=0;i<diff;i++){
                var material = {cardCode:"",cardName:""};
                vm.materials.push(material)
            }

            Document.calcAllMaterials();

            vm.materialTable.reload('materials',{data:vm.materials});
            layer.close(choosePresentMaterialsWindow);
        },
        closePresentMaterialsWindow:function(){
            vm.remotePresentMaterials.forEach(function(item){
                item.useQty=0;
                item.LAY_CHECKED=false;
            });
            vm.materials.forEach(function(item){
                if(item.isGiveGD=='Y'){
                    vm.remotePresentMaterials[item.presentIndex].useQty = item.num;
                    vm.remotePresentMaterials[item.presentIndex].LAY_CHECKED=true;
                }
            })
            layer.close(choosePresentMaterialsWindow);
        },
        /**
         * 赠料
         */
        init:function(){
            vm.remotePresentMaterials = [];
            httpGet('sap/senditem/send',{
                CardCode:vm.customer.code,
                BPLId:vm.sales.salesBranch,
            },function(data){
                vm.remotePresentMaterials= data.send;
                var index=0;
                vm.remotePresentMaterials.forEach(function (item) {
                    item.index = index;
                    index++;
                });
                vm.presentMaterialsTable.reload('presentMaterials',{data:vm.remotePresentMaterials});
            });

        },
    },
    mounted:function(){
        /**
         * 赠料信息
         */
        layui.use('table', function(){
            vm.presentMaterialsTable = layui.table;
            vm.presentMaterialsTable.render({
                elem: '#presentMaterials'
                ,cellMinWidth: 80
                ,cols:presentMaterialsCols
                ,data:null
                ,even: false
                ,limit:1000
                ,height: '510px'
                ,id:'presentMaterials'
                ,totalRow: true
            });
            vm.presentMaterialsTable.on('edit(presentMaterials)', function(obj){
                vm.choosePresentBtnDisabled=false;
                var index = obj.tr.attr('data-index');
                var material = obj.data;

                if(obj.field=='useQty'){
                    var oldValue = $(this).prev().text();
                    if(!validator.isInt(material.useQty) || material.useQty <0){
                        vm.choosePresentBtnDisabled=true;
                        alert('包数请输入整数!');
                        $(this).val(oldValue);
                        material.useQty = oldValue;
                    }
                    if(material.useQty > material.discPack){
                        vm.choosePresentBtnDisabled=true;
                        alert('使用赠包数不能大于可用赠包数!');
                        $(this).val(oldValue);
                        material.useQty = oldValue;
                    }
                    vm.remotePresentMaterials[index].useQty = material.useQty;
                    vm.remotePresentMaterials[index].LAY_CHECKED=true;
                    vm.presentMaterialsTable.reload('presentMaterials',{data: vm.remotePresentMaterials});
                    setTimeout(function () {
                        vm.choosePresentBtnDisabled=false;
                    }, 1000);
                }
            });

        });
    }
};

//赠料信息
var presentMaterialsCols=[[
    {type:'checkbox'}
    ,{field: 'order', title: '唯一序号',width:100,style:'background-color:#eee',totalRowText:'合计'}
    ,{field: 'itemCode', title: '物料编码',width:100,style:'background-color:#eee'}
    ,{field: 'itemName', title: '物料名称',width:110,style:'background-color:#eee'}
    ,{field: 'sourceDN', title: '赠包类型',width:120,style:'background-color:#eee'}
    ,{field: 'discCode', title: '折扣项代码',width:100,style:'background-color:#eee'}
    ,{field: 'disctName', title: '折扣项名称',width:120,style:'background-color:#eee'}
    ,{field: 'discPack', title: '可用赠包数',width:100,style:'background-color:#eee',totalRow:true}
    ,{field: 'openCreQty', title: '已承诺赠包数',width:120,style:'background-color:#eee',totalRow:true}
    ,{field: 'useQty', title: '使用赠包数',edit: 'text',width:100,totalRow:true}
    ,{field: 'stockName', title: '仓库',style:'background-color:#eee'}
]];