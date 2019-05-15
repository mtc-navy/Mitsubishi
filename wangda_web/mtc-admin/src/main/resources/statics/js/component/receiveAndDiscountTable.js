/**
 * 收款,折扣信息
 * @type {{}}
 */
var receiveAndDiscountComponent = {
    template:`
         <div class="form-group">
                <div class="col-md-6" style="padding-left:0px">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="#">收款信息</a></li>
                    </ul>
                    <table class="layui-hide" id="receives" lay-filter="receives"></table>
                </div>

                <div class="col-md-6" style="padding-left:0px">
                    
                </div>
            </div>
    `,
    data:function(){
        return {
            receivesTable:null,
            receives:[],
        }
    },
    methods:{
        init:function(filterVal){
            httpGet('sap/paydisc/list/',{
                CardCode:vm.customer.code,
                BPLId:vm.sales.salesBranch,
                FilterValue:filterVal,
            },function(data){
                vm.receives = data.pay; //收款信息
                vm.receivesTable.reload('receives',{data:vm.receives});
            });
        }
    },
    mounted:function(){

        /**
         * 收款信息
         */
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
                Document.calcArrear();
            });
        });

    }
};


//收款信息
var receivesCols= [[
    {field: 'payCode', title: '收款方式代码',totalRowText:'合计',style:'background-color:#eee'}
    ,{field: 'payName', title: '收款方式名称',style:'background-color:#eee'}
    ,{field: 'payAmt', title: '金额',edit: 'text',totalRow:true}
    ,{field: 'payDocNum', title: '收款单号',style:'background-color:#eee'}
]];
