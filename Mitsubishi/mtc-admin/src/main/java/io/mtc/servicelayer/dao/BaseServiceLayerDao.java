package io.mtc.servicelayer.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import io.mtc.common.exception.Http502Exception;
import io.mtc.common.exception.ServiceLayerException;
import io.mtc.common.utils.HttpUtil;
import io.mtc.modules.sys.shiro.ShiroUtils;
import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.ListResult;
import io.mtc.servicelayer.model.Order;
import io.mtc.servicelayer.model.QueryParam;
import io.mtc.servicelayer.model.Session;
import javassist.tools.web.BadHttpRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by majun on 2018/9/3.
 */
@Component
public class BaseServiceLayerDao<MODEL> {

    private static final Log logger = LogFactory
            .getLog(BaseServiceLayerDao.class);


    private String SESSION_KEY = "SESSION_KEY";

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Value("${sap.servicelayer.url}")
    private String url;

    @Value("${sap.servicelayer.username}")
    private String username;

    @Value("${sap.servicelayer.password}")
    private String password;

    @Value("${sap.servicelayer.company}")
    private String company;

    public Session login() throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put("UserName", username);
        properties.put("Password", password);
        properties.put("CompanyDB", company);
        String result = HttpUtil.post(url + "/Login", properties);
        Session session = JSONObject.parseObject(result, Session.class);
        return session;
    }

    public void logout() throws Exception {
        //开启线程调用logout方法
        String sessionId = getSessionId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil.post(url + "/Logout", "", sessionId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //删除当前session
        sessionMap.remove(getSessionKey());
    }

    public MODEL save(MODEL obj) throws Exception {
        return save(obj, 1);
    }

    private MODEL save(MODEL obj, int time) throws Exception {
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(obj);
            String result = HttpUtil.post(getWholeUrl(), jsonString, getSessionId());
            MODEL re = (MODEL) JSONObject.parseObject(result, obj.getClass());
            logout();
            return re;
        } catch (Http502Exception e) {
            //如果报502错误，尝试登出，休眠5秒后再次提交
            logout();
            if (time >= 5) {
                throw new ServiceLayerException("ServiceLayer 502 错误，已尝试" + time + "次均失败");
            }
            time++;
            Thread.sleep(5000);
            return this.save(obj, time);
        } catch (BadHttpRequest e) {
            //如果报400,再次请求
            logout();
            if (time >= 5) {
                throw new ServiceLayerException("ServiceLayer 400 错误，已尝试" + time + "次均失败");
            }
            time++;
            Thread.sleep(5000);
            return this.save(obj, time);
        }
    }

    public MODEL getObjectById(Object id, Class<MODEL> clazz) throws Exception {
        String url = getWholeUrl();
        if (id instanceof String) {
            url += "('" + id + "')";
        } else {
            url += "(" + id + ")";
        }
        String result = HttpUtil.get(url, getSessionId());
        MODEL model = JSONObject.parseObject(result, clazz);
        logout();
        return model;
    }

    public void delete(Object id) throws Exception {
        String url = getWholeUrl();
        if (id instanceof String) {
            url += "('" + id + "')";
        } else {
            url += "(" + id + ")";
        }
        HttpUtil.delete(url, getSessionId());
        logout();
    }

    public void cancel(Object id) throws Exception {
        String url = getWholeUrl();
        if (id instanceof String) {
            url += "('" + id + "')";
        } else {
            url += "(" + id + ")";
        }
        url += "/Cancel";
        HttpUtil.post(url, "", getSessionId());
        logout();
    }

    public void close(Object id) throws Exception {
        String url = getWholeUrl();
        if (id instanceof String) {
            url += "('" + id + "')";
        } else {
            url += "(" + id + ")";
        }
        url += "/Close";
        HttpUtil.post(url, "", getSessionId());
        logout();
    }

    public void createCancellationDocument(Object id) throws Exception {
        String url = getWholeUrl();
        if (id instanceof String) {
            url += "('" + id + "')";
        } else {
            url += "(" + id + ")";
        }
        url += "/CreateCancellationDocument";
        String rst = HttpUtil.post(url, "", getSessionId());
        HttpUtil.post(getWholeUrl(), rst, getSessionId());
        logout();
    }


    public void update(Object id, MODEL model) throws Exception {

        String query = getWholeUrl();
        if (id instanceof String) {
            query += "('" + id + "')";
        } else {
            query += "(" + id + ")";
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(model);
        String result = HttpUtil.update(query, jsonString, getSessionId());
        logout();
        if (StringUtils.isNotEmpty(result)) {
            throw new Exception(result);
        }
    }

    public void patch(Object id, MODEL model) throws Exception {
        String query = getWholeUrl();
        if (id instanceof String) {
            query += "('" + id + "')";
        } else {
            query += "(" + id + ")";
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(model);
        String result = HttpUtil.patch(query, jsonString, getSessionId());
        logout();
        if (StringUtils.isNotEmpty(result)) {
            throw new Exception(result);
        }
    }

    public List<MODEL> queryList(Class<MODEL> clazz) throws Exception {
        return queryList(clazz, null);
    }

    public List<MODEL> queryList(Class<MODEL> clazz, QueryParam param) throws Exception {
        ListResult<MODEL> result = queryInner(clazz, param);
        return result == null ? Collections.EMPTY_LIST : result.getValue();
    }

    public Page<MODEL> queryPage(int pageNum, int pageSize, Class<MODEL> clazz, QueryParam param) throws Exception {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        param.setTop(pageSize);
        param.setSkip((pageNum - 1) * pageNum);
        ListResult<MODEL> result = queryInner(clazz, param);
        Page<MODEL> page = new Page<>();
        page.setTotal(result.getTotal());
        page.setRecords(result.getValue());
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        logout();
        return page;
    }

    private ListResult queryInner(Class<MODEL> clazz, QueryParam param) throws Exception {
        String query = getWholeUrl();

        if (param != null) {
            String paramstr = "?$inlinecount=allpages";
            if (StringUtils.isNotEmpty(param.getSelect())) {
                paramstr += "&$select=" + URLEncoder.encode(param.getSelect(), "utf-8").replaceAll("[+]", "%20");
            }
            if (StringUtils.isNotEmpty(param.getFilter())) {
                paramstr += "&$filter=" + URLEncoder.encode(param.getFilter(), "utf-8").replaceAll("[+]", "%20");
            }
            if (StringUtils.isNotEmpty(param.getOrderby())) {
                paramstr += "&$orderby=" + URLEncoder.encode(param.getOrderby(), "utf-8").replaceAll("[+]", "%20");
            }
            if (param.getTop() != null) {
                paramstr += "&$top=" + param.getTop();
            }
            if (param.getSkip() != null) {
                paramstr += "&$skip=" + param.getSkip();
            }

            query += paramstr;
        }
        String result = HttpUtil.get(query, getSessionId());
        ListResult<MODEL> list = JSONObject.parseObject(result, getType(ListResult.class, clazz));
        logout();
        return list;
    }

    private <T> ParameterizedType getType(final Class<T> raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }
        };
    }

    private String getWholeUrl() {
        Path path = this.getClass().getAnnotation(Path.class);
        if (path != null) {
            return url + path.value();
        }
        return url;
    }

    private String getSessionId() throws Exception {
        Session session = sessionMap.get(getSessionKey());
        //session不存在或session已过期
        if (session == null || session.getExpireTime() == null || session.getExpireTime().getTime() <= System.currentTimeMillis()) {
            session = login();
            sessionMap.put(getSessionKey(), session);
        }
        return session.getSessionId();
    }

    /**
     * 获取当前web中sessionKey，每个登录使用不同的servicey layer session
     *
     * @return
     */
    private String getSessionKey() {
        return SESSION_KEY + ShiroUtils.getSession().getId();
    }

}
