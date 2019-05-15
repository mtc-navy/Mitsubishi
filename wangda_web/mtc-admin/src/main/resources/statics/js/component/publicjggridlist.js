
(function ($) {
    $.fn.extend({
        setjgGridList: function (options) {
            var $this = $(this); //当前对象
            //设置默认参数
            var defaults = {
                api: '',
                hiddenFields: [],
                buttons: [],
                dpager:'#jqGridPager',
                height:0,
                isred:false,
                ischeck:false,
                docEntry:1,
                version:0

            };
            var opts = $.extend(defaults, options); //合并默认参数与传参
            var jqdata=[];
            var jglengths=[];
            var urlservice = baseURL + opts.api;
            //临时参数
            var temppageSize=1;
            var temppageIndex=1;
            var tempparam=JSON.stringify({});
            var names = [];
            var model = [];
            //取gird结构
            $.ajax({
                type: "get",
                url: urlservice,
                async: true,
                contentType: "application/json",
                data:{  docEntry:opts.docEntry, version:opts.version,},
                success: function (r) {
                    if (r.code == 0) {
                      /* if(r.page.list.length>0)
                        {
                            $.each(r.page.list[0], function (key, value) {
                                jqdata.push(key);
                            });
                        }
                        else
                        {
                            jqdata = r.page.fields;
                        }*/
                        jqdata = r.result.fields;
                        // jglengths=r.page.lengths;

                        //此处因为数据源数组中的结构相同且不为空，直接遍历索引为0的数据即可
                        $.each(jqdata, function (key, value) {
                            // var sWidth=jglengths[key];
                            names.push(value);
                            var sWidth=120;
                            //遍历需要屏蔽的字段
                            if(value.indexOf('月')>=0){
                                sWidth=80;
                            }
                            if (opts.hiddenFields.indexOf(value) >= 0) {
                                model.push({name: value, index: value, sortable:false,hidden: true,});
                            }
                            else {
                                model.push({name: value, index: value,sortable:false,cellattr:addCellAttr,width:sWidth});
                            }


                        });

                        //添加操作列
                        if (opts.buttons.length > 0) {
                            names.push("action");
                            model.push({
                                name: "action", index: "action", formatter: function (value, options, row) {
                                    var html = '<div class="btn-group btn-group-xs" role="group" aria-label="Extra-small button group">';
                                    $.each(opts.buttons, function (bkey, value) {
                                        html += '<button type="button" value="' + row.id + '" status="' + row.id + '" class="edit btn btn-default">' + value + '</button>';
                                    });
                                    html += '</div>'
                                    return html;
                                }
                            });
                        }
                        //初始化
                        init();
                    } else {
                        alert(r.msg);
                    }
                }
            });



            //初始化方法
            function init() {
                //... ...
                $this.jqGrid({
                    url: baseURL + opts.api,
                    datatype: "json",
                    colNames: names,//jqGrid的列显示名字
                    colModel: model,
                    viewrecords: true,
                    height: options.height>0?options.height:$(window).height() - 160,
                    rowNum: 10000,
                    rownumbers: true,
                    rownumWidth: 25,
                    multiselect: options.ischeck ,
                    multiboxonly:true,
                    autowidth: false, //宽度自适应
                    shrinkToFit: true, //列自适应
                    autoScroll: false,

                    jsonReader: {
                        root: "result.details",
                        // page: "page.currPage",
                        // total: "page.totalPage",
                        // records: "page.totalCount"
                    },
                    prmNames: {
                        page: "pageIndex",
                        rows: "pageSize",
                        order: "order"
                    },
                    postData:{
                        docEntry:opts.docEntry,
                        version:opts.version,
                    },

                    gridComplete: function () {

                        $("#cb_jqGridList").hide();
                        $("#cb_jqGrid").hide();
                        //隐藏grid底部滚动条
                        //$this.closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                      //  $this.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
                    },
                    loadComplete: function (data) {
                        if (data.code != 0) {
                            alert(data.msg);
                        }
                    },
                    beforeSelectRow: function (data) {
                        $this.jqGrid('resetSelection');
                        return(true);
                    },
                });
               // jqgridColResize();
              // var cols_array = $this.jqGrid()[0].attributes.style.ownerElement.grid.cols;
              //   $this.setGridWidth(chooseGridWidth(cols_array));
               $this.setGridWidth($(window).width()-1)
            }

            function setGridWidth(num, array) {

                var grid_width = 0;
                for (var i = 0; i < num; i++) {
                    grid_width += array[i].offsetWidth
                }
                return grid_width
            }

            function chooseGridWidth(array) {
                var len = array.length;
                var min = setGridWidth(len, array);
                var num1 = $(window.parent.document).width(), num2 = 0;
                if (min > $(window.parent.document).width()) {
                    min = setGridWidth(1, array)
                }
                ;
                for (var i = 1; i < len; i++) {
                    for (var j = i + 1; j < len; j++) {

                        num2 = setGridWidth(j, array);
                        if (Math.abs(num1 - num2) <= Math.abs(min - num1) && (num1 - num2) >= 0) {

                            min = setGridWidth(j, array)
                        }
                    }
                }
                return min
            }

            function jqgridColResize(){
                var td=$this//获取计算实际列长度的容器
                    ,tds//临时保存列
                    ,arr=[];//用于保存最大的列宽
                //遍历每行获得每行中的最大列宽
                $('.ui-jqgrid-htable tr,.ui-jqgrid-btable tr:gt(0)').each(function(){
                    $(this).find('td,th').each(function(idx){
                        arr[idx]=Math.max(arr[idx]?arr[idx]:0,td.html($(this).text())[0].offsetWidth);
                    })
                });
                $('.ui-jqgrid-labels th').each(function(idx){this.style.width=arr[idx]+'px'});//设置页头单元格宽度
                $('.ui-jqgrid-btable tr:eq(0) td').each(function(idx){this.style.width=arr[idx]+'px'});//设置内容表格中控制单元格宽度的单元格，在第一行
            }
            function hideSelectAll() {
                $("#cb_table_mycars").hide();
                return(true);
            }

            function beforeSelectRow() {
                $("#table_mycars").jqGrid('resetSelection');
                return(true);
            }
            function addCellAttr(rowId, val, rawObject, cm, rdata) {
               // var isadd=rdata[rowId].isAdd;

                if(opts.isred){
                    if(rdata.isAdd)
                    {
                        if(rdata.isAdd>=1){
                            return "style='color:red'";
                        }
                        else if (val.indexOf('/') >= 0){
                            return "style='color:red'";
                        }
                    }
                    else if (val.indexOf('/') >= 0){
                        // console.log(rowId);
                        // console.log(val);
                        // console.log(rawObject);
                        // console.log(rdata);
                        // console.log(cm);
                        return "style='color:red'";
                    }
                }

            }
        }
    });
})(jQuery);


