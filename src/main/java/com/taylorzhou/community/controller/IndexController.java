package com.taylorzhou.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @description index控制类
* @param null
* @return 
**/
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
