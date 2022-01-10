package com.hmh.mmp.controller;

import com.hmh.mmp.dto.MemberLoginDTO;
import com.hmh.mmp.dto.MemberSaveDTO;
import com.hmh.mmp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final이 붙어있는ㄱ ㅓㅅ에 대한 생성자를 만들어줌?
public class MemberController {
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("msave", new MemberSaveDTO()); // new MemberSaveDTO() 는 생성자임.

        return "member/save";
    }

    @PostMapping("save")
    public String save(@PathVariable @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult br) {
        if (br.hasErrors()) {
            return "member/save";
        }

        // 이메일 중복 처리
        try {
            ms.save(memberSaveDTO);
        } catch (IllegalStateException e) {
            br.reject("eCheck", e.getMessage());
        }

        return "index";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("mlogin", new MemberSaveDTO());

        return "member/login";
    }

    @PostMapping("login")
    public String login(@PathVariable @ModelAttribute("member") MemberLoginDTO memberLoginDTO, BindingResult br, HttpSession session) {
        boolean checkResult = ms.login(memberLoginDTO); // MemberLoginDTO에 다가 Entity데이터를 담아서 비교?

        if (br.hasErrors()) {
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            // 해당 loginEmail의 값을 SessionConst라는 폴더를 만들어서 적용도 가능함.

            return "member/login";
        }

        return "main";
    }
}
