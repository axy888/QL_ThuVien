package com.example.ql_thuvien.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String toAdmin(){return "admin";}
}
