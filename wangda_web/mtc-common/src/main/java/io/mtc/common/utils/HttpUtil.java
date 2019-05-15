package io.mtc.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by jun_ma on 2016/4/14.
 */
public class HttpUtil extends HttpConnectionManager {

    public static String get(String url, String sessionId) throws Exception {
        return get(url,"utf-8",sessionId);
    }

    public static String get(String url, String charset, String sessionId) throws Exception {
        List<String> results = doHttpGet(url,charset,sessionId);
        return toResult(results);
    }

    public static String post(String url, Map<String,String> params) throws Exception {
        return post(url,params,"utf-8",null);
    }

    public static String post(String url, Map<String,String> params, String sessionId) throws Exception {
        return post(url,params,"utf-8",sessionId);
    }

    public static String post(String url, Map<String, String> params,
                              String charset, String sessionId) throws Exception {
        return post(url,JSONObject.toJSONString(params),charset,sessionId);
    }

    public static String post(String url, String content, String sessionId) throws Exception {
        return post(url,content,"utf-8",sessionId);
    }

    public static String post(String url, String content, String charset, String sessionId) throws Exception {
        List<String> results = doHttpPost(url,content,charset,sessionId);
        return toResult(results);
    }

    public static String patch(String url,String content,String sessionId) throws Exception {
        List<String> results = doHttpPatch(url,content,"UTF-8",sessionId);
        String result = "";
        for(String s : results){
            result += s;
        }
        return result;
    }

    public static String update(String url,String content,String sessionId) throws Exception {
        List<String> results = doHttpPut(url,content,"UTF-8",sessionId);
        return toResult(results);
    }

    public static String delete(String url,String sessionId) throws Exception {
        List<String> results = doHttpDelete(url,null,"UTF-8",sessionId);
        return toResult(results);
    }


}
