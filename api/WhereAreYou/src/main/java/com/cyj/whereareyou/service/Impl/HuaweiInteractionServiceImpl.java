package com.cyj.whereareyou.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.entity.huaweiserverreturn.TokenReturn;
import com.cyj.whereareyou.entity.huaweiserverreturn.UserInfo;
import com.cyj.whereareyou.service.HuaweiInteractionService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 21:24
 */
@Service
public class HuaweiInteractionServiceImpl implements HuaweiInteractionService {
    @Override
    public TokenReturn getAccessToken(String code) {
        String urlToken = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";
        String grant_type = "authorization_code";
        String redirect_uri = "https://api.jiangtao.website/api/hw_server_redirect";
        String client_id = "104924337";
        String client_secret = "f95ea9cc2fc06dc5ca05386371f02a78a6dac19930c11cc0e81def00d83c51ea";

        JSONObject tokens = null;
        try {
            tokens = getTokenByCode(redirect_uri, urlToken, code, client_secret, client_id, grant_type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TokenReturn(tokens.getString("access_token"), tokens.getString("refresh_token"));
    }

    @Override
    public TokenReturn refreshToken(String refreshToken) {
        String urlToken = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";
        String grant_type = "refresh_token";
        String client_id = "104924337";
        String client_secret = "f95ea9cc2fc06dc5ca05386371f02a78a6dac19930c11cc0e81def00d83c51ea";

        JSONObject tokens = null;
        try {
            tokens = refreshToken(urlToken, grant_type, client_id, client_secret, refreshToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TokenReturn(tokens.getString("access_token"), tokens.getString("refresh_token"));
    }

    @Override
    public UserInfo getUserInfo(String accessToken, int getNickName) {
        String urlToken = "https://account.cloud.huawei.com/rest.php?nsp_svc=GOpen.User.getInfo";

        JSONObject tokens = null;
        try {
            tokens = getUserInfo(urlToken, accessToken, getNickName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new UserInfo(tokens.getString("displayName"), tokens.getString("headPictureURL"), tokens.getString("openID"));
    }

    private JSONObject getUserInfo(String urlToken, String accessToken, int getNickName)  throws IOException {
        HttpPost httpPost = new HttpPost(urlToken);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("access_token", accessToken));
        request.add(new BasicNameValuePair("getNickName", String.valueOf(getNickName)));
        httpPost.setEntity(new UrlEncodedFormEntity(request));

        CloseableHttpResponse response = getClient().execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            String ret = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            JSONObject jsonObject = (JSONObject) JSON.parse(ret);
            EntityUtils.consume(responseEntity);
            return jsonObject;
        } finally {
            response.close();
        }
    }

    private JSONObject refreshToken(String urlToken, String grant_type, String client_id, String client_secret, String refreshToken) throws IOException {
        HttpPost httpPost = new HttpPost(urlToken);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("refresh_token", refreshToken));
        request.add(new BasicNameValuePair("client_secret", client_secret));
        request.add(new BasicNameValuePair("client_id", client_id));
        request.add(new BasicNameValuePair("grant_type", grant_type));
        httpPost.setEntity(new UrlEncodedFormEntity(request));

        CloseableHttpResponse response = getClient().execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            String ret = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            JSONObject jsonObject = (JSONObject) JSON.parse(ret);
            EntityUtils.consume(responseEntity);
            return jsonObject;
        } finally {
            response.close();
        }
    }

    private static JSONObject getTokenByCode(String redirect_uri, String urlToken, String code, String client_secret,String client_id, String grant_type) throws IOException {
        HttpPost httpPost = new HttpPost(urlToken);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        request.add(new BasicNameValuePair("code", code));
        request.add(new BasicNameValuePair("client_secret", client_secret));
        request.add(new BasicNameValuePair("client_id", client_id));
        request.add(new BasicNameValuePair("grant_type", grant_type));
        httpPost.setEntity(new UrlEncodedFormEntity(request));

        CloseableHttpResponse response = getClient().execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            String ret = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            JSONObject jsonObject = (JSONObject) JSON.parse(ret);
            EntityUtils.consume(responseEntity);
            return jsonObject;
        } finally {
            response.close();
        }
    }

    private static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connectionManager = buildConnectionManager("TLSv1.2", new String[]{"TLSv1.2","TLSv1.1"},
                new String[]{"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA"});
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(400);
        RequestConfig config =
                RequestConfig.custom().setConnectionRequestTimeout(100).setRedirectsEnabled(false).build();

        return HttpClients.custom()
                .useSystemProperties()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();
    }

    private static PoolingHttpClientConnectionManager buildConnectionManager(String protocol,
                                                                             String[] supportedProtocols, String[] supportedCipherSuites) {
        PoolingHttpClientConnectionManager connectionManager = null;
        try {
            SSLContext sc = SSLContext.getInstance(protocol);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            sc.init(null, tmf.getTrustManagers(), null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc, supportedProtocols,
                    supportedCipherSuites, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();
            connectionManager = new PoolingHttpClientConnectionManager(registry);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return connectionManager;
    }
}
