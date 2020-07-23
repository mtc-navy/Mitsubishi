//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg,function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

function alertV2(msg,options,callback){
    parent.layer.alert(msg,options,function(index){
        parent.layer.close(index);
        if(typeof(callback) === "function"){
            callback("ok");
        }
    });
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}
function getListSelectedRow() {
    var grid = $("#jqGridList");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    return selectedIDs[0];
}
//选择多条记录
function getSelectedRows(id) {
    var grid = $("#jqGrid");
    /*var rowKey = grid.getGridParam("selrow");
    console.log(rowKey);
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");*/
    var keys = grid.jqGrid('getGridParam', 'selarrrow');
    if(!keys || keys.length ==0 ){
        alert("请选择一条记录");
        return ;
    }
    var ids = [];
    for(var i=0;i<keys.length;i++){
        var rowData = grid.jqGrid('getRowData', keys[i]);
        if(typeof id == 'undefined'){
            ids.push(rowData.DOCENTRY);
        }else{
            ids.push(rowData[id]);
        }
    }
   	return ids;
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

/**
 * 自动完成填充
 * @param id
 * @param result
 * @param afterSelect
 */
function autoComplete(id,result,callback){
	$(id).typeahead({
		source: result,
		autoSelect: true,
		showHintOnFocus:true,
		items:"20",
		fitToElement:true,
		updater:function(obj){
			if(typeof callback != 'undefined'){
				return callback(obj);
			}
			return obj;
		},
		/*afterSelect:function(item){
			if(typeof callback != 'undefined'){
				callback(item);
			}
		}*/
	});
}

function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}


function Add(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)), (Multiply(a, e) + Multiply(b, e)) / e;

}
//修改之后的减法
function Minus(arg1, arg2) {
	var r1, r2, m, n;
	try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
	try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
	m = Math.pow(10, Math.max(r1, r2));
	//动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
//修改之后的乘法
function Multiply(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try { m += s1.split(".")[1].length } catch (e) { }
	try { m += s2.split(".")[1].length } catch (e) { }
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
//修改之后的除法
function Division(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
	try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
//格式化日期
function GetYMD(date) {
    date=new Date(date);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var nowDate = year + "-" + month + "-" + day;
    return nowDate;

}
//判断是否为数字
function isNumber(val){
    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }l

}
//取明细页面列表中可显示的最大行数
function GetLimit() {
	return 1000;
	
}

function getTableHeight(){
	return $(window).height()<400?400:$(window).height()-350;
}

function replaceAll (str,FindText, RepText) {
    var regExp = new RegExp(FindText, "g");
    return str.replace(regExp, RepText);
}
