var unitComponent={
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
        layui.use('table', function(){
            vm.BudTable = layui.table;
            vm.listTable = vm.BudTable.render({
                elem: '#Buds'
                ,cellMinWidth:110
                ,height:getTableHeight()
                ,cols:BudsCols
                ,data:null
                ,even: false
                ,totalRow: false
                ,id:'Buds'
                ,limit:GetLimit()
            });

            //单元格编辑
            vm.BudTable.on('edit(Buds)', function(obj){
                var index = obj.tr.attr('data-index');
                var Bud = obj.data;
                vm.Buds[index] = Bud;
                 vm.BudTable.reload('Buds',{data:vm.Buds});

            });
            var smonth=0;//本次点击的单元格
            var oldmonth=0;//上次点击的单元格
            var rowDoubleCount=0;//相同单元格的点击次数
            //监听事件
            vm.BudTable.on('tool(Buds)', function(obj){
                var index = obj.tr.attr('data-index');
                var layEvent = obj.event;

                if(layEvent == 'del'){


                }
                //记录是否连续点击同一单元格
                switch(layEvent)
                {
                    case 'link0':
                        smonth=0;
                        break;
                    case 'link1':
                        smonth=1;
                        break;
                    case 'link2':
                        smonth=2;
                        break;
                    case 'link3':
                        smonth=3;
                        break;
                    case 'link4':
                        smonth=4;
                        break;
                    case 'link5':
                        smonth=5;
                        break;
                    case 'link6':
                        smonth=6;
                        break;
                    case 'link7':
                        smonth=7;
                        break;
                    case 'link8':
                        smonth=8;
                        break;
                    case 'link9':
                        smonth=9;
                        break;
                    case 'link10':
                        smonth=10;
                        break;
                    case 'link11':
                        smonth=11;
                        break;
                    case 'link12':
                        smonth=12;
                        break;
                    default:
                        smonth=-1;
                }

                if(smonth>=0)
                {
                   // console.log(smonth);
                    if(smonth==oldmonth) rowDoubleCount++;
                   // console.log(rowDoubleCount);

                    oldmonth=smonth;
                }
            });
            vm.BudTable.on('rowDouble(Buds)', function(obj){
                var index = obj.tr.attr('data-index');
                //obj 同上
              //  console.log(obj);
                if(smonth>=0 && rowDoubleCount>0)
                {
                    var proTemplate="01,02,03,08,09,10,11,13";
                    var projCode=vm.Buds[index].projcode;
                    //if(proTemplate.lastIndexOf(projCode)>=0){

                        vm.showDetilTable(projCode,smonth);
                        smonth=0;//本次点击的单元格
                        oldmonth=0;//上次点击的单元格
                        rowDoubleCount=0;//相同单元格的点击次数

                    //}
                    //else{
                      //  alert('此项目无对应数据！');
                    //}
                }



            });

        });
    }
};

/**
 * 预算列
 */
var BudsCols=[[
    ,{field: 'projname', title: '项目',style:'background-color:#eee;text-align: center;font-size:12px',width:'10%',align:'center',event:'link0',fixed:'left'}
    ,{field: 'month01', title: '1月',style:'font-size:12px',align:'center',event:'link1'},
    ,{field: 'month02', title: '2月',style:'font-size:12px',align:'center',event:'link2'},
    ,{field: 'month03', title: '3月',style:'font-size:12px',align:'center',event:'link3'},
    ,{field: 'month04', title: '4月',style:'font-size:12px',align:'center',event:'link4'},
    ,{field: 'month05', title: '5月',style:'font-size:12px',align:'center',event:'link5'},
    ,{field: 'month06', title: '6月',style:'font-size:12px',align:'center',event:'link6'},
    ,{field: 'month07', title: '7月',style:'font-size:12px',align:'center',event:'link7'},
    ,{field: 'month08', title: '8月',style:'font-size:12px',align:'center',event:'link8'},
    ,{field: 'month09', title: '9月',style:'font-size:12px',align:'center',event:'link9'},
    ,{field: 'month10', title: '10月',style:'font-size:12px',align:'center',event:'link10'},
    ,{field: 'month11', title: '11月',style:'font-size:12px',align:'center',event:'link11'},
    ,{field: 'month12', title: '12月',style:'font-size:12px',align:'center',event:'link12'},
    ,{field: 'year', title: '合计',style:'font-size:12px',align:'center',event:'link12'},

]];