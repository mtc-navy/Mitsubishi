
function httpGet(url,params,success,error){
    axios.get(baseURL+url,{params:params})
        .then(function (response) {
            if(response.data.code != 0){
                if(typeof error != 'undefined'){
                    error(response.data);
                    return;
                }else{
                    alert(response.data.msg);
                    return;
                }
            }else{
                success(response.data);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}
function httpGetInfo(url,params,success,error){
    var index = layer.load(1, {
        shade: [0.5,'#fff']
    });
    axios.get(baseURL+url,{params:params})
        .then(function (response) {
            if(response.data.code != 0){
                if(typeof error != 'undefined'){
                    error(response.data);
                    layer.close(index);
                    return;
                }else{
                    alert(response.data.msg);
                    layer.close(index);
                    return;
                }
            }else{
                success(response.data);
                layer.close(index);
            }
        })
        .catch(function (error) {
            console.log(error);
            layer.close(index);
        });
}

/**
 * 同步ajax方法
 * @param url
 * @param params
 */
function httpGetSync(url,params){
    var result = {};
    $.ajax({
        type: "GET",
        url: baseURL + url,
        contentType: "application/json",
        data: params,
        async: false,
        success: function (r) {
            if (r.code != 0) {
                alert(r.msg);
            }else{
            }
            result = r;
        }
    });
    return result;
}

function httpPost(url,params,success,error){
    var index = layer.load(1, {
        shade: [0.5,'#fff']
    });
    axios.post(baseURL+url,params)
        .then(function (response) {
            if(response.data.code != 0){
                if(typeof error != 'undefined'){
                    error(response.data);
                }else{
                    alert(response.data.msg);
                }
                layer.close(index);
            }else{
                success(response.data);
                layer.close(index);
            }
        })
        .catch(function (error) {
            console.log(error);
            layer.close(index);
        });
}

function httpPostFormData(url,params,success,error){
    var index = layer.load(1, {
        shade: [0.5,'#fff']
    });
    axios.post(baseURL+url,null,{
        data:params,
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        },
        transformRequest: [function (data) {
            var ret = ''
                for (var it in data) {
                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                }
                return ret
            }],
        })
        .then(function (response) {
            if(response.data.code != 0){
                if(typeof error != 'undefined'){
                    error(response.data);
                }else{
                    alert(response.data.msg);
                }
                layer.close(index);
            }else{
                success(response.data);
                layer.close(index);
            }
        })
        .catch(function (error) {
            console.log(error);
            layer.close(index);
        });
}