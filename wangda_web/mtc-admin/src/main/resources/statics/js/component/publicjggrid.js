/**
 * 公共列表类
 * 参数：
 *      api: api url;
 *      hiddenFields: 隐藏字段数组;
 *      chooseType: 选择类型，eChooseType 枚举；
 *      pageSize: 一页显示数量
 *      qParam: 查询参数;
 *      buttons: 操作按钮数组；
 *          command: 操作类型(edit：编辑; del: 删除; cancel: 取消; view: 查看; button: 其它按钮)，除其它按钮外，其它按钮均默认生成
 *          class: 类名，保持唯一，用于获取按钮对象时使用 $('.class')
 *          caption: 按钮名称
 *          function: 按钮操作
 *      mode: 列表类型，eListMode 枚举；
 *      localData: 本地数据
 *          cols: [{field: '字段名', title: '显示名称', required: 是否必填, width: '宽度', hidden: 是否隐藏, edittype: 控件类型(text,checkbox,select), editable: 是否可编辑, sortable: 是否可排序,editrules: { required: true }},...]
 *          list: 数据数组
 *      dataType: 数据格式；local:本地; json:远程数据集
 *      loadDefault: 初始时是否载入数据，默认 true
 *      loadComplete: function(data)载入完成后加调
 *      initLineEvent: 编辑状态下初始化行事件
 *      validateEvent: 编辑状态下数据检查事件
 *
 *  方法：
 *      setjgGrid({}) : 设置grid
 *      getData(): 获取表格数据
 */

//选择类型
var eChooseType = {};
eChooseType.None = 0;   //无选择
eChooseType.Single = 1; //单选
eChooseType.Multi = 2;  //多选

//列表模式
var eListMode = {};
eListMode.List = 0; //列表
eListMode.Edit = 1; //编辑
eListMode.Add = 2; //新增

//定义数据类型(DataType)
var DataType = {};
DataType.CheckBox = 'checkbox';
DataType.CheckBoxList = 'checkboxlist';
DataType.Select = 'select';
DataType.MulitSelect = 'mulitselect';
DataType.Radio = 'radio';
DataType.RadioList = 'radiolist';
DataType.String = 'string';
DataType.Date = 'date';
DataType.DateTime = 'datetime';
DataType.Int = 'int';
DataType.Integer = 'integer';
DataType.Number = 'number';
DataType.Numeric = 'numeric';
DataType.Float = 'float';
DataType.Password = 'password';
DataType.Decimal = 'decimal';
DataType.Hidden = 'hidden';
DataType.Email = 'email';
DataType.Url = 'url';
DataType.Memo = 'memo';

