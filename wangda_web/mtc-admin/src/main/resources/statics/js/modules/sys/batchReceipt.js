$(function () {

    $(function () {
        $("#jqGrid").setjgGrid({
            api:  'sys/list/batchReceiptList', //API地址
            height:'full',
            hiddenFields: ['DOCENTRY'],               //隐藏字段
            qParam: vm.search,                  //查询参数
            chooseType: eChooseType.Multi,     //选项类型
            shrinkToFit:false,
            loadDefault: true,                 //初始不载入数据，由 tree 载入
            rowList : [10,50,100,500,1000],
            buttons:[                           //操作按钮

            ],
            loadComplete: function(data){       //载入完成后载入树
                //全选按钮
                $("#cb_jqGrid").change(function(){
                    culcTotal();
                })
            },
            onPaging:function(pageBtn){
                vm.total = 0.0;
            },
            onSelectRow:function(id){
                culcTotal();
            },
        });
        Document.initSalesBranch(null,function(){

        });
    });

    //销售分支
    Document.initSalesBranch(null,function(data){
    });

    function culcTotal(){
        var grid = $("#jqGrid");
        var keys = grid.jqGrid('getGridParam', 'selarrrow');
        var total = 0.0;
        if(keys && keys.length > 0 ){
            for(var i=0;i<keys.length;i++){
                var rowData = grid.jqGrid('getRowData', keys[i]);
                total = Add(total,rowData['收款合计']);
            }
        }
        vm.total = total;
    }


});

var vm = new Vue({
    el:'#rrapp',
    data:{
        search:{
            param1:null,
            param2:null,
            param3:null,
            param4:null,
            param5:'Y',
            param6:null,
        },
        showList:true,
        salesBranches:[],
        sales:{},
        customer:{
            code:'',
        },
        total:0.0,
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
            httpPost('sap/saleinv/batchinpay',ids, function(data){
                alert(data.msg);
                vm.reload();
            });
        },
        cancelReceive:function(){
            var ids = getSelectedRows("DOCENTRY");
            if(ids == null){
                return null;
            }
            httpPost('sap/saleinv/batchcelpay',ids, function(data){
                alert(data.msg);
                vm.reload();
            });
        },
        add:function(){
        },
    },
    mounted:function(){

    }
});
