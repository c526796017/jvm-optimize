/**
 * FileName: HttpClientUtil
 * Author:   何潮
 * Date:     2018/12/17 13:44
 * Description: http请求工具类
 * History:
 * <author>          <time>                <version>          <desc>
 * 何潮           2018/12/17 13:44           1.0             创建版本
 */
package com.mama.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 * @author YHY
 * @create 2018/12/17 13:44
 * @since 1.0.0
 */
public class HttpClientUtil {

    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * HTTP Get 获取内容
     * @param url 请求的url地址 ?之前的地址，不能为空
     * @param params 请求的参数，可以为空
     * @param charset 请求编码格式，不能为空
     * @return 如果正常请求返回内容，否则返回空值
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws Exception{
        String result = "";
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(charset)) {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
            }else{
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
                EntityUtils.consume(entity);
            }
            response.close();
        }
        return result;
    }

    /**
     * HTTP Post 获取内容
     * @param url 请求的url地址 ?之前的地址，不能为空
     * @param params 请求的参数，可以为空
     * @param charset 请求编码格式，不能为空
     * @return 如果正常请求返回内容，否则返回空值
     */
    public static String doPost(String url, Map<String, String> params, String charset) throws Exception{
        String result = "";
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(charset)) {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }
            CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
            }else{
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
                EntityUtils.consume(entity);
            }
            response.close();
        }
        return result;
    }
    /**
     * HTTP Post 获取内容 (用于json格式请求数据)
     * @author ChenHaiTao
     * @date 2019/1/29 16:38No such property: code for class: Script1
     * @return 如果正常请求返回内容，否则返回空值
     */
    public static String doPost(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}