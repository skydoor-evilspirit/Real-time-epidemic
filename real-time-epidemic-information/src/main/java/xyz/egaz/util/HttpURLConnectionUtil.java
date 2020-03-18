package xyz.egaz.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 这个类的作用是解析获取到的json信息，将有用的信息转换为对象，返回给service
 */
public class HttpURLConnectionUtil {

    public static String getUrl(String urlStr) {
        StringBuilder result = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            //创建远程url对象
            URL url = new URL(urlStr);
//            打开conn连接通道
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            if (200 == conn.getResponseCode()) {
                inputStream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String s = reader.readLine();
                result = new StringBuilder();
                while (s != null && !"".equals(s)) {
                    result.append(s);
                    s = reader.readLine();
                }
            } else {
                System.out.println("返回了错误码：" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
