package xyz.egaz.data;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.egaz.bean.DataBean;
import xyz.egaz.service.DataService;
import xyz.egaz.service.serviceimpl.DataServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JsoupHandle {

    public static ArrayList<DataBean> getJsoupData() {
        Document document = null;
        ArrayList<DataBean> result = null;
        try {
            //连接丁香医生的网址
            Connection conn = Jsoup.connect("https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&clicktime=1579579384&enterid=1579579384&from=singlemessage&isappinstalled=0");
//        发送get请求
            document = conn.get();
//        获取指定id的元素
            Element ele = document.getElementById("getAreaStat");
//        获取元素内的信息
            String data = ele.data();
//        截取信息中的json数组字符串
            String subData = data.substring(data.indexOf("["), data.lastIndexOf("]") + 1);
//        将数组字符串解析成真正的list
            Gson gson = new Gson();
            ArrayList arrayList = gson.fromJson(subData, ArrayList.class);
            result = new ArrayList<>();
//        遍历list
            for (int i = 0; i < arrayList.size(); i++) {
                Map map = (Map) arrayList.get(i);
                String provinceName = (String) map.get("provinceName");//省名字
                double currentConfirmedCount = (double) map.get("currentConfirmedCount");//现如今感染人数
                double confirmedCount = (double) map.get("confirmedCount");//总共的感染人数
                double suspectedCount = (double) map.get("suspectedCount");//疑似感染人数
                double curedCount = (double) map.get("curedCount");//治疗人数
                double deadCount = (double) map.get("deadCount");//死亡人数
                DataBean dataBean = new DataBean(null, provinceName, 0, (int) confirmedCount, (int) suspectedCount, (int) deadCount, (int) curedCount);
                result.add(dataBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
