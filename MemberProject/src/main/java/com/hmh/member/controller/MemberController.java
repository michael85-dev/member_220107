package com.hmh.member.controller;

import com.hmh.member.dto.MemberSaveDTO;
import com.hmh.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberService ms;

    @GetMapping("/member/save")
    public String saveForm() {

        return "/member/save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberSaveDTO msDTO) {
        ms.save(msDTO);

        System.out.println("MemberController.save");
        System.out.println("msDTO = " + msDTO);
        
        return "/index/";
    }

    @GetMapping("/member/login")
    public String login() {

        return "/member/";
    }
}
