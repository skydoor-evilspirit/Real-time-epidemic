package xyz.egaz.util;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HttpClientUtil {


    public static String getData(String urlStr) throws IOException {
        //通过默认配置，创建一个httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        创建一个远程连接的实例
        HttpGet httpGet = new HttpGet(urlStr);
//        创建一个http的请求参数对象
        RequestConfig requestConfig = RequestConfig.custom()//设置config定制
                .setConnectionRequestTimeout(15000)//设置请求最长时间
                .setSocketTimeout(60000)//设置获取响应最长时间
                .build();
        //将请求参数放进get请求中
        httpGet.setConfig(requestConfig);
        //将get请求放进httpClient容器中,并且执行
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //从response对象中，获取entity信息对象
        HttpEntity entity = response.getEntity();
        //将返回结果变成字符串返回出来
        String result = EntityUtils.toString(entity);
        return result;
    }



}
