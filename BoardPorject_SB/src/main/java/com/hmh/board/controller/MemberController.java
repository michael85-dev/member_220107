package com.hmh.board.controller;

import com.hmh.board.dto.MemberDetailDTO;
import com.hmh.board.dto.MemberLoginDTO;
import com.hmh.board.dto.MemberSaveDTO;
import com.hmh.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("save", new MemberSaveDTO());

        return "member/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) {
        Long memberId = ms.save(memberSaveDTO);

        return "index";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginDTO());

        return "member/login";
    }

    @PostMapping("login")
    public String login(HttpSession session, @PathVariable @ModelAttribute("member") MemberLoginDTO memberLoginDTO, Model model) {

        boolean checkResult = ms.login(memberLoginDTO);

        if (checkResult) {
            session.setAttribute("login", memberLoginDTO.getMemberEmail());
            session.setAttribute("id", memberLoginDTO.getMemberId());
        }

        return "main";
    }
}
