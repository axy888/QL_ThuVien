package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Repositories.SachRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class HomeController {

    public HomeController() {
    }

    @RequestMapping(
            value = {"/"},
            method = {RequestMethod.GET}
    )
    public String toHome() {
        return "index";
    }
    @GetMapping("/rules")
    public String showRules() {
        return "rules";  // Tên của file Thymeleaf (rules.html)
    }
}
