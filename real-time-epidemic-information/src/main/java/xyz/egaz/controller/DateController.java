package xyz.egaz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.egaz.bean.DataBean;
import xyz.egaz.service.serviceimpl.DataServiceImpl;
import java.util.ArrayList;

@Controller
public class DateController {

    @Autowired
    private DataServiceImpl service;

    @GetMapping("/list")
    public String test1(Model model) {
        System.out.println("进controller了。。。。");
        ArrayList<DataBean> dateList = (ArrayList<DataBean>) service.list();
        model.addAttribute("list", dateList);
        return "list";
    }
}
