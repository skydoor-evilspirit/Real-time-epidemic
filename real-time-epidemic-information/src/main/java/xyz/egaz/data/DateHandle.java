package xyz.egaz.data;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.egaz.bean.DataBean;
import xyz.egaz.bean.GraphBean;
import xyz.egaz.service.DataService;
import xyz.egaz.util.HttpClientUtil;
import xyz.egaz.util.HttpURLConnectionUtil;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * 这个类是用来发送get请求，获取json信息的类
 */
@Component
public class DateHandle {

    private static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
    @Autowired
    private DataService service;

    @PostConstruct//设置开机扫描
    public void saveData(){
        System.out.println("初始化数据。。。");
        List<DataBean> list = getDate();
        //先清空，再保留最新的。。。
        service.remove(null);
        service.saveBatch(list);
    }
    @Scheduled(cron = "0 0 * * * ?")
    public void cron(){//设置定时扫描设置数据库
        System.out.println("重新初始化数据。。。");
        List<DataBean> list = getDate();
        //先清空，再保留最新的。。。
        service.remove(null);
        service.saveBatch(list);
    }

    public static ArrayList<DataBean> getDate() {
        //连接网站，获取json字符串
        String information = HttpURLConnectionUtil.getUrl(urlStr);
//        解析json字符串
        Gson gson = new Gson();
        Map map = gson.fromJson(information, Map.class);
//        拿出date字符串
        information = (String) map.get("data");
//        将date字符串解析成对象
        map = gson.fromJson(information, Map.class);
//        从date对象中，拿出areaTree地区树对象
        ArrayList<Map<String, String>> array = (ArrayList<Map<String, String>>) map.get("areaTree");
//        从中拿出中国的对象
        Map chinaMap = array.get(0);
        //从中国的对象中拿出所有省的对象
        ArrayList list = (ArrayList) chinaMap.get("children");
//        创建出来一个专门用来接收datebean的list集合
        ArrayList<DataBean> beans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
//            将每一个省的信息都拿出来
            Map totalMap = (Map) list.get(i);
            String name = (String) totalMap.get("name");//拿省的名字
            Map today = (Map) totalMap.get("today");
            double confirmAdd = (double) today.get("confirm");
            Map numMap = (Map) totalMap.get("total");
            double confirm = (double) numMap.get("confirm");
            double suspect = (double) numMap.get("suspect");
            double dead = (double) numMap.get("dead");
            double heal = (double) numMap.get("heal");
            DataBean dataBean = new DataBean();
            dataBean.setArea(name);
            dataBean.setConfirmAdd((int) confirmAdd);
            dataBean.setConfirm((int) confirm);
            dataBean.setSuspect((int) suspect);
            dataBean.setDead((int) dead);
            dataBean.setHeal((int) heal);
            beans.add(dataBean);
        }
        System.out.println(beans);
        return beans;
    }

    public static List<GraphBean> getGraph() throws IOException {
        String data = HttpClientUtil.getData("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5&_=1583166559282");
        //        解析json字符串
        Gson gson = new Gson();
        Map map = gson.fromJson(data, Map.class);
//        拿出date字符串
        data = (String) map.get("data");
        Map dataObj = gson.fromJson(data, Map.class);
        //获取数据的最新时间
        String lastUpdateTime = (String) dataObj.get("lastUpdateTime");
        //获取中国今天数据的集合
        ArrayList chinaDayList = (ArrayList) dataObj.get("chinaDayList");
        //专门拿出一个集合来存组合好的对象
        ArrayList<GraphBean> graphBeans = new ArrayList<>();
        for (int i = 0; i < chinaDayList.size(); i++) {
            Map tmp = (Map) chinaDayList.get(i);
            String date = (String) tmp.get("date");
            Double confirm = (Double) tmp.get("confirm");
            Double suspect = (Double) tmp.get("suspect");
            GraphBean graphBean = new GraphBean(null, date, confirm, suspect);
            graphBeans.add(graphBean);
        }
        return graphBeans;
    }



}
