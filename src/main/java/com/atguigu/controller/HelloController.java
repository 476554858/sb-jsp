package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("/abc")
    public String hello(Model model, HttpServletRequest request) {
        model.addAttribute("msg", "你好");
        return "success";
    }
}
