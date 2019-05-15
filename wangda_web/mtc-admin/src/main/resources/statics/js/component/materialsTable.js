var materialsComponent={
    template:`
        <div>
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#">物料信息</a></li>
            </ul>
            <table class="layui-hide" id="materials" lay-filter="materials"></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>
            
            <script type="text/html" id="chooseMaterial">
                {{chooseMaterialHtml}}
            </script>
            
        </div>
    `,
    props: {
        doctype: {
            type: String,
            default: 'sales'
        }
    },
    data: function () {
        return {
            chooseMaterialHtml:`
                <input type="text" lay-event="click" autocomplete="off"
                   {{#  if(typeof(d.itemCode) !="undefined"){ }}
                   value="{{d.itemCode}}"
                   {{#  } }}
                   placeholder="" class="layui-input table-edit tags">
            `,
            materialTable:null,
            materials:[],
            remoteMaterials:[],
            remoteMaterialsMap:{},
        }
    },
    methods:{
        init:function(){
            /**
             * 物料列
             */
            if(typeof materialsCols == 'undefined'){
                materialsCols =[[
                    ,{title: '序号',type:'numbers',templet:'#chooseMaterial'}
                    ,{field: 'itemCode', width:90,title: '物料代码',templet:'#chooseMaterial',totalRowText:'合计'}
                    ,{field: 'itemName',width:225, title: '物料名称',style:'background-color:#eee'}
                    ,{field: 'num', width:this.doctype=='return'?100:70,title: '包数',edit: 'text',totalRow:true}
                    ,{field: 'salFactor1', title: '单包重',style:'background-color:#eee'}
                    ,{field: 'kg',width:this.doctype=='return'?100:90, title: '(数量)KG',edit: 'text',totalRow:true}
                    ,{field: 'packagePrice', title: '包价',style:'background-color:#eee'}
                    ,{field: 'factPrice', title: '单价',style:'background-color:#eee',hide:true}
                    ,{field: 'amount',width:this.doctype=='return'?140:90, title: '金额',style:'background-color:#eee',totalRow:true}
                    ,{field: 'currDisc',width:115, title: '每包折扣金额',style:'background-color:#eee'}
                    ,{field: 'totalDisc',width:100, title: '折扣总金额',style:'background-color:#eee',totalRow:true}
                    ,{field: 'factPrice',title: '出厂价',style:'background-color:#eee',hide:true}
                    ,{field: 'useQty',width:100, title: '已使用数量',style:'background-color:#eee'}
                    ,{field: 'ableQty',width:90, title: '剩余数量',style:'background-color:#eee'}
                    ,{fixed: 'right',width:80,title:'操作',align:'center',toolbar: '#barDemo'}
                ]];
            }

            layui.use('table', function(){
                vm.warehouseMap={};
                vm.materialTable = layui.table;
                vm.materialTable.render({
                    elem: '#materials'
                    ,cellMinWidth: 80
                    ,cols:materialsCols
                    ,data:null
                    ,even: false
                    ,totalRow: true
                    ,id:'materials'
                });

                //单元格编辑
                vm.materialTable.on('edit(materials)', function(obj){
                    var index = obj.tr.attr('data-index');
                    var material = obj.data;

                    //如果输入的是kg
                    if(obj.field == 'kg'){

                        var numOld = material.num;
                        var kgOld = $(this).prev().text();
                        //标包
                        if(material.isPackage=='Y'){
                            material.num = Division(material.kg,material.salFactor1);
                        }else{
                            material.num = material.kg;
                        }

                        if(!validator.isInt(material.num.toString()) || material.num <=0){
                            alert('物料['+material.itemName+ ']标包重为'+material.salFactor1+',输入重量不正确，请重新输入!');
                            material.kg = kgOld;
                            $(this).val(kgOld);
                            material.num = numOld;
                            return;
                        }

                    }
                    //如果输入的是包数
                    if(obj.field == 'num'){

                        if(!validator.isInt(material.num) || material.num <0){
                            alert('请输入整数包数!');
                            material.num = $(this).prev().text();
                            $(this).val($(this).prev().text());
                            return;
                        }

                        //如果是赠料
                        if(material.isGiveGD=='Y'){
                            if(material.num > material.discPack){
                                alert('使用赠包数不能大于可用赠包数!');
                                material.num = $(this).prev().text();
                                $(this).val($(this).prev().text())
                                return;
                            }
                            vm.remotePresentMaterials[material.presentIndex].useQty=material.num;
                        }

                        //标包
                        if(material.isPackage=='Y'){
                            material.kg = Multiply(material.num,material.salFactor1);
                        }else{
                            material.kg = material.num;
                        }

                    }

                    //如果输入的是包价
                    if(obj.field == 'packagePrice'){
                        material.factPrice = Division(material.packagePrice,material.salFactor1);
                    }

                    Document.calcMaterial(index,material);
                    Document.reloadMaterialTable();
                    vm.sales.prsDisc = false;

                });
                //监听事件
                vm.materialTable.on('tool(materials)', function(obj){
                    var index = obj.tr.attr('data-index');
                    var layEvent = obj.event;
                    if(layEvent == 'del'){

                        var material = vm.materials[index];
                        //如果是赠包
                        if(material.order != null){
                            vm.remotePresentMaterials[material.presentIndex].LAY_CHECKED = false;
                            vm.remotePresentMaterials[material.presentIndex].useQty = 0;
                        }

                        vm.materials.splice(index,1);
                        Document.reloadMaterialTable();
                        vm.sales.prsDisc = false;
                    }
                    if(layEvent=='click'){
                        $( ".tags" ).autocomplete({
                            source: vm.remoteMaterials,
                            autoFocus:true,
                            minLength:0,
                            delay:0,
                            select: function( event, ui ) {
                                $(this).val( ui.item.itemCode);

                                var material = vm.materials[index];
                                console.log(material);
                                material.itemCode = ui.item.itemCode;
                                material.itemName = ui.item.itemName;
                                material.factPrice = ui.item.factPrice;
                                material.currDisc = ui.item.currDisc;
                                material.isGiveGD = ui.item.isGiveGD;
                                material.isPackage = ui.item.isPackage;
                                material.salFactor1 = ui.item.salFactor1;
                                //material.num = 0;
                                material.ableQty = ui.item.ableQty;
                                material.useQty = ui.item.useQty;
                                material.warehouseCode = ui.item.whsCode;
                                Document.calcMaterial(index,material);
                                Document.reloadMaterialTable();

                                //Document.createWarehouseSelect(index,vm.sales.salesBranch,ui.item.itemCode);

                                /*//查询出厂价
                                httpGet('sap/item/priceAll',{
                                    CardCode:vm.customer.code,
                                    itemCodes:ui.item.itemCode,
                                    DocDate:vm.sales.taxDate,
                                    BPLId:vm.sales.salesBranch,
                                },function(data){
                                    var material = vm.materials[index];
                                    material.itemCode = ui.item.itemCode;
                                    material.itemName = ui.item.itemName;
                                    material.factPrice = data.price[ui.item.itemCode].price==null?0:data.price[ui.item.itemCode].price;
                                    material.currDisc = data.price[ui.item.itemCode].currDisc;
                                    material.isGiveGD = ui.item.isGiveGD;
                                    material.isPackage = data.price[ui.item.itemCode].isPackage;
                                    material.salFactor1 = data.price[ui.item.itemCode].salFactor1;
                                    material.whsCode = data.price[ui.item.itemCode].whsCode;
                                    material.whsName = data.price[ui.item.itemCode].whsName;
                                    material.regSupName = data.price[ui.item.itemCode].regSupName;
                                    material.stock = ui.item.stock;
                                    material.num = 1;
                                    material.ableQty = ui.item.ableQty;
                                    material.useQty = ui.item.useQty;
                                    Document.calcMaterial(index,material);
                                    vm.materialTable.reload('materials',{data:vm.materials});
                                },function(data){
                                    alert(data.msg);
                                    vm.materials.slice(index,1);
                                    vm.materialTable.reload('materials',{data:vm.materials});
                                });*/

                                return false;
                            }
                        })
                        $(this).autocomplete("search");
                    }
                    if(layEvent=='warehouse'){
                        $("#warehouse_"+index).change(function(){
                            var material = vm.materials[index];
                            material.warehouseCode = $(this).val();
                        });
                    }
                });
            });
        }
    },
    mounted:function(){
        this.init();
    }
};
