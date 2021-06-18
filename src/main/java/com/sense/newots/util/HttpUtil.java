package com.sense.newots.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by session on 2018/7/20 9:33.
 * 功能：
 */
@Slf4j
public class HttpUtil {

    //private static Logger logger = Logger.getLogger(HttpUtil.class);

    public static String post(String url, Map<String, Object> map) throws Exception {
//        if(url == null)
        String res = null;
//        log.info("HttpUtil--Post---"+url+"---"+map);
        //创建一个httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
//        logger.info("HttpUtil--testPost2---");
        //创建一个post对象
        HttpPost post = new HttpPost(url);

        //创建一个Entity，模拟表单数据
        List<NameValuePair> formList = new ArrayList<>();
        // 判断参数map是否为非空
        if (map != null) {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // 添加表单数据
                formList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()==null?null:entry.getValue().toString()));
            }
        }

        //包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");

        //设置请求的内容
        post.setEntity(entity);
//        logger.info("HttpUtil--testPost3---");
        //设置请求的报文头部的编码
        post.setHeader(
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

        //设置期望服务端返回的编码
//        post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
//        logger.info("HttpUtil--testPost4---");
        //执行post请求
        CloseableHttpResponse response = client.execute(post);
//        logger.info("HttpUtil--testPost5---");
        //获取响应码
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            //获取数据
            String resStr = EntityUtils.toString(response.getEntity());
            //输出
            res = resStr;
        } else {
            //输出
            log.info("请求失败,错误码为: " + statusCode);
            Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                log.info("错误信息："+headers[i]);
            }
            log.info("原因："+response.getStatusLine().getReasonPhrase());
            res = "2";
        }

        //关闭response和client
        response.close();
        client.close();
        return res;
    }
    /**
     * 带参数的get请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public void doGet(String url, Map<String, Object> map) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 声明URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 判断参数map是否为非空
        if (map != null) {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // 设置参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 创建httpGet对象，相当于设置url请求地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());
//        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
//        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=UTF-8"));
        // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        int statusCode = response.getStatusLine().getStatusCode();
        log.info("statusCode"+statusCode);
        if (statusCode == 200) {

            //获取返回实例entity
            HttpEntity entity = response.getEntity();

            //通过EntityUtils的一个工具方法获取返回内容
            String resStr = EntityUtils.toString(entity, "utf-8");

            //输出
            log.info("请求成功,请求返回内容为: " + resStr);
        } else {

            //输出
            log.info("请求失败,错误码为: " + statusCode);
            Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                log.info("错误信息："+headers[i]);
            }
            log.info(response.getStatusLine().getReasonPhrase());
        }
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        /*HttpResult httpResult = null;
        // 解析数据封装HttpResult
        if (response.getEntity() != null) {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        } else {
            httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
        }

        // 返回
        return httpResult;*/
    }
}