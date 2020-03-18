package xyz.egaz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        if (!"".equals(username) && !"".equals(password)) {
            session.setAttribute("login", "1");
            return "redirect:list";
        }
        return "login";
    }


}
