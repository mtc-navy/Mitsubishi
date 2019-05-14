package io.mtc.common.utils;

/**
 * Created by majun on 16/8/20.
 */

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import io.mtc.common.exception.Http502Exception;
import io.mtc.common.exception.ServiceLayerException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("ALL")
@Service(value = "httpConnectionManager")
public class HttpConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionManager.class);

    /**
     * 连接池里的最大连接数
     */
    public static final int MAX_TOTAL_CONNECTIONS = 200;

    /**
     * 每个路由的默认最大连接数
     */
    public static final int MAX_ROUTE_CONNECTIONS = 100;

    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT = 100000;

    /**
     * 套接字超时时间
     */
    public static final int SOCKET_TIMEOUT = 100000;

    /**
     * 连接池中 连接请求执行被阻塞的超时时间
     */
    public static final long CONN_MANAGER_TIMEOUT = 300000;

    /**
     * http连接相关参数
     */
    private HttpParams parentParams;

    /**
     * 默认目标主机
     */
    private static HttpHost DEFAULT_TARGETHOST;

    // private static String DEFAULT_IP = "http://180.153.103.86";

    // private static int DEFAULT_PORT = 10050;

    /**
     * http线程池管理器
     */
    private static PoolingClientConnectionManager cm;

    static {
        // SchemeRegistry schemeRegistry = new SchemeRegistry();
        /*
         * schemeRegistry.register(new Scheme("http", DEFAULT_PORT,
         * PlainSocketFactory .getSocketFactory()));
         *
         * DEFAULT_TARGETHOST = new HttpHost( DEFAULT_IP, DEFAULT_PORT);
         */
        // cm = new
        // PoolingClientConnectionManager(schemeRegistry,1,TimeUnit.NANOSECONDS);

        cm = new PoolingClientConnectionManager();

        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        // cm.setMaxPerRoute(new HttpRoute(DEFAULT_TARGETHOST),
        // MAX_ROUTE_CONNECTIONS); // 设置对目标主机的最大连接数
    }

    /**
     * http客户端
     */
    private DefaultHttpClient httpClient;

    /**
     * 初始化http连接池，设置参数、http头等等信息
     */

    private static X509TrustManager tm = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    };

    @SuppressWarnings("deprecation")
    public static DefaultHttpClient getClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            client = new DefaultHttpClient(ccm, client.getParams());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return client;
    }

    @SuppressWarnings("unused")
    private DefaultHttpClient getClientOld() {
        parentParams = new BasicHttpParams();
        parentParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);

        parentParams
                .setParameter(ClientPNames.DEFAULT_HOST, DEFAULT_TARGETHOST); // 设置默认targetHost

        parentParams.setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);

        parentParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
                CONN_MANAGER_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                CONNECT_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.SO_TIMEOUT,
                SOCKET_TIMEOUT);

        parentParams.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        parentParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);

        // 设置头信息,模拟浏览器

        Collection<BasicHeader> collection = new ArrayList<BasicHeader>();
        collection
                .add(new BasicHeader("User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"));
        collection
                .add(new BasicHeader("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8"));
        collection.add(new BasicHeader("Accept-Language",
                "zh-cn,zh,en-US,en;q=0.5"));
        collection.add(new BasicHeader("Accept-Charset",
                "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7"));
        collection.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));

        parentParams.setParameter(ClientPNames.DEFAULT_HEADERS, collection);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 5) {
                    // 如果超过最大重试次数，那么就不要继续了
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // 不要重试SSL握手异常
                    return false;
                }
                HttpRequest request = (HttpRequest) context
                        .getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 如果请求被认为是幂等的，那么就重试
                    return true;
                }
                return false;
            }
        };

        httpClient = new DefaultHttpClient(cm, parentParams);

        httpClient.setHttpRequestRetryHandler(httpRequestRetryHandler);

        httpClient.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);

        return httpClient;
    }

    protected static List<String> doHttpPatch(String url, String content, String encoding, String sessionId) throws Exception {
        return doHttp(new HttpPatch(url), content, encoding, sessionId);
    }


    protected static List<String> doHttpGet(String url, String charset, String sessionId) throws Exception {
        return doHttp(new HttpGet(url), null, charset, sessionId);
    }

    protected static List<String> doHttpGet(String url, String charset, Credentials credentials) throws Exception {
        return doHttp(new HttpGet(url), null, charset, null, credentials);
    }

    protected static List<String> doHttpPost(String url, String content, String encoding, String sessionId) throws Exception {
        return doHttp(new HttpPost(url), content, encoding, sessionId);
    }

    protected static List<String> doHttpPost(String url, String content, String encoding, Credentials credentials) throws Exception {
        return doHttp(new HttpPost(url), content, encoding, null, credentials);
    }

    protected static List<String> doHttpPut(String url, String content, String encoding, String sessionId) throws Exception {
        return doHttp(new HttpPut(url), content, encoding, sessionId);
    }

    protected static List<String> doHttpPut(String url, String content, String encoding, Credentials credentials) throws Exception {
        return doHttp(new HttpPut(url), content, encoding, null, credentials);
    }

    protected static List<String> doHttpDelete(String url, String content, String encoding, String sessionId) throws Exception {
        return doHttp(new HttpDelete(url), content, encoding, sessionId);
    }

    protected static List<String> doHttpDelete(String url, String content, String encoding, Credentials credentials) throws Exception {
        return doHttp(new HttpDelete(url), content, encoding, null, credentials);
    }

    public static byte[] getImg(String url) {
        HttpGet httpMethod = new HttpGet(url);
        BufferedReader reader = null;

        httpMethod.addHeader("Accept-Charset", "utf-8");
        httpMethod.addHeader("Connection", "close");
        httpMethod.addHeader("Content-Type", "application/JSON");
        DefaultHttpClient httpClient = getClient();
        InputStream inputStream = null;
        try {
            HttpResponse response = httpClient.execute(httpMethod);
            if (response.getStatusLine().getStatusCode() == 204) {
            }
            HttpEntity entity = response.getEntity();
            byte[] result = EntityUtils.toByteArray(entity);
            logger.info("response.getStatusLine().getStatusCode():"
                    + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                logger.info("response.getStatusLine().getStatusCode():"
                        + response.getStatusLine().getStatusCode());
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            logger.error("doHttpPost().UnsupportedEncodingException=", e);
        } catch (ClientProtocolException e) {
            logger.error("doHttpPost().ClientProtocolException=", e);
        } catch (IOException e) {
            logger.error("doHttpPost().IOException=", e);
        } catch (Exception e) {
            logger.error("doHttpPost().Exception=", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    logger.error("inputStream.close()", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    logger.error("inputStream.close()", e);
                }
            }
            httpMethod.releaseConnection();
            httpClient.clearRequestInterceptors();
            httpClient.clearResponseInterceptors();
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().closeIdleConnections(1,
                    TimeUnit.NANOSECONDS);
        }
        return null;
    }

    private static List<String> doHttp(HttpRequestBase httpMethod, String content, String encoding, String sessionId) throws Exception {
        return doHttp(httpMethod, content, encoding, sessionId, null);
    }

    private static List<String> doHttp(HttpRequestBase httpMethod, String content, String encoding, String sessionId, Credentials credentials) throws Exception {
        logger.debug("url:"+httpMethod.getURI());
        List<String> result = new ArrayList<String>();
        BufferedReader reader = null;

        //httpMethod.addHeader("Accept-Charset",encoding);
        //httpMethod.addHeader("Connection", "close");
        httpMethod.addHeader("Content-Type", "application/JSON");
        if (httpMethod instanceof HttpGet && StringUtils.isNotEmpty(sessionId)) {
            httpMethod.addHeader("Prefer", "odata.maxpagesize=50");
        }
        if (StringUtils.isNotEmpty(sessionId)) {
            httpMethod.addHeader("Cookie", "B1SESSION=" + sessionId);
        }
        httpMethod.addHeader("B1S-ReplaceCollectionsOnPatch", "true");
        httpMethod.addHeader("Language", "15");
        DefaultHttpClient httpClient = getClient();
        InputStream inputStream = null;
        try {
            //测试502错误使用
           /* if(httpMethod instanceof HttpPost && httpMethod.getURI().toString().contains("/Orders")){
                throw new Http502Exception("Http 502 Error");
            }*/
            if (credentials != null) {
                String str = credentials.getUserPrincipal().getName() + ":" + credentials.getPassword();
                byte[] encodedAuth = Base64.encodeBase64(str.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                httpMethod.addHeader("Authorization", authHeader);
            }

            if (StringUtils.isNotEmpty(content)) {
                logger.debug("content:"+content);
                StringEntity stringEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
                if (httpMethod instanceof HttpEntityEnclosingRequestBase) {
                    HttpEntityEnclosingRequestBase httpEnclosingRequestMethod = (HttpEntityEnclosingRequestBase) httpMethod;
                    httpEnclosingRequestMethod.setEntity(stringEntity);
                }
            }
            HttpResponse response = httpClient.execute(httpMethod);
            if (response.getStatusLine().getStatusCode() == 204) {
                logger.info("response.getStatusLine().getStatusCode():"
                        + response.getStatusLine().getStatusCode());
                return result;
            }
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            // 获取返回的数据信息
            reader = new BufferedReader(new InputStreamReader(inputStream,
                    encoding));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.add(line);
                logger.debug(line);
            }
            inputStream.close();
            inputStream = null;
            reader.close();
            reader = null;
            logger.info("response.getStatusLine().getStatusCode():"
                    + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                String json = toResult(result);
                if (response.getStatusLine().getStatusCode() == 400 ||
                        response.getStatusLine().getStatusCode() == 404) {
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    jsonObject = jsonObject.getJSONObject("error");
                    jsonObject = jsonObject.getJSONObject("message");
                    String value = String.valueOf(jsonObject.get("value"));
                    throw new ServiceLayerException("Service Layer Error:" + value);
                }
                if(response.getStatusLine().getStatusCode() == 502){
                    logger.error("error", result);
                    throw new Http502Exception("Http 502 Error");
                }
                logger.error("error", result);
                logger.info("response.getStatusLine().getStatusCode():"
                        + response.getStatusLine().getStatusCode());
                throw new Exception(json);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("doHttpPost().UnsupportedEncodingException=", e);
        } catch (ClientProtocolException e) {
            logger.error("doHttpPost().ClientProtocolException=", e);
        } catch (IOException e) {
            if (e.getMessage().equalsIgnoreCase("Read timed out")) {
                result.add("success");
                return result;
            }
            logger.error("doHttpPost().IOException=", e);
        } catch (ServiceLayerException se) {
            logger.error("doHttpPost().Exception=", se);
            throw se;
        } catch (Http502Exception he){
            logger.error("doHttpPost().Exception=", he);
            throw he;
        }catch (Exception e) {
            logger.error("doHttpPost().Exception=", e);
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    logger.error("inputStream.close()", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    logger.error("inputStream.close()", e);
                }
            }
            httpMethod.releaseConnection();
            httpClient.clearRequestInterceptors();
            httpClient.clearResponseInterceptors();
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().closeIdleConnections(1,
                    TimeUnit.NANOSECONDS);
        }
        return result;
    }

    protected static String toResult(List<String> results) {
        if (CollectionUtils.isNotEmpty(results)) {
            StringBuffer result = new StringBuffer();
            for (String s : results) {
                result.append(s);
            }
            return result.toString();
        }
        return null;
    }

}

