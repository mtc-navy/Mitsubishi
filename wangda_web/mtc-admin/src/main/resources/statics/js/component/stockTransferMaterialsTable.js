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

    },
    mounted:function(){
        layui.use('table', function(){
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

                //如果输入的是数量
                if(obj.field == 'quantity'){

                    var qtyOld = material.quantity;
                    var factor2Old = $(this).prev().text();
                    //标包
                    if(material.isPackage=='Y'){
                        material.factor2 = Division(material.quantity,material.factor1);
                    }else{
                        material.factor2 = material.quantity;
                    }

                    if(!validator.isInt(material.quantity.toString()) || material.quantity <=0){
                        alert('物料['+material.itemName+ ']标包重为'+material.factor1+',输入重量不正确，请重新输入!');
                        material.quantity = qtyOld;
                        $(this).val(qtyOld);
                        material.factor2 = factor2Old;
                        return;
                    }

                }
                //如果输入的是件数
                if(obj.field == 'factor2'){

                    if(!validator.isInt(material.factor2) || material.factor2 <0){
                        alert('请输入整数件数!');
                        material.quantity = $(this).prev().text();
                        $(this).val($(this).prev().text());
                        return;
                    }

                    //标包
                    if(material.isPackage=='Y'){
                        material.quantity = Multiply(material.factor2,material.factor1);
                    }else{
                        material.quantity = material.factor2;
                    }

                }
                vm.materials[index] = material;
                vm.materialTable.reload('materials',{data:vm.materials});

            });
            //监听事件
            vm.materialTable.on('tool(materials)', function(obj){
                var index = obj.tr.attr('data-index');
                var layEvent = obj.event;
                if(layEvent == 'del'){

                    var material = vm.materials[index];
                    vm.materials.splice(index,1);
                    vm.materialTable.reload('materials',{data:vm.materials});
                }
                if(layEvent=='click'){
                    $( ".tags" ).autocomplete({
                        source: vm.remoteMaterials,
                        autoFocus:true,
                        minLength:0,
                        delay:0,
                        select: function( event, ui ) {
                            $(this).val( ui.item.itemCode);

                            //查询单个物料信息
                            httpGet('sap/item/info',{
                                whsCode:vm.transfer.fromWhsCode,
                                itemCode:ui.item.itemCode,
                                bplid:vm.transfer.bplId
                            },function(data){
                                var material = vm.materials[index];
                                material.itemCode = ui.item.itemCode;
                                material.itemName = ui.item.itemName;
                                material.isPackage = data.item.isPackage;
                                material.factor1 = data.item.salFactor1;
                                material.factor2 = data.item.salFactor2;
                                material.whsCode = data.item.whsCode;
                                material.stock = data.item.stock;
                                if (material.factor2 != null){
                                    if(material.isPackage=='Y'){
                                        material.quantity = Multiply(material.factor1,material.factor2);
                                    }else{
                                        material.quantity = material.factor2;
                                    }
                                }
                                vm.materials[index] = material;
                                vm.materialTable.reload('materials',{data:vm.materials});
                            },function(data){
                                alert(data.msg);
                                vm.materials.slice(index,1);
                                vm.materialTable.reload('materials',{data:vm.materials});
                            });

                            return false;
                        }
                    })
                    $(this).autocomplete("search");
                }
            });
        });
    }
};

/**
 * 物料列
 */
var materialsCols=[[
    ,{title: '序号',type:'numbers',templet:'#chooseMaterial'}
    ,{field: 'itemCode', width:90,title: '物料代码',templet:'#chooseMaterial',totalRowText:'合计'}
    ,{field: 'itemName',width:300, title: '物料名称',style:'background-color:#eee'}
    ,{field: 'factor1', width:120,title: '件重',style:'background-color:#eee'}
    ,{field: 'factor2', width:70,title: '件数',edit: 'text',totalRow:true}
    ,{field: 'quantity',width:90, title: '数量(KG)',edit: 'text',totalRow:true}
    ,{field: 'stock', width:90,title: '库存',style:'background-color:#eee'}
    ,{fixed: 'right',width:80,title:'操作',align:'center',toolbar: '#barDemo'}
]];