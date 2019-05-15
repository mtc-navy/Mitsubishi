

var vm = new Vue({
    el: '#rrapp',
    data: {
    },
    methods: {

    },
    mounted:function(){
        var docEntry = T.p('docEntry');
        var type = T.p('type');
        var docType = T.p('docType');
        var vaildPrintUrl = 'sap/saleinv/vaildPrint/'+docEntry;
        var printUrl = baseURL+'statics/plugins/pdf/web/viewer.html?file=/sap/saleinv/print/'+docEntry;
        if(type != null){
            vaildPrintUrl = vaildPrintUrl+"/"+type;
            printUrl = printUrl+"/"+type;
        }

        httpGetInfo(vaildPrintUrl,{docType:docType},function(){
            window.location.href = printUrl;
        },function(result){
            alert(result.msg,function(){
                window.close();
            });
        });
    }
});