var _GRID = []; //全局GRID，用于外部取数
(function ($) {
    $.fn.extend({
        setjgGrid: function (options) {
            var $this = $(this); //当前对象
            //设置默认参数
            var defaults = {
                api: '',
                hiddenFields: [],
                buttons: [],
                pager:$('#jqGridPager'),
                height:'auto',
                pageSize: 10,
                chooseType: eChooseType.None,
                qParam:JSON.stringify({}),
                localData: null,
                mode: eListMode.List,
                dataType: 'json',
                loadDefault: true,
                loadComplete: null,
                initLineEvent: null,
                validateEvent: null,
                onPaging:null,
                onSelectRow:null,
            };
            var opts = $.extend(defaults, options); //合并默认参数与传参
            var indexOpen = opts.localData ? null : layer.load(1);
            var jqdata=[];
            var jglengths=[];
            var urlservice = baseURL + opts.api;
            //临时参数
            var temppageSize=1;
            var temppageIndex=1;
            var names = [];
            var model = [];
            //计算自适应使用
            var lineheight = 35;
            var gridtitleheight = 45;

            //编辑相关参数
            var data;
            var iSelectedCell = -1;

            //如果是本地数据，则初始本地数据参数
            if (opts.localData && opts.localData.list) {
                opts.dataType = 'local';
                data = opts.localData.list;
            }
            if (opts.localData && opts.localData.cols){
                $.each(opts.localData.cols, function(key, value){
                    var bHide = value.hidden || opts.hiddenFields.indexOf(value.field) > 0;
                    if (opts.mode == eListMode.List) value.editable = false;
                    if (!value.editoptions) value.editoptions = {};
                    var one = {
                        name: value.field,
                        index: value.field,
                        sortable: value.sortable,
                        hidden: bHide,
                        resizable: true,
                        width: value.width,
                        align: value.align,
                        editable: value.editable,
                        editoptions: {}
                    };
                    one.align = 'left';
                    if (opts.mode == eListMode.Add || opts.mode == eListMode.Edit) {
                        one.edittype = 'custom';
                        one.editoptions.custom_element = customElement;
                        one.editoptions.custom_value = customValue;
                    }
                    //不同格式展示
                    switch(value.edittype){
                        case 'number':
                            one.number = true;
                            one.formatter = 'number';
                            one.align = 'right';
                            break;
                        case 'date':
                            one.formatoptions = {srcformat:'Y-m-d',newformat:'Y-m-d' };
                            one.formatter = 'date';
                            break;
                        case 'datetime':
                            one.formatoptions = {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s' };
                            one.formatter = 'datetime';
                            break;
                    }
                    one.editoptions.model = value;
                    model.push(one);
                    names.push(value.title);
                });
            }
            ;
            //取grid结构
            if (!opts.localData || !opts.localData.cols) {
                $.ajax({
                    type: "get",
                    url: urlservice,
                    async: true,
                    contentType: "application/json",
                    data: {
                        deptCode: null,
                        pageSize: temppageSize,
                        pageIndex: temppageIndex,
                        param: JSON.stringify(opts.qParam)
                    },
                    success: function (r) {
                        if (r.code == 0) {
                            jqdata = r.page.fields;
                            jglengths = r.page.lengths;

                            //此处因为数据源数组中的结构相同且不为空，直接遍历索引为0的数据即可
                            $.each(jqdata, function (key, value) {
                                var sWidth = jglengths[key];
                                names.push(value);

                                //遍历需要屏蔽的字段
                                if (opts.hiddenFields.indexOf(value) >= 0) {
                                    model.push({
                                        name: value,
                                        index: value,
                                        sortable: false,
                                        hidden: true,
                                        width: sWidth
                                    });
                                } else {
                                    model.push({name: value, index: value, sortable: false, width: sWidth});
                                }
                            });

                            //添加操作列
                            if (opts.buttons.length > 0) {
                                names.push("操作");
                                model.push({
                                    name: "action",
                                    width: 100,
                                    index: "action",
                                    formatter: function (value, options, row) {
                                        var html = [];
                                        html.push('<div class="btn-group btn-group-xs" role="group" aria-label="Extra-small button group">');
                                        //生成操作
                                        $.each(opts.buttons, function (bkey, button) {
                                            switch (button.command) {
                                                case 'edit': //编辑
                                                    button.class = 'edit';
                                                    html.push('<button type="button" value="' + (button.key==null?row.id:row[button.key]) + '" class="edit btn btn-default">' + (button.caption ? button.caption : '编辑') + '</button>');
                                                    break;
                                                case 'del': //删除
                                                    button.class = 'del';
                                                    html.push('<button type="button" value="' + (button.key==null?row.id:row[button.key]) + '" class="del btn btn-default">' + (button.caption ? button.caption : '删除') + '</button>');
                                                    break;
                                                case 'cancel': //撤消
                                                    button.class = 'cancel';
                                                    html.push('<button type="button" value="' + (button.key==null?row.id:row[button.key]) + '" class="cancel btn btn-default">' + (button.caption ? button.caption : '撤消') + '</button>');
                                                    break;
                                                case 'view': //查看
                                                    button.class = 'view';
                                                    html.push('<button type="button" value="' + (button.key==null?row.id:row[button.key]) + '" class="view btn btn-default">' + (button.caption ? button.caption : '查看') + '</button>');
                                                    break;
                                                default:
                                                    html.push('<button type="button" value="' + (button.key==null?row.id:row[button.key]) + '" class="' + button.class + ' btn btn-default">' + button.caption + '</button>');
                                                    break;
                                            }
                                        });
                                        html.push('</div>');
                                        return html.join('\n');
                                    }
                                });
                            }
                            layer.close(indexOpen);
                            //初始化
                            init();
                        } else {
                            layer.close(indexOpen);
                            alert(r.msg);
                        }
                    }
                });
            } else {
                init();
            }

            //高宽计算
            function getHeight(pObj, pOpts){
                var height = pOpts.height * 1.0;
                var footerheight = pOpts.pager.length > 0 ? 42 : 0;
                var headerheight = 40;
                var obj = $('#gbox_' + pObj.prop('id')).length > 0 ? $('#gbox_' + pObj.prop('id')) : pObj;
                if (obj.is(':hidden')) return;
                height = $(window).height()*1.0 - pObj.offset().top*1.0 - footerheight*1.0 - headerheight*1.0;
                switch (pOpts.height) {
                    case 'choose': //选择页
                        height -= 50;
                        break;
                    case 'auto': //自动
                        var tmpGrid = _GRID[pObj.prop('id')];
                        if (pOpts.localData && pOpts.localData.list){
                            height = gridtitleheight + pOpts.localData.list.length * lineheight;
                        } else if (tmpGrid && tmpGrid.getRowData().length > 0){
                            height = gridtitleheight + tmpGrid.getRowData().length * lineheight;
                        } else if (tmpGrid && tmpGrid.userdata.data){
                            height = gridtitleheight + tmpGrid.userdata.data.length * lineheight;
                        }

                        if (height > $(window).height()) height = $(window).height();
                        break;
                }
                if ((height > 0 && height < 50) || height < 0) height = 50;
                return height;
            }

            //窗体调整，重新设置高度
            $(window).resize(function(){
                for(var p in _GRID){
                    var h = getHeight($('#'+_GRID[p].userdata.id), _GRID[p].userdata.options);
                    if (h != undefined){
                        _GRID[p].setGridHeight(getHeight($('#'+_GRID[p].userdata.id), _GRID[p].userdata.options));
                    }
                    var pObj = $('#gbox_' + _GRID[p].userdata.id).parent();
                    var gw =_GRID[p].getGridParam('width');
                    if (gw > pObj.width() * 2) pObj.css('width', gw);
                    else if (gw < pObj.width()) _GRID[p].setGridWidth(pObj.width());
                }
            });

            //初始化方法
            function init() {
                var oGrid = $this.jqGrid({
                    url: baseURL + opts.api,
                    data: opts.localData? opts.localData.list: [],
                    datatype: opts.loadDefault ? opts.dataType : 'local',
                    colNames: names,//jqGrid的列显示名字
                    colModel: model,
                    viewrecords: true,
                    height: getHeight($this, opts),
                    rowNum: opts.pageSize,
                    rowList: opts.rowList=null?[opts.pageSize, opts.pageSize + 20, opts.pageSize + 20*2]:opts.rowList,
                    rownumbers: true,
                    rownumWidth: 25,
                    multiselect:  opts.chooseType != eChooseType.None,
                    multiboxonly:false,
                    autowidth: true, //宽度自适应
                    shrinkToFit: options.shrinkToFit==null?false:options.shrinkToFit, //列自适应
                    autoScroll: true,
                    pager: opts.pager,
                    editurl: baseURL + 'sap/purchaseorder/empty',
                    jsonReader: {
                        root: "page.list",
                        page: "page.currPage",
                        total: "page.totalPage",
                        records: "page.totalCount"
                    },
                    prmNames: {
                        page: "pageIndex",
                        rows: "pageSize",
                        order: "order"
                    },
                    postData:{
                        param:JSON.stringify(opts.qParam)
                    },
                    //grid 载入完成事件
                    gridComplete: function () {
                        //去掉全选框及更换单选框
                        if (opts.chooseType == eChooseType.Single) {
                            $this.closest('div[role="grid"]').find('thead input[type="checkbox"]').hide();
                            $this.find('td[aria-describedby="jqGrid_cb"] input[type="checkbox"]').attr('type', 'radio');
                        }
                        //判断默认操作按钮
                        $.each(opts.buttons, function (key, button) {
                            if (button.function) {
                                $('.' + button.class, $this).click(function () {
                                    button.function($(this).val());
                                });
                            }
                        });
                    },
                    //数据载入完成
                    loadComplete: function (result) {
                        if ($this.jqGrid('getGridParam', 'datatype') != 'local') {
                            //动态获取列
                            if (result.code == 0) {
                                data = result;
                                $this.jqGrid().userdata.data = result;
                                $this.jqGrid().userdata.lastselected = -1;
                            } else {
                                layer.close(indexOpen);
                                alert(result.msg);
                            }
                            if (opts.loadComplete != null) {
                                opts.loadComplete(result);
                            }
                            //设置高度
                            if (opts && data && opts.height == 'auto'){
                                var h = gridtitleheight + (data.page ? data.page.list.length : data.length) * lineheight;
                                if (h > $(window).height()) h = $(window).height();
                                $this.jqGrid('setGridHeight',h);
                            }
                        }
                        setTimeout(function(){$(window).trigger('resize');}, 200);
                    },
                    //表格选中事件
                    onCellSelect: function (rowid, iCol, cellcontent, e) {
                        iSelectedCell = iCol;
                    },
                    //选中行
                    onSelectRow: function (id) {
                        if (opts.mode == eListMode.Edit || opts.mode == eListMode.Add) {
                            var tmpGrid = _GRID[$this.prop('id')];
                            if (id != undefined && id != tmpGrid.userdata.lastselected) {
                                var tmpLastSelected = tmpGrid.userdata.lastselected;
                                if (tmpLastSelected != undefined) {
                                    tmpGrid.saveRow(tmpLastSelected);

                                    //检查数据输入
                                    if (opts.validateEvent && !opts.validateEvent(tmpGrid, tmpLastSelected)) {
                                        tmpGrid.editRow(tmpLastSelected, { keys: true});
                                        initDom($this);
                                        if (opts.initLineEvent != null) opts.initLineEvent(tmpGrid);
                                        return;
                                    }
                                }
                                tmpGrid.editRow(id, { keys: true, focusField: iSelectedCell });
                                tmpGrid.userdata.lastselected = id;
                                initDom($this);
                                if (opts.initLineEvent != null) opts.initLineEvent(tmpGrid);
                            }

                        }
                        if(opts.onSelectRow != null){
                            opts.onSelectRow(id);
                        }
                    },
                    //行选中前
                    beforeSelectRow: function (data) {
                        if (opts.chooseType == eChooseType.Single)  {
                            $this.jqGrid('resetSelection');
                        }
                        return(true);
                    },
                    onPaging:function(pageBtn){
                        if(opts.onPaging != null){
                            opts.onPaging(pageBtn);
                        }
                    },
                }).navGrid(opts.pager, {edit: false,add: false,del: false,search: false,refresh: false});

                //得到按钮对象
                function getGrid(evt){
                    var objSpan = $(evt.target).prop('tagName') == 'TD'?$(evt.target).find('span') : $(evt.target);
                    var tmpGridID = objSpan.attr('data-gridid');
                    return _GRID[tmpGridID];
                }

                //工具栏按钮
                var buttons;
                if (opts.pager && (opts.mode == eListMode.Add)){
                    buttons = [
                        {caption:'添加',class:'fa fa-plus',id:$this.prop('id')+'_btn_add',click:function(e){
                                var tmpGrid = getGrid(arguments.callee.caller.arguments[0] || event);
                                var ids = oGrid.getDataIDs();
                                var rowid = Math.max.apply(Math, ids);
                                var newid = (rowid == undefined || rowid == null || ids.length == 0) ? 1 : (rowid + 1);
                                var newrowdata = {};
                                $.each(opts.localData.cols, function(key, value){
                                    newrowdata[value.field] = value.editoptions && value.editoptions.defaultValue ? value.editoptions.defaultValue : null;
                                });
                                tmpGrid.addRowData(newid, newrowdata);
                                var curPage = tmpGrid.getGridParam('page'); //当前页
                                var maxPage = Math.ceil(tmpGrid.getGridParam('records') * 1.0 / tmpGrid.getGridParam('rowNum')); //总页数
                                if (curPage != maxPage)
                                    tmpGrid.setGridParam({page: maxPage}).trigger('reloadGrid');
                                tmpGrid.editRow(newid, {key: true, focusField: 1});
                                tmpGrid.userdata.lastselected = newid;
                            }},
                        {caption:'删除',class:'fa fa-remove',id:$this.prop('id')+'_btn_del',click:function(e){
                                var tmpGrid = getGrid(arguments.callee.caller.arguments[0] || event);
                                if (tmpGrid.userdata.lastselected == -1){alert('未选择任何记录，请选中后操作~');return;}
                                tmpGrid.delRowData(tmpGrid.userdata.lastselected);
                            }},
                    ];

                    var html = [];
                    html.push('<table class="ui-pg-table navtable ui-paging-pager"><tr>');
                    $.each(buttons, function(key, value){
                        html.push('<td class="ui-pg-button" title="' + value.caption + '"><div class="ui-pg-div"><span class="' + value.class + '" data-tag="toolbutton" data-gridid="' + $this.prop('id') + '" id="' + value.id + '"></span></div></td>');
                    });
                    html.push('</tr></table>');

                    opts.pager.find('td:first').append(html.join('\n'));

                    //初始按钮事件
                    opts.pager.find('td:first .ui-pg-button').each(function(key, value){
                        $(this).click(function(){
                            buttons[key].click();
                        });
                    });
                }

                //设置全局值
                oGrid.userdata = {
                    id: $this.prop('id'),
                    options: opts,
                    buttons: buttons,
                    data: data,
                    lastselected: -1
                };
                _GRID[$this.prop('id')] = oGrid;
            }

            //自定义控件
            function customElement(value, options){
                var model = options.model;
                if (value != null) {
                    model.editoptions.defaultValue = value;
                }
                var el = $('<div></div>');
                el.html(getDom(options.model, null, opts.mode));
                return el;
            }

            //自定义控件读写
            function customValue(el, operation, value){
                var isSelect = false;
                var elNew = $(el).find('input');
                if (elNew.length == 0) {
                    elNew = $(el).find('select');
                    isSelect = true;
                }
                if (elNew.length == 0) return null;

                var rowid = el.attr('id').split('_')[0];
                var isCheckBox = elNew.attr('type') && elNew.attr('type').toLowerCase() == 'checkbox';
                switch(operation){
                    case 'get':
                        if (isCheckBox){ //checkbox 处理
                            return getCheckboxVal(elNew);
                        } else {
                            return elNew.val();
                        }
                    case 'set':
                        $(el).find('input').val(value);
                        break;
                }
            }

            /**
             * 根据字段信息得到 Dom 元素
             * pModel: 字段对象
             * pData: 值对象
             */
            function getDom(pModel, pData, pMode) {
                if (pModel == null) return '';
                var html = [];
                var strFieldName = pModel.name;
                var strID = $this.prop('id') + '_' + strFieldName;
                if (!pModel.editoptions) pModel.editoptions = {};

                //数据类型
                var strDataType = pModel.edittype.toLowerCase();
                var bReadOnly = strDataType.indexOf('readonly') == 0 || (!pModel.editable && pMode == eListMode.Edit);
                if (bReadOnly) strDataType = strDataType.substring(8);
                var bLink = strDataType.indexOf('link') >= 0;
                if (bLink) strDataType = strDataType.substring(4);

                //公用属性
                var pubProperty = 'id=\'' + strID + '\' data-fieldname=\'' + strFieldName + '\' data-type=\'' + strDataType + '\' data-cn="' + pModel.title + '" placeholder="' + pModel.title + '" ' + (bReadOnly ? 'disabled="disabled" ' : '');

                //必填判断
                if (pModel.required == true) pubProperty += ' required ';

                //原始数据
                var hasData = (pData != undefined && pData[strFieldName] != undefined && pData[strFieldName] != null);
                if (hasData) {
                    pubProperty += ' data-old="' + pData[strFieldName] + '" ';
                    pModel.editoptions.defaultValue = pData[strFieldName];
                }

                //类型判断
                switch (strDataType) {
                    case DataType.CheckBox: //选项框
                        html.push('      <div class="checkbox checkbox-inline checkbox-primary">');
                        if (pModel.editoptions.defaultValue == '1' || pModel.editoptions.defaultValue.toLowerCase() == 'true' || pModel.editoptions.defaultValue.toLowerCase() == 'y')
                            html.push('      <input type="checkbox" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '" checked="checked"/>');
                        else
                            html.push('      <input type="checkbox" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        html.push('    <label></label></div>');
                        break;
                    case DataType.Select: //下拉框
                        html.push('    <select class="form-control" ' + pubProperty + ' >');
                        html.push('      <option value="">' + pModel.DisplayName + '</option>');

                        var bSelected = false;
                        if (pModel.datasource != undefined) {
                            var si = pModel.datasource.length;
                            for (var j = 0; j < si; j++) {
                                var itemDS = pModel.datasource[j];
                                if (pModel.editoptions.defaultValue != null && itemDS.Value.toString().toLowerCase() == pModel.editoptions.defaultValue.toString().toLowerCase()) {
                                    html.push('      <option value="' + itemDS.Value + '" selected="selected">' + itemDS.Name + '</option>');
                                    bSelected = true;
                                } else
                                    html.push('      <option value="' + itemDS.Value + '">' + itemDS.Name + '</option>');
                            }
                        }
                        //如果下拉中无选项，但有值，直接显示值
                        if (!bSelected && pModel.editoptions.defaultValue != undefined && pModel.editoptions.defaultValue != null && pModel.editoptions.defaultValue.toString().length > 0) {
                            html.push('      <option value="' + pModel.editoptions.defaultValue + '" selected="selected">' + pModel.editoptions.defaultValue + '</option>');
                        }

                        html.push('    </select>');
                        break;
                    case DataType.Hidden: //隐藏表单
                        html.push('      <input type="hidden" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Date: //日期
                        html.push('      <input type="date" placeholder="' + pModel.title + ':YYYY-MM-DD" class="laydate-icon form-control layer-date" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.DateTime: //日期时间
                        html.push('      <input type="datetime" placeholder="' + pModel.title + ':YYYY-MM-DD hh:mm:ss" class="laydate-icon form-control layer-date" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Int: //整型
                    case DataType.Integer:
                        html.push('      <input type="number" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Float: //浮点
                    case DataType.Decimal:
                    case DataType.Number:
                    case DataType.Numeric:
                        html.push('      <input type="number" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Email: //email
                        html.push('      <input type="email" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Url: //url
                        html.push('      <input type="url" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Password: //密码
                        html.push('      <input type="password" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                    case DataType.Memo: //备注
                        html.push('      <textarea class="form-control" ' + pubProperty + '>' + pModel.editoptions.defaultValue + '</textarea>');
                        break;
                    default: //文本
                        html.push('      <input type="text" class="form-control" ' + pubProperty + ' value="' + pModel.editoptions.defaultValue + '"/>');
                        break;
                }

                return html.join('\n');
            }


            /**
             * 初始化 dom
             */
            function initDom(pObj) {
                //checkbox change
                $('.checkbox [data-type="checkbox"]').click(function () {
                    $(this).val(getCheckboxVal($(this)));
                });
            }

            /**
             * 取checkbox值
             */
            function getCheckboxVal(obj) {
                var val = obj.val();
                var checked = obj.prop('checked');
                switch (val.toUpperCase()) {
                    case 'Y':
                    case 'N':
                        return checked ? 'Y' : 'N';
                    case 'TRUE':
                    case 'FALSE':
                        return checked ? 'true' : 'false';
                    default: //1:0
                        return checked ? '1' : '0';
                }
                return val;
            }
        },
        /**
         * 获取最后数据
         */
        getData: function(){
            var tmpGrid = _GRID[$(this).prop('id')];
            tmpGrid.saveRow(tmpGrid.userdata.lastselected);
            return tmpGrid.getGridParam('data');
        }
    });

    $.gridhelper = {
        /**
         * 当前页面表格数据保存
         */
        saveData: function(){
            for(var p in _GRID){
                if (_GRID[p].userdata.lastselected> -1) _GRID[p].saveRow(_GRID[p].userdata.lastselected);
            }
        }
    };
})(jQuery);