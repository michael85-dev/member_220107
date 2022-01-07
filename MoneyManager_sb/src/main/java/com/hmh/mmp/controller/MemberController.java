package com.hmh.mmp.controller;

import com.hmh.mmp.dto.MemberSaveDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/*")
public class MemberController {
    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("member", new MemberSaveDTO()); // new MemberSaveDTO() 는 생성자임.

        return "member/save";
    }
}
