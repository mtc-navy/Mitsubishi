var jobstanComponent={
    template:`
        <div id="listTable">           
            <table class="layui-hide" id="Buds" lay-filter="Buds"></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>
            
            <script type="text/html" id="chooseBud">
                {{chooseBudHtml}}
            </script>
            
        </div>
    `,

    data: function () {
        return {
            chooseBudHtml:`
                <input type="text" lay-event="click" autocomplete="off"
                   {{#  if(typeof(d.itemCode) !="undefined"){ }}
                   value="{{d.itemCode}}"
                   {{#  } }}
                   placeholder="" class="layui-input table-edit tags">
            `,
            BudTable:null,
            Buds:[],
            remoteBuds:[],
        }
    },
    methods:{

    },
    mounted:function(){
        var _pasting=false;
        $("#listTable").bind('paste', function (e) {
            if(e.target.nodeName=='INPUT')return;
            //console.log(e);
            if (_pasting) return;
            //得到粘贴内容
            var pastedText = undefined;
            if (window.clipboardData && window.clipboardData.getData) { // IE
                pastedText = window.clipboardData.getData('Text');
            } else {
                pastedText = e.originalEvent.clipboardData.getData('Text');
            }
            //console.log(pastedText);
            var html = [];
            //  html.push('<table>')
            if (pastedText != undefined) {
                _pasting = true;

                if(typeof pastedText != "undefined"){
                    pastedText = replaceAll(pastedText,'\r\n','\r')
                }
                var arrLine = pastedText.split('\r');

                if (arrLine.length > 0) {

                    var arrFieldName = arrLine[0].split('\t');

                    $.each(arrFieldName, function(idx, item){
                        html.push( item )
                    });

                   // console.log(html);

                    //数据识别
                    var l = arrLine.length;
                    var s = 0;
                    for (var d = 1; d < l; d++) { //遍历行
                        if ('' == $.trim(arrLine[d])) continue;
                        var arrValue = arrLine[d].split('\t');
                        var idexBuds;//主数据索引
                        var tempbuds=null ;//临时数据
                        // html.push('<tr>');
                        $.each(arrValue, function(idx, item){
                            if(item)
                            {
                                if(idx==0 )
                                {
                                    $.each(vm.Buds, function(idxb, itemb) {
                                        if(itemb.code==item) {
                                            tempbuds = vm.Buds[idxb];
                                            idexBuds=idxb;
                                        }
                                    });
                                }
                                if(tempbuds){
                                    if(idx>1)
                                    {
                                        if(!isNumber(item)){
                                            item=0;
                                        }
                                        else{
                                            item=parseFloat(item);
                                        }
                                    }
                                    switch(idx)
                                    {
                                        case 2:
                                            tempbuds.month01=item;
                                            break;
                                        case 3:
                                            tempbuds.month02=item;
                                            break;
                                        case 4:
                                            tempbuds.month03=item;
                                            break;
                                        case 5:
                                            tempbuds.month04=item;
                                            break;
                                        case 6:
                                            tempbuds.month05=item;
                                            break;
                                        case 7:
                                            tempbuds.month06=item;
                                            break;
                                        case 8:
                                            tempbuds.month07=item;
                                            break;
                                        case 9:
                                            tempbuds.month08=item;
                                            break;
                                        case 10:
                                            tempbuds.month09=item;
                                            break;
                                        case 11:
                                            tempbuds.month10=item;
                                            break;
                                        case 12:
                                            tempbuds.month11=item;
                                            break;
                                        case 13:
                                            tempbuds.month12=item;
                                            break;
                                        default:
                                            ;
                                    }
                                }
                            }

                            //html.push( item );
                        });
                        if(tempbuds)
                        {
                            vm.Buds[idexBuds]=tempbuds;

                        }

                        s ++;
                    }
                }
                _pasting = false;
                vm.BudTable.reload('Buds',{data:vm.Buds});

            }

        });
        layui.use('table', function(){
            vm.BudTable = layui.table;
            vm.listTable=vm.BudTable.render({
                elem: '#Buds'
                ,cellMinWidth:90
                ,height:getTableHeight()
                ,cols:BudsCols
                ,data:null
                ,even: false
                ,totalRow: true
                ,id:'Buds'
                ,limit:GetLimit()
                ,done: function(res, curr, count){
                    $(".layui-table-total").click(function () {
                        vm.BudTable.reload('Buds', {data: vm.Buds});
                    });
                }
            });

            //单元格编辑
            vm.BudTable.on('edit(Buds)', function(obj){
                var index = obj.tr.attr('data-index');
                var Bud = obj.data;
                var isnum=true;
                $.each(Bud, function (key, value) {
                    if(key.lastIndexOf("month")>=0)
                    {
                        if(value)
                        {
                            if(!isNumber(value)){
                                Bud[key]=0;
                                isnum=false;
                                // alert('请输入数字！')
                            }
                            else{
                                Bud[key]=parseFloat(value);
                            }
                        }

                    }
                });
                if(!isnum)alert('请输入数字！');
                vm.Buds[index] = Bud;
            });
            //监听事件
            vm.BudTable.on('tool(Buds)', function(obj){
                var index = obj.tr.attr('data-index');
                var layEvent = obj.event;
                if(layEvent == 'del'){


                }
                if(layEvent=='click'){
                    $( ".tags" ).autocomplete({
                        source: vm.remoteBuds,
                        autoFocus:true,
                        minLength:0,
                        delay:0,
                        select: function( event, ui ) {
                            $(this).val( ui.item.itemCode);
                            var Buds = vm.Buds[index];
                            Buds.itemCode = ui.item.itemCode;
                            Buds.itemName = ui.item.itemName;
                            vm.Buds[index] = Buds;
                            vm.BudTable.reload('Buds',{data:vm.Buds});
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
 * 预算列
 */
var BudsCols=[[
    ,{field: 'code', title: '费用科目',style:'background-color:#eee;font-size:12px',totalRowText:'合计',align:'center',fixed:'left'}
    ,{field: 'name', title: '费用科目名称',style:'background-color:#eee;font-size:12px',align:'center',fixed:'left'}
    ,{field: 'month01', title: '1月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month02', title: '2月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month03', title: '3月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month04', title: '4月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month05', title: '5月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month06', title: '6月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month07', title: '7月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month08', title: '8月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month09', title: '9月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month10', title: '10月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month11', title: '11月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}
    ,{field: 'month12', title: '12月',edit: 'text',style:'font-size:12px;',totalRow:true,align:'center'}

]];