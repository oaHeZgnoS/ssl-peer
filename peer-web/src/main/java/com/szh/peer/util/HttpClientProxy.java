package com.szh.peer.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class HttpClientProxy {


    private static String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
            .setConnectionRequestTimeout(60000).setCookieSpec(CookieSpecs.STANDARD_STRICT).
                    setExpectContinueEnabled(true).
                    setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).
                    setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

    public static String get(String url) {
        return get(null, url);
    }

    public static String get(HttpHost proxy, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(60 * 1000) //设置连接超时时间
                .setConnectionRequestTimeout(60 * 1000) // 设置请求超时时间
                .setSocketTimeout(60 * 1000).setRedirectsEnabled(true);//默认允许自动重定向
        if (proxy != null) {
            builder.setProxy(proxy);//配置代理
        }
        RequestConfig requestConfig = builder.build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("", "");
        httpGet.setHeader("User-Agent", userAgent); //设user agent
        httpGet.setHeader("cookie", "SUB=_2AkMW7fkwf8NxqwJRmP4Qy27hao93yQrEieKgsQjrJRMxHRl-yT9jqm4ptRB6PW3X36GgosbgJiDe4xNU1ILhS1HGZUUO; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WFIQcbEdLcSZmGbI5ySb-AR; XSRF-TOKEN=7woEBww_XXwByWfVQ6SVsk8t; _s_tentry=weibo.com; Apache=9014704008460.703.1639361182152; SINAGLOBAL=9014704008460.703.1639361182152; ULV=1639361182178:1:1:1:9014704008460.703.1639361182152:; WBPSESS=kErNolfXeoisUDB3d9TFHw-0GHeOfGU_FP5MIZSK74TL5WyKGAdb6L8Vo1BMiVaTe5LB-smmZhh1cGD7NE-OIi3IfR7QATkYa_FSLF1lO-NTG5bz5iNlPYFgQ_vv5ndVoaHU8-pBWGV5ubffYsmwGYsWhKmPJOjKyq7iJ96DKv0=");
        httpGet.setConfig(requestConfig);
        String strResult = "";
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
                return strResult;
            } else {
                System.out.println(httpResponse.getStatusLine().getStatusCode() + "\t" + url); //访问异常
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    public static String getHttps(HttpHost proxy, String url) {

        enableSSL();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("cookie", "UOR=www.miliol.org,widget.weibo.com,www.techug.com; SINAGLOBAL=1231198409666.967.1627475627512; ULV=1628750921250:2:1:1:9996353930739.92.1628750921246:1627475628110; SUB=_2AkMW672ddcPxrABYn_scyG_na45H-jylPtRrAn7uJhMyAxgP7mxfqSVutBF-XGwLULnqa6AgD5PThZ7Riz7Iacjk; SUBP=0033WrSXqPxfM72wWs9jqgMF55529P9D9WhIo0PuZ0VM6.y7qI8gYKZM5JpV2K2Xe0B4eon7e0W5MP2Vqcv_; XSRF-TOKEN=1tvT9DZ6p76cyVTCQOyVmnZE; WBPSESS=Dt2hbAUaXfkVprjyrAZT_GXiQdfn-KTzVH1-rjHugyo9cAJVUwNtKW1X7MyIVixiDW52YumDTye9XxRAo-iSAmWGuZDFpr95Te2cCqGtAiH_zMlyDy0gXY6hTk5cyZbhm4kIf7Pv_cxx6Zl3W5z_jA==");
        //httpGet.setHeader(HttpHeaders.HOST, "d.weibo.com");
        httpGet.setHeader("User-Agent", userAgent); //设user agent
        CloseableHttpResponse response = null;
        String strResult = "";
        try {
            Thread.sleep(1500l); // 防止过于频繁导致拒绝请求
            response = httpClient.execute(httpGet, context);
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity());//获得返回的结果
                return strResult;
            } else {
                System.out.println(response.getStatusLine().getStatusCode() + "\t" + url); //访问异常
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "https://mvnrepository.com/artifact/" + "org.jpmml" + "/" + "pmml-evaluator-extension" + "/" + "1.4.12";
        String s = HttpClientProxy.getHttps(null, url);
        System.out.println(s);
    }

    private static HttpClientContext context = HttpClientContext.create();

    private static SSLConnectionSocketFactory socketFactory;
    private static TrustManager manager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    };
    private static void enableSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{manager}, null);
            socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
