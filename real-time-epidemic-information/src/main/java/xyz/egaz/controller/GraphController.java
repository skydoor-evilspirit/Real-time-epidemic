package xyz.egaz.controller;

import com.google.gson.Gson;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.egaz.bean.GraphBean;
import xyz.egaz.data.DateHandle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphController {
    @GetMapping("/graph")
    public String getGraphList(Model model) throws IOException {
        List<GraphBean> list = DateHandle.getGraph();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Double> confirmList = new ArrayList<>();
        ArrayList<Double> suspectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GraphBean graphBean = list.get(i);
            String date = graphBean.getDate();
            double confirm = graphBean.getConfirm();
            double suspect = graphBean.getSuspect();
            dateList.add(date);
            confirmList.add(confirm);
            suspectList.add(suspect);
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("suspectList", new Gson().toJson(suspectList));
        return "graph";

    }


}
