package com.hmh.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        System.out.println("첫 화면 페이지 출력");

        return "/index";
    }

}
