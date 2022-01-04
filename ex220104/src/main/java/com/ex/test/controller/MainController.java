package com.ex.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 기본 주소가 요청 됐을 때 index를 출력하는 메서드를 정의

// My -> STS 방식
//@Controller
//public class MainController {
//    @RequestMapping(value="/", method=RequestMethod.GET)
//    public String index() {
//
//        return "/index";
//    }
//}

// T -> SpringBoot, STS 방식을 사용해도 됨
/*
   Rest Api 라고 함.
*  GetMapping
*  PostMapping
*  PutMapping
*  DeleteMapping
*/
@Controller
public class MainController {
    @GetMapping("/") // RequestMapping의 Get방식으로 해서 value="/"를 받는 다는 뜻.
    public String index() {
        System.out.println(); //sout
        System.out.println("MainController.index"); // soutm

        return "index";
    }
}