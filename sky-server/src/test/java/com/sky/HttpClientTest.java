package com.sky;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Entity;

import java.io.IOException;

@SpringBootTest
public class HttpClientTest {

    @Test
    public void testHttpCliengGet() throws IOException {

        //创建httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求对像
        HttpGet httpGet = new HttpGet("http://www.163.com");

        //发送请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            httpClient.close();
        }

    }

    @Test
    public void testHttpCliengPost() throws IOException {

        //创建httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求对像
        HttpPost httpPost= new HttpPost("http://localhost:8090/admin/employee/login");

        //设置请求参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","123456");

        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);
        //发送请求
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {
            httpClient.close();
        }

    }
}
